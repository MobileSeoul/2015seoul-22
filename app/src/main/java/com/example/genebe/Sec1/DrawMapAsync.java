package com.example.genebe.Sec1;

import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.genebe.SingletonVolley;
import com.example.genebe.card.NewsFeedConstants;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by yk on 2015-08-21.
 */
public class DrawMapAsync extends AsyncTask<Integer, Void, Integer> {

    public ArrayList<LatLng> latLngs;

    @Override
    protected Integer doInBackground(Integer... integers) {
        latLngs = new ArrayList<>();
        for (int j = 0; j < NewsFeedConstants.getInstance().cardinfo.shopIds.size(); j++) {
            if (NewsFeedConstants.getInstance().cardinfo.shopIds.get(j) != 0) {
                Store store = new Store();

                RequestFuture<JSONArray> future = RequestFuture.newFuture();

                JsonArrayRequest shopArrayRequest = new JsonArrayRequest(getShopUrl(NewsFeedConstants.getInstance().cardinfo.shopIds.get(j)), future, future);
                SingletonVolley.getInstance(NewsFeedConstants.getInstance().ctx).addToRequestQueue(shopArrayRequest);
                try {
                    JSONArray responseArray = future.get();
                    JSONObject shopObject = responseArray.getJSONObject(0);
                    store.shopID = shopObject.getInt("shopid");
                    store.address = NewsFeedConstants.setEncoding(shopObject.getString("old_addr"));
                    store.telephone = NewsFeedConstants.setEncoding(shopObject.getString("phone"));
                    store.storeName = NewsFeedConstants.setEncoding(shopObject.getString("name"));
                    store.latLng = new LatLng(shopObject.getDouble("lat"), shopObject.getDouble("lng"));
                    latLngs.add(store.latLng);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestFuture<JSONArray> future2 = RequestFuture.newFuture();

                JsonArrayRequest bindArrayRequest = new JsonArrayRequest(getBindUrl(NewsFeedConstants.getInstance().cardinfo.postId,
                        NewsFeedConstants.getInstance().cardinfo.shopIds.get(j)), future2, future2);
                SingletonVolley.getInstance(NewsFeedConstants.getInstance().ctx).addToRequestQueue(bindArrayRequest);
                try {
                    JSONArray responseArray = future2.get();
                    Log.d("aaaaa", "post: " + NewsFeedConstants.getInstance().cardinfo.postId + "shop: " + NewsFeedConstants.getInstance().cardinfo.shopIds.get(j) + responseArray.toString());
                    JSONObject bindObject = responseArray.getJSONObject(0);
                    store.storeReview = NewsFeedConstants.setEncoding(bindObject.getString("review"));
                    store.storeRate = bindObject.getInt("rate");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NewsFeedConstants.getInstance().stores.add(store);
            }
        }
        return integers[0];
    }

    private String getBindUrl (int postid, int shopid) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/binders?postid="+postid+"&shopid="+shopid;
    }

    private String getShopUrl (int shopId) {
        return "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=0&shopid=" + shopId;
    }
}


