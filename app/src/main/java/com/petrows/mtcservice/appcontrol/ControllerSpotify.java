package com.petrows.mtcservice.appcontrol;

class ControllerSpotify extends ControllerIntendedApp {
    private static final String SPOTIFY_CLASS_NAME = "com.spotify.music.internal.receiver.MediaButtonReceiver";
    private static final String SPOTIFY_PACKAGE_NAME = "com.spotify.music";

    @Override
    public String getId() {
        return "Spotify";
    }

    @Override
    public String getName() {
        return "Spotify";
    }

    @Override
    protected String getIntendedAppClassName() {
        return SPOTIFY_CLASS_NAME;
    }

    @Override
    protected String getIntendedAppPackageName() {
        return SPOTIFY_PACKAGE_NAME;
    }
}
