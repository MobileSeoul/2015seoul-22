package com.example.genebe.upload;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.SingletonVolley;
import com.example.genebe.card.SearchRecyclerViewAdapter;
import com.example.genebe.card.SearchStoreLocationCardInformation;
import com.example.genebe.slidingtab.SlidingTabLayout;
import com.example.genebe.store.StoreInfoActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kyungrakpark on 15. 6. 26..
 */
public class UploadDrawRouteActivity extends AppCompatActivity implements OnMapReadyCallback {

    UploadConstants storename = new UploadConstants();
    GenebeBase genebebase = new GenebeBase();

    private ArrayList<LatLng> mLatLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_draw_route);
        setTitle(null);

        Toolbar toolbar; //툴바 선언


        toolbar = (Toolbar) findViewById(R.id.upload_tool_bar); //1. 검색 툴바
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //뒤로 가기 버튼 가져오는 부분

        UploadConstants.saveCompleteRouteLatLng();      //선택한 가게 위치 정보를 저장하는 곳

        mLatLngs = new ArrayList<>();

        Collections.addAll(mLatLngs, UploadConstants.uploadcompleteroute);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView storename0 = (TextView) findViewById(R.id.draw_route_store0);
        TextView storename1 = (TextView) findViewById(R.id.draw_route_store1);
        TextView storename2 = (TextView) findViewById(R.id.draw_route_store2);
        TextView storename3 = (TextView) findViewById(R.id.draw_route_store3);

        if (UploadConstants.frag0_shopname != "01") {
            storename0.setText(UploadConstants.frag0_shopname);
            storename0.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        }

        if (UploadConstants.frag1_shopname != "02") {
            storename1.setText(UploadConstants.frag1_shopname);
            storename1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        }

        if (UploadConstants.frag2_shopname != "03") {
            storename2.setText(UploadConstants.frag2_shopname);
            storename2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        }

        if (UploadConstants.frag3_shopname != "04") {
            storename3.setText(UploadConstants.frag3_shopname);
            storename3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        }


    }

    //툴바 불러오기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.upload_toolbar_menu, menu);
        return true;
    }


    //뒤로 가기 버튼 실행하여 홈으로 돌아가는 작업
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_back:
                Intent uploadsummary = new Intent(UploadDrawRouteActivity.this, UploadSummaryActivity.class);
                startActivity(uploadsummary);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        Log.d("genebe", "onMapReady size: "+mLatLngs.size());

        for (int i = 0; i < mLatLngs.size(); i++) {
            map.addMarker(new MarkerOptions()
                    // 커스텀 마커 이미지 설정
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                    .position(mLatLngs.get(i))
                    .title(UploadConstants.getShopName(i)));

            Log.d("genebe", "onMapReady shopname:" + UploadConstants.getShopName(i));
            Log.d("genebe","onMapReady mlatlng:"+mLatLngs.get(i));
        }

        AsyncTask<Integer, Void, Integer> asyncTask = new AsyncTask<Integer, Void, Integer>() {

            @Override
            protected Integer doInBackground(Integer... integers) {
                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(genebebase.getLatLngMinMax(mLatLngs), 200));

            }
        };

        asyncTask.execute();


        for (int i = 0; i < mLatLngs.size() - 1; i++) {
            SingletonVolley.getInstance(this).addToRequestQueue(genebebase.drawPolyLineStartToEnd(map, mLatLngs.get(i), mLatLngs.get(i + 1)));
        }
    }
}
