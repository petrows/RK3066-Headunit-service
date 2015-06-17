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

		// Start main services
		swc = hu.getSwc();

		return true;
	}

	private boolean initUnit() {
		ArrayList<IHeadUnit> huList = getAllUnits();

		hu = null;

		for (IHeadUnit huTest : huList) {
			if (huTest.detectUnit()) {
				// Detected unit!
				hu = huTest;
				return hu.init(ctx);
			}
		}
		return false;
	}

	public ArrayList<IHeadUnit> getAllUnits() {
		ArrayList<IHeadUnit> huList = new ArrayList<>();
		huList.add(new com.petrows.mtcservice.hal.mtc.HeadUnit());
		huList.add(new com.petrows.mtcservice.hal.c200.HeadUnit());
		return huList;
	}

	public ISwc getSwc() { return swc; }
}
