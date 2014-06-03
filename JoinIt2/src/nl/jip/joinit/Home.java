package nl.jip.joinit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity implements OnClickListener {
	Button add_activity, nieuwsoverzicht;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		add_activity = (Button) findViewById(R.id.btn_add_activity_home);
		nieuwsoverzicht = (Button) findViewById(R.id.btn_nieuws);
		
		add_activity.setOnClickListener(this);
		nieuwsoverzicht.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.btn_add_activity_home:
				intent = new Intent(this, AddActivity.class);
				Bundle extras = getIntent().getExtras();
				String UserId = extras.getString("UserId");
				intent.putExtra("UserId", UserId);
				startActivity(intent);
			break;
			case R.id.btn_nieuws:
				intent = new Intent(this, Nieuwsoverzicht.class);
				Bundle extras2 = getIntent().getExtras();
				String UserId2 = extras2.getString("UserId");
				intent.putExtra("UserId", UserId2);
				startActivity(intent);
			break;
			default:
			throw new RuntimeException("Unknow button ID");
		}
	}
}
