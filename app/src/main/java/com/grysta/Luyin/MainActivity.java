package com.grysta.Luyin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.my94my.phonerecoder.R;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.activity_main);
		final Switch mySwitch = (Switch) findViewById(R.id.sw);
		final Intent intent1 = new Intent(MainActivity.this, Shake.class);
		final Intent intent2 = new Intent(MainActivity.this, Luyin.class);
		mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked){startService(intent1);startService(intent2);}
				else {stopService(intent1);stopService(intent2);}
			}
		});
	}


}
