package com.example.searchimage.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.searchimage.Adapter.CommentAdapter;
import com.example.searchimage.DatabaseHelperClass.Sqliteopenhelper;
import com.example.searchimage.PojoCalss.PojoComment;
import com.example.searchimage.R;

import java.util.ArrayList;

public class OpenImgActivity extends AppCompatActivity {

    EditText mEdtComment;
    Button mBtnSubmit;
    ImageView mImgOpen;
    SQLiteDatabase db;
    RecyclerView mRecyclerView;
    ArrayList<PojoComment> commentArrayList = new ArrayList<>();
    String link, imgId1;
    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openimg_activity);

        Sqliteopenhelper helper = new Sqliteopenhelper(this);
        db = helper.getWritableDatabase();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("link");
        imgId1 = bundle.getString("imgId");
        position = bundle.getInt("position");


        mEdtComment = findViewById(R.id.mEdtComment);
        mBtnSubmit = findViewById(R.id.mBtnSubmit);
        mImgOpen = findViewById(R.id.mImgOpen);
        mRecyclerView = findViewById(R.id.myRecycleView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RequestOptions myOptions = new RequestOptions()
                .centerCrop();
        Glide.with(this)
                .asBitmap()
                .apply(myOptions)
                .load(link)
                .placeholder(R.drawable.ic_launcher_background)
                .into(mImgOpen);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEdtComment.getText().toString().equalsIgnoreCase("")) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ImgId", imgId1);
                    contentValues.put("Comment", mEdtComment.getText().toString());
                    System.out.println("contentValues =" + contentValues);
                    long row = db.insert("ImgTable", null, contentValues);
                    if (row > 0) {
                        Log.d("test", "contentValues inserted------ " + row);
                        Toast.makeText(OpenImgActivity.this, "Comment Added Successfully..", Toast.LENGTH_SHORT).show();
                        mEdtComment.setText("");
                    }
                    db.close();
                } else {
                    Toast.makeText(OpenImgActivity.this, "Write something in comment box!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        try {

            PojoComment pojoComment = new PojoComment();
            Cursor cursor2 = db.rawQuery(" select * from ImgTable where imgId like  '" + imgId1 + "'", null);

            while (cursor2.moveToNext()) {


                pojoComment.setImgId(cursor2.getString(cursor2.getColumnIndex("imgId")));
                pojoComment.setComment(cursor2.getString(cursor2.getColumnIndex("Comment")));

                // Adding user record to list
                commentArrayList.add(pojoComment);
            }
            CommentAdapter adapter = new CommentAdapter(this, commentArrayList);
            mRecyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}