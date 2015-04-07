package com.petrows.mtcservice.appcontrol;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

import com.petrows.mtcservice.BootReceiver;
import com.petrows.mtcservice.R;
import com.petrows.mtcservice.Settings;

public class ControllerMediaButtons extends ControllerBase {
	private final static String TAG = "ControllerMediaButtons";
	public ControllerMediaButtons() {
		Log.d(TAG, "Creating");
	}

	//dsa
	private boolean ord = false;

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

	@Override
	public boolean onNext() {
		sendKey(KeyEvent.KEYCODE_MEDIA_NEXT);
		return true;
	}

	@Override
	public boolean onPrev() {
		sendKey(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
		return true;
	}

	@Override
	public boolean onStop() {
		sendKey(KeyEvent.KEYCODE_MEDIA_STOP);
		return true;
	}

	@Override
	public boolean onPlay() {
		sendKey(KeyEvent.KEYCODE_MEDIA_PLAY);
		return true;
	}

	@Override
	public boolean onPlayPause() {
		sendKey(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
		return true;
	}

	public void sendKey(int keycode) {
		Log.d(TAG, "Send key " + keycode);
		long eventtime = SystemClock.uptimeMillis();

		//dsa
		if (false == ord) if (BootReceiver.syn) ord = true;

		Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent downEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_DOWN, keycode, 0);
		downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);

		//dsa
		fireIT(ctx, downIntent, true);

		Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
		KeyEvent upEvent = new KeyEvent(eventtime, eventtime,
				KeyEvent.ACTION_UP, keycode, 0);
		upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);

		//dsa
		fireIT(ctx, upIntent, false);
		if (!ord) {
			Settings.get(ctx).mySleep(400);
			Intent blankIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
			KeyEvent blankEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_UNKNOWN);
			blankIntent.putExtra(Intent.EXTRA_KEY_EVENT, blankEvent);
			fireIT(ctx, blankIntent, true);
		}
	}

	private void fireIT(Context ctx, Intent it, boolean frrp) {
		if (ord)
			ctx.sendOrderedBroadcast(it, null);
		else {
			if (frrp) it.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
			ctx.sendBroadcast(it);
		}
	}
}
