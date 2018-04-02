package com.senla.sqlite.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.senla.sqlite.R;
import com.senla.sqlite.helpers.DbCrudHelper;
import com.senla.sqlite.models.Post;

public class MainActivity extends AppCompatActivity {

    private Cursor mPostCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPostCursor = DbCrudHelper.getPosts();
        CursorAdapter adapter = new PostCursorAdapter(this, mPostCursor);
        ListView lvPosts = (ListView) findViewById(R.id.main_lv_posts);
        lvPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) view.getTag();
                if (cursor != null) {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow("author"));
                    Post post = new Post(id, title, email, body, author);

                    startActivity(new Intent(
                            getApplicationContext(),
                            PostActivity.class)
                            .putExtra(PostActivity.EXTRA_POST, post)
                    );
                }
            }
        });
        lvPosts.setAdapter(adapter);
    }

    private class PostCursorAdapter extends CursorAdapter {

        PostCursorAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_post, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            view.setTag(cursor);
            TextView tvTitle = (TextView) view.findViewById(R.id.list_post_tv_title);
            TextView tvEmail = (TextView) view.findViewById(R.id.list_post_tv_email);
            TextView tvBody = (TextView) view.findViewById(R.id.list_post_tv_body);

            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));

            tvTitle.setText(title);
            tvEmail.setText(email);
            tvBody.setText(body);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mPostCursor.isClosed()) {
            mPostCursor.close();
        }
    }
}
