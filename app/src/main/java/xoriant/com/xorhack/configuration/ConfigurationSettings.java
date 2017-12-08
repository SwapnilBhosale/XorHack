package xoriant.com.xorhack.configuration;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by C5265858 on 12/5/2017.
 */

public class ConfigurationSettings {


    private SharedPreferences sharedPreferences;

    private static String XOR_HACK_PREF = "xorHackPrefs";

    public ConfigurationSettings(Context context){
        sharedPreferences = context.getSharedPreferences(XOR_HACK_PREF,Context.MODE_PRIVATE);
        readPrefs();
    }

    private void readPrefs(){

    }
}
