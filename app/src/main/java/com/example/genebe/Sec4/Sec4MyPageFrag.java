package com.example.genebe.Sec4;

/**
 * Created by 사하앍 on 2015-05-20.
 */

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.com.google.gson.Gson;
import com.amazonaws.com.google.gson.reflect.TypeToken;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.example.genebe.DebugUtil;
import com.example.genebe.GenebeBase;
import com.example.genebe.Http.BaseRequest;
import com.example.genebe.MainActivity;
import com.example.genebe.Model.Category;
import com.example.genebe.R;
import com.example.genebe.SingletonLoginUser;
import com.example.genebe.SingletonVolley;
import com.example.genebe.card.RecyclerItemClickListener;
import com.example.genebe.pic_crop.RoundImage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class Sec4MyPageFrag extends Fragment {
    public static String INTENT_BOARD_GROUP_NAME = "intent_board_group_name";
    public static String INTENT_BOARD_GROUP_ID = "intente_board_group_id";

    private GridLayoutManager glm;
    private RecyclerView sec4Rv;
    private Sec4RvAdapter sec4Adapter;
    private List<Sec4ThemeCollection> sec4theme;

    public static Sec4MyPageFrag fragment;
            ImageView imageView1;

    RoundImage roundedImage;

    //user info
    int numOfupload;
    int numOffollowers;
    int numOffollowing;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static Sec4MyPageFrag newInstance(int sectionNumber) {
        fragment = new Sec4MyPageFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Sec4MyPageFrag() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.sec4mypagefrag, container, false);
        imageView1 = (ImageView) rootView.findViewById(R.id.imageView1);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        roundedImage = new RoundImage(bm);
        imageView1.setImageDrawable(roundedImage);

        TextView sec4upload = (TextView) rootView.findViewById(R.id.sec4upload);
        TextView sec4followers = (TextView) rootView.findViewById(R.id.sec4followers);
        TextView sec4following = (TextView) rootView.findViewById(R.id.sec4following);

        //this will be show only my page
        if ( BoardActivity.IS_MY_PAGE )
        {
            SingletonLoginUser userInfo = SingletonLoginUser.getInstance(DebugUtil.USER_ID, getActivity().getApplicationContext());

            sec4upload.setText( String.format("%d", userInfo.getUploaded().size()) );
            sec4upload.append("\n 업로드");

            sec4followers.setText(String.format("%d", userInfo.getFollower().size()));
            sec4followers.append("\n 팔로워");


            sec4following.setText( String.format( "%d", userInfo.getFollowing().size() ) );
            sec4following.append( "\n 팔로잉" );
        }


        sec4Rv = (RecyclerView) rootView.findViewById(R.id.sec4rv);
        sec4Rv.setHasFixedSize(true);
        sec4Rv.addOnItemTouchListener(
                new RecyclerItemClickListener(rootView.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        //add board button인지 판단
                        if (position == sec4theme.size() - 1) {
                            DialogFragment dialog = new Sec4DialogFragmentBoardAdd();
                            dialog.show(getActivity().getFragmentManager(), "Sec4DialogFragmentBoardAdd");

                        } else {
                            Intent intentSubActivity = new Intent(rootView.getContext(), BoardActivity.class);
                            intentSubActivity.putExtra( INTENT_BOARD_GROUP_NAME, sec4theme.get( position ).card_title );
                            intentSubActivity.putExtra( INTENT_BOARD_GROUP_ID, "1" );
                            startActivity(intentSubActivity);
                        }


                    }
                }));
        glm = new GridLayoutManager(rootView.getContext(), 2,  GridLayoutManager.VERTICAL, false );


        sec4theme = new ArrayList<>();
        sec4Adapter = new Sec4RvAdapter(sec4theme);
        sec4Rv.setLayoutManager(glm);
        sec4Rv.setAdapter(sec4Adapter);
        initializeData();

        return rootView;
    }



    private void initializeData(){
        sec4theme.add(new Sec4ThemeCollection("새로운 보드 추가", R.drawable.stake, R.drawable.stake, R.drawable.stake, R.drawable.stake, true ));
        sec4Adapter.notifyDataSetChanged();

//        Map<String,String> params = new HashMap<String, String>();
//        params.put( "userid", Integer.toString( DebugUtil.USER_ID ) );
//
//        Map<String,String> headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/x-www-form-urlencoded");

//        BaseRequest request = new BaseRequest(
//                Request.Method.GET,
//                GenebeBase.NRDB_URL + "/users/mypage_category",
//                params,
//                headers,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
//                                "성공적으로 등록하였습니다", Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//
//                        try {
//                            Gson gson = new Gson();
//                            Category[] categories = gson.fromJson( response.toString(), new TypeToken<ArrayList<Category>>(){}.getType() );
//                        } catch ( Exception e ) {
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
//                                "목록을 불러오는데 실패하였습니다 잠시후 다시 시도해주세요.", Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//                    }
//                });
//        SingletonVolley.getInstance( getActivity().getApplicationContext() ).addToRequestQueue(request);
    }

    public void createBoard( String name ) {
        if ( sec4theme == null )
            sec4theme = new ArrayList<>();

        //add body
        Map<String,String> params = new HashMap<String, String>();
        params.put( "userid", Integer.toString( DebugUtil.USER_ID ) );
        params.put("categoryname", name);


        Map<String,String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        BaseRequest request = new BaseRequest(
                Request.Method.POST,
                GenebeBase.NRDB_URL + "/users/mypage_category_create",
                params,
                headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                "성공적으로 등록하였습니다", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        try {

                            sec4theme.add(sec4theme.size() - 1, new Sec4ThemeCollection( response.get( "categoryname" ).toString(), R.drawable.stake, R.drawable.stake, R.drawable.stake, R.drawable.stake, false));
                            sec4Adapter.notifyDataSetChanged();
                        } catch ( Exception e ) {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                "등록에 실패하였습니다 잠시후 다시 시도해주세요", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
        SingletonVolley.getInstance( getActivity().getApplicationContext() ).addToRequestQueue(request);


    }

}
