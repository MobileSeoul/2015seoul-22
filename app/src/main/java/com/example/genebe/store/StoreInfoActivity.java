package com.example.genebe.store;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.genebe.MainActivity;
import com.example.genebe.Model.Feed;
import com.example.genebe.R;
import com.example.genebe.card.GenebeCardInformation;
import com.example.genebe.card.NewsFeedConstants;
import com.example.genebe.slidingtab.SlidingTabLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.HashMap;

/**
 * Created by 사하앍 on 2015-05-23.
 */
public class StoreInfoActivity extends AppCompatActivity implements StoreInfoFrag.OnDetailClickedListener{

    private int currentPosition=0;
    private SectionsPagerAdapter adapterViewPager; //페이지 어뎁터 선언
    private GenebeCardInformation mCardInfo;
    private Feed feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storeinfo); //1. 바탕 화면
        setTitle(null);
        currentPosition = NewsFeedConstants.getInstance().selectedStoreIndex;
        feed = (Feed)getIntent().getSerializableExtra("FEED");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            int actionBarColor = Color.parseColor("#211f0b");
            tintManager.setStatusBarTintColor(actionBarColor);
        }

        Toolbar toolbar; //툴바 선언
        ViewPager vpPager; //뷰페이져 선언
        SlidingTabLayout slidingTabLayout; //슬라이딩 탭 바 선언

        toolbar = (Toolbar) findViewById(R.id.store_tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() !=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }

            TextView toolbarTitle = (TextView)findViewById(R.id.card_toolbar_title);
            toolbarTitle.setText(feed.getPostname());
        }

        vpPager = (ViewPager) findViewById(R.id.store_view_pager); // 3. 뷰페이져
        adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager(), NewsFeedConstants.getInstance().stores.size());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOffscreenPageLimit(4);

        /*슬라이딩탭바 테마와 컨텐츠 구현 by jhh*/
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.store_sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        slidingTabLayout.setCustomTabView(R.layout.activity_storeinfo_slidingtab_layout, R.id.storeSlidingtabText);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.ColorPrimary);
            }
        });
        slidingTabLayout.setViewPager(vpPager);

        vpPager.setCurrentItem(currentPosition);
    }

    public void onDetailClicked(View view){
        Fragment fragment = adapterViewPager.getCurrentFragment(currentPosition);
        if(fragment.getView().findViewById(R.id.store_detail).getVisibility()==View.VISIBLE) {
            fragment.getView().findViewById(R.id.store_detail).setVisibility(View.INVISIBLE);
            fragment.getView().findViewById(R.id.store_review).setVisibility(View.VISIBLE);
        } else {
            fragment.getView().findViewById(R.id.store_review).setVisibility(View.INVISIBLE);
            fragment.getView().findViewById(R.id.store_detail).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_close:
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
        inflater.inflate(R.menu.storeinfo_toolbar_menu, menu);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private int NUM_ITEMS = 0;
        HashMap<Integer,Fragment> fragmentHashMap = new HashMap<>();
        public SectionsPagerAdapter(FragmentManager fm, int num_items) {
            super(fm);
            NUM_ITEMS = num_items;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return StoreInfoFrag.newInstance(position,feed);
                case 1:
                    return StoreInfoFrag.newInstance(position,feed);
                case 2:
                    return StoreInfoFrag.newInstance(position,feed);
                case 3:
                    return StoreInfoFrag.newInstance(position,feed);
                default:
                    return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            Fragment fragment = (Fragment)super.instantiateItem(container, position);
            fragmentHashMap.put(position,fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            fragmentHashMap.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return NewsFeedConstants.getInstance().stores.get(0).storeName;
                case 1:
                    return NewsFeedConstants.getInstance().stores.get(1).storeName;
                case 2:
                    return NewsFeedConstants.getInstance().stores.get(2).storeName;
                case 3:
                    return NewsFeedConstants.getInstance().stores.get(3).storeName;
            }
            return null;
        }

        public Fragment getCurrentFragment(int position)
        {
            return fragmentHashMap.get(position);
        }
    }
}
