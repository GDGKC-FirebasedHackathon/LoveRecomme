package com.dlinkddns.racersyun.loverecomme;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CoupleInfoActivity extends AppCompatActivity {

    private ImageView ivMyImage, ivCoupleImage;
    private EditText etMyName, etCoupleName, etMyBirthDay, etCoupleBirthDay, etMeetDate, etRememberWord;
    private boolean bIsModifyEnabled = false;

    private final static int REQ_MYBIRTHDAY = 1;
    private final static int REQ_CPBIRTHDAY = 2;
    private final static int REQ_MEETDAY = 3;
    private final static int REQ_MYPICTURE = 4;
    private final static int REQ_CPPICTURE = 5;

    private String myImageUri, coupleImageUri;

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
            if (bIsModifyEnabled) {
                bIsModifyEnabled = !bIsModifyEnabled;
                enableModeChange(bIsModifyEnabled);

                SharedPreferences mSharedPreferences = getSharedPreferences("USER_LOGIN_INFO", MODE_PRIVATE);
                String firebaseUid = mSharedPreferences.getString("FIREBASE_UID", "");

                CoupleInfoController.createInfo(firebaseUid, etMyName.getText().toString(), etMyBirthDay.getText().toString(), myImageUri
                        , etCoupleName.getText().toString(), etCoupleBirthDay.getText().toString(), coupleImageUri
                        , etMeetDate.getText().toString(), etRememberWord.getText().toString());
            } else {
                bIsModifyEnabled = !bIsModifyEnabled;
                enableModeChange(bIsModifyEnabled);
            }
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
        etRememberWord = (EditText) findViewById(R.id.etRememberWord);

        ivMyImage.setBackground(new ShapeDrawable(new OvalShape()));
        ivMyImage.setClipToOutline(true);
        ivCoupleImage.setBackground(new ShapeDrawable(new OvalShape()));
        ivCoupleImage.setClipToOutline(true);

        etMyBirthDay.setInputType(0);
        etCoupleBirthDay.setInputType(0);
        etMeetDate.setInputType(0);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = databaseReference.child("coupleInfos");

        SharedPreferences mSharedPreferences = getSharedPreferences("USER_LOGIN_INFO", MODE_PRIVATE);
        String firebaseUid = mSharedPreferences.getString("FIREBASE_UID", "");



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
        etRememberWord.setEnabled(mode);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMyImage:
                getImageFromStorage(REQ_MYPICTURE);
                break;
            case R.id.ivCoupleImage:
                getImageFromStorage(REQ_CPPICTURE);
                break;
            case R.id.etMyBirthDay:
                openDatePickerDialog(REQ_MYBIRTHDAY);
                break;
            case R.id.etCoupleBirthDay:
                openDatePickerDialog(REQ_CPBIRTHDAY);
                break;
            case R.id.etMeetDate:
                openDatePickerDialog(REQ_MEETDAY);
                break;
        }
    }

    private void getImageFromStorage(int requestCode) {
        Intent mIntent = new Intent(Intent.ACTION_PICK);
        mIntent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        mIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(mIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_MYPICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                ivMyImage.setImageURI(data.getData());
                myImageUri = data.getData().toString();
            }
        } else if (requestCode == REQ_CPPICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                ivCoupleImage.setImageURI(data.getData());
                coupleImageUri = data.getData().toString();
            }
        }
    }

    public void openDatePickerDialog(final int flag) {
        Calendar mCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CoupleInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    switch (flag) {
                        case REQ_MYBIRTHDAY:
                            etMyBirthDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                            break;
                        case REQ_CPBIRTHDAY:
                            etCoupleBirthDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                            break;
                        case REQ_MEETDAY:
                            etMeetDate.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }
}
