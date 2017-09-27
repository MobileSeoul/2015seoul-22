package com.example.genebe.upload;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.Sec1.Store;
import com.example.genebe.SingletonVolley;
import com.example.genebe.card.NewsFeedConstants;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class UploadSelectActionFrag0 extends Fragment implements View.OnClickListener{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    final int REQ_CODE_SELECT_IMAGE=100;

    private ImageButton imageselectbtn;
    private ImageButton searchlocationbtn;
    private ImageButton galleryimgbtn;
    private View mView;

    String lat;
    String lng;

    TextView storeName;
    TextView storeAddress;
    TextView storeTel;
    ToggleButton emoticonLike;
    ToggleButton emoticonSoso;
    ToggleButton emoticonHate;

    UploadConstants uploadconst = new UploadConstants();
    GenebeBase genebebase = new GenebeBase();

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UploadSelectActionFrag0 newInstance(int sectionNumber) {
        UploadSelectActionFrag0 fragment = new UploadSelectActionFrag0();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadSelectActionFrag0() {
    }

    //Fragment 처음 화면 설정
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;

        if(uploadconst.frag0_isfirst==true){
            //초기에 이미지 업로드/ 검색 선택하는 화면

            rootView = inflater.inflate(R.layout.uploadselectactionfrag, container, false);

            imageselectbtn = (ImageButton) rootView.findViewById(R.id.upload_main_camera);
            imageselectbtn.setBackgroundResource(R.drawable.upload_main_camera);
            imageselectbtn.setOnClickListener(this);

            searchlocationbtn = (ImageButton) rootView.findViewById(R.id.upload_main_search);
            searchlocationbtn.setBackgroundResource(R.drawable.upload_main_search);
            searchlocationbtn.setOnClickListener(this);
        }else if(uploadconst.frag0_isPic==true) {

            //이미지 업로드할 경우
            rootView = LayoutInflater.from(getActivity()).inflate(R.layout.uploadpicturefrag0, container, false);

            storeName = (TextView) rootView.findViewById(R.id.search_store_info_name);
            storeAddress = (TextView) rootView.findViewById(R.id.search_store_info_address);
            storeTel = (TextView) rootView.findViewById(R.id.search_store_info_tel);

            if(uploadconst.frag0_shopname!="01") {
                if (uploadconst.frag0_shopphone == null || uploadconst.frag0_shopphone.contains("nan")) {
                    storeName.setText(uploadconst.frag0_shopname);
                    storeAddress.setText(uploadconst.frag0_shopaddr);
                } else {
                    storeName.setText(uploadconst.frag0_shopname);
                    storeAddress.setText(uploadconst.frag0_shopaddr);
                    storeTel.setText(uploadconst.frag0_shopphone);
                }
            }

            ImageButton storeresearchbtn = (ImageButton) rootView.findViewById(R.id.upload_research_button_frag0);
            storeresearchbtn.setOnClickListener(this);

            if(uploadconst.frag0_img==null) {
                galleryimgbtn = (ImageButton) rootView.findViewById(R.id.findgalleryimgbtn_frag0);
                galleryimgbtn.setOnClickListener(this);
            }else{
                ImageView selectedgalleryimage = (ImageView) rootView.findViewById(R.id.findgalleryimgbtn_frag0);
                selectedgalleryimage.setImageBitmap(uploadconst.frag0_img);
            }

            emoticonLike = (ToggleButton)rootView.findViewById(R.id.upload_picture_store_like_frag0);
            emoticonSoso = (ToggleButton)rootView.findViewById(R.id.upload_picture_store_soso_frag0);
            emoticonHate = (ToggleButton)rootView.findViewById(R.id.upload_picture_store_hate_frag0);

            emoticonLike.setOnClickListener(this);
            emoticonSoso.setOnClickListener(this);
            emoticonHate.setOnClickListener(this);

            ImageButton imageaddbtn;

            final LinearLayout uploadpicturecontainer=(LinearLayout) rootView.findViewById(R.id.upload_picture_container);
            final EditText uploadoriginaledittext = (EditText) rootView.findViewById(R.id.upload_picture_store_review_frag0);

            imageaddbtn = (ImageButton)rootView.findViewById(R.id.upload_add_btn_frag0);
            imageaddbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final View addView = LayoutInflater.from(getActivity()).inflate(R.layout.uploadpicturerow, null);

                    EditText uploadaddedittext = (EditText) addView.findViewById(R.id.upload_add_edittext);
                    ImageButton uploadaddimage = (ImageButton) addView.findViewById(R.id.addgalleryimgbtn_frag0);

                    uploadaddedittext.setText(uploadoriginaledittext.getText().toString());

                    ImageButton uploadremoverow = (ImageButton) addView.findViewById(R.id.removerowbutton_frag0);

                    uploadremoverow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((LinearLayout) addView.getParent()).removeView(addView);
                        }
                    });

                    uploadpicturecontainer.addView(addView);

                    uploadoriginaledittext.getText().clear();
                    uploadaddimage.setImageBitmap(uploadconst.frag0_img);
                    galleryimgbtn.setImageResource(R.drawable.upload_camera);


                    //혹여나 각 아이템을 지우는 방법을 구현해야할시
                    //http://android-er.blogspot.kr/2013/05/add-and-remove-view-dynamically.html 참고
                }
            });


        }else{
            //가게 검색할 경우
            rootView = LayoutInflater.from(getActivity()).inflate(R.layout.uploadsearchfrag0, container, false);

            storeName = (TextView) rootView.findViewById(R.id.search_store_info_name);
            storeAddress = (TextView) rootView.findViewById(R.id.search_store_info_address);
            storeTel = (TextView) rootView.findViewById(R.id.search_store_info_tel);

            if(uploadconst.frag0_shopphone==null||uploadconst.frag0_shopphone.contains("nan")){
                storeName.setText(uploadconst.frag0_shopname);
                storeAddress.setText(uploadconst.frag0_shopaddr);
            }else{
                storeName.setText(uploadconst.frag0_shopname);
                storeAddress.setText(uploadconst.frag0_shopaddr);
                storeTel.setText(uploadconst.frag0_shopphone);
            }

            emoticonLike = (ToggleButton)rootView.findViewById(R.id.upload_search_store_like_frag0);
            emoticonSoso = (ToggleButton)rootView.findViewById(R.id.upload_search_store_soso_frag0);
            emoticonHate = (ToggleButton)rootView.findViewById(R.id.upload_search_store_hate_frag0);

            emoticonLike.setOnClickListener(this);
            emoticonSoso.setOnClickListener(this);
            emoticonHate.setOnClickListener(this);
        }


        //서버에서 데이터 불러오는 작업 (비동기화 작업 완성 안했기에 여기에 임시적으로 설정)
        //genebebase.getSearchStoreInfoFromServer(0, "abc");

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
    }


    //선택된 이미지 화면에 짤라서 넣기
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE_SELECT_IMAGE)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try{
                    //URI로부터 이미지 원래 경로와 이름을 얻어옴.
                    String originalfullname = getImageNameFromUri(getActivity(), data.getData());

                    //이미지 데이터를 비트맵으로 받아오기
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                    Log.d("genebe", "uri path: " + data.getData());

                    //getView()를 통해 root view를 불러와 findViewById를 가능하게 한다
                    ImageView image = (ImageView) getView().findViewById(R.id.findgalleryimgbtn_frag0);

                    //Exif을 통해 모든 이미지 데이터를 불러온다
                    ExifInterface exif = new ExifInterface(originalfullname);


//                    강제로 좌표 사진에 박는 부분
//                    double latitude = 37.578497;
//                    double longitude = 126.895802;
//
//                    Log.d("genebe", "convertgpstoexif latitude: "+ convertgpstoexif(latitude));
//                    Log.d("genebe", "convertgpstoexif longitude: "+ convertgpstoexif(longitude));
//
//                    exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, convertgpstoexif(latitude));
//                    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, convertgpstoexif(longitude));
//                    exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latitude > 0 ? "N" : "S");
//                    exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, longitude>0?"N":"S");
//
//                    exif.saveAttributes();

                    //String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap bmRotated = rotateBitmap(image_bitmap, orientation);
                    //EXIF에 rotate 정보가 있을 경우 반영해서 제대로 회전시킨 Bitmap 을 만듬.

                    Bitmap resized_image = resizeBitmapImage(bmRotated, 1000);
                    //이미지 크기 resizing 작업

                    uploadconst.frag0_img = resized_image;

                    //배치해놓은 ImageView에 set
                    image.setImageBitmap(resized_image);

                    lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                    lng = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);

                    if(!lat.isEmpty()) {
                        lat=convertexiftogps(lat);
                        lng=convertexiftogps(lng);
                        Log.d("genebe", "gps latitude : " + lat);
                        Log.d("genebe", "gps longitude : " + lng);

                        //자체 서버 GPS 기반 가게 검색
                        AsyncTask<Integer, Void, Integer> servertest = new AsyncTask<Integer, Void, Integer>() {
                            @Override
                            protected Integer doInBackground(Integer... integers) {
                                ArrayList<Store> stores = new ArrayList<>();

                                String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=3&lat=" + lat + "&lng=" + lng;
                                //String url = "http://ec2-54-178-222-47.ap-northeast-1.compute.amazonaws.com:3000/food/searching?search=3&lat=37.579588&lng=126.980801";

                                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                                JsonArrayRequest jsArrayRequest = new JsonArrayRequest(url, future, future);

                                SingletonVolley.getInstance(NewsFeedConstants.getInstance().ctx).addToRequestQueue(jsArrayRequest);

                                try {
                                    JSONArray response = future.get();
                                    for(int i=0;i<response.length();i++) {
                                        Store store = new Store();
                                        JSONObject responseObject = response.getJSONObject(i);
                                        store.shopID = responseObject.getInt("shopid");
                                        store.address = NewsFeedConstants.getInstance().setEncoding(responseObject.getString("old_addr"));
                                        store.telephone = NewsFeedConstants.getInstance().setEncoding(responseObject.getString("phone"));
                                        store.storeName = NewsFeedConstants.getInstance().setEncoding(responseObject.getString("name"));
                                        store.latLng = new LatLng(responseObject.getDouble("lat"), responseObject.getDouble("lng"));
                                        stores.add(store);
                                        uploadconst.storesUploadSelectionFrag = stores;
                                    }

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                return integers[0];
                            }

                            @Override
                            protected void onPostExecute(Integer integer) {
                                if(uploadconst.storesUploadSelectionFrag.size() > 1) {
                                    FragmentDialogList df = new FragmentDialogList().getInstance();
                                    df.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            uploadconst.frag0_shopid = Integer.toString(((Store)view.getTag()).shopID);
                                            uploadconst.frag0_shopaddr = ((Store)view.getTag()).address;
                                            uploadconst.frag0_shopname = ((Store)view.getTag()).storeName;
                                            uploadconst.frag0_shopphone = ((Store)view.getTag()).telephone;
                                            uploadconst.setlatlngFrag0(((Store) view.getTag()).latLng);

                                            startActivity(new Intent(mView.getContext(), UploadActivity.class));
                                            getActivity().finish();
                                        }
                                    });
                                    Fragment fr = getActivity().getSupportFragmentManager().findFragmentByTag(FragmentDialogList.TAG);
                                    if (fr == null) {
                                        df.show(getActivity().getSupportFragmentManager(), FragmentDialogList.TAG);
                                    }
                                } else if(uploadconst.storesUploadSelectionFrag.size() == 1) {
                                    uploadconst.frag0_shopid = Integer.toString(uploadconst.storesUploadSelectionFrag.get(0).shopID);
                                    uploadconst.frag0_shopaddr = uploadconst.storesUploadSelectionFrag.get(0).address;
                                    uploadconst.frag0_shopname = uploadconst.storesUploadSelectionFrag.get(0).storeName;
                                    uploadconst.frag0_shopphone = uploadconst.storesUploadSelectionFrag.get(0).telephone;
                                    uploadconst.setlatlngFrag0(uploadconst.storesUploadSelectionFrag.get(0).latLng);

                                } else {
                                    Log.d("genebe", "매치되는 가게가 없다.");
                                }
                            }
                        };

                        servertest.execute(0);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*
    GPS를 억지로 이미지로에 exif 형태로 넣기 위한 부분
    @param GPS 형식의 lat/lng
    @return exif 형식의 lat/lng
    */
    public String convertgpstoexif(double gps){

        double gpstemp;
        String gpsStr="";
        String dms="";
        String[] splits;
        String[] secnds;
        String seconds;

        //3으로 시작하면 위도 1로 시작하면 경도 분리하는 작업
        if(Double.toString(gps).startsWith(Integer.toString(3))){

            gpstemp = Math.abs(gps);

            dms = Location.convert(gpstemp, Location.FORMAT_SECONDS);
            splits = dms.split(":");
            secnds = (splits[2]).split("\\.");

            if(secnds.length==0)
            {
                seconds = splits[2];
            }
            else
            {
                seconds = secnds[0];
            }

            gpsStr = splits[0] + "/1," + splits[1] + "/1," + seconds + "/1";

        }else{

            gpstemp = Math.abs(gps);

            dms = Location.convert(gpstemp, Location.FORMAT_SECONDS);
            splits = dms.split(":");
            secnds = (splits[2]).split("\\.");

            if(secnds.length==0)
            {
                seconds = splits[2];
            }
            else
            {
                seconds = secnds[0];
            }

            gpsStr = splits[0] + "/1," + splits[1] + "/1," + seconds + "/1";
        }

        Log.d("genebe", "convertgpstoexif return value test :" + gpsStr);

        return gpsStr;
    }

    /*
    Exif 파일 형식으로 불러온 것에서 실제 사용 가능한 gps 형태로 바꾸는 작업
    @param exif 형식의 lat/lng
    @return GPS 형식의 lat/lng
    */
    private String convertexiftogps(String exifgps){

        String gpsresult = null;
        Double result = null;

        //데이터 형식상 일/분/초 되어 있는 것을 파싱
        String[] DMS = exifgps.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        //float하게 될시 마지막 데이터가 짤리므로 double로 표시
        result = new Double(FloatD + (FloatM/60) + (FloatS/3600));

        gpsresult=result.toString();

        //모든 데이터를 확인하면 마지막에 무의미한 나눗셈 계산시 꼬이는 데이터 들어가서 지워줌
        //모든 데이터를 출력할시 경도에서 마지막 한자리 숫자가 더 추가 됨
        if(gpsresult.startsWith(String.valueOf(3))){
            gpsresult=gpsresult.substring(0, 9);
        }else{
            gpsresult=gpsresult.substring(0, 10);
        }

        return gpsresult;
    }


    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    public String getImageNameFromUri(Context context, Uri data){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        //String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgPath;
    }

    /*
    Bitmap 이미지의 가로, 세로 이미지를 리사이징
    @param bmpSource 원본 Bitmap 객체
    @param maxResolution 제한 해상도
    @return 리사이즈된 이미지 Bitmap 객체
     */
    public Bitmap resizeBitmapImage(Bitmap bmpSource, int maxResolution){
        int iWidth = bmpSource.getWidth();
        int iHeight = bmpSource.getHeight();
        int newWidth = iWidth;
        int newHeight = iHeight;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(iWidth>iHeight){
            if(maxResolution<iWidth) {
                rate = maxResolution / (float) iWidth;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }else{
                newWidth = iWidth;
                newHeight = iHeight;
            }
        }else{
            if(maxResolution<iHeight){
                rate = maxResolution/(float)iHeight;
                newWidth = (int)(iWidth*rate);
                newHeight = maxResolution;
            }else {
                newWidth = iWidth;
                newHeight = iHeight;
            }
        }
        //Log.d("genebe", "resize testing newWidth: "+newWidth);
        //Log.d("genebe", "resize testing newHeight: "+newHeight);

        Bitmap resized = Bitmap.createScaledBitmap(bmpSource, newWidth, newHeight, true);

        return resized;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.upload_add_btn_frag0:
                //이미지 업로드 화면에서 이미지를 동적으로 추가할시 선택하는 버튼
                Log.d("genebe", "UploadSelectActionFrag0 이미지 추가 버튼 눌림");

//                View picAddView = LayoutInflater.from(getActivity()).inflate(R.layout.uploadpicturefrag0, null, false);
//
//                test.add("testing 1");
//
//                Log.d("genebe", "testing 0"+test.toString());
//
//
//                //Todo 여기서 동적으로 추가하는 부분 구현하자!
//                ListView addlistview = (ListView) picAddView.findViewById(R.id.upload_add_listview_frag0);
//
//                addlistview.setAdapter(new ArrayAdapter(getActivity(), R.layout.uploadpicturefrag0, test));
//
//                Log.d("genebe", "UploadSelectActionFrag0 이미지 추가 버튼 눌림 끝");

                break;

            case R.id.upload_main_camera:
                //버튼 클릭시 이미지 로컬에서 불러오는 작업

                uploadconst.setIsPicFrag0(true);
                uploadconst.setIsFirstFrag0(false);
                uploadconst.currentworkingfrag=0;

                //genebebase.getImageStoreInfoFromServer(0, uploadconst.frag0_lat, uploadconst.frag0_lng);

                Intent uploadintent = new Intent(getActivity(), UploadActivity.class);
                startActivity(uploadintent);
                getActivity().finish();

                break;

            case R.id.upload_main_search:

                uploadconst.setIsPicFrag0(false);
                uploadconst.setIsFirstFrag0(false);
                uploadconst.currentworkingfrag=0;

                Intent searchintent = new Intent(getActivity(), UploadSearchActivity.class);

                startActivity(searchintent);
                getActivity().finish();

                break;

            case R.id.upload_research_button_frag0:

                uploadconst.setIsPicFrag0(true);
                uploadconst.setIsFirstFrag0(false);
                uploadconst.currentworkingfrag=0;

                Intent researchstoreintent = new Intent(getActivity(), UploadSearchActivity.class);

                startActivity(researchstoreintent);
                getActivity().finish();

                break;

            case R.id.findgalleryimgbtn_frag0:

                Intent imageintent = new Intent(Intent.ACTION_PICK);
                imageintent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                imageintent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(imageintent, REQ_CODE_SELECT_IMAGE);

                break;

            case R.id.upload_picture_store_like_frag0:
                emoticonSoso.setChecked(false);
                emoticonHate.setChecked(false);
                emoticonLike.setClickable(false);
                emoticonSoso.setClickable(true);
                emoticonHate.setClickable(true);

                break;

            case R.id.upload_picture_store_soso_frag0:
                emoticonLike.setChecked(false);
                emoticonHate.setChecked(false);
                emoticonSoso.setClickable(false);
                emoticonLike.setClickable(true);
                emoticonHate.setClickable(true);

                break;

            case R.id.upload_picture_store_hate_frag0:
                emoticonLike.setChecked(false);
                emoticonSoso.setChecked(false);
                emoticonHate.setClickable(false);
                emoticonLike.setClickable(true);
                emoticonSoso.setClickable(true);

                break;

            case R.id.upload_search_store_like_frag0:
                emoticonSoso.setChecked(false);
                emoticonHate.setChecked(false);
                emoticonLike.setClickable(false);
                emoticonSoso.setClickable(true);
                emoticonHate.setClickable(true);

                break;

            case R.id.upload_search_store_soso_frag0:
                emoticonLike.setChecked(false);
                emoticonHate.setChecked(false);
                emoticonSoso.setClickable(false);
                emoticonLike.setClickable(true);
                emoticonHate.setClickable(true);

                break;

            case R.id.upload_search_store_hate_frag0:

                emoticonLike.setChecked(false);
                emoticonSoso.setChecked(false);
                emoticonHate.setClickable(false);
                emoticonLike.setClickable(true);
                emoticonSoso.setClickable(true);

                break;
        }
    }

    public String getBitmapPathandSave(Bitmap bitmap, String folder, String name, int save){
        String ex_storage = Environment.getExternalStorageDirectory().getAbsolutePath(); // Get Absolute Path in External Sdcard
        String folder_name = "/"+folder+"/";
        String file_name = name+".jpg";
        String string_path = ex_storage+folder_name;

        File file_path;
        try{
            file_path = new File(string_path);
            if(!file_path.isDirectory()){
                file_path.mkdirs();
            }
            if(save==1) {
                FileOutputStream out = new FileOutputStream(string_path + file_name);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.close();
            }
            else{
                return string_path;
            }

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }
        return string_path + file_name;
    }
}
