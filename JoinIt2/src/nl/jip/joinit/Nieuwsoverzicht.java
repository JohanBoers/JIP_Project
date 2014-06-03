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
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Nieuwsoverzicht extends Activity implements OnClickListener {
	ScrollView nieuwsoverzicht;
	LinearLayout nieuws;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nieuwsoverzicht);
		new task().execute();
		
		nieuwsoverzicht = (ScrollView) findViewById(R.id.sv_nieuwsoverzicht);
		nieuws = (LinearLayout) findViewById(R.id.ll_nieuwsoverzicht);
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
		private ProgressDialog progressDialog = new ProgressDialog(Nieuwsoverzicht.this);
		String result = "";
		protected void onPreExecute() 
		{
			progressDialog.setMessage("Collecting data.....");
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
			String url = "http://10.0.1.21:8888/app/activitys.php";
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			Bundle extras = getIntent().getExtras();
			String newString = extras.getString("UserId");
				
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("UserId", newString));
			InputStream is = null;
			try
			{
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				
				is = httpEntity.getContent();
			} catch(Exception e) {
				Log.e("log_tag","Error in http connection"+e.toString());
				Toast.makeText(Nieuwsoverzicht.this, "Please try again", Toast.LENGTH_LONG).show();
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
				for(int i=0; i<Jarray.length(); i++)
				{
					JSONObject Jasonobject = Jarray.getJSONObject(i);
					LinearLayout ll = new LinearLayout(Nieuwsoverzicht.this);
		            ll.setOrientation(LinearLayout.VERTICAL);
		            ll.setId(Jasonobject.getInt("Id"));
		            ll.setOnClickListener(Nieuwsoverzicht.this);
					TextView db_detail = new TextView(Nieuwsoverzicht.this);
					TextView db_detail1 = new TextView(Nieuwsoverzicht.this);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					db_detail.setTextSize(25);
					db_detail.setTextColor(Color.BLACK);
					db_detail.setTypeface(null, Typeface.BOLD);
					db_detail.setText(Jasonobject.getString("Titel"));
					ll.addView(db_detail);
					db_detail1.setText(Jasonobject.getString("Omschrijving")+ "\n\n");
					db_detail1.setTextColor(Color.BLACK);
					ll.addView(db_detail1);
					nieuws.addView(ll);
				}
				this.progressDialog.dismiss();
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data "+e.toString());
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = new Intent(this, Activity_View.class);
		intent.putExtra("id", ""+id);
		startActivity(intent);
	}
}
