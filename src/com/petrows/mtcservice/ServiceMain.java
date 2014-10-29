package com.petrows.mtcservice;

import java.util.Arrays;
import java.util.List;

import android.app.Notification;
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

	final static String TAG = "MTCService.ServiceMain";
	
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

		Notification note = new NotificationCompat.Builder(this)
				.setContentTitle(getString(R.string.app_service_title))
				.setContentText(getString(R.string.app_service_descr))
				.setSmallIcon(R.drawable.ic_launcher).build();
		startForeground(1337, note);
		
		LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);
		
		return START_STICKY;
	}

	@Override
	public void onLocationChanged(Location location) {
		
		List<Integer> speed_steps = Arrays.asList(20, 60, 100, 120);
		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		int volChange = Settings.get(this).getSpeedChangeValue();
		double speed = location.getSpeed();
		speed = speed * 3.6; // m/s => km/h
		if (speed == last_speed) return;
		Log.d(TAG, "Speed is: " + speed);		
		Log.d(TAG, "Last Speed is: " + last_speed);		
		if (speed > last_speed) {
			// Speed is bigger!
			for (Integer spd_step : speed_steps) {				
				if ((last_speed < spd_step) && (speed > spd_step))
				{
					Log.d(TAG, last_speed + " < " + spd_step + " && " + speed + " > " + spd_step);
					// Speed is changed! (higher)
					am.setStreamVolume(
							AudioManager.STREAM_MUSIC,
							vol+volChange,
						    0);
					Log.d(TAG, "Set (+) voume: " + vol + "+" + volChange + " / " + last_speed + " / " + speed + "("+spd_step+")");
				}
			}
		} else {
			// Speed is lower!
			for (Integer spd_step : speed_steps) {
				if ((last_speed > spd_step) && (speed < spd_step))
				{
					// Speed is changed! (lower)
					am.setStreamVolume(
							AudioManager.STREAM_MUSIC,
							vol-volChange,
						    0);
					Log.d(TAG, "Set (-) voume: " + vol + "-" + volChange + " / " + last_speed + " / " + speed + "("+spd_step+")");
				}
			}
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
