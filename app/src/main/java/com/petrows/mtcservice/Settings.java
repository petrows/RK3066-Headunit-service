package com.petrows.mtcservice;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.AudioManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.petrows.mtcservice.appcontrol.ControllerBase;
import com.petrows.mtcservice.appcontrol.ControllerList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Settings {
    final static String MTCBroadcastIrkeyUp = "com.microntek.irkeyUp";
    //final static String MTCBroadcastIrkeyDown = "com.microntek.irkeyDown";
    final static String MTCBroadcastACC = "com.microntek.acc";
    final static List<Integer> MTCKeysPrev = Arrays.asList(45, 58, 22, 63);
    final static List<Integer> MTCKeysNext = Arrays.asList(46, 59, 24, 64);
    final static List<Integer> MTCKeysPause = Arrays.asList(3);
    final static List<Integer> MTCKeysPhone = Arrays.asList(69);
    final static List<String> MTCMusicApps = Arrays.asList(
            "com.microntek.radio.RadioActivity", "com.microntek.music.MusicActivity", "com.microntek.dvd.DVDActivity", "com.microntek.ipod.IPODActivity", "com.microntek.media.MediaActivity", "com.microntek.bluetooth.BlueToothActivity"
    );
    final static List<Integer> MTCKeysVolumeUp = Arrays.asList(19);
    final static List<Integer> MTCKeysVolumeDown = Arrays.asList(27);
    final static List<Integer> MTCKeysPrevInCat = Arrays.asList(61);
    final static List<Integer> MTCKeysNextInCat = Arrays.asList(62);
    final static String MTCBroadcastWidget = "com.android.MTClauncher.action.INSTALL_WIDGETS";
    final static int MTCWidgetAdd = 10520;
    private final static String TAG = "Settings";
    //final static int MTCWidgetRemove = 10521;
    private static Settings instance = null;
    private static SharedPreferences prefs;
    private ArrayList<Integer> speedValues = new ArrayList<Integer>();
    private String speedValuesDef = "";
    private float volumeMax = 30f;
    private int buildTimestamp = 0;
    private AudioManager am = null;
    private Context ctx;

    private Settings(Context context) {
        ctx = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        speedValuesDef = ctx.getString(R.string.cfg_def_speed_values);

        am = ((AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE));

        // Get max volume
        try {
            String vol_max_s = am.getParameters("cfg_maxvolume=");
            volumeMax = Float.parseFloat(vol_max_s);
        } catch (Exception e) {
            volumeMax = 0f;
        }

        if (0f == volumeMax) {
            Log.e(TAG, "Cant get max volume, set to default 30.0");
            volumeMax = 30.0f;
        }
        Log.d(TAG, "Max volume = " + String.valueOf(volumeMax));

        buildTimestamp = Integer.parseInt(getPropValue("ro.build.date.utc"));
        Log.d(TAG, "Build timestamp: " + String.valueOf(buildTimestamp));

        Set<String> wasInstalledApps = prefs.getStringSet("keys.apps.installed", new HashSet<String>());
        Set<String> wasEnabledApps = prefs.getStringSet("keys.apps", new HashSet<String>());


        Set<String> defaultList = new HashSet<String>();
        Set<String> installedList = new HashSet<String>();
        // Get all controllers list and add default-enabled to list
        ArrayList<ControllerBase> appsList = ControllerList.get(ctx).getListDisplay();
        for (ControllerBase app : appsList) {
            installedList.add(app.getId());
            if (wasEnabledApps.contains(app.getId())) {
                // This app was enabled
                defaultList.add(app.getId());
                Log.d(TAG, "App " + app.getId() + " was enabled by user");
                continue;
            }
            if (app.isDefaultEnabled() && !wasInstalledApps.contains(app.getId())) {
                // This app is new and enabled by-default
                defaultList.add(app.getId());
                Log.d(TAG, "App " + app.getId() + " is new and was enabled by default");
                continue;
            }
        }

        Log.d(TAG, "Set default apps list: " + defaultList.toString());
        Editor editor = prefs.edit();
        editor.putStringSet("keys.apps", defaultList);
        editor.putStringSet("keys.apps.installed", installedList);
        editor.apply();


        Log.d(TAG, "Settings created");
    }

    public static Settings get(Context context) {
        if (null == instance)
            instance = new Settings(context);
        return (instance);
    }

    public static void destroy() {
        instance = null;
    }

    public static boolean isNotificationServiceEnabled() {
        return (Build.VERSION.SDK_INT >= 19);
    }

    public static String getPropValue(String value) {
        Process p;
        String ret = "";
        try {
            p = new ProcessBuilder("/system/bin/getprop", value).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                ret = line;
            }
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean getServiceEnable() {
        return prefs.getBoolean("service.enable", true);
    }

    public void setServiceEnable(boolean enable) {
        setCfgBool("service.enable", enable);
    }

    public static boolean getServiceToast() {
        return prefs.getBoolean("service.toast", true);
    }

    private void setCfgBool(String name, boolean val) {
        Editor editor = prefs.edit();
        editor.putBoolean(name, val);
        editor.apply();
    }

    public void setCfgString(String name, String val) {
        Editor editor = prefs.edit();
        editor.putString(name, val);
        editor.apply();
    }

    //dsa
    public void mySleep(long ms) {
        long endTime = System.currentTimeMillis() + ms;
        while (System.currentTimeMillis() < endTime) {
            synchronized (this) {
                try {
                    wait(endTime -
                            System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startMyServices() {
        if (getServiceEnable()) {
            if (!ServiceMain.isRunning) {
                if (isNotificationServiceEnabled()) {
                    if (NotificationService.isInit) mySleep(2000);
                }
                Log.d(TAG, "Starting service!");
                ctx.startService(new Intent(ctx, ServiceMain.class));
            }

            if (isNotificationServiceEnabled()) {
                if (!NotificationService.isInit) {
                    if (ServiceMain.isRunning) mySleep(2000);
                    Log.d(TAG, "Starting Notification!");
                    ctx.startService(new Intent(ctx, NotificationService.class));
                }
            }
        } else {
            ctx.stopService(new Intent(ctx, ServiceMain.class));
            if (isNotificationServiceEnabled()) {
                ctx.stopService(new Intent(ctx, NotificationService.class));
            }
        }
    }

    public void announce(Service srv, int id) {
        Intent notificationIntent = new Intent(srv, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(srv, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification note = new NotificationCompat.Builder(srv)
                .setContentTitle(srv.getString(R.string.app_service_title) + " " + Settings.get(srv).getVersion())
                .setContentText(srv.getString(R.string.app_service_descr))
                .setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher).build();
        srv.startForeground(id, note);
    }

    public String getVersion() {
        String version = "?";
        try {
            PackageInfo pInfo = ctx.getPackageManager().getPackageInfo(
                    ctx.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public boolean isMTCAppRunning() {
        ActivityManager mng = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> activities = mng.getRunningTasks(0x7fffffff);
        for (ActivityManager.RunningTaskInfo task : activities) {
            if (MTCMusicApps.contains(task.topActivity.getClassName())) {
                Log.d(TAG, "Activity running: " + task.topActivity.getClassName());
                return true;
            }
        }
        return false;
    }

    public void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(String text, int length) {
        Log.d(TAG, "Toast: " + text);
        if (getServiceToast())
            Toast.makeText(ctx, text, length).show();
    }

    public boolean getCallerEnable() {
        return prefs.getBoolean("caller.enable", true);
    }

    public int getCallerVersionAuto() {
        // We should test ro.build.date.utc to be > 15 dec 2014
        // 1418642842 (2014-12-15) uses OLD API
        // 1419924496 (2014-12-30) uses NEW API
        // 1421921736 (2015-01-22) uses NEW API
        if (buildTimestamp <= 1418642842) // <= 15 dec 2015
            return 1;
        else
            return 2;
    }

    public int getCallerVersion() {
        int version = Integer.parseInt(prefs.getString("caller.api", "0"));
        if (0 == version) {
            return getCallerVersionAuto();
        }
        return version;
    }

    public boolean getMediaKeysEnable() {
        return prefs.getBoolean("keys.enable", true);
    }

    public HashSet<String> getMediaApps() {
        return new HashSet<String>(prefs.getStringSet("keys.apps", null));
    }

    public String getMediaPlayerApp() {
        return prefs.getString("player.app", "");
    }

    public boolean getMediaPlayerAutoplay() {
        return prefs.getBoolean("player.autoplay", false);
    }

    public boolean getMediaPlayerAutorun() {
        return prefs.getBoolean("player.autorun", false);
    }

    public boolean getMediaPlayerPhonerun() {
        return prefs.getBoolean("player.phonerun", false);
    }

    public void startMediaPlayer() {
        String launchapp = Settings.get(ctx).getMediaPlayerApp();
        Log.d(TAG, "Launch app: " + launchapp);
        if (launchapp != null && !launchapp.equals("")) {
            try {
                Intent appintent = ctx.getPackageManager().getLaunchIntentForPackage(launchapp);
                if (null != appintent) {
                    appintent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_RECEIVER_FOREGROUND);
                    appintent.setPackage(launchapp);
                    showToast(ctx.getString(R.string.toast_player_start));
                    ctx.startActivity(appintent);
                }
            } catch (Exception e) {
                // Error :-(
            }
        }
    }

    public boolean getSafeVolumeEnable() {
        return prefs.getBoolean("svol.enable", false);
    }

    public int getSafeVolumeLevel() {
        return Integer.valueOf(prefs.getString("svol.level", "5"));
    }

    public boolean getSpeedEnable() {
        return prefs.getBoolean("speed.enable", true);
    }

    public int getSpeedTolerance() {
        return Integer.valueOf(prefs.getString("speed.tol", "5"));
    }

    public int getTheme() {
        return Integer.valueOf(prefs.getString("design.theme", "-1"));
    }

    public int getSpeedChangeValue() {
        return Integer.valueOf(prefs.getString("speed.speedvol", "5"));
    }

    public boolean getUSBenable() {
        return prefs.getBoolean("usbvol.enable", false);
    }

    public String USBnumber() {
        return prefs.getString("usbvol.number", "2");
    }

    public String USBname() {
        return prefs.getString("usbvol.name", "PCM");
    }

    public List<Integer> getSpeedValues() {
        // Load speed values
        if (speedValues == null || speedValues.size() <= 0) {
            // Try to calc it
            String spd_cfg = prefs
                    .getString("speed.speedrange", speedValuesDef);
            // Calc it
            List<String> speed_vals_str = Arrays.asList(spd_cfg
                    .split("\\s*,\\s*"));
            StringBuilder speed_vals_clr = new StringBuilder();
            for (String spd_step : speed_vals_str) {
                Integer s;
                try {
                    s = Integer.valueOf(spd_step);
                } catch (Exception e) {
                    s = -1;
                }
                if (s > 0 && s < 500) {
                    if (speedValues.size() > 0)
                        speed_vals_clr.append(", ");
                    speed_vals_clr.append(s.toString());
                    speedValues.add(s);
                }
            }
            if (!prefs.getString("speed.speedrange", speedValuesDef).equals(speed_vals_clr.toString())) {
                setCfgString("speed.speedrange", speed_vals_clr.toString());
            }
        }
        return speedValues;
    }

    public boolean getMute() {
        return am.getParameters("av_mute=").equals("true");
    }

    // This function is reversed from package
    // android.microntek.service.MicrontekServer
    private int mtcGetRealVolume(int paramInt) {
        float f1 = 100.0F * paramInt / volumeMax;
        float f2;
        if (f1 < 20.0F) {
            f2 = f1 * 3.0F / 2.0F;
        } else if (f1 < 50.0F) {
            f2 = f1 + 10.0F;
        } else {
            f2 = 20.0F + f1 * 4.0F / 5.0F;
        }
        return (int) f2;
    }

    public void setVolumeUSB_alsa(int level) {
        //Need app AlsaMixer and ROOT
        if (getUSBenable()) {
            Log.d(TAG, "Settings new volume USB: " + level + ", real: " + mtcGetRealVolume(level) + "%");
            RootSession.get(ctx).exec("alsa_amixer -c" + USBnumber() + " sset '" + USBname() + "' " + mtcGetRealVolume(level) + "%");
        }
    }

    public void setVolumeUSB(int level) {
        if (level < 0 || level > (int) volumeMax) {
            Log.w(TAG, "Volume USB level " + level + " is wrong, ignore it");
            return;
        }

        setVolumeUSB_alsa(level);
    }

    public void setVolumeSafe() {
        showToast(ctx.getString(R.string.toast_safe_volume));
        setVolume(getSafeVolumeLevel());
    }

    public int getVolume() {
        return android.provider.Settings.System.getInt(
                ctx.getContentResolver(), "av_volume=", 15);
    }

    public void setVolume(int level) {
        if (level < 0 || level > (int) volumeMax) {
            Log.w(TAG, "Volume level " + level + " is wrong, ignore it");
            return;
        }

        Log.d(TAG, "Settings new volume system: " + level + ", real: "
                + mtcGetRealVolume(level));
        android.provider.Settings.System.putInt(ctx.getContentResolver(),
                "av_volume=", level);
        am.setParameters("av_volume=" + mtcGetRealVolume(level));

        // notify MTC Server
        // We should not to do this - this cause annoying MTC popup on top of screen
        // Intent intent = new Intent("com.microntek.setVolume");
        // ctx.sendBroadcast(intent);

        setVolumeUSB_alsa(level);
    }
}
