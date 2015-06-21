package com.petrows.mtcservice.hal.mtc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.petrows.mtcservice.hal.ISwc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Swc extends ISwc {

	BroadcastReceiver keysHandler;
	static final String mtcKeyUp = "com.microntek.irkeyUp";
	private HashMap<Key, ArrayList<Integer> > defaultKeys;

	Swc(Context context) {
		super(context);

		defaultKeys = new HashMap<>();
		defaultKeys.put(Key.KEY_PREV, new ArrayList<Integer>(Arrays.asList( 45, 58, 22 )));
		defaultKeys.put(Key.KEY_NEXT, new ArrayList<Integer>(Arrays.asList( 46, 59, 24 )));
		defaultKeys.put(Key.KEY_PAUSE, new ArrayList<Integer>(Arrays.asList( 3 )));

		// Create handler
		keysHandler = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				handleKey(intent);
			}
		};
	}

	private void handleKey(Intent intent)
	{
		if (!intent.getAction().equals(mtcKeyUp)) return; // Not our intent
		int keyCode = intent.getIntExtra("keyCode", -1);
		if (-1 == keyCode) return; // No key

		// Send raw key
		sendPressRaw(String.valueOf(keyCode));

		// Okay, now map TheKey
		Key keyMapped = mapKey(String.valueOf(keyCode));
		if (Key.KEY_NONE == keyMapped)
		{
			// Unknown key
			return;
		}

		// Send mapped key
		sendPressMapped(keyMapped);
	}
}
