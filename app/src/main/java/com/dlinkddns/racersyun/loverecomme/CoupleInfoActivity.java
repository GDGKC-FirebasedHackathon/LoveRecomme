package com.dlinkddns.racersyun.loverecomme;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.net.URI;
import java.util.Calendar;

public class CoupleInfoActivity extends AppCompatActivity {
    private ImageView ivMyImage, ivCoupleImage;
    private EditText etMeetDate, etMyName, etCoupleName, etMyBirthDay, etCoupleBirthDay, etMemoryTalk;
    private boolean bIsModifyEnabled = false;

    private final static int REQ_MYBIRTHDAY = 1;
    private final static int REQ_CPBIRTHDAY = 2;
    private final static int REQ_MEETDAY = 3;
    private final static int REQ_MYPICTURE = 4;
    private final static int REQ_CPPICTURE = 5;

    private SharedPreferences mPrefLoginInfo, mPrefCoupleInfo;
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

        ivMyImage = (ImageView) findViewById(R.id.ivMyImage);
        ivMyImage.setBackground(new ShapeDrawable(new OvalShape()));
        ivMyImage.setClipToOutline(true);

        ivCoupleImage = (ImageView) findViewById(R.id.ivCoupleImage);
        ivCoupleImage.setBackground(new ShapeDrawable(new OvalShape()));
        ivCoupleImage.setClipToOutline(true);

        etMyName = (EditText) findViewById(R.id.etMyName);
        etCoupleName = (EditText) findViewById(R.id.etCoupleName);
        etMyBirthDay = (EditText) findViewById(R.id.etMyBirthDay);
        etCoupleBirthDay = (EditText) findViewById(R.id.etCoupleBirthDay);
        etMeetDate = (EditText) findViewById(R.id.etMeetDate);
        etMemoryTalk = (EditText) findViewById(R.id.etRememberWord);

        etMyBirthDay.setInputType(0);
        etCoupleBirthDay.setInputType(0);
        etMeetDate.setInputType(0);

        enableModeChange(false);

        mPrefLoginInfo = getSharedPreferences("USER_LOGIN_INFO", MODE_PRIVATE);
        mPrefCoupleInfo = getSharedPreferences("USER_CP_INFO", MODE_PRIVATE);

