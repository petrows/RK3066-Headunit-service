package com.petrows.mtcservice;

import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by petro on 13.04.2015.
 */
public class HeadUnit {
	private final static String TAG = "HeadUnit";
	private static HeadUnit instance = null;

	private float volumeMax = 30f;
	private int buildTimestamp = 0;
	private AudioManager am;

	// MTC intents
	final static String MTCBroadcastIrkeyUp = "com.microntek.irkeyUp";
	final static String MTCBroadcastIrkeyDown = "com.microntek.irkeyDown";

	private Context ctx = null;
	private HeadUnit(Context context) {
		ctx = context;

		buildTimestamp = Integer.parseInt(getPropValue("ro.build.date.utc"));
		Log.d(TAG, "Build timestamp: " + String.valueOf(buildTimestamp));

		am = ((AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE));
	}

	public static HeadUnit get(Context context)
	{
		if (null == instance)
			instance = new HeadUnit(context);
		return (instance);
	}

	final static List<String> MTCMusicApps = Arrays.asList(
			"com.microntek.radio.RadioActivity", "com.microntek.music.MusicActivity", "com.microntek.dvd.DVDActivity", "com.microntek.ipod.IPODActivity", "com.microntek.media.MediaActivity", "com.microntek.bluetooth.BlueToothActivity"
	);

	public static String getPropValue(String value) {
		Process p;
		String ret = "";
		try {
			p = new ProcessBuilder("/system/bin/getprop", value).redirectErrorStream(true).start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				ret = line;
			}
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public boolean isMTCAppRunning()
	{
		boolean flag = false;
		ActivityManager mng = (ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningTaskInfo> activities = mng.getRunningTasks(0x7fffffff);
		for (ActivityManager.RunningTaskInfo task : activities)
		{
			if (MTCMusicApps.contains(task.topActivity.getClassName()))
			{
				Log.d(TAG, "Activity running: " + task.topActivity.getClassName());
				return true;
			}
		}

		return false;
	}

	public int getCallerVersionAuto() {
		// We should test ro.build.date.utc to be > 15 dec 2014
		// 1418642842 (2014-12-15) uses OLD API
		// 1419924496 (2014-12-30) uses NEW API
		// 1421921736 (2015-01-22) uses NEW API
		if (buildTimestamp <= 1418642842) // <= 15 dec 2015
			return 1;
		else
			return 2;
	}

	// Volume

	private int mtcGetRealVolume(int paramInt) {
		float f1 = 100.0F * paramInt / volumeMax;
		float f2;
		if (f1 < 20.0F) {
			f2 = f1 * 3.0F / 2.0F;
		} else if (f1 < 50.0F) {
			f2 = f1 + 10.0F;
		} else {
			f2 = 20.0F + f1 * 4.0F / 5.0F;
		}
		return (int) f2;
	}

	public void setVolume(int level) {
		if (level < 0 || level > (int) volumeMax) {
			Log.w(TAG, "Volume level " + level + " is wrong, ignore it");
			return;
		}

		Log.d(TAG, "Settings new volume system: " + level + ", real: "
				+ mtcGetRealVolume(level));
		android.provider.Settings.System.putInt(ctx.getContentResolver(),
				"av_volume=", level);
		am.setParameters("av_volume=" + mtcGetRealVolume(level));
	}

	public int getVolume() {
		return android.provider.Settings.System.getInt(
				ctx.getContentResolver(), "av_volume=", 15);
	}
}
