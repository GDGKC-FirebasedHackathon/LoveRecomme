package com.dlinkddns.racersyun.loverecomme;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.provider.CalendarContract.CalendarCache.URI;

public class CoupleInfoActivity extends AppCompatActivity {

    private ImageView ivMyImage, ivCoupleImage;
    private EditText etMyName, etCoupleName, etMyBirthDay, etCoupleBirthDay, etMeetDate, etMemoryTalk;
    private boolean bIsModifyEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.inflateMenu(R.menu.activity_couple_info);

        InitPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_couple_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.itEnableChangeInfo) {
            bIsModifyEnabled = !bIsModifyEnabled;
            enableModeChange(bIsModifyEnabled);
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

    private void InitPage() {
        ivMyImage = (ImageView) findViewById(R.id.ivMyImage);
        ivCoupleImage = (ImageView) findViewById(R.id.ivCoupleImage);
        etMyName = (EditText) findViewById(R.id.etMyName);
        etCoupleName = (EditText) findViewById(R.id.etCoupleName);
        etMyBirthDay = (EditText) findViewById(R.id.etMyBirthDay);
        etCoupleBirthDay = (EditText) findViewById(R.id.etCoupleBirthDay);
        etMeetDate = (EditText) findViewById(R.id.etMeetDate);
        etMemoryTalk = (EditText) findViewById(R.id.etMemoryTalk);

        ivMyImage.setBackground(new ShapeDrawable(new OvalShape()));
        ivMyImage.setClipToOutline(true);
        ivCoupleImage.setBackground(new ShapeDrawable(new OvalShape()));
        ivCoupleImage.setClipToOutline(true);

        etMyBirthDay.setInputType(0);
        etCoupleBirthDay.setInputType(0);
        etMeetDate.setInputType(0);

        enableModeChange(false);
    }

    private void enableModeChange(boolean mode) {
        ivMyImage.setEnabled(mode);
        ivCoupleImage.setEnabled(mode);
        etMyName.setEnabled(mode);
        etCoupleName.setEnabled(mode);
        etMyBirthDay.setEnabled(mode);
        etCoupleBirthDay.setEnabled(mode);
        etMeetDate.setEnabled(mode);
        etMemoryTalk.setEnabled(mode);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMyImage:
                break;
            case R.id.ivCoupleImage:
                break;
            case R.id.etMyBirthDay:
                break;
            case R.id.etCoupleBirthDay:
                break;
            case R.id.etMeetDate:
                break;
        }
    }
}