        setInitialData();
    }

    private void setInitialData() {
        try {
            ivMyImage.setImageURI(CalendarContract.CalendarCache.URI.parse(mPrefLoginInfo.getString("USER_PIC", "")));
            etMyName.setText(mPrefLoginInfo.getString("USER_NAME", ""));
            etMyBirthDay.setText(getDateStrFromPref(REQ_MYBIRTHDAY));
            ivCoupleImage.setImageURI(CalendarContract.CalendarCache.URI.parse(mPrefCoupleInfo.getString("USER_PIC", "")));
            etCoupleName.setText(mPrefCoupleInfo.getString("USER_NAME", ""));
            etCoupleBirthDay.setText(getDateStrFromPref(REQ_CPBIRTHDAY));
            etMeetDate.setText(getDateStrFromPref(REQ_MEETDAY));
            etMemoryTalk.setText(mPrefCoupleInfo.getString("USER_REMEMBER", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getDateStrFromPref(int requestCode) {
        String strTemp;
        Calendar mCalendar = Calendar.getInstance();
        int year, month, day;
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH);
        day = mCalendar.get(Calendar.DAY_OF_MONTH);

        switch (requestCode) {
            case REQ_MYBIRTHDAY:
                year = mPrefLoginInfo.getInt("USER_BD_Y", 1);
                month = mPrefLoginInfo.getInt("USER_BD_M", 1);
                day = mPrefLoginInfo.getInt("USER_BD_D", 1);
                break;
            case REQ_CPBIRTHDAY:
                year = mPrefCoupleInfo.getInt("USER_BD_Y", 1);
                month = mPrefCoupleInfo.getInt("USER_BD_M", 1);
                day = mPrefCoupleInfo.getInt("USER_BD_D", 1);
                break;
            case REQ_MEETDAY:
                year = mPrefCoupleInfo.getInt("USER_MD_Y", 1);
                month = mPrefCoupleInfo.getInt("USER_MD_M", 1);
                day = mPrefCoupleInfo.getInt("USER_MD_D", 1);
                break;
        }

        if (year == 1) {
            strTemp = "날짜를 선택하세요";
        } else {
            strTemp = String.valueOf(year) + "년 " + String.valueOf(month) + "월 " + String.valueOf(day) + "일";
        }
        return strTemp;
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
                SharedPreferences.Editor mMyEditor = mPrefLoginInfo.edit();
                mMyEditor.putString("USER_PIC", data.getData().toString());
                mMyEditor.commit();
                myImageUri = data.getData().toString();
            }
        } else if (requestCode == REQ_CPPICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                ivCoupleImage.setImageURI(data.getData());
                SharedPreferences.Editor mCpEditor = mPrefCoupleInfo.edit();
                mCpEditor.putString("USER_PIC", data.getData().toString());
                mCpEditor.commit();
                coupleImageUri = data.getData().toString();
            }
        }
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
            if (!bIsModifyEnabled) {
                bIsModifyEnabled = true;
            } else {
                SharedPreferences.Editor mMyEditor = mPrefLoginInfo.edit();
                SharedPreferences.Editor mCpEditor = mPrefCoupleInfo.edit();
                mMyEditor.putString("USER_NAME", etMyName.getText().toString());
                mCpEditor.putString("USER_NAME", etCoupleName.getText().toString());
                mCpEditor.putString("USER_REMEMBER", etMemoryTalk.getText().toString());
                mMyEditor.commit();
                mCpEditor.commit();

                SharedPreferences mSharedPreferences = getSharedPreferences("USER_LOGIN_INFO", MODE_PRIVATE);
                String firebaseUid = mSharedPreferences.getString("FIREBASE_UID", "");

                CoupleInfoController.createInfo(firebaseUid, etMyName.getText().toString(), etMyBirthDay.getText().toString(), myImageUri
                        , etCoupleName.getText().toString(), etCoupleBirthDay.getText().toString(), coupleImageUri
                        , etMeetDate.getText().toString(), etMemoryTalk.getText().toString());


                bIsModifyEnabled = false;
                Snackbar.make(getWindow().getDecorView().getRootView(), "저장되었습니다.", Snackbar.LENGTH_SHORT).show();
            }
            enableModeChange(bIsModifyEnabled);
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

    public void openDatePickerDialog(final int flag) {
        Calendar mCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(CoupleInfoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    SharedPreferences.Editor mMyEditor = mPrefLoginInfo.edit();
                    SharedPreferences.Editor mCpEditor = mPrefCoupleInfo.edit();
                    switch (flag) {
                        case REQ_MYBIRTHDAY:
                            etMyBirthDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                            mMyEditor.putInt("USER_BD_Y", year);
                            mMyEditor.putInt("USER_BD_M", (monthOfYear + 1));
                            mMyEditor.putInt("USER_BD_D", dayOfMonth);
                            mMyEditor.commit();
                            break;
                        case REQ_CPBIRTHDAY:
                            etCoupleBirthDay.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                            mCpEditor.putInt("USER_BD_Y", year);
                            mCpEditor.putInt("USER_BD_M", (monthOfYear + 1));
                            mCpEditor.putInt("USER_BD_D", dayOfMonth);
                            mCpEditor.commit();
                            break;
                        case REQ_MEETDAY:
                            etMeetDate.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
                            mCpEditor.putInt("USER_MD_Y", year);
                            mCpEditor.putInt("USER_MD_M", (monthOfYear + 1));
                            mCpEditor.putInt("USER_MD_D", dayOfMonth);
                            mCpEditor.commit();
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
}
