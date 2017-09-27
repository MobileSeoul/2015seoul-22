//coapion 푸시 테스팅
package com.example.genebe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.genebe.Sec1.Store;
import com.example.genebe.card.NewsFeedConstants;
import com.example.genebe.upload.UploadConstants;
import com.example.genebe.upload.UploadSearchActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by kyungrakpark on 15. 6. 24..
 */

/**
 * add commentfafafkj;kj;
 * sumin commit
 */
public class GenebeBase extends Activity {
    public static final String RDB_URL = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000";
    public static final String NRDB_URL = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000";

    public static String setEncoding(String beforeencoding){
        String encodedstring="";

        try {
            encodedstring=new String(beforeencoding.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedstring;
    }

    public void getStoreInfoFromPictureGPS(final String lat,final String lng){

        AsyncTask<Void, Void, Void> servertest = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=3&lat=" + lat + "&lng=" + lng;
                //String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=3&lat=37.579588&lng=126.980801";

                final UploadConstants uploadconsts = new UploadConstants();

                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                JsonArrayRequest jsArrayRequest = new JsonArrayRequest(url, future, future);

                SingletonVolley.getInstance(getBaseContext()).addToRequestQueue(jsArrayRequest);

                try {
                    JSONArray response = future.get();
                    for(int i=0;i<response.length();i++) {
                        Store store = new Store();
                        JSONObject responseObject = response.getJSONObject(i);
                        store.shopID = responseObject.getInt("shopid");
                        store.address = NewsFeedConstants.getInstance().setEncoding(responseObject.getString("old_addr"));
                        store.telephone = NewsFeedConstants.getInstance().setEncoding(responseObject.getString("phone"));
                        store.storeName = NewsFeedConstants.getInstance().setEncoding(responseObject.getString("name"));
                        store.latLng = new LatLng(responseObject.getDouble("lat"), responseObject.getDouble("lng"));

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

        };

        servertest.execute();

    }


    /**
     @GET /food/searching
    */
    public void getSearchStoreInfoFromServer(final int storenumber, final String storename){

        AsyncTask<Void, Void, Void> servertest = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=1&name="+storename+"&lat=37.587106&lng=127.029422";

                //String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.-=com:3000/food/searching?search=1&name=%EB%AA%AC%EC%8A%A4%ED%84%B0";

                final UploadConstants uploadconsts = new UploadConstants();

                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                JsonArrayRequest jsArrayRequest = new JsonArrayRequest(url, future,future);

                SingletonVolley.getInstance(getBaseContext()).addToRequestQueue(jsArrayRequest);

                String entirestring="";
                String shopid="";
                String shopname="";
                String shopaddr="";
                String shopphone="";
                String shoplat="";
                String shoplng="";

                try {
                    JSONArray response = future.get();

                    uploadconsts.searchresultarray=response;

                    //uploadsearchactivity.dataReceivedFromServer();

                    try {
                        entirestring = setEncoding(response.toString());

                        Log.d("genebe", "return data testing response.length(): " + response.length());
                        Log.d("genebe", "return data testing uploadconsts.searchresultarray.length(): " + uploadconsts.searchresultarray.length());
                        Log.d("genebe", "return data testing entire: " + entirestring);

                        for(int i=0; i< response.length(); i++) {

                            shopid = setEncoding(response.getJSONObject(i).getString("shopid"));
                            shopname = setEncoding(response.getJSONObject(i).getString("name"));
                            shopaddr = setEncoding(response.getJSONObject(i).getString("old_addr"));
                            shopphone = setEncoding(response.getJSONObject(i).getString("phone"));
                            shoplat = setEncoding(response.getJSONObject(i).getString("lat"));
                            shoplng = setEncoding(response.getJSONObject(i).getString("lng"));
                            
                        }
                    } catch (JSONException e) {
                        //json parsing error
                        e.printStackTrace();
                    }

                    switch (storenumber){
                        case 0:
//                            uploadconsts.frag0_shopid=Integer.parseInt(shopid);
//                            uploadconsts.frag0_shopname=shopname;
//                            uploadconsts.frag0_shopaddr=shopaddr;
//                            uploadconsts.frag0_shopphone=shopphone;
//                            uploadconsts.setlatlngFrag0(Double.parseDouble(shoplat), Double.parseDouble(shoplng));
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        servertest.execute();

    }

    /**
     * @GET food/searching
     * @param searchinput
     */
    public void getSearchUserInfoFromServer(final String searchinput){

        AsyncTask<Void, Void, Void> servertest = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=1&name="+searchinput+"&lat=37.587106&lng=127.029422";

                //String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.-=com:3000/food/searching?search=1&name=%EB%AA%AC%EC%8A%A4%ED%84%B0";

                final UploadConstants uploadconsts = new UploadConstants();

                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                JsonArrayRequest jsArrayRequest = new JsonArrayRequest(url, future,future);

                SingletonVolley.getInstance(getBaseContext()).addToRequestQueue(jsArrayRequest);

                String entirestring="";
                String shopid="";
                String shopname="";
                String shopaddr="";
                String shopphone="";
                String shoplat="";
                String shoplng="";

                try {
                    JSONArray response = future.get();

                    uploadconsts.searchresultarray=response;

                    //uploadsearchactivity.dataReceivedFromServer();

                    try {
                        entirestring = setEncoding(response.toString());

                        Log.d("genebe", "return data testing response.length(): " + response.length());
                        Log.d("genebe", "return data testing uploadconsts.searchresultarray.length(): " + uploadconsts.searchresultarray.length());
                        Log.d("genebe", "return data testing entire: " + entirestring);

                        for(int i=0; i< response.length(); i++) {

                            shopid = setEncoding(response.getJSONObject(i).getString("shopid"));
                            shopname = setEncoding(response.getJSONObject(i).getString("name"));
                            shopaddr = setEncoding(response.getJSONObject(i).getString("old_addr"));
                            shopphone = setEncoding(response.getJSONObject(i).getString("phone"));
                            shoplat = setEncoding(response.getJSONObject(i).getString("lat"));
                            shoplng = setEncoding(response.getJSONObject(i).getString("lng"));

//                            Log.d("genebe", "----------------------------------------------");
//                            Log.d("genebe", "return data testing shopid: " + shopid);
//                            Log.d("genebe", "return data testing shopname: " + shopname);
//                            Log.d("genebe", "return data testing shopaddr: " + shopaddr);
//                            Log.d("genebe", "return data testing shopphone: " + shopphone);
//                            Log.d("genebe", "return data testing shoplat: " + shoplat);
//                            Log.d("genebe", "return data testing shoplng: " + shoplng);
                        }
                    } catch (JSONException e) {
                        //json parsing error
                        e.printStackTrace();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        servertest.execute();

    }


    public static JsonObjectRequest drawPolyLineStartToEnd(final GoogleMap map, final LatLng start, final LatLng end) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,getDirectionUrl(start,end),null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.add(start);
                        try {
                            JSONArray jFeatures = response.getJSONArray("features");
                            JSONArray jCoordinates;
                            for (int i = 0; i < jFeatures.length(); i++)
                            {
                                if (jFeatures.getJSONObject(i).getJSONObject("geometry").getString("type").equals("LineString"))
                                {
                                    jCoordinates = jFeatures.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
                                    for (int j = 0; j < jCoordinates.length() - 1; j++)
                                    {
                                        polylineOptions.add(new LatLng(jCoordinates.getJSONArray(j).getDouble(1)
                                                ,jCoordinates.getJSONArray(j).getDouble(0)));
                                    }
                                    if (i == jFeatures.length() - 1)
                                    {
                                        polylineOptions.add(new LatLng(jCoordinates.getJSONArray(jCoordinates.length()-1).getDouble(1)
                                                ,jCoordinates.getJSONArray(jCoordinates.length()-1).getDouble(0)));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        polylineOptions.add(end);
                        polylineOptions.width(8);
                        polylineOptions.color(Color.parseColor("#C91140"));
                        map.addPolyline(polylineOptions);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("VolleyError",volleyError.toString());
                    }
                }
        );
        return jsonObjectRequest;

//        SingletonVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private static String getDirectionUrl(LatLng start, LatLng end)
    {
        String str_start_name = "startName=출발지";
        String str_end_name = "endName=도착지";
        String str_start = "startX=" + start.longitude + "&startY=" + start.latitude;
        String str_end = "endX=" + end.longitude + "&endY=" + end.latitude;
        String str_req_type = "reqCoordType=WGS84GEO";
        String str_res_type = "resCoordType=WGS84GEO";
        String str_version = "version=1";
        String appKey = "d2e5c839-bed4-3c5e-b40b-aed4805cbaad";
        String str_app_key = "appKey=" + appKey;
        String str_format = "format=JSON";
        String str_walk = "routes/pedestrian";
        String parameters = str_start_name + "&" + str_start + "&" + str_end_name + "&" + str_end + "&" + str_format + "&" + str_req_type + "&" + str_res_type + "&" + str_version + "&" + str_app_key;

        return "https://apis.skplanetx.com/tmap/" + str_walk + "/?" + parameters;
    }

    public LatLng getAverageLatLng(ArrayList<LatLng> latLngList) {
        double latSum = 0;
        double lngSum = 0;

        for(int i=0;i<latLngList.size();i++)
        {
            latSum += latLngList.get(i).latitude;
            lngSum += latLngList.get(i).longitude;
        }
        return  new LatLng(latSum/latLngList.size(), lngSum/latLngList.size());
    }


    // latlngbound쓰면 뻥나는 경우가
    public static LatLngBounds getLatLngMinMax(ArrayList<LatLng> latLngList) {
        double latMin = latLngList.get(0).latitude;
        double lngMin = latLngList.get(0).longitude;
        double latMax = latLngList.get(0).latitude;
        double lngMax = latLngList.get(0).longitude;

        for(int i=1;i<latLngList.size();i++)
        {
            if(latLngList.get(i).latitude < latMin)
                latMin = latLngList.get(i).latitude;
            else if(latLngList.get(i).latitude > latMax)
                latMax = latLngList.get(i).latitude;

            if(latLngList.get(i).longitude < lngMin)
                lngMin = latLngList.get(i).longitude;
            else if(latLngList.get(i).longitude > lngMax)
                lngMax = latLngList.get(i).longitude;
        }

        return new LatLngBounds(new LatLng(latMin,lngMin),new LatLng(latMax,lngMax));
    }
}
