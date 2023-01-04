package com.example.iot_bikelock_mobileapplication.data;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSharedPreferenceDataSource {
    private static LoginSharedPreferenceDataSource instance = null;

    public static final String SHARED_PREFERENCE_NAME = "backend_login";
    public static final String TOKEN_KEY_NAME = "token";
    private SharedPreferences sharedPreferences = null;

    public LoginSharedPreferenceDataSource(SharedPreferences prefs) {
        this.sharedPreferences = prefs;
    }


    public static LoginSharedPreferenceDataSource getInstance(Context applicationContext) {
        if(instance == null) {
            SharedPreferences prefs = applicationContext.getSharedPreferences( SHARED_PREFERENCE_NAME, applicationContext.MODE_PRIVATE);
            instance = new LoginSharedPreferenceDataSource(prefs);
        }
        return instance;
    }

    public void storeBackendAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY_NAME, token);
        editor.commit();
    }

    public String getBackendAuthToken() {
        String token = sharedPreferences.getString(TOKEN_KEY_NAME, null);
        return token;
    }

}
