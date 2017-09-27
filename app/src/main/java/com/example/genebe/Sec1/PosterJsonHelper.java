package com.example.genebe.Sec1;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.genebe.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by sumin on 2015-06-23.
 */
public class PosterJsonHelper extends AsyncTask<Void, Void, Void>{
    public Activity activity;
    public ArrayList<Poster> posters = new ArrayList<>();
    public Poster userInfo;

    public PosterJsonHelper(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getPosterUrl(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            Poster poster = new Poster();
                            try
                            {
                                JSONObject jObject = response.getJSONObject(i);
                                try
                                {
                                    int postID = jObject.getInt("postid");
                                    poster.setPostID(postID);

                                    int uploaderID = jObject.getInt("uploader");
                                    poster.setUploaderID(uploaderID);

                                    //Poster userInfo = getUserInfo(uploaderID);

                                    //poster.setUploaderName(userInfo.getUploaderName());
                                    //poster.setProfilePic(userInfo.getProfilePic());

                                    String routeReview = jObject.getString("review");
                                    routeReview = new String(routeReview.getBytes("8859_1"),"utf-8");
                                    poster.setRouteReview(routeReview);

                                    JSONArray hashTagArray = jObject.getJSONArray("hashtag");
                                    ArrayList<String> hashTagList = new ArrayList<>();
                                    for(int j=0; j<hashTagArray.length(); j++)
                                    {
                                        hashTagList.add(hashTagArray.getString(j));
                                    }
                                    poster.setHashTagList(hashTagList);

                                    JSONArray storeArray = jObject.getJSONArray("route");
                                    ArrayList<Integer> storeList = new ArrayList<>();
                                    for(int j=0; j<storeArray.length(); j++)
                                    {
                                        storeList.add(storeArray.getInt(j));
                                    }
                                    poster.setStoreList(storeList);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                            posters.add(poster);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("VolleyError",volleyError.toString());
                    }
                }
        );
        SingletonVolley.getInstance(activity).addToRequestQueue(jsonArrayRequest);

        return null;
    }

    //sumin
    private String getPosterUrl()
    {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/posts?postid=1";
    }
    private String getUserUrl(int userID)
    {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/users/users?userid="+userID;
    }
    private Poster getUserInfo(int userID)
    {
        //final JSONObject profilePicLink = new JSONObject();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getUserUrl(userID),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            userInfo = new Poster();
                            try
                            {
                                JSONObject jObject = response.getJSONObject(i);
                                userInfo.setProfilePic(jObject.getString("profilepiclink"));
                                userInfo.setUploaderName("username");//jObject.getString("username"));
                            }catch(JSONException e){
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
        /*try {
            return profilePicLink.getString("profile");
        }catch (JSONException e){
            e.printStackTrace();
        }
        return "";*/
        return userInfo;
    }
}
