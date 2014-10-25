package com.petrows.mtcservice;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

public class Settings {	
	final static String TAG = "MTCService.Settings";
	
	final static String MTCBroadcastIrkeyUp    = "com.microntek.irkeyUp";
	final static String MTCBroadcastIrkeyDown  = "com.microntek.irkeyDown";

	final static List<Integer> MTCKeysPrev  = Arrays.asList(58, 22);
	final static List<Integer> MTCKeysNext  = Arrays.asList(59, 24);
	final static List<Integer> MTCKeysPause = Arrays.asList(3);
	
	final static String MTCBroadcastWidget = "com.android.MTClauncher.action.INSTALL_WIDGETS";
	final static int    MTCWidgetAdd    = 10520;
	final static int    MTCWidgetRemove = 10521;
	
	public boolean autorun = true;
	public boolean toast = true;
	
	public static Settings instance = null;
	public SharedPreferences prefs;
	
	public Settings(Context ctx) 
	{
		prefs = ctx.getSharedPreferences("MTCService", ctx.MODE_PRIVATE);
	}
	
	public static Settings get(Context ctx)
	{
		if (null == instance) instance = new Settings(ctx);
		return instance;
	}
	
	boolean getServiceEnable()
	{
		return true;
	}
	
	boolean getServiceToast()
	{
		return true;
	}
}
