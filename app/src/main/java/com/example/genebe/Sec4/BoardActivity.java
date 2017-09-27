package com.example.genebe.Sec4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.JsonRequest;
import com.example.genebe.R;

/**
 * Created by 사하앍 on 2015-06-03.
 */
public class BoardActivity extends AppCompatActivity {
    public static final Boolean IS_MY_PAGE = true;
    public String boardGroupName;
    public String boardGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sec4activity_board);
        setTitle(null); //왼쪽 타이틀 삭제

        //intent info
        Intent intent = getIntent();
        boardGroupName = intent.getStringExtra( Sec4MyPageFrag.INTENT_BOARD_GROUP_NAME );
        boardGroupID = intent.getStringExtra( Sec4MyPageFrag.INTENT_BOARD_GROUP_ID );

        Toolbar toolbar; //툴바 선언
        toolbar = (Toolbar) findViewById(R.id.board_tool_bar); // 2. 툴바

        TextView toolbarTitle = ( TextView ) findViewById( R.id.board_toolbar_title );
        if( boardGroupName != null ) toolbarTitle.setText( boardGroupName );

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if(getSupportActionBar() !=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                // 여기에 화살표 아이콘 설정
                toolbar.setNavigationIcon(R.drawable.ic_back);
            }
        }

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.board_toolbar_menu, menu);
        return true;
    }

    //뒤로 가기 버튼 실행하여 홈으로 돌아가는 작업
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            //back button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
