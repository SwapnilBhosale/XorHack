package xoriant.com.xorhack;

import android.app.Application;
import android.content.Context;

import xoriant.com.xorhack.utils.BluetoothControllerImpl;

/**
 * Created by C5265858 on 12/5/2017.
 */

public class App extends Application {

    private static App mInstance;
    private static Context context;
    private static Context baseContext;

    private int activitiesCount;
    private BluetoothControllerImpl bluetoothController;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = this;
        baseContext = getBaseContext();
        bluetoothController = new BluetoothControllerImpl(this);
    }

    public static Context getContext() {
        return context;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }

    public static Context getAppBaseContext(){
        return baseContext;
    }


    protected void onActivityResume() {
        if (activitiesCount++ == 0) { // on become foreground
            bluetoothController.start();
        }
    }

    protected void onActivityPaused() {
        if (--activitiesCount == 0) { // on become background
            bluetoothController.stop();
        }
    }

    private boolean isInForeground() {
        return activitiesCount > 0;
    }
}
