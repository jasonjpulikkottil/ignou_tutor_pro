package com.jdots.ignoupro;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * Created for chatting on 26/08/16.
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }


}
