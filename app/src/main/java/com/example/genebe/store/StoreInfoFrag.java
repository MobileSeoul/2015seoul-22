package com.example.genebe.store;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.genebe.Model.Feed;
import com.example.genebe.R;
import com.example.genebe.card.GenebeCardInformation;
import com.example.genebe.card.NewsFeedConstants;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 사하앍 on 2015-05-23.
 */
public class StoreInfoFrag extends Fragment {

    private int sectionNumber;
    private Feed feed;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private OnDetailClickedListener detailClickedListener;

    //sumin
    URLConnection connection = null;
    Bitmap bitmap = null;
    ImageView storeImage;

    public static StoreInfoFrag newInstance(int sectionNumber, Feed feed) {
        StoreInfoFrag fragment = new StoreInfoFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        fragment.setFeed(feed);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            detailClickedListener = (OnDetailClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDetailClickedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        View rootView = inflater.inflate(R.layout.storeinfofrag, container, false);

        //sumin
        storeImage = (ImageView) rootView.findViewById(R.id.store_imageView);
        new LoadImage().execute(feed.getImgs().get(sectionNumber));
        TextView storeName = (TextView) rootView.findViewById(R.id.store_name);
        TextView storeAddr = (TextView) rootView.findViewById(R.id.store_addr);
        TextView storeTel = (TextView) rootView.findViewById(R.id.store_tel);
        TextView storeReviewAndRate = (TextView) rootView.findViewById(R.id.store_review_text);

        storeName.setText(NewsFeedConstants.getInstance().stores.get(sectionNumber).storeName);
        storeAddr.setText(NewsFeedConstants.getInstance().stores.get(sectionNumber).address);
        if(!NewsFeedConstants.getInstance().stores.get(sectionNumber).telephone.equals("nan\r"))
            storeTel.setText("TEL. " + NewsFeedConstants.getInstance().stores.get(sectionNumber).telephone);
        storeReviewAndRate.setText(NewsFeedConstants.getInstance().stores.get(sectionNumber).storeReview + "\n작성자의 평점: "
                + NewsFeedConstants.getInstance().stores.get(sectionNumber).storeRate);



        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public interface OnDetailClickedListener {
        public void onDetailClicked(View view);
    }

    //sumin
    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null)
                storeImage.setImageBitmap(image);
        }
    }

    public void setFeed(Feed feed){
        this.feed = feed;
    }
    /*
    class WebGetImage extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                URL url = new URL(newsFeedConst.cardinfo.photoIds.get(0));
                connection = url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                int length = connection.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(connection.getInputStream(),length);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/
}
