package nl.jip.joinit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
		throw new RuntimeException("Unknown button ID");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		menu.add("Info")
			.setIcon(R.drawable.ic_menu_info)
			.setIntent(new Intent(this, Help.class));
		menu.add("Opties")
			.setIcon(R.drawable.ic_menu_opties)
			.setIntent(new Intent(this, Options.class));
		return true;
	}
}
