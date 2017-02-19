package com.petrows.mtcservice.appcontrol;

import android.content.Context;
import android.util.Log;

import com.petrows.mtcservice.Settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class ControllerList {
    private final static String TAG = "ControllerList";
    private static ControllerList instance = null;
    private ArrayList<ControllerBase> controllersList = new ArrayList<ControllerBase>();
    private ArrayList<ControllerBase> controllersListCall = new ArrayList<ControllerBase>();
    private Context ctx;

    private ControllerList(Context context) {
        ctx = context;
        controllersList.add(new ControllerSpotify());
        controllersList.add(new ControllerMediaButtons());
        controllersList.add(new ControllerPcRadio());
        controllersList.add(new ControllerPowerAmp());

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

        for (ControllerBase s : controllersList) {
            Log.d(TAG, "App (list): " + s.getName());
        }

        for (ControllerBase s : controllersListCall) {
            Log.d(TAG, "App (call): " + s.getName());
        }
    }

    public static ControllerList get(Context context) {
        if (null == instance)
            instance = new ControllerList(context);
        return (instance);
    }

    public ArrayList<ControllerBase> getListDisplay() {
        return controllersList;
    }

    public ArrayList<ControllerBase> getListCall() {
        ArrayList<ControllerBase> out = new ArrayList<ControllerBase>();
        HashSet<String> appsEnabled = Settings.get(ctx).getMediaApps();
        for (ControllerBase itm : controllersListCall) {
            if (appsEnabled.contains(itm.getId())) {
                out.add(itm);
            }
        }
        return out;
    }

    public boolean sendKeyNext() {
        ArrayList<ControllerBase> apps = getListCall();
        for (ControllerBase app : apps) {
            Log.d(TAG, "Probe NEXT : " + app.getId());
            if (app.onNext()) {
                // Key is sended!
                Log.d(TAG, "Sended");
                return true;
            }
        }
        return false; // No app!
    }

    public boolean sendKeyPrev() {
        ArrayList<ControllerBase> apps = getListCall();
        for (ControllerBase app : apps) {
            Log.d(TAG, "Probe PREV : " + app.getId());
            if (app.onPrev()) {
                // Key is sended!
                Log.d(TAG, "Sended");
                return true;
            }
        }
        return false; // No app!
    }

    public boolean sendKeyStop() {
        ArrayList<ControllerBase> apps = getListCall();
        for (ControllerBase app : apps) {
            Log.d(TAG, "Probe STOP : " + app.getId());
            if (app.onStop()) {
                // Key is sended!
                Log.d(TAG, "Sended");
                return true;
            }
        }
        return false; // No app!
    }

    public boolean sendKeyPlay() {
        ArrayList<ControllerBase> apps = getListCall();
        for (ControllerBase app : apps) {
            Log.d(TAG, "Probe PLAY : " + app.getId());
            if (app.onPlay()) {
                // Key is sended!
                Log.d(TAG, "Sended");
                return true;
            }
        }
        return false; // No app!
    }

    public boolean sendKeyPlayPause() {
        ArrayList<ControllerBase> apps = getListCall();
        for (ControllerBase app : apps) {
            Log.d(TAG, "Probe PLAY/PAUSE : " + app.getId());
            if (app.onPlayPause()) {
                // Key is sended!
                Log.d(TAG, "Sended");
                return true;
            }
        }
        return false; // No app!
    }

    public boolean sendKeyNextInCat() {
        ArrayList<ControllerBase> apps = getListCall();
        for (ControllerBase app : apps) {
            Log.d(TAG, "Probe NEXT IN CAT : " + app.getId());
            if (app.onNextInCat()) {
                // Key is sended!
                Log.d(TAG, "Sended");
                return true;
            }
        }
        return false; // No app!
    }

    public boolean sendKeyPrevInCat() {
        ArrayList<ControllerBase> apps = getListCall();
        for (ControllerBase app : apps) {
            Log.d(TAG, "Probe PREV IN CAT : " + app.getId());
            if (app.onPrevInCat()) {
                // Key is sended!
                Log.d(TAG, "Sended");
                return true;
            }
        }
        return false; // No app!
    }
}
