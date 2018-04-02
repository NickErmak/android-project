package com.senla.sqlite.activities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.senla.sqlite.R;
import com.senla.sqlite.helpers.DbCrudHelper;

public class CommentActivity extends AppCompatActivity {

    public static final String EXTRA_POST_ID = "EXTRA_POST_ID";
    private Cursor mPostCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        long id = getIntent().getLongExtra(EXTRA_POST_ID, 0);
        mPostCursor = DbCrudHelper.getComments(id);
        CommentCursorAdapter mCursorAdapter = new CommentCursorAdapter(this, mPostCursor);
        ListView lvPosts = (ListView) findViewById(R.id.comment_lv);
        lvPosts.setAdapter(mCursorAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mPostCursor.isClosed()) {
            mPostCursor.close();
        }
    }

    private class CommentCursorAdapter extends CursorAdapter {

        CommentCursorAdapter(Context context, Cursor c) {
            super(context, c, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.list_comment, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvEmail = (TextView) view.findViewById(R.id.list_comment_tv_email);
            TextView tvText = (TextView) view.findViewById(R.id.list_comment_tv_text);

            String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));

            tvEmail.setText(email);
            tvText.setText(text);
        }
    }
}
