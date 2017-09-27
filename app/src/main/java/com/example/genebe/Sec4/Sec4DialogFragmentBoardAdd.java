package com.example.genebe.Sec4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.genebe.DebugUtil;
import com.example.genebe.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sec4DialogFragmentBoardAdd.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sec4DialogFragmentBoardAdd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sec4DialogFragmentBoardAdd extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Button dialogBoardAddButton;
    public static Sec4DialogFragmentBoardAdd fragment;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sec4DialogFragmentBoardAdd.
     */
    // TODO: Rename and change types and number of parameters
    public static Sec4DialogFragmentBoardAdd newInstance(String param1, String param2) {
        fragment = new Sec4DialogFragmentBoardAdd();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Sec4DialogFragmentBoardAdd() {
        // Required empty public constructor
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate( R.layout.sec4_dialog_board_add, null );

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView( root );

        dialogBoardAddButton = (Button) root.findViewById(R.id.dialog_board_add_button);
        dialogBoardAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sec4DialogFragmentBoardAdd.this.onButtonPressed(v);
            }
        });

        return builder.create();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed( View v ) {
        Log.d(DebugUtil.TAG
        , ( ( EditText ) getDialog().findViewById( R.id.dialog_board_add_text ) ).getText().toString() + "버튼 클릭됨" );
        if (mListener != null) {
            mListener.onCreateBoard( ( ( EditText ) getDialog().findViewById( R.id.dialog_board_add_text ) ).getText().toString() );
            this.dismiss();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onCreateBoard(String boardName);
    }

}
