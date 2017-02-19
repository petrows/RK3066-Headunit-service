package com.petrows.mtcservice.appcontrol;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

import com.petrows.mtcservice.BootReceiver;
import com.petrows.mtcservice.Settings;

abstract class ControllerIntendedApp extends ControllerBase {
    private final static String TAG = "ControllerIntendedApp";
    //dsa
    private boolean ord = false;

    ControllerIntendedApp() {
        Log.d(TAG, "Creating");
    }

    @Override
    public int getEventPriority() {
        return 40;
    }

    @Override
    public boolean onNext() {
        if (!isRunning()) return false;
        sendKey(KeyEvent.KEYCODE_MEDIA_NEXT);
        return true;
    }

    @Override
    public boolean onPrev() {
        if (!isRunning()) return false;
        sendKey(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
        return true;
    }

    @Override
    public boolean onStop() {
        if (!isRunning()) return false;
        sendKey(KeyEvent.KEYCODE_MEDIA_STOP);
        return true;
    }

    @Override
    public boolean onPlay() {
        if (!isRunning()) return false;
        sendKey(KeyEvent.KEYCODE_MEDIA_PLAY);
        return true;
    }

    @Override
    public boolean onPlayPause() {
        if (!isRunning()) return false;
        sendKey(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
        return true;
    }

    private boolean isRunning() {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo pid : am.getRunningServices(200)) {
            if (pid.service.getClassName().contains(getIntendedAppPackageName())) {
                Log.d(TAG, getName() + " is running: " + pid.service.getClassName());
                return true;
            }
        }

        Log.d(TAG, getName() + " is not running");
        return false;
    }

    private void sendKey(int keycode) {
        Log.d(TAG, "Send key " + keycode);
        long eventtime = SystemClock.uptimeMillis();

        if (!ord) {
            if (BootReceiver.isReceived) {
                ord = true;
            }
        }

        Intent downIntent = createMediaKeyIntent(eventtime, keycode, KeyEvent.ACTION_DOWN);
        fireIT(ctx, downIntent, true);

        Intent upIntent = createMediaKeyIntent(eventtime, keycode, KeyEvent.ACTION_UP);
        fireIT(ctx, upIntent, false);

        if (!ord) {
            Settings.get(ctx).mySleep(400);
            Intent blankIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
            KeyEvent event = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_UNKNOWN);
            blankIntent.putExtra(Intent.EXTRA_KEY_EVENT, event);
            fireIT(ctx, blankIntent, true);
        }
    }

    private Intent createMediaKeyIntent(long eventtime, int keycode, int action) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        intent.setClassName(getIntendedAppPackageName(), getIntendedAppClassName());
        KeyEvent event = new KeyEvent(eventtime, eventtime, action, keycode, 0);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, event);
        return intent;
    }

    private void fireIT(Context ctx, Intent it, boolean frrp) {
        if (ord) {
            ctx.sendOrderedBroadcast(it, null);
        } else {
            if (frrp) {
                it.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
            }
            ctx.sendBroadcast(it);
        }
    }

    protected abstract String getIntendedAppClassName();

    protected abstract String getIntendedAppPackageName();
}
