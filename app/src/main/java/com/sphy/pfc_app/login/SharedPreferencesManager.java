package com.sphy.pfc_app.login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String SHARED_PREF_NAME = "auth_pref";
    private static final String KEY_TOKEN = "auth_token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAuthToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clear() {
        editor.clear();
        editor.apply();
    }
}