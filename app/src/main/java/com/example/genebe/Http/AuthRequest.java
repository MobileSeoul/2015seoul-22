package com.example.genebe.Http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by malgogi on 15. 9. 10..
 */
public class AuthRequest extends BaseRequest {
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String token;

    public AuthRequest(String url, Map<String, String> params, String token,
                       Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener ) {
        super( Request.Method.GET, url, errorListener );
        this.listener = reponseListener;
        this.params = params;
        this.token = token;
    }

    public AuthRequest(int method, String url, Map<String, String> params, String token,
                       Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener ) {
        super( method, url, errorListener );
        this.listener = reponseListener;
        this.params = params;
        this.token = token;
    }

    public AuthRequest(int method, String url, Map<String, String> params, Map<String, String> headers, String token,
                       Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.headers = headers;
        this.params = params;
        this.token = token;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params != null ? params : super.getParams();
    };

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if ( this.token == null || this.token.isEmpty() ) {
            throw new AuthFailureError();
        }

        if ( this.headers == null ) {
            headers = new HashMap< String, String>();
        }

        headers.put( "Authorization", "Bearer " + this.token );
        return headers != null ? headers : super.getHeaders();
    }

}
