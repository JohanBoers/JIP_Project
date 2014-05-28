package nl.jip.joinit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends Activity implements OnClickListener {
	Button fetch;
	TextView text;
	EditText et, et2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		fetch = (Button) findViewById(R.id.fetch);
		text = (TextView) findViewById(R.id.text);
		et = (EditText) findViewById(R.id.et_email_login);
		et2 = (EditText) findViewById(R.id.et_wachtwoord_login);
		text.setMovementMethod(new ScrollingMovementMethod());
		
		fetch.setOnClickListener(this);
	}
	
	public static final String md5(final String toEncrypt) {
	    try {
	        final MessageDigest digest = MessageDigest.getInstance("md5");
	        digest.update(toEncrypt.getBytes());
	        final byte[] bytes = digest.digest();
	        final StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < bytes.length; i++) {
	            sb.append(String.format("%02X", bytes[i]));
	        }
	        return sb.toString().toLowerCase();
	    } catch (Exception exc) {
	        return ""; 
	    }
	}
	
	class task extends AsyncTask<String, String, Void>
	{
		private ProgressDialog progressDialog = new ProgressDialog(Login.this);
		String result = "";
		protected void onPreExecute() 
		{
			progressDialog.setMessage("Loggin in.....");
			progressDialog.show();
			progressDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface arg0) {
					// TODO Auto-generated method stub
					task.this.cancel(true);
				}
			});
		}
		
		protected Void doInBackground(String... params)
		{
			String url = "http://10.0.1.21:8888/app/app.php";
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
				
				ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
			
			InputStream is = null;
			try
			{
				httpPost.setEntity(new UrlEncodedFormEntity(param));
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				
				is = httpEntity.getContent();
			} catch(Exception e) {
				Log.e("log_tag","Error in http connection"+e.toString());
				Toast.makeText(Login.this, "Please try again", Toast.LENGTH_LONG).show();
			}
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while((line=br.readLine())!=null)
				{
					sb.append(line+"\n");
				}
				is.close();
				result = sb.toString();
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result"+e.toString());
			}
			return null;
		}
		
		protected void onPostExecute (Void v)
		{
			try
			{
				JSONArray Jarray = new JSONArray(result);
				String db_detail = "";
				for(int i=0; i<Jarray.length(); i++)
				{
					JSONObject Jasonobject = Jarray.getJSONObject(i);
					String name = Jasonobject.getString("Email");
					
					if(md5(et.getText().toString()).equalsIgnoreCase(name)) {
						String wachtwoord = Jasonobject.getString("Wachtwoord");
						String wachtwoord3 = et2.getText().toString();
						String wachtwoord2 = md5(wachtwoord3);
						if(wachtwoord2.equalsIgnoreCase(wachtwoord)){
							progressDialog.setMessage("Preparing data.......");
							Intent intent = new Intent(Login.this, AddActivity.class);
							startActivity(intent);
							progressDialog.dismiss();
							finish();
						} if(!wachtwoord2.equalsIgnoreCase(wachtwoord)) {
							db_detail = "Uw wachtwoord komt niet overeen! \n";
						}
					} if (!md5(et.getText().toString()).equalsIgnoreCase(name)){
						db_detail = "Geen e-mail adres gevonden, maak nu een account aan en 'share the fun'!";
					}
						
				}
				text.setText(db_detail);
				this.progressDialog.dismiss();
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data "+e.toString());
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
	//	Toast.makeText(v.getContext(), "rodekool", Toast.LENGTH_SHORT).show();
		switch(v.getId())
		{
		case R.id.fetch:
			new task().execute();
		break;
		default:
		throw new RuntimeException("Unknow button ID");
		}
	}
}
