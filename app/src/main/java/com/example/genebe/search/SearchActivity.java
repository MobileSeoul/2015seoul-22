package com.example.genebe.search;

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
import android.widget.SearchView;

import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.slidingtab.SlidingTabLayout;
import com.example.genebe.upload.UploadServerSearchFrag;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchActivity extends AppCompatActivity {

    SearchView searchView;

    ViewPager vpPager; //뷰페이져 선언
    SectionsPagerAdapter adapterViewPager; //페이지 어뎁터 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        setTitle(null);

        Toolbar toolbar; //툴바 선언

        SlidingTabLayout slidingTabLayout; //슬라이딩 탭 바 선언

        toolbar = (Toolbar) findViewById(R.id.search_tool_bar); //1. 검색 툴바

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() !=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                // 여기에 화살표 아이콘 설정
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }
        }
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.upload_search_view); //검색 입력 위한 부분

        vpPager = (ViewPager) findViewById(R.id.search_view_pager); // 2. 뷰페이져
        adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOffscreenPageLimit(3);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.search_sliding_tabs); // 3. 슬라이딩 탭 바
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.search_slidingtab_layout, R.id.searchSlidingtabText);
        // Setting Custom Color for the Scroll bar indicator of the Tab View
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.SearchtabsScrollColor);
            }
        });
        slidingTabLayout.setViewPager(vpPager);
    }

    //뒤로 가기 버튼 실행하여 홈으로 돌아가는 작업
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.search_action_button:
                Log.d("genebe", "Search Activity 검색 버튼 실행");
                String searchinput = searchView.getQuery().toString();
                Log.d("genebe", "searchstorename: "+searchinput);
                Log.d("genebe", "current item: " + vpPager.getCurrentItem());

//                GenebeBase genebebase = new GenebeBase();

                //추후 작업 예정
                //genebebase.getSearchUserInfoFromServer(searchinput);
//
//                vpPager = (ViewPager) findViewById(R.id.search_view_pager);
//                adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
//                vpPager.setAdapter(adapterViewPager);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    //툴바 불러오기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_toolbar_menu, menu);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 3;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Sec1PersonFrag.newInstance(position);
                case 1:
                    return Sec2TagFrag.newInstance(position);
                case 2:
                    return Sec3LocationFrag.newInstance(position);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.search_by_Person);
                case 1:
                    return getString(R.string.search_by_Tag);
                case 2:
                    return getString(R.string.search_by_Location);
            }
            return null;
        }
    }
}
