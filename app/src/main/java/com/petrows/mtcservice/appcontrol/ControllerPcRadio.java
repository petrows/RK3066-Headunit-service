package com.petrows.mtcservice.appcontrol;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.petrows.mtcservice.R;
import com.petrows.mtcservice.RootSession;


public class ControllerPcRadio extends ControllerBase {
    final static String appPackage = "com.maxxt.pcradio";
    final static String appService = ".service.RadioService";

    public boolean isRunning() {
        ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo pid : am.getRunningServices(100)) {
            if (pid.service.getClassName().equals(appPackage + appService)) {
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

    @Override
    public boolean onNext() {
        if (!isRunning()) return false; // No app - no key
        return RootSession.get(ctx).exec("am startservice -n com.maxxt.pcradio/.service.RadioService -a com.maxxt.radio.ACTION_PLAY_NEXT_STREAM");
    }

    @Override
    public boolean onPrev() {
        if (!isRunning()) return false; // No app - no key
        return RootSession.get(ctx).exec("am startservice -n com.maxxt.pcradio/.service.RadioService -a com.maxxt.radio.ACTION_PLAY_PREV_STREAM");
    }

    @Override
    public boolean onStop() {
        if (!isRunning()) return false; // No app - no key
        return RootSession.get(ctx).exec("am startservice -n com.maxxt.pcradio/.service.RadioService -a com.maxxt.radio.ACTION_STOP_PLAYBACK");
    }

    @Override
    public boolean onPlayPause() {
        return onStop(); // Dont supported, do stop unstead
    }
}
