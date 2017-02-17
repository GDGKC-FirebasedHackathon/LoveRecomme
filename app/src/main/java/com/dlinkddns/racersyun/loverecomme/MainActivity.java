package com.dlinkddns.racersyun.loverecomme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private final static int REQ_COUPLEINFO = 101;
    private final static int REQ_RECOMMENDCOURSE = 102;
    private final static int REQ_COUPLEGALLERY = 103;
    private final static int REQ_COUPLEDIARY = 104;
    private final static int REQ_COUPLEDAY = 105;
    private final static int REQ_SETTINGS = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (intervalTime >= 0 && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Snackbar.make(getWindow().getDecorView().getRootView(), "한번 더 누르시면 종료됩니다.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itCoupleInfo) {
            Intent mIntent = new Intent(MainActivity.this, CoupleInfoActivity.class);
            startActivityForResult(mIntent, REQ_COUPLEINFO);
        } else if (id == R.id.itCoupleDay) {
            Intent mIntent = new Intent(MainActivity.this, CoupleDayActivity.class);
            startActivityForResult(mIntent, REQ_COUPLEDAY);
        } else if (id == R.id.itRecommendCourse) {
            Intent mIntent = new Intent(MainActivity.this, RecommendCourseActivity.class);
            startActivityForResult(mIntent, REQ_RECOMMENDCOURSE);
        } else if (id == R.id.itSettings) {
            Intent mIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivityForResult(mIntent, REQ_SETTINGS);
        } else if (id == R.id.itCoupleGallery) {
            Intent mIntent = new Intent(MainActivity.this, CoupleGalleryActivity.class);
            startActivityForResult(mIntent, REQ_COUPLEGALLERY);
        } else if (id == R.id.itCoupleDiary) {
            Intent mIntent = new Intent(MainActivity.this, CoupleDiaryActivity.class);
            startActivityForResult(mIntent, REQ_COUPLEDIARY);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.btCoupleInfo:
                mIntent = new Intent(MainActivity.this, CoupleInfoActivity.class);
                startActivityForResult(mIntent, REQ_COUPLEINFO);
                break;
            case R.id.btRecommendCourse:
                mIntent = new Intent(MainActivity.this, RecommendCourseActivity.class);
                startActivityForResult(mIntent, REQ_RECOMMENDCOURSE);
                break;
            case R.id.btCoupleGallery:
                mIntent = new Intent(MainActivity.this, CoupleGalleryActivity.class);
                startActivityForResult(mIntent, REQ_COUPLEGALLERY);
                break;
            case R.id.btCoupleDiary:
                mIntent = new Intent(MainActivity.this, CoupleDiaryActivity.class);
                startActivityForResult(mIntent, REQ_COUPLEDIARY);
                break;
        }
    }

}
