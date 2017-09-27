package com.example.genebe.main;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.genebe.DB.SQLHelper;
import com.example.genebe.DebugUtil;
import com.example.genebe.GenebeBase;
import com.example.genebe.Http.BaseRequest;
import com.example.genebe.MainActivity;
import com.example.genebe.Model.User;
import com.example.genebe.R;
import com.example.genebe.SingletonVolley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class loginFragment extends Fragment implements View.OnClickListener{

    public loginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        v.findViewById( R.id.button_sign_in ).setOnClickListener(this);
        v.findViewById( R.id.button_sign_up ).setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick( View view )
    {
        Log.d(DebugUtil.TAG, "onclick start");
        if ( view.getId() == R.id.button_sign_in )
        {
            //signIn("coapion", "1111");

            signIn( ( (EditText) getView().findViewById( R.id.text_id ) ).getText().toString(),
                    ( ( EditText ) getView().findViewById( R.id.text_password ) ).getText().toString() );
        }

        if ( view.getId() == R.id.button_sign_up )
        {
            moveSignUpPage();
        }

    }

    public void moveSignUpPage()
    {
        SQLTest();
        //Intent intent = new Intent( getActivity(), SignUpActivity.class );
        //startActivity( intent );
    }

    public void signIn( String id, String password )
    {
        if ( id == null || id == "" )
        {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "아이디를 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return ;
        }

        if ( password == null || password == "" )
        {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "비밀번호를 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return ;
        }

        /**
         * @POST /oauth/token
         *
         * @Param
         * grant_type : string
         * client_id : string
         * client_secret : string
         * username : string
         * password : string
         *
         * @Response
         * token_type : string
         * access_type : string
         * expires_in : millsec
         * refresh_token : string
         */

        //add body
        Map<String,String> params = new HashMap<String, String>();
        params.put( "grant_type", "password" );
        params.put( "client_id", DebugUtil.CLIENT_ID );
        params.put( "client_secret", DebugUtil.CLIENT_SECRET );
        params.put( "username", id );
        params.put( "password", password );

        Map<String,String> headers = new HashMap<String, String>();
        headers.put( "Content-Type", "application/x-www-form-urlencoded" );

        BaseRequest request = new BaseRequest(
                Request.Method.POST,
                GenebeBase.NRDB_URL + "/oauth/token",
                params,
                headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                "성공", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Intent intent = new Intent( getActivity(), MainActivity.class );
                        getActivity().startActivity( intent );

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                "에러", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Intent intent = new Intent( getActivity(), MainActivity.class );
                        getActivity().startActivity(intent);
                    }
                });

        SingletonVolley.getInstance( getActivity().getApplicationContext() ).addToRequestQueue( request );
    }

    public void SQLTest() {
        SQLHelper sqlHelper = SQLHelper.getInstance(getActivity().getApplicationContext());
        try {
            Thread.sleep(3000);
        } catch( Exception e ) {

        }

        sqlHelper.insert("malgogi", "1111", "1111", "1111");
        User user = sqlHelper.select();

        Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                "이름:" + user.username + "비번:" + user.password + "토큰:" + user.access_token + "리프레쉬 토큰:" + user.refresh_token, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }
}
