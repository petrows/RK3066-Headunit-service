package com.petrows.mtcservice.appcontrol;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public abstract class ControllerBase {

	protected Context ctx;

	protected Drawable getPackageIcon(String pkg)
	{
		try {
			Drawable icon = ctx.getPackageManager().getApplicationIcon(pkg);
		} catch (PackageManager.NameNotFoundException ne) {

		}
		return null;
	}

	// Class identification
	public abstract String getId();
	public abstract String getName();
	public int getEventPriority() { return 50; }

	// Work methods:
	public boolean init(Context ctx) {
		this.ctx = ctx;
		return true;
	}

	public Drawable getIcon() { return null; }

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
