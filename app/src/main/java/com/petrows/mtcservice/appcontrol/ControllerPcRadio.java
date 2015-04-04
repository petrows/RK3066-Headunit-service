package com.petrows.mtcservice.appcontrol;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.petrows.mtcservice.ExecuteAsRootBase;
import com.petrows.mtcservice.R;

import java.util.ArrayList;


public class ControllerPcRadio extends ControllerBase {
	final static String appPackage = "com.maxxt.pcradio";
	final static String appService = ".service.RadioService";

	private ExecuteAsRootBase cmdNext = new ExecuteAsRootBase() {
		@Override
		protected ArrayList<String> getCommandsToExecute() {
			ArrayList<String> cmd = new ArrayList<String>();
			cmd.add("am startservice -n com.maxxt.pcradio/.service.RadioService -a com.maxxt.radio.ACTION_PLAY_NEXT_STREAM");
			return cmd;
		}
	};
	private ExecuteAsRootBase cmdPrev = new ExecuteAsRootBase() {
		@Override
		protected ArrayList<String> getCommandsToExecute() {
			ArrayList<String> cmd = new ArrayList<String>();
			cmd.add("am startservice -n com.maxxt.pcradio/.service.RadioService -a com.maxxt.radio.ACTION_PLAY_PREV_STREAM");
			return cmd;
		}
	};
	private ExecuteAsRootBase cmdStop = new ExecuteAsRootBase() {
		@Override
		protected ArrayList<String> getCommandsToExecute() {
			ArrayList<String> cmd = new ArrayList<String>();
			cmd.add("am startservice -n com.maxxt.pcradio/.service.RadioService -a com.maxxt.radio.ACTION_STOP_PLAYBACK");
			return cmd;
		}
	};

	public boolean isRunning() {
		ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo pid : am.getRunningServices(100)) {
			if (pid.service.getClassName().equals(appPackage + appService))
			{
				// Running!
				return true;
			}
		}

		return false;
	}

	@Override
	public String getId() {
		return "pcradio";
	}

	@Override
	public String getName() {
		return ctx.getString(R.string.appcontrol_name_pcradio);
	}

	@Override
	public Drawable getIcon() {
		return getPackageIcon(appPackage);
	}
}
