package com.example.genebe.card;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.genebe.R;

import java.util.List;

/**
 * Created by kyungrakpark on 15. 6. 28..
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.CardViewHolder>{

    private List<SearchStoreLocationCardInformation> searchcard;

    public SearchRecyclerViewAdapter(List<SearchStoreLocationCardInformation> searchcard){
        if (searchcard == null){
            throw new IllegalArgumentException(
                    "Must Not be null"
            );
        }
        this.searchcard = searchcard;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // Inflate the CardView for the genebe:
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_location_card_layout, viewGroup, false);
        // Create a ViewHolder to hold view references inside the CardView:
        CardViewHolder noti_cvh = new CardViewHolder(view);
        return noti_cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        cardViewHolder.storeimgurl.setImageResource(searchcard.get(i).storeimgurl);
        cardViewHolder.storename.setText(searchcard.get(i).storename);
        cardViewHolder.storeaddress.setText(searchcard.get(i).storeaddress);
        cardViewHolder.storephone.setText(searchcard.get(i).storephone);
    }

    @Override
    public int getItemCount() {
        return searchcard.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    /*Each PhotoViewHolder instance holds references to the ImageView and
     TextView of an associated row item, which is laid out in a CardView*/
    public class CardViewHolder extends RecyclerView.ViewHolder{
        ImageView storeimgurl;
        TextView storename;
        TextView storeaddress;
        TextView storephone;
        public CardViewHolder(View itemView) {
            super(itemView);
            storeimgurl = (ImageView)itemView.findViewById(R.id.search_location_store_img);
            storename = (TextView)itemView.findViewById(R.id.search_location_store_name);
            storeaddress = (TextView)itemView.findViewById(R.id.search_location_store_address);
            storephone = (TextView)itemView.findViewById(R.id.search_location_store_phone);
        }

    }
}

