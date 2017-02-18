package com.dlinkddns.racersyun.loverecomme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import java.util.Calendar;
import java.util.Date;

public class CoupleDayActivity extends AppCompatActivity {

    private ListView lvCoupleDayList;
    private ArrayAdapter<String> adapter;
    private TextView tvDPlusDay, tvDBirthDay;

    private String CoupleBirthDay = "";
    private String MeetDate = "";

    private SharedPreferences mPrefCoupleInfo;

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

        mPrefCoupleInfo = getSharedPreferences("USER_CP_INFO", MODE_PRIVATE);

        CoupleDayViewList();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void CoupleDayViewList() {
        adapter.clear();

        int bdyear = mPrefCoupleInfo.getInt("USER_BD_Y", 1);
        int bdmonth = mPrefCoupleInfo.getInt("USER_BD_M", 1);
        int bdday = mPrefCoupleInfo.getInt("USER_BD_D", 1);

        int mdyear = mPrefCoupleInfo.getInt("USER_MD_Y", 1);
        int mdmonth = mPrefCoupleInfo.getInt("USER_MD_M", 1);
        int mdday = mPrefCoupleInfo.getInt("USER_MD_D", 1);
        MeetDate = mdyear + "년 " + mdmonth + "월 " + mdday + "일";

        if (mdyear == 1 || bdyear == 1) {
            tvDPlusDay.setText("먼저 만난 날짜를 설정해 주세요");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
            Date dMeetDate, dBirthDay;
            Date dToday = new Date();

            long diff;
            long diffDays;

            try {
                dMeetDate = sdf.parse(MeetDate);

                diff = dToday.getTime() - dMeetDate.getTime();
                diffDays = diff / (24 * 60 * 60 * 1000);
                tvDPlusDay.setText("D + " + diffDays);

                Calendar cal = Calendar.getInstance();
                if (cal.get(Calendar.MONTH) + 1 > bdmonth) {
                    CoupleBirthDay = (cal.get(Calendar.YEAR) + 1) + "년 " + bdmonth + "월 " + bdday + "일";
                } else {
                    CoupleBirthDay = cal.get(Calendar.YEAR) + "년 " + bdmonth + "월 " + bdday + "일";
                }
                dBirthDay = sdf.parse(CoupleBirthDay);
                diff = dToday.getTime() - dBirthDay.getTime();
                diffDays = diff / (24 * 60 * 60 * 1000);
                tvDBirthDay.setText("D " + diffDays);

                Date today = new Date();
                Log.d("TEST", Integer.toString(cal.get(Calendar.MONTH)));
                int count = 100;
                cal.set(mdyear, mdmonth, mdday);
                boolean bIsRecentDay = false;
                for (int i = 1; i <= 10; i++) {
                    cal.add(Calendar.DATE, +100);
                    MeetDate = cal.get(Calendar.YEAR) + "년 " + cal.get(Calendar.MONTH) + "월 " + cal.get(Calendar.DAY_OF_MONTH) + "일";

                    Date dDay = sdf.parse(MeetDate);

                    if (today.getTime() > dDay.getTime()) {
                        i--;
                        count += 100;
                    } else {
                        diffDays = (today.getTime() - dDay.getTime()) / (24 * 60 * 60 * 1000);
                        if (!bIsRecentDay) {
                            SharedPreferences.Editor editor = mPrefCoupleInfo.edit();
                            editor.putInt("USER_RECENT_DAY", count);
                            bIsRecentDay = true;
                        }
                        adapter.add(count + "일    D-" + diffDays);
                        count += 100;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
