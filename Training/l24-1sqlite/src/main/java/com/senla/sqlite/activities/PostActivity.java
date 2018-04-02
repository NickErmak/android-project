package com.senla.sqlite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.senla.sqlite.R;
import com.senla.sqlite.models.Post;

public class PostActivity extends AppCompatActivity {

    public static final String EXTRA_POST = "EXTRA_POST";
    private long POST_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Post post = getIntent().getParcelableExtra(EXTRA_POST);
        ((TextView) findViewById(R.id.post_tv_title)).setText(post.getTitle());
        ((TextView) findViewById(R.id.post_tv_email)).setText(post.getEmail());
        ((TextView) findViewById(R.id.post_tv_fullName)).setText(post.getAuthor());
        ((TextView) findViewById(R.id.post_tv_body)).setText(post.getBody());
        POST_ID = post.getId();
    }

    public void onClickComments(View view) {
        startActivity(new Intent(this, CommentActivity.class).putExtra(CommentActivity.EXTRA_POST_ID, POST_ID));
    }
}
