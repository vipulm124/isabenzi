package com.example.isebenzi.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.isebenzi.R;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

public class SalectModuleActivity extends AppCompatActivity {
    private Button btnProvider;
    private Button btnSeeker;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_module);
        CommonObjects.setContext(getApplicationContext());
        init();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    1);
        }
    }

    private void init() {
        btnProvider = (Button) findViewById(R.id.btnProvider);
        btnSeeker = (Button) findViewById(R.id.btnSeeker);
        btnProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(SalectModuleActivity.this, ProLoginActivity.class);
            }
        });
        btnSeeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(SalectModuleActivity.this, SeekerLoginActivity.class);
            }
        });
    }
}
