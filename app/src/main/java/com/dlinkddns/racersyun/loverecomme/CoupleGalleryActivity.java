package com.dlinkddns.racersyun.loverecomme;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class CoupleGalleryActivity extends AppCompatActivity {

    private String PhotoUrl;
    private ImageView ivCouplePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivCouplePhoto = (ImageView) findViewById(R.id.ivCouplePhoto1);
        ivCouplePhoto.setBackground(new ShapeDrawable(new OvalShape()));
        ivCouplePhoto.setClipToOutline(true);
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

    private void getImageFromStorage() {
        Intent mIntent = new Intent(Intent.ACTION_PICK);
        mIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        mIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(mIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            ivCouplePhoto.setImageURI(data.getData());
            PhotoUrl = data.getData().toString();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCouplePhoto1:
                getImageFromStorage();
                break;
        }
    }
}
