package com.example.genebe.card;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.genebe.Sec1.Poster;
import com.example.genebe.Sec1.Store;
import com.example.genebe.SingletonVolley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by sumin on 2015-06-23.
 */
public class StoreJsonHelper extends AsyncTask<ArrayList<Integer>, Void, Void> {
    public Activity activity;
    public ArrayList<Store> stores = new ArrayList<>();
    public ArrayList<Integer> ID;
    public Store binderInfo;

    public StoreJsonHelper(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(final ArrayList<Integer>... ID)
    {
        this.ID = ID[0];
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getStoreUrl(ID[0].get(0)),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                Store store = new Store();
                                try {
                                    JSONObject jObject = response.getJSONObject(i);
                                    try {
                                        int shopID = ID[0].get(0);
                                        store.setShopID(shopID);

                                        int postID = ID[0].get(1);
                                        store.setPostID(postID);

                                        String name = jObject.getString("name");
                                        name = new String(name.getBytes("895_1"), "utf-8");
                                        store.setStoreName(name);

                                        String address = jObject.getString("new_addr");
                                        address = new String(address.getBytes("8895_1"), "utf-8");
                                        store.setAddress(address);

                                        String phone = jObject.getString("phone");
                                        store.setTelephone(phone);

                                        LatLng position = new LatLng(jObject.getDouble("lat"),jObject.getDouble("lng"));
                                        store.setPosition(position);

                                        Store binderInfo = getBinderInfo();
                                        store.setPhotoList(binderInfo.getPhotoList());
                                        store.setImgCount(binderInfo.getImgCount());
                                        store.setStoreReview(binderInfo.getStoreReview());
                                        store.setStoreRate(binderInfo.getStoreRate());
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                stores.add(store);
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("VolleyError", volleyError.toString());
                    }
                }
        );
        SingletonVolley.getInstance(activity).addToRequestQueue(jsonArrayRequest);

        return null;
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();
    }

    //sumin
    private String getStoreUrl(int shopID)
    {
        return "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/test/searching?search=0&shopid="+shopID;
    }
    private String getBinderUrl(ArrayList<Integer> ID)
    {
        String postID = ID.get(1).toString();
        String shopID = ID.get(0).toString();
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/binders?postid="+postID+"&shopid="+shopID;
    }

    private Store getBinderInfo()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getBinderUrl(ID),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            binderInfo = new Store();
                            try {
                                JSONObject jObject = response.getJSONObject(i);
                                try {
                                    JSONArray photoArray = jObject.getJSONArray("img");
                                    ArrayList<Integer> photoList = new ArrayList<>();
                                    for(int j=0; j<photoArray.length(); j++)
                                    {
                                        photoList.add(photoArray.getInt(j));
                                    }
                                 //   binderInfo.setPhotoList(photoList);

                                    int imgCount = jObject.getInt("imgcount");
                                    binderInfo.setImgCount(imgCount);

                                    String storeReview = jObject.getString("review");
                                    storeReview = new String(storeReview.getBytes("895_1"), "utf-8");
                                    binderInfo.setStoreReview(storeReview);

                                    int storeRate = jObject.getInt("rate");
                                    binderInfo.setStoreRate(storeRate);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("VolleyError", volleyError.toString());
                    }
                }
        );
        SingletonVolley.getInstance(activity).addToRequestQueue(jsonArrayRequest);
        return binderInfo;
    }

}
