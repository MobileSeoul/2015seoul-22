package com.example.genebe.comment;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.genebe.Model.Comment;
import com.example.genebe.Model.Comments;
import com.example.genebe.Model.Feed;
import com.example.genebe.R;
import com.example.genebe.card.GenebeRecyclerViewAdapter;

import java.util.ArrayList;

public class CommentActivity extends Activity {

    public static final String TAG = CommentActivity.class.getSimpleName();
    private RecyclerView mRecyclerview;
    private LinearLayoutManager mlinearLayoutManager;
    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    private Comments mComments;
    private ArrayList<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        comments = new ArrayList<>();
        mComments = (Comments)getIntent().getSerializableExtra("COMMENTS");
        mRecyclerview = (RecyclerView)findViewById(R.id.comment_recycler_view);
        mRecyclerview.setHasFixedSize(true);
        mlinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mlinearLayoutManager);
        commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(comments);
        mRecyclerview.setAdapter(commentRecyclerViewAdapter);
        if(mComments!=null)
            comments.addAll(mComments.getComment());
        commentRecyclerViewAdapter.notifyDataSetChanged();
    }
}
