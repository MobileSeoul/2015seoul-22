package com.example.genebe.upload;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.genebe.R;
import com.example.genebe.Sec1.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yk on 2015-08-22.
 */
public class FragmentDialogList extends DialogFragment {

    private android.widget.AdapterView.OnItemClickListener onItemClickListener;
   // private Core application;
    public static String TAG = "FragmentDialogList";

    public static FragmentDialogList getInstance() {
        FragmentDialogList df = new FragmentDialogList();
        return df;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ListView list = new ListView(getActivity());

      //  application = (Core) getActivity().getApplication();

        ArrayList<Store> elements = new ArrayList<Store>();
        for(int i=0;i < UploadConstants.storesUploadSelectionFrag.size();i++)
        {
            elements.add(UploadConstants.storesUploadSelectionFrag.get(i));
        }


        StoreAdapter adapter = new StoreAdapter(getActivity(), R.layout.view_dialog_frag_list_item, elements);

        list.setAdapter(adapter);
        list.setOnItemClickListener(onItemClickListener);

        builder.setView(list);

        return builder.create();

    }

    private class StoreAdapter extends ArrayAdapter<Store> {

        private ArrayList<Store> items;

        public StoreAdapter(Context context, int textViewResourceId, ArrayList<Store> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.view_dialog_frag_list_item, null);
            }
            Store s = items.get(position);
            if (s != null) {
                TextView nameText = (TextView) v.findViewById(R.id.dialog_list_store_name);
                TextView addrText = (TextView) v.findViewById(R.id.dialog_list_store_address);
                TextView telText = (TextView) v.findViewById(R.id.dialog_list_store_tel);
                if (nameText != null){
                    nameText.setText(s.storeName);
                }
                if(addrText != null){
                    addrText.setText(s.address);
                }
                if(telText != null) {
                    if(!s.telephone.isEmpty())
                        telText.setText("TEL. " + s.telephone);
                    else
                        telText.setText("  ");
                }
            }
            v.setTag(s);
            return v;
        }
    }

}