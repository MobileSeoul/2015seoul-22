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


public class Sec1NewsFeedRandom extends Fragment {

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



        /*
        sec1Rv.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        NewsFeedConstants.getInstance().cardinfo  = (GenebeCardInformation) view.getTag();
                        Intent intentSubActivity = new Intent(view.getContext(), CardDetailActivity.class);
                        startActivity(intentSubActivity);
                    }
                }));
        */
        //sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //sec1Rv.setLayoutManager(sglm);
        /*
        sec1Rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = sglm.getItemCount();

                if(recyclerView.canScrollVertically(1) && totalItemCount != 0 && mLockListView == false) {
                    if(uploadedsList.size() > cardIndex) {
                        mLockListView = true;
                        getUploadedInfo(cards);
                    }
                }

            }
        });*/
    }

    public void getFeeds (final ArrayList<Feed> cards) {
        FeedRequest feedRequest = new FeedRequest("http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/newsfeed/hot_posts?userid="+AppController.getInstance().getLoginUserID(),
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


    /*
    public class AsyncInitHttpTask extends AsyncTask<Void, Void, ArrayList<GenebeCardInformation>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList doInBackground(Void... voids) {
            ArrayList<GenebeCardInformation> cards = new ArrayList<>();
            RequestFuture<JSONArray> future = RequestFuture.newFuture();
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getUploadedsUrl(SingletonLoginUser.getInstance(0,getActivity()).getUserid()),
                    future,future);
            SingletonVolley.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
            try {
                JSONArray responseArray = future.get();
                JSONObject userObject;
                JSONArray uploaded;
                for(int i=0;i<responseArray.length();i++) {

                    userObject = responseArray.getJSONObject(i);
                    ArrayList<String> nameAndPic = new ArrayList<>();
                    nameAndPic.add(0, userObject.getString("username"));

                    if(!userObject.isNull("profilepiclink"))
                        nameAndPic.add(1,userObject.getString("profilepiclink"));
                    else
                        nameAndPic.add(1,"no url");
                    uploaded = userObject.getJSONArray("uploaded");

                    for(int j=0;j<uploaded.length();j++) {
                        uploadedsList.add(uploaded.getInt(j));
                        hashMapUploadedidAndNamePiclink.put(uploaded.getInt(j),nameAndPic);
                    }

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // 내림차순 정렬
            Collections.sort(uploadedsList, new Comparator<Integer>() {
                @Override
                public int compare(Integer integer, Integer integer2) {
                    return integer > integer2 ? -1 : integer < integer2 ? 1 : 0;
                }
            });

            if(uploadedsList.size()<SIZE_OF_LOADING+1)
            {
                for(int i=0;i<uploadedsList.size();i++)
                {
                    getUploadedInfo(cards,i);
                }
                cardIndex = uploadedsList.size();
            }
            else {
                for(int i=0;i<SIZE_OF_LOADING;i++)
                {
                    getUploadedInfo(cards,i);
                }
                cardIndex = SIZE_OF_LOADING;
            }
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            persons.addAll(result);
            sec1Adapter.notifyDataSetChanged();
            mLockListView = false;
        }
    }

    public class AsyncScrollHttpTask extends AsyncTask<Void, Void, ArrayList<GenebeCardInformation>> {

        @Override
        protected ArrayList<GenebeCardInformation> doInBackground(Void... voids) {
            ArrayList<GenebeCardInformation> cards = new ArrayList<>();
            if(uploadedsList.size() - cardIndex > SIZE_OF_LOADING) {
                for(int i=cardIndex;i<cardIndex+SIZE_OF_LOADING;i++) {
                    getUploadedInfo(cards,i);
                }
                cardIndex += SIZE_OF_LOADING;
            }
            else if(uploadedsList.size() > cardIndex) {
                for (int i = cardIndex; i < uploadedsList.size(); i++) {
                    getUploadedInfo(cards, i);
                }
                cardIndex = uploadedsList.size();
            }
            return cards;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            persons.addAll(result);
            sec1Adapter.notifyDataSetChanged();
            mLockListView = false;
        }
    }




    public void shopImgParsing (GenebeCardInformation cardinfo, int shopIndex){
        JSONArray imgUrlArray;
        RequestFuture<JSONArray> img1Future = RequestFuture.newFuture();
        Log.d("url",getBindUrl(cardinfo.postId,cardinfo.shopIds.get(shopIndex)));
        JsonArrayRequest img1JsonArrayRequest = new JsonArrayRequest(getBindUrl(cardinfo.postId,cardinfo.shopIds.get(shopIndex)),
        //테스트용 JsonArrayRequest img1JsonArrayRequest = new JsonArrayRequest(getTestBindUrl(),
                img1Future,img1Future);
        SingletonVolley.getInstance(getActivity()).addToRequestQueue(img1JsonArrayRequest);
        try {
            JSONArray imgArrayResponse = img1Future.get();
            JSONObject jsonObject = imgArrayResponse.getJSONObject(0);
            imgUrlArray = jsonObject.getJSONArray("img");
            cardinfo.photoIds.add(imgUrlArray.getString(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getUploadedsUrl (int userid) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/users/followingposts?userid="+userid;
    }

    private String getPostsUrl (int uploadedid) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/posts?postid="+uploadedid;
    }

    private String getTestBindUrl () {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/binders?postid=1&shopid=2";
    }

    private String getBindUrl (int postid, int shopid) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/binders?postid="+postid+"&shopid="+shopid;
    }
    */
}