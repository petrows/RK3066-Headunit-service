package com.petrows.mtcservice.appcontrol;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public abstract class ControllerBase {

    protected Context ctx;

    protected Drawable getPackageIcon(String pkg) {
        try {
            Drawable icon = ctx.getPackageManager().getApplicationIcon(pkg);
        } catch (PackageManager.NameNotFoundException ne) {

        }
        return null;
    }

    // Class identification
    public abstract String getId();

    public abstract String getName();

    public int getEventPriority() {
        return 50;
    }

    public boolean isDefaultEnabled() {
        return true;
    }

    // Work methods:
    public boolean init(Context ctx) {
        this.ctx = ctx;
        return true;
    }

    public Drawable getIcon() {
        return null;
    }

    // Play NEXT
    public boolean onNext() {
        return false;
    }

    // Play PREV
    public boolean onPrev() {
        return false;
    }

    // Play STOP
    public boolean onStop() {
        return false;
    }

    // Play PLAY
    public boolean onPlay() {
        return false;
    }

    // Play PLAY/PAUSE
    public boolean onPlayPause() {
        return false;
    }

    // Play NEXT/IN CAT
    public boolean onNextInCat() {
        return false;
    }

    // Play PREV/IN CAT
    public boolean onPrevInCat() {
        return false;
    }

}
