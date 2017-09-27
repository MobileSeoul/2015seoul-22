package com.example.genebe.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.bumptech.glide.Glide;
import com.example.genebe.GenebeBase;
import com.example.genebe.MainActivity;
import com.example.genebe.Model.Feed;
import com.example.genebe.R;
import com.example.genebe.Sec1.Store;
import com.example.genebe.SingletonVolley;
import com.example.genebe.app.AppController;
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
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CardDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String TAG = CardDetailActivity.class.getSimpleName();
    GenebeBase genebebase = new GenebeBase();
    private Map<Marker,Store> markerMapStore; //지금은 쓰지 않지만 마커에 store를 매칭시킬때 쓰자
    private ArrayList<LatLng> mLatLngs;
    Feed feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        setTitle(null);
        mLatLngs = new ArrayList<>();
        NewsFeedConstants.getInstance().stores.clear();
        feed = (Feed)getIntent().getSerializableExtra("FEED");

        // 상태바 배경을 액션바와 동일하게 하기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            int actionBarColor = Color.parseColor("#211f0b");
            tintManager.setStatusBarTintColor(actionBarColor);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.card_tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbarTitle = (TextView)findViewById(R.id.card_toolbar_title);
            toolbarTitle.setText(feed.getPostname());
        }

        ImageView imgCard = (ImageView) findViewById(R.id.card_detail_image); //하드코딩
        TextView txtTitle = (TextView) findViewById(R.id.card_detail_title);
        TextView txtDescription = (TextView) findViewById(R.id.card_detail_description);
        TextView txtTags = (TextView) findViewById(R.id.card_detail_tags);

        Glide.with(this).load(feed.getUploaderpic()).into(imgCard);
        txtTitle.setText(feed.getPostname());
        txtDescription.setText(feed.getReview());
        String tags = "";
        for(int i=0; i<feed.getHashtag().size();i++)
        {
            tags += "#"+feed.getHashtag().get(i)+" ";
        }
        txtTags.setText(tags);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.detail_action_close:
                Intent intentHome = new Intent(this, MainActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentHome);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_detail, menu);
        return true;
    }



    @Override
    public void onMapReady(final GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.586572, 127.029062),17));
        AsyncTask<Integer,Void,Integer> drawMapAsync = new AsyncTask<Integer, Void, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {

                for(int j=0;j<feed.getRoute().size();j++)
                {
                    if(feed.getRoute().get(j)!=0) {
                        Store store = new Store();

                        RequestFuture<JSONArray> future = RequestFuture.newFuture();

                        JsonArrayRequest shopArrayRequest = new JsonArrayRequest(getShopUrl(feed.getRoute().get(j)), future, future);
                        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(shopArrayRequest);
                        try {
                            JSONArray responseArray = future.get();
                            JSONObject shopObject = responseArray.getJSONObject(0);
                            store.shopID = shopObject.getInt("shopid");
                            store.address = setEncoding(shopObject.getString("old_addr"));
                            store.telephone = setEncoding(shopObject.getString("phone"));
                            store.storeName =setEncoding(shopObject.getString("name"));
                            store.latLng = new LatLng(shopObject.getDouble("lat"), shopObject.getDouble("lng"));
                            mLatLngs.add(store.latLng);

                        } catch (InterruptedException | ExecutionException | JSONException e) {
                            e.printStackTrace();
                        }

                        RequestFuture<JSONArray> future2 = RequestFuture.newFuture();
                        Log.d(TAG,getBindUrl(feed.getPostid(),
                                feed.getRoute().get(j)));
                        JsonArrayRequest bindArrayRequest = new JsonArrayRequest(getBindUrl(feed.getPostid(),
                                feed.getRoute().get(j)), future2, future2);
                        SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(bindArrayRequest);
                        try {
                            JSONArray responseArray = future2.get();
                            JSONObject bindObject = responseArray.getJSONObject(0);
                            store.storeReview = setEncoding(bindObject.getString("review"));
                            store.storeRate = bindObject.getInt("rate");
                        } catch (InterruptedException | ExecutionException | JSONException e) {
                            e.printStackTrace();
                        }
                        NewsFeedConstants.getInstance().stores.add(store);
                    }
                }

                return integers[0];
            }

            @Override
            protected void onPostExecute(final Integer integer) {
                for(int i=0; i<NewsFeedConstants.getInstance().stores.size();i++)
                {
                    Marker m = map.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                            .position(NewsFeedConstants.getInstance().stores.get(i).latLng)
                            .title("" + i));
                    //여기에 마커에 스토어 마칭시키는 코드 쓰자 markerMapStore.put(marker, store)
                }
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        NewsFeedConstants.getInstance().selectedStoreIndex = Integer.parseInt(marker.getTitle());
                        Intent intent = new Intent(CardDetailActivity.this, StoreInfoActivity.class);
                        intent.putExtra("FEED",feed);
                        startActivity(intent);
                        return true;
                    }
                });
                if(mLatLngs.size()==1)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLngs.get(0),17));
                else
                    map.moveCamera(CameraUpdateFactory.newLatLngBounds(genebebase.getLatLngMinMax(mLatLngs),150));

                for (int i = 0; i < NewsFeedConstants.getInstance().stores.size() - 1; i++) {
                    SingletonVolley.getInstance(getApplicationContext()).addToRequestQueue(genebebase.drawPolyLineStartToEnd(map,
                            NewsFeedConstants.getInstance().stores.get(i).latLng, NewsFeedConstants.getInstance().stores.get(i + 1).latLng));
                }

            }
        };
        drawMapAsync.execute(0);
    }

    private String getShopUrl (int shopId) {
        return "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=0&shopid="+shopId;
    }

    private String getBindUrl (int postId, int shopId) {
        return "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/binders?postid="+postId+"&shopid="+ shopId;
    }

    public String setEncoding(String beforeencoding){
        String encodedstring="";
        try {
            encodedstring=new String(beforeencoding.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedstring;
    }

}
