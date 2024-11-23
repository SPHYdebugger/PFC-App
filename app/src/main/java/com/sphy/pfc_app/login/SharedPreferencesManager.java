package com.sphy.pfc_app.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.sphy.pfc_app.DTO.UserDTO;
import com.sphy.pfc_app.api.StationApi;
import com.sphy.pfc_app.api.StationApiInterface;
import com.sphy.pfc_app.api.UserApi;
import com.sphy.pfc_app.api.UserApiInterface;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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



    public long getUserIdFromJWT(String token) {
        try {
            String[] parts = token.split("\\.");
            String payload = parts[1];
            String decodedPayload = new String(Base64.decode(payload, Base64.URL_SAFE));

            JSONObject jsonObject = new JSONObject(decodedPayload);

            long userId = jsonObject.getLong("userId");
            return userId;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getEmailFromJWT(String token) {
        try {
            String[] parts = token.split("\\.");
            String payload = parts[1];
            String decodedPayload = new String(Base64.decode(payload, Base64.URL_SAFE));

            JSONObject jsonObject = new JSONObject(decodedPayload);

            String userEmail = jsonObject.getString("email");
            return userEmail;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getUsernameFromJWT(String token) {
        try {
            String[] parts = token.split("\\.");
            String payload = parts[1];
            String decodedPayload = new String(Base64.decode(payload, Base64.URL_SAFE));

            JSONObject jsonObject = new JSONObject(decodedPayload);

            String userName = jsonObject.getString("username");
            return userName;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    public void clear() {
        editor.clear();
        editor.apply();
    }
}