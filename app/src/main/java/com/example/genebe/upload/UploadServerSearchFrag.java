package com.example.genebe.upload;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.card.CardDetailActivity;
import com.example.genebe.card.NotificationCardInformation;
import com.example.genebe.card.NotificationRecyclerViewAdapter;
import com.example.genebe.card.RecyclerItemClickListener;
import com.example.genebe.card.SearchRecyclerViewAdapter;
import com.example.genebe.card.SearchStoreLocationCardInformation;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadServerSearchFrag extends android.support.v4.app.Fragment {

    private static final String LOG_TAG = UploadServerSearchFrag.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final Integer SIZE_OF_LOADING = 100;

    private LinearLayoutManager llm;
    private RecyclerView searchRv;
    private SearchRecyclerViewAdapter searchAdapter;

    UploadConstants uploadconsts = new UploadConstants();
    GenebeBase genebebase = new GenebeBase();

    public static UploadServerSearchFrag newInstance(int sectionNumber) {
        UploadServerSearchFrag fragment = new UploadServerSearchFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadServerSearchFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.genebe_norefresh_recyclerview, container, false);
        searchRv = (RecyclerView)rootView.findViewById(R.id.rv);

        new AsyncHttpTask().execute(SIZE_OF_LOADING);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchRv.setHasFixedSize(true);
        searchRv.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d("genebe", "다음 각 아이템 온 클릭 실행 position: "+position);

                        uploadconsts.saveSelectedSearchStore(uploadconsts.currentworkingfrag, position);

                        Intent storesearchcomplete = new Intent(view.getContext(), UploadActivity.class);
                        startActivity(storesearchcomplete);

                    }
                }));
        llm = new LinearLayoutManager(this.getActivity());
        searchRv.setLayoutManager(llm);
    }

    private List<SearchStoreLocationCardInformation> searchcard;

    private void initializeData(){
        searchcard.clear();
        //searchcard.add(new SearchStoreLocationCardInformation(null, null, null, null));
    }

    public void setMap(){

        double lattmp;
        double lngtmp;

        uploadconsts.searchresultlatlngarray = new ArrayList<>();

        Log.d("genebe", "서버 받은 데이터 개수 확인 :"+uploadconsts.searchresultarray.length());

        if(uploadconsts.searchresultarray.length()!=0) {
            for (int i = 0; i < uploadconsts.searchresultarray.length(); i++) {
                try {
                    //서버에서 받은 값에서 가게 별로 latlng 생성하는 부분
                    Log.d("genebe", "서버 수집 정보 확인 :" + i + "\t" +
                            uploadconsts.setEncoding(uploadconsts.searchresultarray.getJSONObject(i).getString("name"))
                            + "\t" + uploadconsts.searchresultarray.getJSONObject(i).getString("shopid"));
                    lattmp = Double.parseDouble(uploadconsts.searchresultarray.getJSONObject(i).getString("lat"));
                    lngtmp = Double.parseDouble(uploadconsts.searchresultarray.getJSONObject(i).getString("lng"));
                    uploadconsts.searchresultlatlngarray.add(new LatLng(lattmp, lngtmp));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            SupportMapFragment mapFragment;
            GoogleMap gMap;
            mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
            gMap = mapFragment.getMap();
            gMap.clear();

            for (int i = 0; i < uploadconsts.searchresultarray.length(); i++) {
                try {
                    Marker newmarker = gMap.addMarker(new MarkerOptions().position(uploadconsts.searchresultlatlngarray.get(i)).
                            title(uploadconsts.setEncoding(uploadconsts.searchresultarray.getJSONObject(i).getString("name"))).
                            icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(genebebase.getLatLngMinMax(uploadconsts.searchresultlatlngarray), 20));
        }else{

            Toast.makeText(getActivity(), "검색 결과가 없어요ㅠㅜ", Toast.LENGTH_LONG).show();
        }

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
            searchcard = new ArrayList<>();

            if(uploadconsts.searchresultarray==null) {
                //처음에 데이터 입력 없을 경우 밑에 화면을 비워둠
                initializeData();
            }else {
                Log.d("genebe", "UploadServerSearchFrag 입력 받은 개수 확인: " + uploadconsts.searchresultarray.length());

                String storename = "";
                String storeaddr = "";
                String storephone = "";

                setMap();

                for (int i = 0; i < uploadconsts.searchresultarray.length(); i++) {
                    try {
                        storename = genebebase.setEncoding(uploadconsts.searchresultarray.getJSONObject(i).getString("name"));
                        storeaddr = genebebase.setEncoding(uploadconsts.searchresultarray.getJSONObject(i).getString("old_addr"));
                        storephone = genebebase.setEncoding(uploadconsts.searchresultarray.getJSONObject(i).getString("phone"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    searchcard.add(new SearchStoreLocationCardInformation(R.drawable.store_sample, storename, storeaddr, storephone));
                }
            }
            searchAdapter = new SearchRecyclerViewAdapter(searchcard);
            searchRv.setAdapter(searchAdapter);

        }
    }

}