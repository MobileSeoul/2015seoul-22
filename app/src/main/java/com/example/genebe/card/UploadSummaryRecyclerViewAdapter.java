package com.example.genebe.card;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.genebe.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by kyungrakpark on 15. 6. 29..
 */
public class UploadSummaryRecyclerViewAdapter extends RecyclerView.Adapter<UploadSummaryRecyclerViewAdapter.CardViewHolder>{

        private List<UploadSummaryCardInformation> uploadsummarycard;

        public UploadSummaryRecyclerViewAdapter(List<UploadSummaryCardInformation> uploadsummarycard){
            if (uploadsummarycard == null){
                throw new IllegalArgumentException(
                        "Must Not be null"
                );
            }
            this.uploadsummarycard = uploadsummarycard;
        }

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            // Inflate the CardView for the genebe:
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.upload_summary_card_layout, viewGroup, false);
            // Create a ViewHolder to hold view references inside the CardView:
            CardViewHolder noti_cvh = new CardViewHolder(view);
            return noti_cvh;
        }

        @Override
        public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {
            cardViewHolder.userID.setText(uploadsummarycard.get(i).userID);
            cardViewHolder.storeimgurl1.setImageBitmap(uploadsummarycard.get(i).storeimgurl1);//.setImageResource(uploadsummarycard.get(i).storeimgurl1);
            cardViewHolder.storeimgurl2.setImageBitmap(uploadsummarycard.get(i).storeimgurl2);//.setImageResource(uploadsummarycard.get(i).storeimgurl2);
            cardViewHolder.storeimgurl3.setImageBitmap(uploadsummarycard.get(i).storeimgurl3);//.setImageResource(uploadsummarycard.get(i).storeimgurl1);
            cardViewHolder.storeimgurl4.setImageBitmap(uploadsummarycard.get(i).storeimgurl4);
            cardViewHolder.posttitle.setText(uploadsummarycard.get(i).posttitle);
            cardViewHolder.postreview.setText(uploadsummarycard.get(i).postreview);
            cardViewHolder.hashtag.setText(uploadsummarycard.get(i).hashtag);

            //sumin
            if(uploadsummarycard.get(i).storeimgurl1==null) {
                cardViewHolder.storeimgurl1.setVisibility(View.GONE);
            }
            if(uploadsummarycard.get(i).storeimgurl2==null) {
                cardViewHolder.storeimgurl2.setVisibility(View.GONE);
            }
            if(uploadsummarycard.get(i).storeimgurl3==null) {
                cardViewHolder.storeimgurl3.setVisibility(View.GONE);
            }
            if(uploadsummarycard.get(i).storeimgurl4==null) {
                cardViewHolder.storeimgurl4.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return uploadsummarycard.size();
        }
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }
        /*Each PhotoViewHolder instance holds references to the ImageView and
         TextView of an associated row item, which is laid out in a CardView*/
        public class CardViewHolder extends RecyclerView.ViewHolder{
            TextView userID;
            ImageView storeimgurl1;
            ImageView storeimgurl2;
            ImageView storeimgurl3;
            ImageView storeimgurl4;
            TextView posttitle;
            TextView postreview;
            TextView hashtag;

            public CardViewHolder(View itemView) {
                super(itemView);
                userID = (TextView)itemView.findViewById(R.id.upload_summary_userid);
                storeimgurl1 = (ImageView)itemView.findViewById(R.id.upload_summary_store_img1);
                storeimgurl2 = (ImageView)itemView.findViewById(R.id.upload_summary_store_img2);
                storeimgurl3 = (ImageView)itemView.findViewById(R.id.upload_summary_store_img3);
                storeimgurl4 = (ImageView)itemView.findViewById(R.id.upload_summary_store_img4);
                posttitle = (TextView)itemView.findViewById(R.id.upload_summary_post_title);
                postreview = (TextView)itemView.findViewById(R.id.upload_summary_post_review);
                hashtag = (TextView)itemView.findViewById(R.id.upload_summary_post_hashtag);
            }

        }
    }

