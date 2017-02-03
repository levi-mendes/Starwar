package br.com.levimendesestudos.starwars;

import android.app.Application;
import android.content.Context;

/**
 * Created by 809778 on 03/02/2017.
 */
public class ApplicationStarWarApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ApplicationStarWarApp.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}