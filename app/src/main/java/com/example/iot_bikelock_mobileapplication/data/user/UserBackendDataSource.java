package com.example.iot_bikelock_mobileapplication.data.user;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.example.iot_bikelock_mobileapplication.application.AppVolleyRequestQueue;
import com.example.iot_bikelock_mobileapplication.application.BackendJsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserBackendDataSource {
    private static UserBackendDataSource instance;

    private Context appContext;
    private RequestQueue queue;

    /**
     * @param applicationContext pass getApplicationContext() to avoid leaking.
     */
    private UserBackendDataSource(Context applicationContext) {
        appContext = applicationContext;
    }

    /**
     * @param applicationContext pass getApplicationContext() to avoid leaking.
     */
    public static UserBackendDataSource getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new UserBackendDataSource(applicationContext);
            instance.queue = AppVolleyRequestQueue.getInstance(applicationContext);
        }
        return instance;
    }

    /**
     * @param backendToken access token for backend. stored in SharedPreferences.
     * @see com.example.iot_bikelock_mobileapplication.data.LoginSharedPreferenceDataSource
     */
    public void retrieveUserFromBackend(
            String backendToken,
            Response.Listener<JSONObject> responseListener,
            @Nullable Response.ErrorListener errorListener
    ) throws JSONException {
        JSONObject requestParams = new JSONObject();
        BackendJsonObjectRequest request = new BackendJsonObjectRequest(
                Request.Method.GET,
                "/api/auth/user",
                requestParams,
                responseListener,
                errorListener
        );
        request.authenticated(backendToken);
        queue.add(request);
    }

    public void submitCompleteRegistration(
            String backendToken,
            String section,
            String phone,
            String imageBase64,
            Response.Listener<JSONObject> responseListener,
            @Nullable Response.ErrorListener errorListener
    )  throws  JSONException{
        JSONObject form = new JSONObject();
        form.put("section", section);
        form.put("phone", phone);
        form.put("bike_image_base64", imageBase64);

        BackendJsonObjectRequest request = new BackendJsonObjectRequest(
                Request.Method.POST,
                "/api/auth/complete_registration",
                form,
                responseListener,
                errorListener
        );
        request.authenticated(backendToken);
        queue.add(request);
    }
}
