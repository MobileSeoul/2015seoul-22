package com.example.genebe.Http;

import com.android.volley.Response;
import com.example.genebe.Model.Comments;

import java.util.Map;

/**
 * Created by yk on 2015-10-31.
 */
public class CommentRequest extends GsonRequest {
    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url           URL of the request to make
     * @param clazz         Relevant class object, for Gson's reflection
     * @param headers       Map of request headers
     * @param listener
     * @param errorListener
     */
    public CommentRequest(String url, Class<Comments> clazz, Map headers, Response.Listener listener, Response.ErrorListener errorListener) {
        super(url, clazz, headers, listener, errorListener);
    }


}
