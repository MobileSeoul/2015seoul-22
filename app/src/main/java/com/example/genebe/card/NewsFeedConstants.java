package com.example.genebe.card;

import android.content.Context;
import android.util.Log;

import com.example.genebe.Sec1.Store;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by yk on 2015-06-29.
 */
public class NewsFeedConstants {

    private static NewsFeedConstants mInstance;

    public JSONArray shopInfo;
    public GenebeCardInformation cardinfo;
    public int selectedStoreIndex;
    public  ArrayList<Store> stores;
    public  Context ctx;

    private NewsFeedConstants() {
        if(stores==null)
        stores = new ArrayList<>();
    }

    public static synchronized NewsFeedConstants getInstance() {
        if (mInstance == null) {
            mInstance = new NewsFeedConstants();
            Log.d("newsconst", "Making singleton newsconst complete.");
        }
        return mInstance;
    }

    public void setContext(Context context) {
        ctx = context;
    }

    public static String setEncoding(String beforeencoding){
        String encodedstring="";

        try {
            encodedstring=new String(beforeencoding.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedstring;
    }

}
