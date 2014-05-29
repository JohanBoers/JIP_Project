package nl.jip.joinit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity implements OnClickListener {
	Button add_activity;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		add_activity = (Button) findViewById(R.id.btn_add_activity_home);
		
		add_activity.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.btn_add_activity_home:
				intent = new Intent(this, AddActivity.class);
				startActivity(intent);
			break;
			default:
			throw new RuntimeException("Unknow button ID");
		}
	}
}
