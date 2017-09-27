package com.example.genebe.Sec3;

/**
 * Created by 사하앍 on 2015-05-20.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.genebe.R;
import com.example.genebe.card.NotificationCardInformation;
import com.example.genebe.card.NotificationRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class Sec3NotificationFrag extends Fragment{

    private static final String LOG_TAG = Sec3NotificationFrag.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final Integer SIZE_OF_LOADING = 100;

    private LinearLayoutManager llm;
    private RecyclerView sec3Rv;
    private NotificationRecyclerViewAdapter sec3Adapter;

    public static Sec3NotificationFrag newInstance(int sectionNumber) {
        Sec3NotificationFrag fragment = new Sec3NotificationFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Sec3NotificationFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.genebe_recyclerview, container, false);

        sec3Rv = (RecyclerView)rootView.findViewById(R.id.rv);

        new AsyncHttpTask().execute(SIZE_OF_LOADING);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sec3Rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(this.getActivity());
        sec3Rv.setLayoutManager(llm);
    }

    private List<NotificationCardInformation> notifications;

    private void initializeData(){
        notifications.add(new NotificationCardInformation(R.drawable.heungguk, "jaeyeonii 님이 회원님의 사진을 좋아합니다.", "12분"));
        notifications.add(new NotificationCardInformation(R.drawable.heungguk, "jaeyeonii 님이 회원님의 사진에 댓글을 남겼습니다.", "1일"));
        notifications.add(new NotificationCardInformation(R.drawable.heungguk, "jaeyeonii 님이 회원님을 팔로우하기 시작했습니다.", "1주"));
        notifications.add(new NotificationCardInformation(R.drawable.heungguk, "jaeyeonii 님이 회원님을 팔로우하기 시작했습니다", "1주"));
    }

    public class AsyncHttpTask extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            return integers[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            notifications = new ArrayList<>();
            initializeData();
            sec3Adapter = new NotificationRecyclerViewAdapter(notifications);
            sec3Rv.setAdapter(sec3Adapter);
        }
    }
}