package nl.jip.joinit;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Register extends Activity implements OnClickListener {
	Button add, exit;
	EditText et, et2, et3;
	private ProgressBar pb;
	TextView tv_register;
	String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		et = (EditText) findViewById(R.id.et_email);
		et2 = (EditText) findViewById(R.id.et_wachtwoord);
		et3 = (EditText) findViewById(R.id.et_wachtwoord2);
		add = (Button) findViewById(R.id.add);
		exit = (Button) findViewById(R.id.exit);
		tv_register = (TextView) findViewById(R.id.tv_register);
		
		add.setOnClickListener(this);
		exit.setOnClickListener(this);
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
	        return ""; // Impossibru!
	    }
	}
	

	
	private class MyAsyncTask extends AsyncTask<String, Integer, Double>{
		private ProgressDialog progressDialog = new ProgressDialog(Register.this);
		protected void onPreExecute() 
		{
		progressDialog.setMessage("Creating your account! Please be patient....");
		progressDialog.show();
		progressDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				// TODO Auto-generated method stub
				MyAsyncTask.this.cancel(true);
			}
		});
		}

		@Override
		protected Double doInBackground(String... params) {
			onPostExecute();
			return null;
		}
		
		protected void onProgressUpdate(Integer... progress){
			pb.setProgress(progress[0]);
		}
		
		public void onPostExecute() {
		    // Create a new HttpClient and Post Header
		    HttpClient httpclient = new DefaultHttpClient();
		    HttpPost httppost = new HttpPost("http://whi.wha.la/project4/items.php");

		    
		    
		    try {
		        // Add your data
		    		String Email2 = et.getText().toString();
		    		String Email = md5(Email2);
		    		String Wachtwoord2 = et2.getText().toString();
		    		String Wachtwoord = md5(Wachtwoord2);
		    		String Wachtwoord4 = et3.getText().toString();
		    		String Wachtwoord3 = md5(Wachtwoord4);
		    	if(Wachtwoord.equalsIgnoreCase(Wachtwoord3)) {
		    		
		    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    		nameValuePairs.add(new BasicNameValuePair("Email", Email));
		    		nameValuePairs.add(new BasicNameValuePair("Wachtwoord", Wachtwoord));
		    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    		
		    		// Execute HTTP Post Request
		    		HttpResponse response = httpclient.execute(httppost);
		    		
		    		result = "Uw account is aangemaakt, keer terug naar het inlogscherm om in te loggen!";
		    		Intent intent = new Intent(Register.this, Login.class);
		    		startActivity(intent);
		    	} if(!Wachtwoord.equalsIgnoreCase(Wachtwoord3)) {
		    		result = "De ingevoerde wachtwoorden komen niet overeen!";
		    		
		    	}
		    	this.progressDialog.dismiss();
		    	tv_register.setText(result);
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    	e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    	e.printStackTrace();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		} 
		
				
	}



	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.add:
			new MyAsyncTask().execute();
		break;
		case R.id.exit:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		break;
		default:
		throw new RuntimeException("Unknow button ID");
		}
		
	}
}
