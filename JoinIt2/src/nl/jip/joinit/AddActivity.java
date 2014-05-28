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

public class AddActivity extends Activity implements OnClickListener {
	Button add;
	EditText et_titel, et_omschrijving, et_postcode, et_huisnr, et_plaats, et_datum;
	private ProgressBar pb;
	TextView tv_register;
	String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		et_titel = (EditText) findViewById(R.id.et_titel);
		et_omschrijving = (EditText) findViewById(R.id.et_omschrijving);
		et_postcode = (EditText) findViewById(R.id.et_postcode);
		et_huisnr = (EditText) findViewById(R.id.et_huisnr);
		et_plaats = (EditText) findViewById(R.id.et_plaats);
		et_datum = (EditText) findViewById(R.id.et_datum);
		add = (Button) findViewById(R.id.btn_add);
		
		add.setOnClickListener(this);
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
		private ProgressDialog progressDialog = new ProgressDialog(AddActivity.this);
		protected void onPreExecute() 
		{
		progressDialog.setMessage("Creating the activity! Please be patient....");
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
		    HttpPost httppost = new HttpPost("http://10.0.1.21:8888/app/add_activity.php");

		    
		    
		    try {
		        // Add your data
		    		String Titel = et_titel.getText().toString();
		    		String Omschrijving = et_omschrijving.getText().toString();
		    		String Postcode = et_postcode.getText().toString();
		    		String Huisnummer = et_huisnr.getText().toString();
		    		String Plaats = et_plaats.getText().toString();
		    		String Datum = et_datum.getText().toString();
		    		
		    		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    		nameValuePairs.add(new BasicNameValuePair("Titel", Titel));
		    		nameValuePairs.add(new BasicNameValuePair("Omschrijving", Omschrijving));
		    		nameValuePairs.add(new BasicNameValuePair("Postcode", Postcode));
		    		nameValuePairs.add(new BasicNameValuePair("Huisnummer", Huisnummer));
		    		nameValuePairs.add(new BasicNameValuePair("Plaats", Plaats));
		    		nameValuePairs.add(new BasicNameValuePair("Datum", Datum));
		    		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    		
		    		// Execute HTTP Post Request
		    		HttpResponse response = httpclient.execute(httppost);
		    		
		    		Intent intent = new Intent(AddActivity.this, Home.class);
		    		startActivity(intent);
		    	this.progressDialog.dismiss();
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
		case R.id.btn_add:
			new MyAsyncTask().execute();
		break;
		default:
		throw new RuntimeException("Unknow button ID");
		}
		
	}
}

