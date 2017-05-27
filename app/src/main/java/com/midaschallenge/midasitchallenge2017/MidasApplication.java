package com.midaschallenge.midasitchallenge2017;

import android.app.Application;
import android.content.Context;

/**
 * Created by bo on 2017. 5. 27..
 */

public class MidasApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
