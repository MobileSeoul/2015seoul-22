package com.example.genebe.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.com.google.gson.Gson;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.genebe.DB.SQLHelper;
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
public class SignUpActivityFragment extends Fragment implements View.OnClickListener {

    public SignUpActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, container, false);

        v.findViewById(R.id.button_sign_up)
                .setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick( View view ) {
        if ( view.getId() == R.id.button_sign_up )
        {
            register();
        }
    }

    private void register() {
        String id = ( (EditText) getView().findViewById( R.id.input_id ) ).getText().toString();
        String email = ( ( EditText ) getView().findViewById( R.id.input_email ) ).getText().toString();
        String firstName = ( ( EditText ) getView().findViewById( R.id.input_first_name ) ).getText().toString();
        String lastName = ( ( EditText ) getView().findViewById( R.id.input_last_name ) ).getText().toString();
        String password = ( ( EditText ) getView().findViewById( R.id.input_password ) ).getText().toString();
        String confirmPassword = ( ( EditText ) getView().findViewById( R.id.input_confirm_password ) ).getText().toString();

        if ( id.isEmpty() || id == null ) {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "아이디를 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return ;
        }

        if ( email.isEmpty() || email == null ) {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "이메일을 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return ;
        }

        if ( firstName.isEmpty() || firstName == null ) {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "이름을 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return ;
        }

        if ( lastName.isEmpty() || lastName == null ) {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "성을 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return ;
        }

        if ( password.isEmpty() || password == null ) {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "비밀번호를 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return ;
        }

        if ( confirmPassword.isEmpty() || confirmPassword == null ) {
            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                    "비밀번호 확인을 입력해주세요.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return ;
        }

//        if ( password.equals( confirmPassword ) ) {
//            Toast toast = Toast.makeText( getActivity().getApplicationContext(),
//                    "비밀번호가 서로 다릅니다.", Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        }

        Map<String,String> params = new HashMap<String, String>();

        params.put( "username", id );
        params.put( "password", password );
        params.put( "confirm_password", confirmPassword );
        params.put( "email", email );
        params.put( "firstname", firstName );
        params.put( "lastname", lastName );

        final Map<String,String> headers = new HashMap<String, String>();
        headers.put( "Content-Type", "application/x-www-form-urlencoded" );

        BaseRequest request = new BaseRequest(
                Request.Method.POST,
                GenebeBase.NRDB_URL + "/login/register",
                params,
                headers,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                "회원가입에 성공하였습니다.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        SQLHelper helper = SQLHelper.getInstance( getActivity().getApplicationContext() );
                        Gson gson = new Gson();
                        User user = gson.fromJson( response.toString(), User.class );
                        User savedUser = helper.select();



                        if ( savedUser != null ) {
                            helper.update( savedUser._id, user.username, user.password, user.access_token, user.refresh_token );
                        } else {
                            helper.insert( user.username, user.password, user.access_token, user.refresh_token );
                        }

                        moveMainPage();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast toast = Toast.makeText( getActivity().getApplicationContext(),
                                "잠시후 다시 시도해주세요.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
        SingletonVolley.getInstance(getActivity().getApplicationContext()).addToRequestQueue( request );

        return ;
    }

    public void moveMainPage() {
        Intent intent = new Intent( this.getActivity(), MainActivity.class );
        this.getActivity().startActivity( intent );
    }
}
