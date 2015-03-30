package com.petrows.mtcservice.appcontrol;

import android.content.Context;

import java.util.ArrayList;

public abstract class ControllerBase {

	protected Context ctx;

	// Class identification
	public abstract String getId();
	public abstract String getName();
	public int getEventPriority() { return 50; }

	// Work methods:
	public boolean init(Context ctx) {
		this.ctx = ctx;
		return true;
	}

	// Play NEXT
	public boolean onNext() {
		return true;
	}

	// Play PREV
	public boolean onPrev() {
		return true;
	}

	// Play STOP
	public boolean onStop() {
		return true;
	}
}
