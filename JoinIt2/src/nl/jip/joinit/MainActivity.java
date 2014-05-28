package nl.jip.joinit;

import nl.jip.joinit.Login.task;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	Button knop, knop2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		knop = (Button) findViewById(R.id.Button3);
		knop2 = (Button) findViewById(R.id.Button2);
		
		knop.setOnClickListener(this);
		knop2.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		Intent intent = null;
	//	Toast.makeText(v.getContext(), "rodekool", Toast.LENGTH_SHORT).show();
		switch(v.getId())
		{
		case R.id.Button3:
			intent = new Intent(this, Login.class);
			startActivity(intent);
		break;
		case R.id.Button2:
			intent = new Intent(this, Register.class);
			startActivity(intent);
		break;
		default:
		throw new RuntimeException("Unknow button ID");
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
