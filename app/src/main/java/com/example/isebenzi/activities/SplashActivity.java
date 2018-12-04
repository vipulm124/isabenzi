package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.example.isebenzi.R;
import com.example.isebenzi.utils.CommonMethods;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
//                    if (CommonMethods.getBooleanPreference(SplashActivity.this, "swipe-league", "tutorial-screen", false)){
//                        CommonMethods.callAnActivity(SplashActivity.this,LoginActivity.class);
//                    }else {
//                        CommonMethods.callAnActivity(SplashActivity.this, TutorialActivity.class);
//                    }
                    CommonMethods.callAnActivity(SplashActivity.this, SalectModuleActivity.class);
                    finish();
                }
            }
        };

        timer.start();
    }
}
