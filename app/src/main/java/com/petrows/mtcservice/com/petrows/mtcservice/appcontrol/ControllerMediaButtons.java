package com.petrows.mtcservice.com.petrows.mtcservice.appcontrol;

import android.util.Log;

public class ControllerMediaButtons extends ControllerBase {
	private final static String TAG = "ControllerMediaButtons";
	public ControllerMediaButtons() {
		Log.d(TAG, "Creating");
	}

	@Override
	public String getId() {
		return "media";
	}

	@Override
	public String getName() {
		return "Media buttons";
	}

	@Override
	public int getCallPriority() {
		return 80; // Low priority
	}

	@Override
	public boolean init() {
		return true;
	}
}
