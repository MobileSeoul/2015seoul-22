package com.example.genebe.upload;

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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by 사하앍 on 2015-05-23.
 *
 * KR=Jack
 */
public class UploadSearchActivity extends AppCompatActivity {
    //sqz
    SearchView searchView;

    private Button storeselectbtn;
    private Button findgps;
    UploadConstants uploadconsts = new UploadConstants();
    GenebeBase genbebase = new GenebeBase();

    SupportMapFragment mapFragment;
    GoogleMap gMap;

    SectionsPagerAdapter adapterViewPager; //페이지 어뎁터 선언
    ViewPager vpPager; //뷰페이져 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_search);

        Toolbar toolbar; //툴바 선언

        toolbar = (Toolbar) findViewById(R.id.search_tool_bar); //1. 검색 툴바
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //뒤로 가기 버튼 가져오는 부분

        vpPager = (ViewPager) findViewById(R.id.upload_search_main_view_pager); // 3. 뷰페이져
        adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        searchView = (SearchView) findViewById(R.id.upload_search_view); //검색 입력 위한 부분

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        gMap = mapFragment.getMap();
        gMap.clear();

        LatLng latlngtest = new LatLng(37.587106, 127.029422);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlngtest, 17);
        gMap.moveCamera(update);

        Marker newmarker = gMap.addMarker(new MarkerOptions().position(latlngtest).title("현재 위치").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));

//        findgps = (Button) findViewById(R.id.findgps);
//        findgps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //GenebeBase genebebasetest = new GenebeBase();
//                //Log.d("genebe", "genebebase testing: " + genebebasetest.getLocation());
//
//            }
//        });

    }

    //툴바 불러오기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_toolbar_menu, menu);

        return true;
    }

    //뒤로 가기 버튼 실행하여 홈으로 돌아가는 작업
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.search_action_button:
                Log.d("genebe", "검색 버튼 실행");
                String searchstorename = searchView.getQuery().toString();
                int searchstorenamelength = searchstorename.length();

                Log.d("genebe", "입력값 확인 : "+searchstorename);
                Log.d("genebe", "입력값 길이 확인 : " + searchstorenamelength);

                if(searchstorenamelength<1){
                    Toast.makeText(getApplicationContext(),"그그....그러지마", Toast.LENGTH_LONG).show();
                }else {
                    genbebase.getSearchStoreInfoFromServer(uploadconsts.currentworkingfrag, searchstorename);
                };

                vpPager = (ViewPager) findViewById(R.id.upload_search_main_view_pager); // 3. 뷰페이져
                adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
                vpPager.setAdapter(adapterViewPager);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 1;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return UploadServerSearchFrag.newInstance(position);
        }
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.title_Notification);
        }
    }

}
