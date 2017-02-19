package com.petrows.mtcservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.petrows.mtcservice.appcontrol.ControllerList;

import java.util.ArrayList;
import java.util.List;

public class ServiceMain extends Service implements LocationListener {

    private final static String TAG = "ServiceMain";
    public static boolean isRunning = false;
    //dsa
    SWCReceiver mtc;
    ControllerList appController;
    private int nxtLower = -1;
    private int nxtHigher = -1;

    @Override
    public IBinder onBind(Intent intent) {
        return (null);
    }

    @Override
    public void onCreate() {
        if (isRunning) return;

        isRunning = true;

        Log.d(TAG, "MTCService onCreate");

        //dsa
        IntentFilter intf = new IntentFilter();
        intf.addAction(Settings.MTCBroadcastIrkeyUp);
        intf.addAction(Settings.MTCBroadcastACC);
        intf.addAction(Settings.MTCBroadcastWidget);
        mtc = new SWCReceiver();
        registerReceiver(mtc, intf);
        Log.d(TAG, "SWCReceiver registerReceiver");

        appController = ControllerList.get(this);

        //dsa
        Settings.get(this).startMyServices();

        // Root?
        RootSession.get(this).open();

        ServiceBtReciever.get(this).connectBt();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mtc);
        mtc = null;
        isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Settings.getServiceEnable()) {
            Log.d(TAG, "MTCService onStartCommand");

            Settings.get(this).announce(this, startId);

            LocationManager locationManager = (LocationManager) this
                    .getSystemService(Context.LOCATION_SERVICE);
            if (null != locationManager) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            }

            // Set safe volume?
            if (Settings.get(this).getSafeVolumeEnable()) {
                Settings.get(this).setVolumeSafe();
            }

            Settings.get(this).getCallerVersionAuto();

            // Check that MTC app is not running before start
            if (!Settings.get(this).isMTCAppRunning()) {
                if (Settings.get(this).getMediaPlayerAutorun()) {
                    Settings.get(this).startMediaPlayer();
                }
                if (Settings.get(this).getMediaPlayerAutoplay()) {
                    ControllerList.get(this).sendKeyPlay();
                }
            } else {
                Log.d(TAG, "MTC app is running!");
            }

            return (START_STICKY);
        }
        stopSelf();
        return (START_NOT_STICKY);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (!Settings.get(this).getSpeedEnable()) return; // Speed control is disabled
        if (!location.hasSpeed()) return; // Speed control is disabled

        if (Settings.get(this).getMute()) {
            // Skip volume change on Mute
            Log.d(TAG, "Set volume skipped - mute is active");
            return;
        }

        List<Integer> speed_steps = new ArrayList<Integer>(Settings.get(this).getSpeedValues());
        speed_steps.add(0, -999);
        speed_steps.add(999);
        int tol = Settings.get(this).getSpeedTolerance();
        int vol = Settings.get(this).getVolume();
        int volNew = vol;
        int volChange = Settings.get(this).getSpeedChangeValue();

        if (0 == vol) {
            // Skip volume change on Volume == 0
            Log.d(TAG, "Set volume skipped - volume == 0");
            return;
        }

        double speed = location.getSpeed();
        speed = speed * 3.6; // m/s => km/h


        if (nxtHigher == -1 || nxtLower == -1) {
            for (int i = 0; i < speed_steps.size(); i++) {
                if (speed > speed_steps.get(i)) {
                    nxtLower = i;
                    nxtHigher = i + 1;
                    if (nxtHigher == speed_steps.size())
                        nxtHigher--;
                }
            }
        }

        if (nxtHigher == -1 || nxtLower == -1) {
            Log.e(TAG, "config error");
            return;
        }

        Log.d(TAG, "Speed is: " + speed + ", steps are: " + speed_steps.toString());

        if (speed > speed_steps.get(nxtHigher) + tol) {
            //Log.d(TAG, "Set (+) volume: " + volNew + "+" + volChange + " / " + last_speedstep + " / " + speed + "(" + spd_step + ")");
            volNew = volNew + volChange;
            nxtLower = nxtHigher;
            if (nxtHigher < speed_steps.size() - 1)
                nxtHigher++;
        }

        if (speed < speed_steps.get(nxtLower) - tol) {
            // Log.d(TAG, "Set (-) volume: " + volNew + "-" + volChange + " / " + last_speed + " / " + speed + "(" + spd_step + ")");
            volNew = volNew - volChange;
            nxtHigher = nxtLower;
            if (nxtLower > 0)
                nxtLower--;

        }

        if (volNew > 0 && volNew != vol) {
            // Change it!
            Settings.get(this).setVolume(volNew);
            Settings.get(this).showToast("Volume " + (volNew > vol ? "+" : "-") + " (" + volNew + ")");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
