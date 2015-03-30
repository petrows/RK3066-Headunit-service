package com.petrows.mtcservice.appcontrol;

import android.content.Context;
import android.util.Log;

import com.petrows.mtcservice.R;

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
		return ctx.getString(R.string.appcontrol_name_media_buttons);
	}

	@Override
	public int getEventPriority() {
		return 80; // Low priority
	}
}
