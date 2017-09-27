package com.example.genebe.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transfermanager.TransferManager;
import com.amazonaws.mobileconnectors.s3.transfermanager.Upload;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.genebe.GenebeBase;
import com.example.genebe.R;
import com.example.genebe.SingletonLoginUser;
import com.example.genebe.SingletonVolley;
import com.example.genebe.card.NewsFeedConstants;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class UploadSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    UploadConstants uploadconsts = new UploadConstants();
    GenebeBase genebebase = new GenebeBase();

    private ProgressBar spinner;

    int postid = 0;
    int userid = SingletonLoginUser.getInstance(0, NewsFeedConstants.getInstance().ctx).getUserid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_summary);
        setTitle(null);

        Toolbar toolbar; //툴바 선언
        SectionsPagerAdapter adapterViewPager; //페이지 어뎁터 선언
        ViewPager vpPager; //뷰페이져 선언

        spinner = (ProgressBar)findViewById(R.id.upload_summary_progressbar);

        spinner.setVisibility(View.GONE);
        //Squizz
        Button uploadbutton = (Button) findViewById(R.id.upload_summary_button);
        uploadbutton.setOnClickListener(this);

        toolbar = (Toolbar) findViewById(R.id.upload_tool_bar); //1. 검색 툴바
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //뒤로 가기 버튼 가져오는 부분

        vpPager = (ViewPager) findViewById(R.id.upload_summary_view_pager); // 3. 뷰페이져
        adapterViewPager = new SectionsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 1;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            return UploadSummaryFrag.newInstance(position);
        }
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(R.string.title_Notification);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_summary_button:
                Log.d("genebe", "업로드시작");

                spinner = (ProgressBar)findViewById(R.id.upload_summary_progressbar);
                spinner.setVisibility(View.VISIBLE);

                String url_getpostid = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/lastpostids";

                getLastPostid(url_getpostid);   // 가장 최근 postid 얻어옴.

                break;
        }
    }

    public int uploadRest(){
        Log.d("genebe", "postid 얻은 이후: "+postid);   // 가장 최근 postid보다 1 큰 값으로 새 게시물 업로드

        EditText posttitle = (EditText) findViewById(R.id.upload_summary_post_title);
        EditText postreview = (EditText) findViewById(R.id.upload_summary_post_review);
        EditText posthashtag = (EditText) findViewById(R.id.upload_summary_post_hashtag);

        String postname =  posttitle.getText().toString();
        String review = postreview.getText().toString();
        String hashtag = hashtagParser(posthashtag.getText().toString());

        Log.d("genebe", "name "+postname+" / review "+review+" / tag " +hashtag);

        Log.d("genebe", "Userid: "+userid);

        int shopid1 = Integer.parseInt(uploadconsts.frag0_shopid);
        int shopid2 = Integer.parseInt(uploadconsts.frag1_shopid);
        int shopid3 = Integer.parseInt(uploadconsts.frag2_shopid);
        int shopid4 = Integer.parseInt(uploadconsts.frag3_shopid);

        String route = shopid1 + "-" + shopid2 + "-" + shopid3 + "-" + shopid4;

        int img1exist;
        int img2exist;
        int img3exist;
        int img4exist;

        String shopimg1;
        if(uploadconsts.frag0_img==null){
            shopimg1 = "";
            img1exist = 0;
        }
        else {
            shopimg1 = imageServerUpload(uploadconsts.frag0_img);
            img1exist = 1;
        }

        String shopimg2;
        if(uploadconsts.frag1_img==null){
            shopimg2 = "";
            img2exist = 0;
        }
        else {
            shopimg2 = imageServerUpload(uploadconsts.frag1_img);
            img2exist = 1;
        }

        String shopimg3;
        if(uploadconsts.frag2_img==null){
            shopimg3 = "";
            img3exist = 0;
        }
        else {
            shopimg3 = imageServerUpload(uploadconsts.frag2_img);
            img3exist = 1;
        }

        String shopimg4;
        if(uploadconsts.frag3_img==null){
            shopimg4 = "";
            img4exist = 0;
        }
        else {
            shopimg4 = imageServerUpload(uploadconsts.frag3_img);
            img4exist = 1;
        }



        String url_uploadpost = null;
        try {
            url_uploadpost = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/uploads?" +
                    "postid=" + postid + "&postname=" + URLEncoder.encode(postname, "UTF-8") +
                    "&route=" + route + "&review=" + URLEncoder.encode(review, "UTF-8") +
                    "&uploader=" + userid + "&img1=" + img1exist + "&img2=" + img2exist + "&img3=" + img3exist + "&img4=" + img4exist +
                    "&hashtag=" + hashtag;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Log.d("genebe", "upload post: "+url_uploadpost);
        uploadSummaryRest(url_uploadpost);

        if(uploadconsts.frag0_shopname.equals("01")) {
            Log.d("genebe", "가게1없음");
            //Toast.makeText(getApplicationContext(),"가게 1 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else{
            String shopreview1 = uploadconsts.frag0_store_review;
            int shoprate1 = uploadconsts.frag0_store_like;

            String url_uploadbinder1 = null;
            try {
                url_uploadbinder1 = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/uploads?" +
                        "postid=" + postid + "&shopid=" + shopid1 +
                        "&review=" + URLEncoder.encode(shopreview1, "UTF-8") + "&rate=" + shoprate1 + "&img=" + shopimg1;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.d("genebe", "upload binder1: " + url_uploadbinder1);
            uploadSummaryRest(url_uploadbinder1);
        }


        if(uploadconsts.frag1_shopname.equals("02")) {
            Log.d("genebe", "가게2없음");
            //Toast.makeText(getApplicationContext(), "가게 2 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            String shopreview2 = uploadconsts.frag1_store_review;
            int shoprate2 = uploadconsts.frag1_store_like;

            String url_uploadbinder2 = null;
            try {
                url_uploadbinder2 = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/uploads?" +
                        "postid=" + postid + "&shopid=" + shopid2 +
                        "&review=" + URLEncoder.encode(shopreview2, "UTF-8") + "&rate=" + shoprate2 + "&img=" + shopimg2;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.d("genebe", "upload binder2: " + url_uploadbinder2);
            uploadSummaryRest(url_uploadbinder2);
        }

        if(uploadconsts.frag2_shopname.equals("03")) {
            Log.d("genebe", "가게3없음");
            //Toast.makeText(getApplicationContext(), "가게 3 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            String shopreview3 = uploadconsts.frag2_store_review;
            int shoprate3 = uploadconsts.frag2_store_like;

            String url_uploadbinder3 = null;
            try {
                url_uploadbinder3 = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/uploads?" +
                        "postid=" + postid + "&shopid=" + shopid3 +
                        "&review=" + URLEncoder.encode(shopreview3, "UTF-8") + "&rate=" + shoprate3 + "&img=" + shopimg3;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.d("genebe", "upload binder3: " + url_uploadbinder3);
            uploadSummaryRest(url_uploadbinder3);
        }

        if(uploadconsts.frag3_shopname.equals("04")) {
            Log.d("genebe", "가게4없음");
            //Toast.makeText(getApplicationContext(), "가게 4 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            String shopreview4 = uploadconsts.frag3_store_review;
            int shoprate4 = uploadconsts.frag3_store_like;

            String url_uploadbinder4 = null;
            try {
                url_uploadbinder4 = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/binders/uploads?" +
                        "postid=" + postid + "&shopid=" + shopid4 +
                        "&review=" + URLEncoder.encode(shopreview4, "UTF-8") + "&rate=" + shoprate4 + "&img=" + shopimg4;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Log.d("genebe", "upload binder4: " + url_uploadbinder4);
            uploadSummaryRest(url_uploadbinder4);
        }

        String url_uploaduser = "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/users/uploads?" +
                "postid=" + postid + "&userid=" + userid;

        Log.d("genebe", "upload user info: " + url_uploaduser);
        uploadSummaryRest(url_uploaduser);



        return 0;
    }

    public String hashtagParser(String s) {
        String tagString;

        Log.d("genebe", "original tag: "+s);
        if(s.substring(0, 1).equals("#")){
            s=s.substring(1);
        }
        Log.d("genebe", "replaced tag: "+s);
        tagString = s.replace("#","-").replace(" ","");
        Log.d("genebe", "new tag: " + tagString);
        return tagString;
    }

    public void getLastPostid(final String url){

        final AsyncTask<Void, Void, Integer> servertest = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                JsonArrayRequest jsArrayRequest = new JsonArrayRequest(url, future,future);

                SingletonVolley.getInstance(getBaseContext()).addToRequestQueue(jsArrayRequest);

                try {
                    JSONArray response = future.get();
                    try {
                        Log.d("genebe", "upload response: "+response.toString());
                        postid=Integer.parseInt(response.getJSONObject(0).getString("postid"));

                        postid = postid + 1;

                        if(postid==0){
                            postid = 1;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                int result = uploadRest();
                // 스레드에서 postid를 완벽히 얻어오고 나서 다른 항목들을 업로드 하기 위해, onPostExecute에서 uploadRest() 함수를 호출.
                if(result == 0){
                    //액티비티(와 안에 딸린 프레그먼트들) 종료시키고 메인으로 돌아가는 코드 필요! 업로드 되고나서 아무 일도 안생기니까.
                    Toast.makeText(getApplicationContext(), "업로드가 성공적으로 완성되었습니다!", Toast.LENGTH_LONG).show();

                    spinner = (ProgressBar)findViewById(R.id.upload_summary_progressbar);
                    spinner.setVisibility(View.GONE);


//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }


                    //Here's a runnable/handler combo
                    Runnable mMyRunnable = new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            //앱 재시작
                            Intent i = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    };

                    Handler myHandler = new Handler();
                    myHandler.postDelayed(mMyRunnable, 5000);//Message will be delivered in 1 second.


                }
            }
        };

        servertest.execute();
    }

    public void uploadSummaryRest(final String url){

        final AsyncTask<Void, Void, Integer> servertest = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                JsonArrayRequest jsArrayRequest = new JsonArrayRequest(url, future,future);

                SingletonVolley.getInstance(getBaseContext()).addToRequestQueue(jsArrayRequest);

                try {
                    JSONArray response = future.get();
                    Log.d("genebe", "upload response: "+response.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                return 0;
            }

            @Override
            protected void onPostExecute(Integer integer) {

            }
        };

        servertest.execute();
    }

    public String imageServerUpload(Bitmap resized_image){
        String date = Long.toString(System.currentTimeMillis());


        // 리사이징된 이미지의 절대 경로를 알아낸다.
        // 마지막 파라미터를 1로 설정했으므로, genebeUploads 폴더에 저장한다
        String uploadfilename = getBitmapPathandSave(resized_image, "genebeUploads", userid+"_"+postid+"_"+date.replace(":","-").replace(" ","-"), 1);

        Log.d("genebe", "upload file name: "+uploadfilename);

        String bucket = "genebestorage";    // 사용할 bucket의 이름
        File file = new File(uploadfilename);    //uploadfilename에 확장자 포함 파일명 들어가야 함
        // 서버에 업로드 되는 파일명: 3_7_2015-07-01-09-46-38-92.jpg 형식. (유저아이디_게시물번호_현재날짜시간.jpg)

        // new way of uploading
        final AWSCredentials credentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "AKIAJQNQWSOXO5XYHN4Q";
            }

            @Override
            public String getAWSSecretKey() {
                return "dsnOBd4tXmO4HWOfpxu+19LrVcpWuOBkV+JgF682";
            }
        };

        final StaticCredentialsProvider credentialsProvider = new StaticCredentialsProvider(credentials);

        final TransferManager transferManager = new TransferManager(credentialsProvider);

        final Upload upload = transferManager.upload(bucket, file.getName(), file);
        while(true) {
            if (upload.isDone() == false) {
                Log.d("genebe", "Transfer: " + upload.getDescription());
                Log.d("genebe", "  - State: " + upload.getState());
                Log.d("genebe", "  - Progress: "
                        + upload.getProgress().getBytesTransferred());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                //Toast.makeText(getBaseContext(), "Upload Complete", Toast.LENGTH_LONG).show();
                Log.d("genebe", "upload complete");
                transferManager.shutdownNow();
                break;
            }
        }

        String uploadedURL = "https://s3-ap-northeast-1.amazonaws.com/genebestorage/"+file.getName();
        Log.d("genebe", "image URL (uploaded): "+uploadedURL);

        return uploadedURL;
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
