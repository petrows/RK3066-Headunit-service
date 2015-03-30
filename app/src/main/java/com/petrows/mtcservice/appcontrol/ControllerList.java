package com.petrows.mtcservice.appcontrol;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ControllerList {
	private final static String TAG = "ControllerList";
	private ArrayList<ControllerBase> controllersList = new ArrayList<ControllerBase>();
	private ArrayList<ControllerBase> controllersListCall = new ArrayList<ControllerBase>();

	public ControllerList(Context ctx)
	{
		controllersList.add(new ControllerMediaButtons());
		controllersList.add(new ControllerPcRadio());

		// Sort by call priority 0->100
		for (ControllerBase s : controllersList) {
			s.init(ctx);
			controllersListCall.add(s);
		}

		Collections.sort(controllersListCall, new Comparator<ControllerBase>() {
			@Override
			public int compare(ControllerBase s1, ControllerBase s2) {
				return (s1.getEventPriority() - s2.getEventPriority()); // Sort by Priority
			}
		});

		for (ControllerBase s : controllersList)
		{
			Log.d(TAG, "App (list): " + s.getName());
		}

		for (ControllerBase s : controllersListCall)
		{
			Log.d(TAG, "App (call): " + s.getName());
		}
	}

}
