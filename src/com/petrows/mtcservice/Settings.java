package com.petrows.mtcservice;

import java.util.ArrayList;

import android.media.AudioManager;
import android.provider.Settings.System;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Settings {	
	final static String TAG = "Settings";
	
	final static String MTCBroadcastIrkeyUp    = "com.microntek.irkeyUp";
	final static String MTCBroadcastIrkeyDown  = "com.microntek.irkeyDown";
	final static String MTCBroadcastACC  = "com.microntek.acc";

	final static List<Integer> MTCKeysPrev  = Arrays.asList(45, 58, 22);
	final static List<Integer> MTCKeysNext  = Arrays.asList(46, 59, 24);
	final static List<Integer> MTCKeysPause = Arrays.asList(3);
	
	final static String MTCBroadcastWidget = "com.android.MTClauncher.action.INSTALL_WIDGETS";
	final static int    MTCWidgetAdd    = 10520;
	final static int    MTCWidgetRemove = 10521;

	private ArrayList<Integer> speedValues = new ArrayList<Integer>();
	private String speedValuesDef = "";
	private int speedValueChange = 1;
	
	private AudioManager am = null;
	
	private static Settings instance = null;
	private SharedPreferences prefs;
	
	private Context ctx;
		
	public Settings(Context context) 
	{
		ctx = context;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		speedValuesDef = ctx.getString(R.string.cfg_def_speed_values);
		
		am = ((AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE));
		
		Log.d(TAG, "Settings created");
	}
	
	public static Settings get(Context ctx)
	{
		if (null == instance) instance = new Settings(ctx);
		return instance;
	}
	
	public static void destroy()
	{	
		 instance = null;
	}
	
	private void setCfgBool(String name, boolean val)
	{
		Editor editor = prefs.edit();
		editor.putBoolean(name, val);
		editor.commit();
	}
	
	private void setCfgString(String name, String val)
	{
		Editor editor = prefs.edit();
		editor.putString(name, val);
		editor.commit();
	}
	
	public String getVersion()
	{
		String version = "?";
		try {
			PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
			version = pInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
			
	public boolean getServiceEnable() { return prefs.getBoolean("service.enable", true); }	
	public void setServiceEnable(boolean enable) { setCfgBool("service.enable", enable); }
		
	public boolean getServiceToast() { return prefs.getBoolean("service.toast", true); }
	public void setServiceToast(boolean enable) { setCfgBool("service.toast", enable); }
	public void showToast(String text) { showToast(text, Toast.LENGTH_SHORT); }
	public void showToast(String text, int length)
	{
		Log.d(TAG, "Toast: " + text);
		if (getServiceToast()) Toast.makeText(ctx, text, length).show();
	}
	
	public boolean getCallerEnable() { return prefs.getBoolean("caller.enable", true); }	
	
	public boolean getMediaKeysEnable() { return prefs.getBoolean("keys.enable", true); }	
	
	public boolean getSafeVolumeEnable() { return prefs.getBoolean("svol.enable", false); }
	public int getSafeVolumeLevel() { return Integer.valueOf(prefs.getString("svol.level", "5")); }
	
	public boolean getSpeedEnable() { return prefs.getBoolean("speed.enable", true); }
	public int getSpeedChangeValue() { return Integer.valueOf(prefs.getString("speed.speedvol", "5")); }
	public List<Integer> getSpeedValues() {
		// Load speed values		
		if (speedValues == null || speedValues.size() <= 0)
		{
			// Try to calc it
			String spd_cfg = prefs.getString("speed.speedrange", speedValuesDef);
			// Calc it
			List<String> speed_vals_str = Arrays.asList(spd_cfg.split("\\s*,\\s*"));
			StringBuilder speed_vals_clr = new StringBuilder();
			for (String spd_step : speed_vals_str)
			{
				Integer s = -1;
				try {
					s = Integer.valueOf(spd_step);
				} catch (Exception e) {
					s = -1;
				}
				if (s > 0 && s < 500)
				{
					if (speedValues.size() > 0) speed_vals_clr.append(", ");
					speed_vals_clr.append(s.toString());
					speedValues.add(s);
				}
			}
			
			setCfgString("speed.speedrange", speed_vals_clr.toString());
		}
		return speedValues;
	}
	
	public boolean getMute()
	{
		return am.getParameters("av_mute=").equals("true");
	}
	
	// This function is reversed from package android.microntek.service.MicrontekServer
	private int mtcGetRealVolume(int paramInt)
	  {
	    int i = paramInt * 100 / 30;
	    if (i < 20) {
	      return i + i / 2;
	    }
	    if (i < 50) {
	      return i + 10;
	    }
	    return i + (100 - i) / 5;
	  }
	
	public void setVolume(int level)
	{
		if (level < 0 || level > 30)
		{
			Log.w(TAG, "Volume level " + level + " is wrong, ignore it"); return;
		}
		
		Log.d(TAG, "Settings new volume system: " + level + ", real: " + mtcGetRealVolume(level));
		android.provider.Settings.System.putInt(ctx.getContentResolver(), "av_volume=", level);
		am.setParameters("av_volume="+mtcGetRealVolume(level));
	}
	public void setVolumeSafe() { showToast(ctx.getString(R.string.toast_safe_volume)); setVolume(getSafeVolumeLevel()); }
	
	public int getVolume()
	{
		return android.provider.Settings.System.getInt(ctx.getContentResolver(), "av_volume=", 15);
	}
}
