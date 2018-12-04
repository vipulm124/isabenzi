package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.isebenzi.R;
import com.example.isebenzi.utils.CommonMethods;

public class ScoreAssessmentActivity extends AppCompatActivity {
    private Button btnNext;
    private TextView tvScore;
    private Button btnMoreAssessments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_assessment);
        init();

    }

    private void init() {
        String score;
        btnNext = (Button) findViewById(R.id.btnNext);
        tvScore = (TextView) findViewById(R.id.tvScore);
        btnMoreAssessments = (Button) findViewById(R.id.btnMoreAssessments);
        if (CommonMethods.getBooleanPreference(ScoreAssessmentActivity.this, "isebenzi", "score", false))
            score = getIntent().getStringExtra("score") + " of 10";
        else score = getIntent().getStringExtra("score") + " of 12";

        tvScore.setText(score);
        CommonMethods.setBooleanPreference(ScoreAssessmentActivity.this, "isebenzi", "score", false);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonMethods.getBooleanPreference(ScoreAssessmentActivity.this, "isebenzi", "isLogin", false)){
                    CommonMethods.callAnActivity(ScoreAssessmentActivity.this, ProviderDashboardActivity.class);
                }
                finish();
            }
        });
        btnMoreAssessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivity(ScoreAssessmentActivity.this,AssessmentSelectActivity.class);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
