package com.example.genebe.upload;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.card.UploadSummaryCardInformation;
import com.example.genebe.card.UploadSummaryRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyungrakpark on 15. 6. 29..
 */
public class UploadSummaryFrag extends android.support.v4.app.Fragment{


    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final Integer SIZE_OF_LOADING = 100;

    private LinearLayoutManager llm;
    private RecyclerView summaryRv;
    private UploadSummaryRecyclerViewAdapter searchAdapter;

    //sumin
    private ImageView summaryStore1;
    private ImageView summaryStore2;
    private ImageView summaryStore3;
    private ImageView summaryStore4;

    UploadConstants uploadconst = new UploadConstants();
    GenebeBase genebebase = new GenebeBase();

    public static UploadSummaryFrag newInstance(int sectionNumber) {
        UploadSummaryFrag fragment = new UploadSummaryFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadSummaryFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.genebe_norefresh_recyclerview, container, false);
        summaryRv = (RecyclerView)rootView.findViewById(R.id.rv);

        //sumin
        summaryStore1 = (ImageView)rootView.findViewById(R.id.upload_summary_store_img1);
        summaryStore2 = (ImageView)rootView.findViewById(R.id.upload_summary_store_img2);
        summaryStore3 = (ImageView)rootView.findViewById(R.id.upload_summary_store_img3);
        summaryStore4 = (ImageView)rootView.findViewById(R.id.upload_summary_store_img4);

        new AsyncHttpTask().execute(SIZE_OF_LOADING);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        summaryRv.setHasFixedSize(true);

        llm = new LinearLayoutManager(this.getActivity());
        summaryRv.setLayoutManager(llm);

        Log.d("genebe", "UploadSummaryFrag 확인 0: " + uploadconst.frag0_img);
        Log.d("genebe", "UploadSummaryFrag 확인 1: " + uploadconst.frag1_img);

        //sumin
        //summaryStore1.setImageBitmap(uploadconst.frag0_img);
        //summaryStore2.setImageBitmap(uploadconst.frag1_img);
    }

    private List<UploadSummaryCardInformation> uploadsummarycard;

    private void initializeData(){

        uploadsummarycard.add(new UploadSummaryCardInformation("작성자 ID", uploadconst.frag0_img,uploadconst.frag1_img,uploadconst.frag2_img,uploadconst.frag3_img,  null, null, null));
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
            uploadsummarycard = new ArrayList<>();

            initializeData();

            searchAdapter = new UploadSummaryRecyclerViewAdapter(uploadsummarycard);
            summaryRv.setAdapter(searchAdapter);
        }
    }
}
