package com.petrows.mtcservice;

public class Settings {
	
	final static String TAG = "MTCService.Settings";
	
	public boolean autorun = true;
	public boolean toast = true;
	
	public static Settings instance = null;
	
	public Settings() {}
	
	public static Settings get()
	{
		if (null == instance) instance = new Settings();
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
