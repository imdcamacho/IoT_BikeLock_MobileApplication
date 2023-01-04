package com.example.iot_bikelock_mobileapplication.application;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

public class BackendJsonObjectRequest extends JsonObjectRequest {
    protected static final String BASE_URL = "http://10.0.2.2:8000";
    @Nullable
    protected String authenticationToken;

    /* constructors */


    /**
     * {@inheritDoc}
     * @param endpoint the path after BASE_URL. start with slash.
     * (e.g. "/api/login")
     *                 
     * @see JsonObjectRequest#JsonObjectRequest(String, Response.Listener, Response.ErrorListener)  JsonObjectRequest
     */
    public BackendJsonObjectRequest(
            String endpoint,
            Response.Listener<JSONObject> listener,
            @Nullable Response.ErrorListener errorListener
    ) {
        super(getFullPath(endpoint) , listener, errorListener);
    }

    /**
     * @param endpoint the path after BASE_URL. start with slash.
     * (e.g. "/api/login")
     *
     * @see JsonObjectRequest#JsonObjectRequest(int, String, JSONObject, Response.Listener, Response.ErrorListener)  JsonObjectRequest
     */

    public BackendJsonObjectRequest(
            int method,
            String endpoint,
            @Nullable JSONObject jsonRequest,
            Response.Listener<JSONObject> listener,
            @Nullable Response.ErrorListener errorListener
    ) {
        super(method, getFullPath(endpoint), jsonRequest, listener, errorListener);
    }

    /* public methods */

    /**
     * call for authenticated requests.
     *
     * @param token token given by backend on Login.
     */
    public void authenticated(String token) {
        this.authenticationToken = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        if (this.isAuthenticated()) {
            headers.put("Authorization", "Bearer " + this.authenticationToken);
        }
        return headers;
    }

    /* internal methods */

    protected boolean isAuthenticated() {
        return this.authenticationToken != null;
    }

    protected static String getFullPath(String endpoint) {
        return BackendJsonObjectRequest.BASE_URL + endpoint;
    }
}
