package com.petrows.mtcservice;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.petrows.mtcservice.appcontrol.ControllerBase;
import com.petrows.mtcservice.appcontrol.ControllerList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Settings {
	private final static String TAG = "Settings";

	public final static String MTCBroadcastIrkeyUp = "com.microntek.irkeyUp";
	//final static String MTCBroadcastIrkeyDown = "com.microntek.irkeyDown";
	public final static String MTCBroadcastACC = "com.microntek.acc";

	public final static List<Integer> MTCKeysPrev = Arrays.asList(45, 58, 22);
	public final static List<Integer> MTCKeysNext = Arrays.asList(46, 59, 24);
	public final static List<Integer> MTCKeysPause = Arrays.asList(3);
	public final static List<Integer> MTCKeysPhone = Arrays.asList(69);



	public final static String MTCBroadcastWidget = "com.android.MTClauncher.action.INSTALL_WIDGETS";
	public final static int MTCWidgetAdd = 10520;
	//final static int MTCWidgetRemove = 10521;

	private ArrayList<Integer> speedValues = new ArrayList<Integer>();
	private String speedValuesDef = "";
	private float volumeMax = 30f;
	private int buildTimestamp = 0;

	private AudioManager am = null;

	private static Settings instance = null;
	private static SharedPreferences prefs;

	private Context ctx;

	private Settings(Context context) {
		ctx = context;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		speedValuesDef = ctx.getString(R.string.cfg_def_speed_values);

		am = ((AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE));

		// Get max volume
		try {
			String vol_max_s = am.getParameters("cfg_maxvolume=");
			volumeMax = Float.parseFloat(vol_max_s);
		} catch (Exception e) {
			volumeMax = 0f;
		}

		if (0f == volumeMax) {
			Log.e(TAG, "Cant get max vlume, set to default 30.0");
			volumeMax = 30.0f;
		}
		Log.d(TAG, "Max volume = " + String.valueOf(volumeMax));

		buildTimestamp = Integer.parseInt(HeadUnit.getPropValue("ro.build.date.utc"));
		Log.d(TAG, "Build timestamp: " + String.valueOf(buildTimestamp));

		Set<String> wasInstalledApps = prefs.getStringSet("keys.apps.installed", new HashSet<String>());
		Set<String> wasEnabledApps = prefs.getStringSet("keys.apps", new HashSet<String>());


		Set<String> defaultList = new HashSet<String>();
		Set<String> installedtList = new HashSet<String>();
		// Get all controlles list and add default-enabled to list
		ArrayList<ControllerBase> appsList = ControllerList.get(ctx).getListDisplay();
		for (ControllerBase app : appsList) {
			installedtList.add(app.getId());
			if (wasEnabledApps.contains(app.getId()))
			{
				// This app was enabled
				defaultList.add(app.getId());
				Log.d(TAG, "App " + app.getId() + " was enabled by user");
				continue;
			}
			if (app.isDefaultEnabled() && !wasInstalledApps.contains(app.getId()))
			{
				// This app is new and enabled by-default
				defaultList.add(app.getId());
				Log.d(TAG, "App " + app.getId() + " is new and was enabled by default");
				continue;
			}
		}

		Log.d(TAG, "Set default apps list: " + defaultList.toString());
		Editor editor = prefs.edit();
		editor.putStringSet("keys.apps", defaultList);
		editor.putStringSet("keys.apps.installed", installedtList);
		editor.apply();


		Log.d(TAG, "Settings created");
	}

	public static Settings get(Context context) {
		if (null == instance)
			instance = new Settings(context);
		return (instance);
	}

	public static void destroy() {
		instance = null;
	}

	public static boolean isNotifitcationServiceEnabled() {
		return (Build.VERSION.SDK_INT >= 19);
	}

	private void setCfgBool(String name, boolean val) {
		Editor editor = prefs.edit();
		editor.putBoolean(name, val);
		editor.apply();
	}

	public void setCfgString(String name, String val) {
		Editor editor = prefs.edit();
		editor.putString(name, val);
		editor.apply();
	}

	//dsa
	public void mySleep(long ms) {
		long endTime = System.currentTimeMillis() + ms;
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					wait(endTime -
							System.currentTimeMillis());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void startMyServices() {
		if (getServiceEnable()) {
			if (!ServiceMain.isRunning) {
				if (isNotifitcationServiceEnabled()) {
					if (NotificationService.isInit) mySleep(2000);
				}
				Log.d(TAG, "Starting service!");
				ctx.startService(new Intent(ctx, ServiceMain.class));
			}

			if (isNotifitcationServiceEnabled()) {
				if (!NotificationService.isInit) {
					if (ServiceMain.isRunning) mySleep(2000);
					Log.d(TAG, "Starting Notification!");
					ctx.startService(new Intent(ctx, NotificationService.class));
				}
			}
		} else {
			ctx.stopService(new Intent(ctx, ServiceMain.class));
			if (isNotifitcationServiceEnabled()) {
				ctx.stopService(new Intent(ctx, NotificationService.class));
			}
		}
	}

	public void announce(Service srv, int id) {
		Intent notificationIntent = new Intent(srv, com.petrows.mtcservice.MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(srv, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		Notification note = new NotificationCompat.Builder(srv)
				.setContentTitle(srv.getString(R.string.app_service_title) + " " + Settings.get(srv).getVersion())
				.setContentText(srv.getString(R.string.app_service_descr))
				.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.ic_launcher).build();
		srv.startForeground(id, note);
	}

	public String getVersion() {
		String version = "?";
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}





	public static boolean getServiceEnable() {
		return prefs.getBoolean("service.enable", true);
	}

	public void setServiceEnable(boolean enable) {
		setCfgBool("service.enable", enable);
	}

	public static boolean getServiceToast() {
		return prefs.getBoolean("service.toast", true);
	}

	public void showToast(String text) {
		showToast(text, Toast.LENGTH_SHORT);
	}

	public void showToast(String text, int length) {
		Log.d(TAG, "Toast: " + text);
		if (getServiceToast())
			Toast.makeText(ctx, text, length).show();
	}

	public boolean getCallerEnable() {
		return prefs.getBoolean("caller.enable", true);
	}

	public boolean getMediaKeysEnable() {
		return prefs.getBoolean("keys.enable", true);
	}

	public HashSet<String> getMediaApps() {
		return new HashSet<String>(prefs.getStringSet("keys.apps", null));
	}

	public String getMediaPlayerApp() { return prefs.getString("player.app", ""); }
	public boolean getMediaPlayerAutoplay() { return prefs.getBoolean("player.autoplay", false); }
	public boolean getMediaPlayerAutorun() { return prefs.getBoolean("player.autorun", false); }
	public boolean getMediaPlayerPhonerun() { return prefs.getBoolean("player.phonerun", false); }

	public void startMediaPlayer()
	{
		String launchapp = Settings.get(ctx).getMediaPlayerApp();
		Log.d(TAG, "Launch app: " + launchapp);
		if (launchapp != null && !launchapp.equals("")){
			try {
				Intent appintent = ctx.getPackageManager().getLaunchIntentForPackage(launchapp);
				if (null != appintent) {
					appintent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_RECEIVER_FOREGROUND);
					appintent.setPackage(launchapp);
					showToast(ctx.getString(R.string.toast_player_start));
					ctx.startActivity(appintent);
				}
			} catch (Exception e) {
				// Error :-(
			}
		}
	}

	public boolean getSafeVolumeEnable() {
		return prefs.getBoolean("svol.enable", false);
	}

	public int getSafeVolumeLevel() {
		return Integer.valueOf(prefs.getString("svol.level", "5"));
	}

	public boolean getSpeedEnable() {
		return prefs.getBoolean("speed.enable", true);
	}

	public int getSpeedChangeValue() {
		return Integer.valueOf(prefs.getString("speed.speedvol", "5"));
	}

	public List<Integer> getSpeedValues() {
		// Load speed values
		if (speedValues == null || speedValues.size() <= 0) {
			// Try to calc it
			String spd_cfg = prefs
					.getString("speed.speedrange", speedValuesDef);
			// Calc it
			List<String> speed_vals_str = Arrays.asList(spd_cfg
					.split("\\s*,\\s*"));
			StringBuilder speed_vals_clr = new StringBuilder();
			for (String spd_step : speed_vals_str) {
				Integer s;
				try {
					s = Integer.valueOf(spd_step);
				} catch (Exception e) {
					s = -1;
				}
				if (s > 0 && s < 500) {
					if (speedValues.size() > 0)
						speed_vals_clr.append(", ");
					speed_vals_clr.append(s.toString());
					speedValues.add(s);
				}
			}
			if (!prefs.getString("speed.speedrange", speedValuesDef).equals(speed_vals_clr.toString())) {
				setCfgString("speed.speedrange", speed_vals_clr.toString());
			}
		}
		return speedValues;
	}

	public boolean getMute() {
		return am.getParameters("av_mute=").equals("true");
	}
}
