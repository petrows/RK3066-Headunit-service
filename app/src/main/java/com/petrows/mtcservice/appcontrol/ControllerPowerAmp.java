package com.petrows.mtcservice.appcontrol;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.maxmpz.poweramp.player.PowerampAPI;

public class ControllerPowerAmp extends ControllerBase {
    final static String appPackage = "com.maxmpz.audioplayer";
    final static String appService = ".player.PlayerService";

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
        return "poweramp";
    }

    @Override
    public String getName() {
        return "PowerAMP";
    }

    @Override
    public boolean onNext() {
        if (!isRunning()) return false; // No app - no key
        Intent control = PowerampAPI.newAPIIntent();
        control.putExtra(PowerampAPI.COMMAND, PowerampAPI.Commands.NEXT);
        ctx.startService(control);
        return true;
    }

    @Override
    public boolean onPrev() {
        if (!isRunning()) return false; // No app - no key
        Intent control = PowerampAPI.newAPIIntent();
        control.putExtra(PowerampAPI.COMMAND, PowerampAPI.Commands.PREVIOUS);
        ctx.startService(control);
        return true;
    }

    @Override
    public boolean onStop() {
        if (!isRunning()) return false; // No app - no key
        Intent control = PowerampAPI.newAPIIntent();
        control.putExtra(PowerampAPI.COMMAND, PowerampAPI.Commands.STOP);
        ctx.startService(control);
        return true;
    }

    @Override
    public boolean onPlayPause() {
        if (!isRunning()) return false; // No app - no key
        Intent control = PowerampAPI.newAPIIntent();
        control.putExtra(PowerampAPI.COMMAND, PowerampAPI.Commands.TOGGLE_PLAY_PAUSE);
        ctx.startService(control);
        return true;
    }

    @Override
    public boolean onNextInCat() {
        if (!isRunning()) return false; // No app - no key
        Intent control = PowerampAPI.newAPIIntent();
        control.putExtra(PowerampAPI.COMMAND, PowerampAPI.Commands.NEXT_IN_CAT);
        ctx.startService(control);
        return true;
    }

    @Override
    public boolean onPrevInCat() {
        if (!isRunning()) return false; // No app - no key
        Intent control = PowerampAPI.newAPIIntent();
        control.putExtra(PowerampAPI.COMMAND, PowerampAPI.Commands.PREVIOUS_IN_CAT);
        ctx.startService(control);
        return true;
    }
}
