package xoriant.com.xorhack.utils;

import android.content.Context;
import android.util.Log;

import ai.api.util.BluetoothController;

/**
 * Created by C5265858 on 12/12/2017.
 */

public class BluetoothControllerImpl extends BluetoothController {


    private static final String TAG = BluetoothControllerImpl.class.getSimpleName();
    public BluetoothControllerImpl(Context context){
        super(context);
    }

    @Override
    public void onHeadsetDisconnected() {
        Log.d(TAG, "Bluetooth headset disconnected");
    }

    @Override
    public void onHeadsetConnected() {
        Log.d(TAG, "Bluetooth headset connected");

        //if (isInForeground() && !bluetoothController.isOnHeadsetSco()) {
            super.start();
        //}
    }

    @Override
    public void onScoAudioDisconnected() {
        super.stop();
    }

    @Override
    public void onScoAudioConnected() {
        Log.d(TAG, "Bluetooth sco audio started");
    }
}
