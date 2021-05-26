package com.seek.hrm.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import com.seek.hrm.R;
import com.seek.hrm.Fragments.Adapter;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final int REQUEST_CODE_CAMERA = 0;
    public static final int MESSAGE_UPDATE_REALTIME = 1;
    public static final int MESSAGE_UPDATE_FINAL = 2;
    public static final int MESSAGE_PROGRESS_REALTIME = 3;
    boolean justShared = false;

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        cameraService.stop();
//        if (analyzer != null) analyzer.stop();
//        analyzer = new OutputAnalyzer(this, findViewById(R.id.graphTextureView), mainHandler);
    }
    void initView(){
        Toolbar toolbar = findViewById(R.id.topAppBar);
        TabLayout tabs =  findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpage);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(viewPager);
        tabs.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logout) {
                //API logout here
                //this.deleteDatabase("measureapp.db");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            return false;
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CODE_CAMERA);
        }
        initView();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Snackbar.make(
                        findViewById(R.id.constraintLayout),
                        getString(R.string.cameraPermissionRequired),
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //this.deleteDatabase("measureapp.db");
    }
}
