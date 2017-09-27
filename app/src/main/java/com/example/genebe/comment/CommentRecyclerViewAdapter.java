package com.example.genebe.comment;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.genebe.Model.Comment;
import com.example.genebe.Model.Feed;
import com.example.genebe.R;

import java.util.List;

/**
 * Created by yk on 2015-10-31.
 */
public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder> {

    private List<Comment> comments;
    public static final String TAG = CommentRecyclerViewAdapter.class.getSimpleName();

    public CommentRecyclerViewAdapter(List<Comment> comments){
        if (comments == null){
            throw new IllegalArgumentException(
                    "Must Not be null"
            );
        }
        this.comments = comments;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item_layout, parent, false);
        // Create a ViewHolder to hold view references inside the CardView:
        final CommentViewHolder cvh = new CommentViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.likeCount.setText("" + comments.get(position).getLike_cnt());
        holder.time.setText(comments.get(position).getTime());
        holder.likeBtn.setBackgroundResource(R.drawable.ic_like);
        holder.review.setText(comments.get(position).getComment());
        Log.d(TAG,"bindbindbindbindbind");
        holder.user_name.setText(comments.get(position).getUsername());
        holder.user_id = comments.get(position).getUserid();
        holder.post_id = comments.get(position).getPostid();
        Glide.with(holder.user_pic.getContext()).load(comments.get(position).getUserpic())
                .fitCenter()
                .into(holder.user_pic);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        protected View mView;
        protected int post_id;
        protected int user_id;
        protected TextView user_name;
        protected ImageView user_pic;
        protected TextView time;
        protected TextView review;
        protected ImageButton likeBtn;
        protected TextView likeCount;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            time = (TextView)itemView.findViewById(R.id.comment_time);
            review = (TextView)itemView.findViewById(R.id.comment_review);
            likeBtn = (ImageButton)itemView.findViewById(R.id.comment_like_btn);
            likeCount = (TextView)itemView.findViewById(R.id.comment_like_count);
            user_name = (TextView)itemView.findViewById(R.id.comment_uploader_name);
            user_pic = (ImageView)itemView.findViewById(R.id.comment_uploader_image);
        }
    }
}


