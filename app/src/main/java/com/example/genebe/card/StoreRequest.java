package com.example.genebe.card;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

/**
 * Created by sumin on 2015-06-23.
 */
public class StoreRequest extends JsonArrayRequest {
    public StoreRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}
