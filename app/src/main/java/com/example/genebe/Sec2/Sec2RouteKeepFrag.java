package com.example.genebe.Sec2;

/**
 * Created by 사하앍 on 2015-05-20.
 */

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.LoginFilter;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.Sec1.Store;
import com.example.genebe.SingletonVolley;
import com.example.genebe.card.CardDetailActivity;
import com.example.genebe.card.GenebeCardInformation;
import com.example.genebe.card.GenebeRecyclerViewAdapter;
import com.example.genebe.card.NewsFeedConstants;
import com.example.genebe.card.RecyclerItemClickListener;
import com.example.genebe.pic_crop.RoundImage;
import com.example.genebe.store.StoreInfoActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Sec2RouteKeepFrag extends Fragment implements OnMapReadyCallback{

    private MapFragment mMapFragment;
    private GoogleMap mMap;
    private static View rootViewRandom;
    ArrayList<LatLng> mLatLngs;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Sec2RouteKeepFrag newInstance(int sectionNumber) {
        Sec2RouteKeepFrag fragment = new Sec2RouteKeepFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootViewRandom != null) {
            ViewGroup parent = (ViewGroup) rootViewRandom.getParent();
            if (parent != null)
                parent.removeView(rootViewRandom);
        }

        try {
            rootViewRandom = inflater.inflate(R.layout.fragment_sec1_random, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        return rootViewRandom;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mMapFragment == null) {

            mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
            mMapFragment.getMapAsync(this);
        }
        else {
            mMap.clear();
            new DrawMapAsync().execute(0);

        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.586572, 127.029062), 17));
        new DrawMapAsync().execute(0);

    }

    private class DrawMapAsync extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {

            mLatLngs = new ArrayList<>();
            NewsFeedConstants.getInstance().stores.clear();
            GenebeCardInformation cardinfo = new GenebeCardInformation();
            int isImg1 = 0;
            int isImg2 = 0;
            int isImg3 = 0;
            int isImg4 = 0;
            RequestFuture<JSONArray> future2 = RequestFuture.newFuture();
            JsonArrayRequest postRequest = new JsonArrayRequest(getPostsUrl(4),
                    future2,future2);
            SingletonVolley.getInstance(NewsFeedConstants.getInstance().ctx).addToRequestQueue(postRequest);
            try {
                JSONArray responseArray2 = future2.get();
                JSONObject postObject;
                JSONArray hashtagArray;
                JSONArray shopIdArray;

                postObject = responseArray2.getJSONObject(0);
                cardinfo.postId = postObject.getInt("postid");
                //      cardinfo.writer_id = hashMapUploadedidAndNamePiclink.get(cardinfo.postId).get(0);
                cardinfo.route_content = GenebeBase.setEncoding(postObject.getString("review"));
                cardinfo.route_name = GenebeBase.setEncoding(postObject.getString("postname"));
                hashtagArray = postObject.getJSONArray("hashtag");
                for(int j=0;j<hashtagArray.length();j++)
                    cardinfo.tags.add(GenebeBase.setEncoding(hashtagArray.getString(j)));
                shopIdArray = postObject.getJSONArray("route");
                for(int j=0;j<shopIdArray.length();j++)
                    cardinfo.shopIds.add(shopIdArray.getInt(j));
                isImg1 = postObject.getInt("img1");
                isImg2 = postObject.getInt("img2");
                isImg3 = postObject.getInt("img3");
                isImg4 = postObject.getInt("img4");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(isImg1 == 1)
                shopImgParsing(cardinfo,0);
            else
                cardinfo.photoIds.add("http://");

            if(isImg2 == 1)
                shopImgParsing(cardinfo,1);
            else
                cardinfo.photoIds.add("http://");

            if(isImg3 == 1)
                shopImgParsing(cardinfo,2);
            else
                cardinfo.photoIds.add("http://");

            if(isImg4 == 1)
                shopImgParsing(cardinfo,3);
            else
                cardinfo.photoIds.add("http://");

            NewsFeedConstants.getInstance().cardinfo  = cardinfo;

            for(int j=0;j<NewsFeedConstants.getInstance().cardinfo.shopIds.size();j++)
            {
                if(NewsFeedConstants.getInstance().cardinfo.shopIds.get(j)!=0) {
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
                        mLatLngs.add(store.latLng);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestFuture<JSONArray> future3 = RequestFuture.newFuture();

                    JsonArrayRequest bindArrayRequest = new JsonArrayRequest(getBindUrl(NewsFeedConstants.getInstance().cardinfo.postId,
                            NewsFeedConstants.getInstance().cardinfo.shopIds.get(j)), future3, future3);
                    SingletonVolley.getInstance(NewsFeedConstants.getInstance().ctx).addToRequestQueue(bindArrayRequest);
                    try {
                        JSONArray responseArray = future3.get();
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

        @Override
        protected void onPostExecute(Integer integer) {

            ImageView imgCard = (ImageView) rootViewRandom.findViewById(R.id.card_detail_image); //하드코딩
            TextView txtTitle = (TextView) rootViewRandom.findViewById(R.id.card_detail_title);
            TextView txtDescription = (TextView) rootViewRandom.findViewById(R.id.card_detail_description);
            TextView txtTags = (TextView) rootViewRandom.findViewById(R.id.card_detail_tags);

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.image);
            RoundImage roundedImage = new RoundImage(bm);
            imgCard.setImageDrawable(roundedImage);

            txtTitle.setText(NewsFeedConstants.getInstance().cardinfo.route_name);
            txtDescription.setText(NewsFeedConstants.getInstance().cardinfo.route_content);
            String tags = "";
            for(int i=0; i<NewsFeedConstants.getInstance().cardinfo.tags.size();i++)
            {
                tags += "#"+NewsFeedConstants.getInstance().cardinfo.tags.get(i)+" ";
            }
            txtTags.setText(tags);

            for(int i=0; i<NewsFeedConstants.getInstance().stores.size();i++)
            {
                Log.d("store's name",""+i);
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                        .position(NewsFeedConstants.getInstance().stores.get(i).latLng)
                        .title("" + i));
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        NewsFeedConstants.getInstance().selectedStoreIndex = Integer.parseInt(marker.getTitle());
                        Intent intent = new Intent(rootViewRandom.getContext(), StoreInfoActivity.class);
                        startActivity(intent);
                        return true;
                    }
                });
            }

            if(mLatLngs.size()==1)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLngs.get(0),17));
            else
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(GenebeBase.getLatLngMinMax(mLatLngs),150));

            for (int i = 0; i < NewsFeedConstants.getInstance().stores.size() - 1; i++) {
                SingletonVolley.getInstance(NewsFeedConstants.getInstance().ctx).addToRequestQueue(GenebeBase.drawPolyLineStartToEnd(mMap,
                        NewsFeedConstants.getInstance().stores.get(i).latLng, NewsFeedConstants.getInstance().stores.get(i + 1).latLng));
            }
            mLatLngs = null;
        }
    }

    private void shopImgParsing (GenebeCardInformation cardinfo, int shopIndex){
        JSONArray imgUrlArray;
        RequestFuture<JSONArray> img1Future = RequestFuture.newFuture();
        Log.d("url", getBindUrl(cardinfo.postId, cardinfo.shopIds.get(shopIndex)));
        JsonArrayRequest img1JsonArrayRequest = new JsonArrayRequest(getBindUrl(cardinfo.postId,cardinfo.shopIds.get(shopIndex)),
                //테스트용 JsonArrayRequest img1JsonArrayRequest = new JsonArrayRequest(getTestBindUrl(),
                img1Future,img1Future);
        SingletonVolley.getInstance(NewsFeedConstants.getInstance().ctx).addToRequestQueue(img1JsonArrayRequest);
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

    private Integer getRandomPostId (int index) {
        return index;
    }

    private String getPostsUrl (int postId) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/posts?postid="+ postId;
    }

    private String getBindUrl (int postid, int shopid) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/binders?postid="+postid+"&shopid="+shopid;
    }

    private String getShopUrl (int shopId) {
        return "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=0&shopid="+shopId;
    }

}