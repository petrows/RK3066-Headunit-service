package com.petrows.mtcservice.appcontrol;

import java.util.ArrayList;

public abstract class ControllerBase {

	// Class identification
	public abstract String getId();
	public abstract String getName();
	public int getCallPriority() { return 50; }

	// Work methods:
	public boolean init() {
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
