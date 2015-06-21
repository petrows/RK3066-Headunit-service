package com.petrows.mtcservice.hal;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Hal {
	private static final String TAG = "Hal";
	private static Hal instance = null;
	private Context ctx = null;
	private IHeadUnit hu = null;
	private ISwc swc = null;

	private Hal(Context context) {
		ctx = context;
	}

	public static Hal get(Context ctx) {
		if (null == instance) instance = new Hal(ctx);
		return instance;
	}

	public boolean init() {
		// Now detect - what HAL do we use
		if (!initUnit()) {
			// Unit unsupported!
			Log.e(TAG, "Unit unsupported!");
			return false;
		}

		Log.d(TAG, "Init done, type is " + hu.getName());

		// Create main services
		swc = hu.getSwc();

		// Init main services
		swc.init();

		return true;
	}

	private boolean initUnit() {
		ArrayList<IHeadUnit> huList = getAllUnits();

		hu = null;

		for (IHeadUnit huTest : huList) {
			if (huTest.detectUnit()) {
				// Detected unit!
				hu = huTest;
				return hu.init();
			}
		}
		return false;
	}

	public ArrayList<IHeadUnit> getAllUnits() {
		ArrayList<IHeadUnit> huList = new ArrayList<>();
		huList.add(new com.petrows.mtcservice.hal.mtc.HeadUnit(ctx));
		huList.add(new com.petrows.mtcservice.hal.c200.HeadUnit(ctx));
		return huList;
	}

	public IHeadUnit getHeadUnit() { return hu; }
	public ISwc getSwc() { return swc; }
}
