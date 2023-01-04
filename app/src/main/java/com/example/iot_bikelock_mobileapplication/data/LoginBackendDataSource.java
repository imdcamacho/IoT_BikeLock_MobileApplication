package com.example.iot_bikelock_mobileapplication.data;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.iot_bikelock_mobileapplication.application.AppVolleyRequestQueue;
import com.example.iot_bikelock_mobileapplication.application.BackendJsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginBackendDataSource {
    private static LoginBackendDataSource instance = null;
    RequestQueue queue;

    /**
     * @param applicationContext
     * always pass the application context (getApplicationContext()) method
     * to avoid context leaking.
     */
    public LoginBackendDataSource(Context applicationContext) {
        this.queue = AppVolleyRequestQueue.getInstance(applicationContext);
    }

    /**
     *
     * @see LoginBackendDataSource#LoginBackendDataSource(Context)
     *
     * @link https://medium.com/swlh/context-and-memory-leaks-in-android-82a39ed33002
     *
     * @return singleton instance.
     */
    public static LoginBackendDataSource getInstance(Context applicationContext) {
        if(instance == null) {
            instance = new LoginBackendDataSource(applicationContext);
        }
        return instance;
    }

    /**
     * send a request to the backend to login/register to the app.
     *
     * @param idToken Google ID token
     * @param responseListener code to call once the request finishes.
     * @param errorListener code to call if the request fails.
     * @throws JSONException failure in making the request body
     */
    public void authenticateWithGoogleIdToken(
            String idToken,
            Response.Listener<JSONObject> responseListener,
            @Nullable Response.ErrorListener errorListener
    ) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("google_id_token", idToken);

        BackendJsonObjectRequest request = new BackendJsonObjectRequest(
                Request.Method.POST,
                "/api/auth/google_id_token",
                requestBody,
                responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        if(errorListener != null) {
                            errorListener.onErrorResponse(error);

                        }
                    }
                }
        );
        this.queue.add(request);
    }
}
