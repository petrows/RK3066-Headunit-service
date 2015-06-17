package com.petrows.mtcservice.hal;

import android.content.Context;
import android.util.Log;

public abstract class IHeadUnit {
	protected static final String TAG = "IHeadUnit";
	protected Context ctx;
	public enum HalType {
		HAL_MTC,    // Micronteck units family
		HAL_C200,   // C200 units family
	}

	abstract public boolean detectUnit();
	abstract public HalType getType();
	abstract public String getName();

	public boolean init(Context context) {
		Log.d(TAG, "Init unit!");
		ctx = context;
		return true;
	}

	public ISwc getSwc() { return null; }
}
