package com.example.iot_bikelock_mobileapplication.data;

import androidx.annotation.Nullable;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRepository {
    private static LoginRepository instance = null;

    private LoginBackendDataSource backendDataSource;
    private LoginSharedPreferenceDataSource sharedPreferenceDataSource;

    private LoginRepository(
            LoginBackendDataSource backendDataSource,
            LoginSharedPreferenceDataSource sharedPreferenceDataSource
    ) {
        this.backendDataSource = backendDataSource;
        this.sharedPreferenceDataSource = sharedPreferenceDataSource;
    }


    public static LoginRepository getInstance(
            LoginBackendDataSource dataSource,
            LoginSharedPreferenceDataSource sharedPreferenceDataSource
    ) {
        if (instance == null) {
            LoginRepository.instance = new LoginRepository(dataSource, sharedPreferenceDataSource);
        }
        return LoginRepository.instance;
    }


    /**
     * Authenticate to the backend using Google Account.
     * @param googleIdToken Google ID token.
     * @param listener  code to call when the user is successfully authenticated.
     *                  response has the object token.
     * @param errorListener
     * @throws JSONException
     */
    public void login(
            String googleIdToken,
            Response.Listener<JSONObject> listener,
            @Nullable Response.ErrorListener errorListener
    ) throws JSONException {
        this.backendDataSource.authenticateWithGoogleIdToken(
                googleIdToken,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String backendToken = response.getString("token");
                            sharedPreferenceDataSource.storeBackendAuthToken(backendToken);

                            listener.onResponse(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                errorListener
        );
    }

}
