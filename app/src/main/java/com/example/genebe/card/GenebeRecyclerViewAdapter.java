package com.example.genebe.card;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.example.genebe.Http.BaseRequest;
import com.example.genebe.Http.CommentRequest;
import com.example.genebe.Http.FeedRequest;
import com.example.genebe.Model.Comment;
import com.example.genebe.Model.Comments;
import com.example.genebe.Model.Feed;
import com.example.genebe.Model.Feeds;
import com.example.genebe.R;
import com.example.genebe.SingleTonImgUploader;
import com.example.genebe.SingletonVolley;
import com.example.genebe.app.AppController;
import com.example.genebe.comment.CommentActivity;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 사하앍 on 2015-05-28.
 */
public class GenebeRecyclerViewAdapter extends RecyclerView.Adapter<GenebeRecyclerViewAdapter.CardViewHolder>{
    private List<Feed> feeds;
    public static final String TAG = GenebeRecyclerViewAdapter.class.getSimpleName();

    public GenebeRecyclerViewAdapter(List<Feed> feeds){
        if (feeds == null){
            throw new IllegalArgumentException(
                    "Must Not be null"
            );
        }
        this.feeds = feeds;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the CardView for the genebe:parent
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genebe_card_layout, parent, false);
        // Create a ViewHolder to hold view references inside the CardView:
        final CardViewHolder cvh = new CardViewHolder(view);

        return cvh;
    }

    //여기서 온클릭 스리너 단다.
    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int i) {
        cardViewHolder.setIsRecyclable(false);
        cardViewHolder.likeCount.setText("" + feeds.get(i).getLike_cnt());
        cardViewHolder.commentCount.setText(""+feeds.get(i).getComment_cnt());
        cardViewHolder.cardid = feeds.get(i).getPostid();
        cardViewHolder.writer_id.setText(feeds.get(i).getUploader());
        LinearLayout inflatedLayout = (LinearLayout)cardViewHolder.mView.findViewById(R.id.photo_layout);
        // LayoutInflater 객체 생성
        LayoutInflater inflater =  (LayoutInflater)cardViewHolder.mView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Inflated_Layout.xml로 구성한 레이아웃을 inflatedLayout 영역으로 확장
        for(int j=0;j<feeds.get(i).getImgs().size();j++)
        {
            if(!feeds.get(i).getImgs().get(j).equals("https://s3-ap-northeast-1.amazonaws.com/genebestorage/storeimgbasic.png")) {
                ImageView img = (ImageView) inflater.inflate(R.layout.image_view_photo, null);
                inflatedLayout.addView(img);
                Glide.with(img.getContext())
                        .load(feeds.get(i).getImgs().get(j))
                        .fitCenter()
                        .into(img);
            }
        }
        cardViewHolder.routeName.setText(feeds.get(i).getPostname());
        cardViewHolder.routeContent.setText(feeds.get(i).getReview());
        String tags = "";
        for(int j=0;j<feeds.get(i).getHashtag().size();j++)
        {
            tags+= "#" + feeds.get(i).getHashtag().get(j) + " ";
        }
        cardViewHolder.tags.setText(tags);
        if(feeds.get(i).getLike_pressed().equals("true")) {
            cardViewHolder.likeBtn.setBackgroundResource(R.drawable.ic_like_dark);
        }
        cardViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, CardDetailActivity.class);
                intent.putExtra("FEED",feeds.get(i));
                context.startActivity(intent);
            }
        });
        cardViewHolder.tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                여기에 태그 눌렀을때 검색하는 엑티비티로 이동하게 만들어주자 나머지도 마찬가지
                */
                Context context = view.getContext();
                Log.d("dddddddddd", feeds.get(i).getHashtag().toString());

            }
        });

        cardViewHolder.likeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(feeds.get(i).getLike_pressed().equals("false")) {
                    BaseRequest request = new BaseRequest(
                            Request.Method.GET,
                            "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/post_like?postid="+feeds.get(i).getPostid()+"&userid="+AppController.getInstance().getLoginUserID()+"&unlike=0",
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.d(TAG,"like ERROR");
                                }
                            }
                    );
                    AppController.getInstance().addToRequestQueue(request,TAG);
                    view.setBackgroundResource(R.drawable.ic_like_dark);
                    feeds.get(i).setLike_cnt(feeds.get(i).getLike_cnt()+1);
                    cardViewHolder.likeCount.setText(""+feeds.get(i).getLike_cnt());
                    feeds.get(i).setLike_pressed("true");
                } else {
                    BaseRequest request = new BaseRequest(
                            Request.Method.GET,
                            "http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/post_like?postid="+feeds.get(i).getPostid()+"&userid="+AppController.getInstance().getLoginUserID()+"&unlike=1",
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Log.d(TAG,"hate ERROR");
                                }
                            }
                    );
                    AppController.getInstance().addToRequestQueue(request,TAG);
                    view.setBackgroundResource(R.drawable.ic_like);
                    feeds.get(i).setLike_cnt(feeds.get(i).getLike_cnt() - 1);
                    cardViewHolder.likeCount.setText(""+feeds.get(i).getLike_cnt());
                    feeds.get(i).setLike_pressed("false");
                }
            }
        });

        cardViewHolder.commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                CommentRequest commentRequest = new CommentRequest("http://ec2-52-68-87-68.ap-northeast-1.compute.amazonaws.com:3000/posts/post_read_comment?postid="+feeds.get(i).getPostid(),
                        Comments.class,
                        null,
                        new Response.Listener<Comments>() {
                            @Override
                            public void onResponse(Comments response) {
                                Comments temp = response;
                                Context context = view.getContext();
                                Intent intent = new Intent(context, CommentActivity.class);
                                intent.putExtra("COMMENTS",temp);
                                context.startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.d("error","reponse error");
                                Context context = view.getContext();
                                Intent intent = new Intent(context, CommentActivity.class);
                                context.startActivity(intent);
                            }
                        }
                );
                AppController.getInstance().addToRequestQueue(commentRequest);


            }
        });

    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /*Each PhotoViewHolder instance holds references to the ImageView and
     TextView of an associated row item, which is laid out in a CardView*/
    public class CardViewHolder extends RecyclerView.ViewHolder {
        protected View mView;
        protected int cardid;
        protected TextView writer_id;
        protected TextView routeName;
        protected TextView routeContent;
        protected TextView tags;
        protected ImageButton likeBtn;
        protected ImageButton commentBtn;
        protected TextView likeCount;
        protected TextView commentCount;

        public CardViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            writer_id = (TextView)itemView.findViewById(R.id.writer_id);
            routeName = (TextView)itemView.findViewById(R.id.route_name);
            routeContent = (TextView)itemView.findViewById(R.id.route_content);
            tags = (TextView)itemView.findViewById(R.id.tags);
            likeBtn = (ImageButton)itemView.findViewById(R.id.like_button);
            commentBtn = (ImageButton)itemView.findViewById(R.id.comment_button);
            likeCount = (TextView)itemView.findViewById(R.id.like_count);
            commentCount = (TextView)itemView.findViewById(R.id.comment_count);
        }
    }
}
