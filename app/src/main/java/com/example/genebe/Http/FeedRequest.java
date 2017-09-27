package com.example.genebe.Http;

import com.android.volley.Response;
import com.example.genebe.Model.Feeds;

import java.util.Map;

/**
 * Created by yk on 2015-09-27.
 */
public class FeedRequest extends GsonRequest<Feeds> {

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url           URL of the request to make
     * @param clazz         Relevant class object, for Gson's reflection
     * @param headers       Map of request headers
     * @param listener
     * @param errorListener
     */
    public FeedRequest(String url, Class<Feeds> clazz, Map<String, String> headers, Response.Listener<Feeds> listener, Response.ErrorListener errorListener) {
        super(url, clazz, headers, listener, errorListener);
    }
}
