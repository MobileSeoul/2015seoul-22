package com.example.genebe;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yk on 2015-06-26.
 */
public class SingletonLoginUser {
    private static SingletonLoginUser mInstance;
    private int userid;
    private String username;
    private String profilepiclink;
    private ArrayList<Integer> following;
    private ArrayList<Integer> follower;
    private ArrayList<Integer> uploaded;
    private static Context mCtx;

    private SingletonLoginUser (final int userid, Context context) {
        mCtx = context;
        this.userid = userid;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getUrl(userid),
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            JSONArray followingArray = jsonObject.getJSONArray("following");
                            JSONArray followerArray = jsonObject.getJSONArray("follower");
                            JSONArray uploadedArray = jsonObject.getJSONArray("uploaded");
                            username = jsonObject.getString("username");
                            if(!jsonObject.isNull("profilepiclink")) {
                                profilepiclink =jsonObject.getString("profilepiclink");
                            }
                            following = new ArrayList<>();
                            if(!followingArray.isNull(0)) {
                                for (int i = 0; i < followingArray.length(); i++) {
                                    following.add(followingArray.getInt(i));
                                    Log.d("aaaloop", following.get(i).toString());
                                }
                            }
                            follower = new ArrayList<>();
                            if(!followerArray.isNull(0)) {
                                for (int i = 0; i < followerArray.length(); i++) {
                                    follower.add(followerArray.getInt(i));
                                }
                            }
                            uploaded = new ArrayList<>();
                            if(!uploadedArray.isNull(0)) {
                                for (int i = 0; i < uploadedArray.length(); i++) {
                                    uploaded.add(uploadedArray.getInt(i));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("VolleyError", volleyError.toString());
            }
        });
        SingletonVolley.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static synchronized SingletonLoginUser getInstance(int userid,Context context) {
        if (mInstance == null) {
            mInstance = new SingletonLoginUser(userid,context);
        }
        return mInstance;
    }

    private String getUrl(int userid) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/users/users?userid="+userid;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilepiclink() {
        return profilepiclink;
    }

    public ArrayList<Integer> getFollowing() {
        return following == null ? new ArrayList<Integer>() : following;
    }

    public ArrayList<Integer> getFollower() {
        return follower == null ? new ArrayList<Integer>() : follower;
    }

    public ArrayList<Integer> getUploaded() {
        return uploaded == null ? new ArrayList<Integer>() : uploaded;
    }
}
