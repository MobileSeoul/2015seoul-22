package com.example.genebe.Sec1;

/**
 김영규
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.genebe.GenebeBase;
import com.example.genebe.Http.FeedRequest;
import com.example.genebe.Model.Feed;
import com.example.genebe.Model.Feeds;
import com.example.genebe.R;
import com.example.genebe.SingletonLoginUser;
import com.example.genebe.SingletonVolley;
import com.example.genebe.app.AppController;
import com.example.genebe.card.CardDetailActivity;
import com.example.genebe.card.GenebeCardInformation;
import com.example.genebe.card.GenebeRecyclerViewAdapter;
import com.example.genebe.card.NewsFeedConstants;
import com.example.genebe.card.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Sec1NewsFeedFollowers extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final Integer SIZE_OF_LOADING = 6;
    private static final String LOG_TAG = Sec1NewsFeedFollowers.class.getSimpleName();

    private StaggeredGridLayoutManager sglm;

    private RecyclerView sec1Rv;
    private GenebeRecyclerViewAdapter sec1Adapter;

    private int cardIndex;
    private boolean mLockListView;
    private boolean mIsFirst;
    private ArrayList<Feed> feeds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feeds = new ArrayList<>();
        mLockListView = true;
        mIsFirst = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootViewFollow = inflater.inflate(R.layout.genebe_recyclerview, container, false);
        sec1Rv = (RecyclerView) rootViewFollow.findViewById(R.id.rv);
        sec1Rv.setHasFixedSize(true);
        sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        sec1Rv.setLayoutManager(sglm);
        sec1Adapter = new GenebeRecyclerViewAdapter(feeds);
        if(sec1Adapter!=null) {
            sec1Rv.setAdapter(sec1Adapter);
            getFeeds(feeds);
        }
        return rootViewFollow;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void getFeeds (final ArrayList<Feed> cards) {
        FeedRequest feedRequest = new FeedRequest("http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/newsfeed/followers_posts?userid="+AppController.getInstance().getLoginUserID(),
                Feeds.class,
                null,
                new Response.Listener<Feeds>() {
                    @Override
                    public void onResponse(Feeds response) {
                        cards.addAll(response.getFeed());
                        sec1Adapter.notifyDataSetChanged();
                        mLockListView = false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("error","reponse error");
                    }
                }
        );
        AppController.getInstance().addToRequestQueue(feedRequest);
    }

}