package com.petrows.mtcservice;

import java.util.Arrays;
import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ServiceMain extends Service implements LocationListener  {

	final static String TAG = "ServiceMain";
	
	public static boolean isRunning = false;
	public static ServiceMain inst;
	public double last_speed = 0;
	
	public ServiceMain() {
		inst = this;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		isRunning = true;
		super.onCreate();
		Log.d(TAG, "MTCService onCreate");
	}

	@Override
	public void onDestroy() {
		isRunning = false;
		inst = null;
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		Log.d(TAG, "MTCService onStartCommand");
		
		Intent notificationIntent = new Intent(this, ActivityMain.class);  
	    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,   
	            PendingIntent.FLAG_UPDATE_CURRENT);

		Notification note = new NotificationCompat.Builder(this)
				.setContentTitle(getString(R.string.app_service_title) + " " + Settings.get(this).getVersion())
				.setContentText(getString(R.string.app_service_descr))
				.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.ic_launcher).build();
		startForeground(startId, note);
		
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		
		if (null != locationManager)
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,	0, this);
		}
		
		return START_STICKY;
	}

	@Override
	public void onLocationChanged(Location location) {
		if (!Settings.get(this).getSpeedEnable()) return; // Speed control is disabled
		if (!location.hasSpeed()) return; // Speed control is disabled
		List<Integer> speed_steps = Settings.get(this).getSpeedValues();
		int vol = Settings.get(this).getVolume();
		int volNew = vol;
		int volChange = Settings.get(this).getSpeedChangeValue();
		double speed = location.getSpeed();
		speed = speed * 3.6; // m/s => km/h
		if (speed == last_speed) return;
		Log.d(TAG, "Speed is: " + speed + ", spteps is: " + speed_steps.toString());		
		Log.d(TAG, "Last Speed is: " + last_speed);		
		if (speed > last_speed) {
			// Speed is bigger!
			for (Integer spd_step : speed_steps) {				
				if ((last_speed < spd_step) && (speed > spd_step))
				{
					Log.d(TAG, "Set (+) voume: " + volNew + "+" + volChange + " / " + last_speed + " / " + speed + "("+spd_step+")");
					volNew = volNew + volChange;					
				}
			}
		} else {
			// Speed is lower!
			for (Integer spd_step : speed_steps) {
				if ((last_speed > spd_step) && (speed < spd_step))
				{
					// Speed is changed! (lower)
					Log.d(TAG, "Set (-) voume: " + volNew + "-" + volChange + " / " + last_speed + " / " + speed + "("+spd_step+")");
					volNew = volNew - volChange;
				}
			}
		}
		
		if (volNew != vol)
		{
			// Change it!
			Settings.get(this).setVolume(volNew);
			Settings.get(this).showToast("Volume " + (volNew>vol?"+":"-") + " ("+volNew+")");
		}
		
		last_speed = speed;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onProviderDisabled(String provider) {}
	
	
}
