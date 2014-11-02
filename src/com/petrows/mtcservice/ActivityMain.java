package com.petrows.mtcservice;

import java.io.FileDescriptor;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;
import android.widget.TextView;

public class ActivityMain extends Activity implements OnClickListener {
	final static String TAG = "ActivityMain";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView txtVersion = (TextView)findViewById(R.id.txtVersion);		
		txtVersion.setText("Version: " + Settings.get(this).getVersion());
		
		Switch chService = (Switch)findViewById(R.id.swServiceEnable);
		chService.setChecked(Settings.get(this).getServiceEnable());

		if (Settings.get(this).getServiceEnable() && !ServiceMain.isRunning)
		{
			Log.d(TAG, "Starting service!");
			startService(new Intent(this, ServiceMain.class));
		}
	}
		 
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.swServiceEnable:
			Log.d(TAG, "Service enable: " + (((Switch)findViewById(R.id.swServiceEnable)).isChecked()));
			Settings.get(this).setServiceEnable(((Switch)findViewById(R.id.swServiceEnable)).isChecked());
			setService(((Switch)findViewById(R.id.swServiceEnable)).isChecked());
			break;
		case R.id.btnSettings:
			// Run advanced settings
			Intent intent = new Intent(this, ActivitySettings.class);
			startActivity(intent);
			break;
		}
		
	}
	
	public void setService(boolean state)
	{
		stopService(new Intent(this, ServiceMain.class)); // Stop it		
		// If enable - start it
		if (state)
		{
			Log.d(TAG, "Starting service!");
			startService(new Intent(this, ServiceMain.class));
		}
	}
}
