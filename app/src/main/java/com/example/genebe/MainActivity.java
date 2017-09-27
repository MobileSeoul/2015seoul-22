package com.example.genebe;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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

import com.example.genebe.Sec1.Sec1NewsFeedParentFrag;
import com.example.genebe.Sec2.Sec2RouteKeepFrag;
import com.example.genebe.Sec3.Sec3NotificationFrag;
import com.example.genebe.Sec4.Sec4DialogFragmentBoardAdd;
import com.example.genebe.Sec4.Sec4MyPageFrag;
import com.example.genebe.app.AppController;
import com.example.genebe.card.NewsFeedConstants;
import com.example.genebe.search.SearchActivity;
import com.example.genebe.slidingtab.SlidingTabLayout;
import com.example.genebe.upload.UploadActivity;
import com.example.genebe.upload.UploadConstants;
import com.readystatesoftware.systembartint.SystemBarTintManager;


public class MainActivity extends AppCompatActivity implements Sec4DialogFragmentBoardAdd.OnFragmentInteractionListener {
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //1. 바탕 화면
        setTitle(null);

        Log.d("MainActivity", "");
       // volley 싱글톤으로 선언
       SingletonVolley.getInstance(this.getApplicationContext());
       SingleTonImgUploader.getInstance(this.getApplicationContext());
       try {
           Thread.sleep(1000);
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       SingletonLoginUser.getInstance(3,getApplicationContext());
       NewsFeedConstants.getInstance().setContext(this);

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
           SystemBarTintManager tintManager = new SystemBarTintManager(this);
           tintManager.setStatusBarTintEnabled(true);
           int actionBarColor = Color.parseColor("#211f0b");
           tintManager.setStatusBarTintColor(actionBarColor);
       }

        //임시 로그인
        AppController.getInstance().setLoginUserID(3);
        SectionsPagerAdapter adapterViewPager; //페이지 어뎁터 선언
        Toolbar toolbar; //툴바 선언
        ViewPager vpPager; //뷰페이져 선언
        SlidingTabLayout slidingTabLayout; //슬라이딩 탭 바 선언

        toolbar = (Toolbar) findViewById(R.id.main_tool_bar); // 2. 툴바
        setSupportActionBar(toolbar);

        vpPager = (ViewPager) findViewById(R.id.main_view_pager); // 3. 뷰페이져
        adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.main_sliding_tabs); // 4. 슬라이딩 탭 바
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.activity_main_slidingtab_layout, R.id.mainSlidingtabText);

         // Setting Custom Color for the Scroll bar indicator of the Tab View
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
           @Override
           public int getIndicatorColor(int position) {
               return getResources().getColor(R.color.ColorPrimary);
           }
        });
        slidingTabLayout.setViewPager(vpPager);
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                UploadConstants storename = new UploadConstants();
                storename.initialize();
                Intent uploadintent = new Intent(MainActivity.this, UploadActivity.class);
                startActivityForResult(uploadintent, 0);
                return true;
            case R.id.action_search:
                Intent searchintent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(searchintent, 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 4;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Sec1NewsFeedParentFrag.newInstance(position);
                case 1:
                    return Sec2RouteKeepFrag.newInstance(position);
                case 2:
                    return Sec3NotificationFrag.newInstance(position);
                case 3:
                    return Sec4MyPageFrag.newInstance(position);
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
                    return getString(R.string.title_NewsFeed);
                case 1:
                    return getString(R.string.title_Routekeep);
                case 2:
                    return getString(R.string.title_Notification);
                case 3:
                    return getString(R.string.title_MyPage);
            }
            return null;
        }
    }


    //sec4 dialog click event
    @Override
    public void onCreateBoard( String boardName ) {
        //you can leave it empty
        //post create board
        if ( Sec4MyPageFrag.fragment != null )
            Sec4MyPageFrag.fragment.createBoard( boardName );

    }
}
