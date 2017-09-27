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
import java.util.Map;

/**
 * Created by malgogi on 15. 9. 10..
 */

public class BaseRequest extends Request<JSONObject> {
    private Response.Listener<JSONObject> listener;
    private Map<String, String> params;
    private Map<String, String> headers;

    public BaseRequest( int method, String url, Response.ErrorListener errorListener ) {
        super( method, url, errorListener );
    }

    public BaseRequest(String url, Map<String, String> params, Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public BaseRequest(int method, String url, Map<String, String> params,
                        Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public BaseRequest(int method, String url, Map<String, String> params, Map<String, String> headers,
                        Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.headers = headers;
        this.params = params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded;";
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
    }
}
