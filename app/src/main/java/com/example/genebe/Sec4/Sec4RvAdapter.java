package com.example.genebe.Sec4;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.genebe.R;

import java.util.List;

public class Sec4RvAdapter extends RecyclerView.Adapter<Sec4RvAdapter.CardViewHolder>{
    private List<Sec4ThemeCollection> item;

    Sec4RvAdapter(List<Sec4ThemeCollection> item){
        if (item == null){
            throw new IllegalArgumentException(
                    "Must Not be null"
            );
        }
        this.item = item;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // Inflate the CardView for the genebe:
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sec4mypage_card_layout, viewGroup, false);
        // Create a ViewHolder to hold view references inside the CardView:
        CardViewHolder cvh = new CardViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
        int adapterPosition = cardViewHolder.getAdapterPosition();

        if( !(  item.get( adapterPosition ).isBoardAddButton ) ) {
            cardViewHolder.board_add_layout.setVisibility( View.INVISIBLE );
            cardViewHolder.board_view_layout.setVisibility( View.VISIBLE );

            cardViewHolder.card_title.setText(item.get( i ).card_title);
            cardViewHolder.main_thumbnail.setImageResource(item.get( i ).main_thumbnail_id);
            cardViewHolder.sub_thumbnail1.setImageResource(item.get( i ).sub_thumbnail1_id);
            cardViewHolder.sub_thumbnail2.setImageResource(item.get( i ).sub_thumbnail2_id);
            cardViewHolder.sub_thumbnail3.setImageResource(item.get( i ).sub_thumbnail3_id);
        } else {
            cardViewHolder.board_add_layout.setVisibility( View.VISIBLE );
            cardViewHolder.board_view_layout.setVisibility( View.INVISIBLE );
        }

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /*Each CardViewHolder instance holds references to the ImageView and
     TextView of an associated row item, which is laid out in a CardView*/
    public static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView card_title;
        ImageView main_thumbnail;
        ImageView sub_thumbnail1;
        ImageView sub_thumbnail2;
        ImageView sub_thumbnail3;
        LinearLayout board_add_layout;
        LinearLayout board_view_layout;

        CardViewHolder(View itemView) {
            super(itemView);

            card_title = (TextView)itemView.findViewById(R.id.card_title);
            card_title.setTypeface(null, Typeface.BOLD);
            main_thumbnail = (ImageView)itemView.findViewById(R.id.main_thumbnail);
            sub_thumbnail1 = (ImageView)itemView.findViewById(R.id.sub_thumnail1);
            sub_thumbnail2 = (ImageView)itemView.findViewById(R.id.sub_thumnail2);
            sub_thumbnail3 = (ImageView)itemView.findViewById(R.id.sub_thumnail3);
            board_add_layout = (LinearLayout)itemView.findViewById( R.id.board_add_layout );
            board_view_layout = ( LinearLayout )itemView.findViewById( R.id.board_view_layout );
        }
    }
}
