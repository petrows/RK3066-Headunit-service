package com.petrows.mtcservice.com.petrows.mtcservice.appcontrol;

import java.util.ArrayList;

public abstract class ControllerBase {

	// Base methods
	static ArrayList<ControllerBase> getControllers()
	{
		ArrayList<ControllerBase> out = new ArrayList<ControllerBase>();
		
		out.add(new ControllerMediaButtons());

		return out;
	}

	// Class identification
	public abstract String getId();
	public abstract String getName();

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
