package com.dlinkddns.racersyun.loverecomme;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RecommendCourseActivity extends AppCompatActivity {
    private ListView lvRecommendStoreList;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvRecommendStoreList = (ListView) findViewById(R.id.lvRecommendStoreList);
        adapter = new ListViewAdapter();
        lvRecommendStoreList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        mIntent.putExtra("keyExtra", "결과내용");
        setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String StoreName = data.getStringExtra("StoreName");
                String Comment = data.getStringExtra("Comment");
                String PhotoUrl = data.getStringExtra("PhotoUrl");

                Drawable drawable;
                try {
                    Uri uri = Uri.parse(PhotoUrl);
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    drawable = Drawable.createFromStream(inputStream, uri.toString());
                } catch (FileNotFoundException e) {
                    drawable = getResources().getDrawable(R.drawable.img_blank_profile);
                }

                adapter.addItem(drawable, StoreName);
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAddRecommendStore:
                Intent mIntent = new Intent(RecommendCourseActivity.this, RecommendAddActivity.class);
                startActivityForResult(mIntent, 1);
                break;
        }
    }
}
