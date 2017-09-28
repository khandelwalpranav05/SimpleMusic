package com.example.pranav.playmusic;

import android.app.Application;

/**
 * Created by piyush0 on 12/07/17.
 */

public class PMApp extends Application {
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "fonts/" + FontsOverride.FONT_PROXIMA_NOVA);
    }

}
