package nl.jip.joinit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener
{
	Button knop;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		knop = (Button) findViewById(R.id.btn_register);
		knop.setOnClickListener(this);
	}
	
	public void onClick(View v)
	{
		Intent intent;
	//	Toast.makeText(v.getContext(), "rodekool", Toast.LENGTH_SHORT).show();
		switch(v.getId())
		{
		case R.id.btn_register:
			intent = new Intent(this, Register.class);
			intent.putExtra("naam", "Boudewijn");
		break;
		default:
		throw new RuntimeException("Unknow button ID");
		}
		
		


	    startActivity(intent);
		
	}
}