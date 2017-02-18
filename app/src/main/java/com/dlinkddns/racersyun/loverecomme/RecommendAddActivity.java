package com.dlinkddns.racersyun.loverecomme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import static com.dlinkddns.racersyun.loverecomme.R.id.ivMyImage;

public class RecommendAddActivity extends AppCompatActivity {

    private EditText etStoreName, etOneLineComment;
    private ImageView ivRecommendCoursePhoto;
    private String PhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recommend_add);

        etStoreName = (EditText) findViewById(R.id.etStoreName);
        etOneLineComment = (EditText) findViewById(R.id.etOneLineComment);
        ivRecommendCoursePhoto = (ImageView) findViewById(R.id.ivRecommendCoursePhoto);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btCommit:
                Intent intent = new Intent();
                intent.putExtra("StoreName", etStoreName.getText().toString());
                intent.putExtra("Comment", etOneLineComment.getText().toString());
                intent.putExtra("PhotoUrl", PhotoUrl);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btCancel:
                finish();
                break;
            case R.id.ivRecommendCoursePhoto:
                getImageFromStorage();
                break;

        }
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
            ivRecommendCoursePhoto.setImageURI(data.getData());
            PhotoUrl = data.getData().toString();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }
}
