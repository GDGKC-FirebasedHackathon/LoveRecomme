package com.dlinkddns.racersyun.loverecomme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CoupleDayActivity extends AppCompatActivity {

    private ListView lvCoupleDayList;
    private ArrayAdapter<String> adapter;
    private TextView tvDPlusDay, tvDBirthDay;

    private String CoupleBirthDay = "";
    private String MeetDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple_day);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvCoupleDayList = (ListView) findViewById(R.id.lvCoupleDayList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lvCoupleDayList.setAdapter(adapter);

        tvDPlusDay = (TextView) findViewById(R.id.tvDPlusDay);
        tvDBirthDay = (TextView) findViewById(R.id.tvDBirthDay);

        SharedPreferences mSharedPreferences = getSharedPreferences("USER_LOGIN_INFO", MODE_PRIVATE);
        String firebaseUid = mSharedPreferences.getString("FIREBASE_UID", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("coupleInfos/" + firebaseUid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CoupleInfo coupleInfo = dataSnapshot.getValue(CoupleInfo.class);
                try {
                    CoupleBirthDay = coupleInfo.coupleBirthDay;
                    MeetDate = coupleInfo.meetDate;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CoupleDayViewList();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void CoupleDayViewList() {
        adapter.clear();

        if (MeetDate.equals("")) {
            tvDPlusDay.setText("먼저 만난 날짜를 설정해 주세요");
        }
        if (CoupleBirthDay.equals("")) {
            tvDBirthDay.setText("먼저 생일을 설정해 주세요");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date dMeetDate, dBirthDay;
        Date dToday = new Date();

        long diff;
        long diffDays;

        try {
            dMeetDate = sdf.parse(MeetDate);
            dBirthDay = sdf.parse(CoupleBirthDay);

            diff = dToday.getTime() - dMeetDate.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);

            tvDPlusDay.setText("D + " + diffDays);
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 10; i++) {
            String tmp = "test";

            adapter.add(tmp);
        }
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
}
