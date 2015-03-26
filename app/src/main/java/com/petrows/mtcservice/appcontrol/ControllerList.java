package com.petrows.mtcservice.appcontrol;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ControllerList {
	private final static String TAG = "ControllerList";
	private ArrayList<ControllerBase> controllersList = new ArrayList<ControllerBase>();
	private ArrayList<ControllerBase> controllersListCall = new ArrayList<ControllerBase>();

	public ControllerList()
	{
		controllersList.add(new ControllerMediaButtons());

		// Sort by call priority
		controllersListCall = controllersList;
		Collections.sort(controllersListCall, new Comparator<ControllerBase>() {
			@Override
			public int compare(ControllerBase s1, ControllerBase s2) {
				return (s1.getCallPriority() - s2.getCallPriority());
			}
		});

		for (ControllerBase s : controllersListCall)
		{
			Log.d(TAG, "App: " + s.getName());
		}
	}



}
