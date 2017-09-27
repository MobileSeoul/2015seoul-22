//GeneBeA 테스팅

package com.example.genebe.upload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.slidingtab.SlidingTabLayout;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by 사하앍 on 2015-05-23.
 *
 * KR=Jack
 */
public class UploadActivity extends AppCompatActivity {

    UploadConstants uploadbase = new UploadConstants();
    GenebeBase genebebase = new GenebeBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_main);
        setTitle(null);

        Toolbar toolbar; //툴바 선언
        SectionsPagerAdapter adapterViewPager; //페이지 어뎁터 선언
        ViewPager vpPager; //뷰페이져 선언
        SlidingTabLayout slidingTabLayout; //슬라이딩 탭 바 선언

        toolbar = (Toolbar) findViewById(R.id.upload_tool_bar); //1. 업로드 툴바

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() !=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                // 여기에 화살표 아이콘 설정
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }
        }
        setSupportActionBar(toolbar);

        vpPager = (ViewPager) findViewById(R.id.upload_view_pager); // 2. 뷰페이져
        adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOffscreenPageLimit(4);
        vpPager.setCurrentItem(uploadbase.currentworkingfrag);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.upload_sliding_tabs); // 3. 슬라이딩 탭 바
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabView(R.layout.activity_main_slidingtab_layout, R.id.mainSlidingtabText);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int currentworkingfrag) {
                return getResources().getColor(R.color.tabsScrollColor);
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
            case R.id.action_back:
                Log.d("genebe", "다음 버튼 눌러짐");

                ToggleButton picturestorelike0 = (ToggleButton) findViewById(R.id.upload_picture_store_like_frag0);
                ToggleButton searchstorelike0 = (ToggleButton) findViewById(R.id.upload_search_store_like_frag0);
                ToggleButton picturestorehate0 = (ToggleButton) findViewById(R.id.upload_picture_store_hate_frag0);
                ToggleButton searchstorehate0 = (ToggleButton) findViewById(R.id.upload_search_store_hate_frag0);

                EditText picturestorereview0 = (EditText) findViewById(R.id.upload_picture_store_review_frag0);
                EditText searchstorereview0 = (EditText) findViewById(R.id.upload_search_store_review_frag0);


                ToggleButton picturestorelike1 = (ToggleButton) findViewById(R.id.upload_picture_store_like_frag1);
                ToggleButton searchstorelike1 = (ToggleButton) findViewById(R.id.upload_search_store_like_frag1);
                ToggleButton picturestorehate1 = (ToggleButton) findViewById(R.id.upload_picture_store_hate_frag1);
                ToggleButton searchstorehate1 = (ToggleButton) findViewById(R.id.upload_search_store_hate_frag1);

                EditText picturestorereview1 = (EditText) findViewById(R.id.upload_picture_store_review_frag1);
                EditText searchstorereview1 = (EditText) findViewById(R.id.upload_search_store_review_frag1);


                ToggleButton picturestorelike2 = (ToggleButton) findViewById(R.id.upload_picture_store_like_frag2);
                ToggleButton searchstorelike2 = (ToggleButton) findViewById(R.id.upload_search_store_like_frag2);
                ToggleButton picturestorehate2 = (ToggleButton) findViewById(R.id.upload_picture_store_hate_frag2);
                ToggleButton searchstorehate2 = (ToggleButton) findViewById(R.id.upload_search_store_hate_frag2);

                EditText picturestorereview2 = (EditText) findViewById(R.id.upload_picture_store_review_frag2);
                EditText searchstorereview2 = (EditText) findViewById(R.id.upload_search_store_review_frag2);

                ToggleButton picturestorelike3 = (ToggleButton) findViewById(R.id.upload_picture_store_like_frag3);
                ToggleButton searchstorelike3 = (ToggleButton) findViewById(R.id.upload_search_store_like_frag3);
                ToggleButton picturestorehate3 = (ToggleButton) findViewById(R.id.upload_picture_store_hate_frag3);
                ToggleButton searchstorehate3 = (ToggleButton) findViewById(R.id.upload_search_store_hate_frag3);

                EditText picturestorereview3 = (EditText) findViewById(R.id.upload_picture_store_review_frag3);
                EditText searchstorereview3 = (EditText) findViewById(R.id.upload_search_store_review_frag3);


                if(uploadbase.frag0_isfirst==false){
                    if(uploadbase.frag0_isPic){
                        if(picturestorelike0.isChecked()){
                            uploadbase.frag0_store_like++;
                        }else if(picturestorehate0.isChecked()){
                            uploadbase.frag0_store_like--;
                        }

                        uploadbase.frag0_store_review=picturestorereview0.getText().toString();
                    }
                    else {
                        if (searchstorelike0.isChecked()) {
                            uploadbase.frag0_store_like++;
                        } else if (searchstorehate0.isChecked()) {
                            uploadbase.frag0_store_like--;
                        }

                        uploadbase.frag0_store_review=searchstorereview0.getText().toString();
                    }
                }


                if(uploadbase.frag1_isfirst==false){
                    if(uploadbase.frag1_isPic){
                        if(picturestorelike1.isChecked()){
                            uploadbase.frag1_store_like++;
                        }else if(picturestorehate1.isChecked()){
                            uploadbase.frag1_store_like--;
                        }

                        uploadbase.frag1_store_review=picturestorereview1.getText().toString();
                    }
                    else {
                        if (searchstorelike1.isChecked()) {
                            uploadbase.frag1_store_like++;
                        } else if (searchstorehate1.isChecked()) {
                            uploadbase.frag1_store_like--;
                        }

                        uploadbase.frag1_store_review=searchstorereview1.getText().toString();
                    }
                }

                if(uploadbase.frag2_isfirst==false){
                    if(uploadbase.frag2_isPic){
                        if(picturestorelike2.isChecked()){
                            uploadbase.frag2_store_like++;
                        }else if(picturestorehate2.isChecked()){
                            uploadbase.frag2_store_like--;
                        }

                        uploadbase.frag2_store_review=picturestorereview2.getText().toString();
                    }
                    else {
                        if (searchstorelike2.isChecked()) {
                            uploadbase.frag2_store_like++;
                        } else if (searchstorehate2.isChecked()) {
                            uploadbase.frag2_store_like--;
                        }

                        uploadbase.frag2_store_review=searchstorereview2.getText().toString();
                    }
                }

                if(uploadbase.frag3_isfirst==false){
                    if(uploadbase.frag3_isPic){
                        if(picturestorelike3.isChecked()){
                            uploadbase.frag3_store_like++;
                        }else if(picturestorehate3.isChecked()){
                            uploadbase.frag3_store_like--;
                        }

                        uploadbase.frag3_store_review=picturestorereview3.getText().toString();
                    }
                    else {
                        if (searchstorelike3.isChecked()) {
                            uploadbase.frag3_store_like++;
                        } else if (searchstorehate3.isChecked()) {
                            uploadbase.frag3_store_like--;
                        }

                        uploadbase.frag3_store_review=searchstorereview3.getText().toString();
                    }
                }


                Intent drawroute = new Intent(UploadActivity.this, UploadDrawRouteActivity.class);
                startActivity(drawroute);
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.upload_toolbar_menu, menu);
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
                    return UploadSelectActionFrag0.newInstance(position);
                case 1:
                    return UploadSelectActionFrag1.newInstance(position);
                case 2:
                    return UploadSelectActionFrag2.newInstance(position);
                case 3:
                    return UploadSelectActionFrag3.newInstance(position);
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
                    return uploadbase.frag0_shopname;
                case 1:
                    return uploadbase.frag1_shopname;
                case 2:
                    return uploadbase.frag2_shopname;
                case 3:
                    return uploadbase.frag3_shopname;
            }
            return null;
        }
    }
}
