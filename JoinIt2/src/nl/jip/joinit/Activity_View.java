package nl.jip.joinit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_View extends Activity {
	TextView idview;
	LinearLayout nieuws;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity__view);
		
		nieuws = (LinearLayout) findViewById(R.id.ll_nieuwsoverzicht);
		
		
		new task().execute();
		}
	
	class task extends AsyncTask<String, String, Void>
	{
		private ProgressDialog progressDialog = new ProgressDialog(Activity_View.this);
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
			String url = "http://145.24.243.123:8888/app/activity.php";
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			Bundle extras = getIntent().getExtras();
			String newString = extras.getString("id");
			
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("Id", newString));
			InputStream is = null;
			try
			{
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				HttpEntity httpEntity = httpResponse.getEntity();
				
				is = httpEntity.getContent();
			} catch(Exception e) {
				Log.e("log_tag","Error in http connection"+e.toString());
				Toast.makeText(Activity_View.this, "Please try again", Toast.LENGTH_LONG).show();
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
					LinearLayout ll = new LinearLayout(Activity_View.this);
		            ll.setOrientation(LinearLayout.VERTICAL);
		            ll.setId(Jasonobject.getInt("Id"));
					TextView db_detail = new TextView(Activity_View.this);
					TextView db_detail1 = new TextView(Activity_View.this);
					TextView db_detail2 = new TextView(Activity_View.this);
					TextView db_detail3 = new TextView(Activity_View.this);
					TextView db_detail4 = new TextView(Activity_View.this);
					TextView db_detail5 = new TextView(Activity_View.this);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					db_detail.setTextSize(25);
					db_detail.setWidth(lp.MATCH_PARENT);
					db_detail.setGravity(Gravity.CENTER);
					db_detail.setTypeface(null, Typeface.BOLD);
					db_detail.setText(Jasonobject.getString("Titel") + "\n");
					ll.addView(db_detail);
					db_detail1.setText("Omschrijving: \n"+Jasonobject.getString("Omschrijving")+ "\n\n");
					db_detail1.setWidth(lp.MATCH_PARENT);
					db_detail1.setGravity(Gravity.CENTER);
					ll.addView(db_detail1);
					db_detail2.setText("Postcode: \n"+Jasonobject.getString("Postcode")+ "\n\n");
					db_detail2.setWidth(lp.MATCH_PARENT);
					db_detail2.setGravity(Gravity.CENTER);
					ll.addView(db_detail2);
					db_detail3.setText("Huisnummer: \n"+Jasonobject.getString("Huisnummer")+ "\n\n");
					db_detail3.setWidth(lp.MATCH_PARENT);
					db_detail3.setGravity(Gravity.CENTER);
					ll.addView(db_detail3);
					db_detail4.setText("Plaats: \n"+Jasonobject.getString("Plaats")+ "\n\n");
					db_detail4.setWidth(lp.MATCH_PARENT);
					db_detail4.setGravity(Gravity.CENTER);
					ll.addView(db_detail4);
					db_detail5.setText("Datum: \n"+Jasonobject.getString("Datum")+ "\n\n");
					db_detail5.setWidth(lp.MATCH_PARENT);
					db_detail5.setGravity(Gravity.CENTER);
					ll.addView(db_detail5);
					nieuws.addView(ll);
				}
				this.progressDialog.dismiss();
			} catch (Exception e) {
				Log.e("log_tag", "Error parsing data "+e.toString());
			}
		}
	}
}
