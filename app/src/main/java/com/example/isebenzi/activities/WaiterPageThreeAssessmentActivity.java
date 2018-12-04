package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.isebenzi.R;
import com.example.isebenzi.utils.CommonMethods;

public class WaiterPageThreeAssessmentActivity extends AppCompatActivity {
    private Button btnNext;
    private RadioGroup rGQuestionOne;
    private RadioGroup rGQuestionTwo;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_page_three);
        init();

    }

    private void init() {
        btnNext = (Button) findViewById(R.id.btnNext);
        score = Integer.valueOf(getIntent().getStringExtra("score"));
        rGQuestionOne = (RadioGroup) findViewById(R.id.rGQuestionOne);
        rGQuestionTwo = (RadioGroup) findViewById(R.id.rGQuestionTwo);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIdOne = rGQuestionOne.getCheckedRadioButtonId();
                int selectedIdTwo = rGQuestionTwo.getCheckedRadioButtonId();
                if (selectedIdOne == R.id.rbqOneA)
                    score++;
                if (selectedIdTwo == R.id.rbqTwoB)
                    score++;
                CommonMethods.setBooleanPreference(WaiterPageThreeAssessmentActivity.this,"isebenzi","score",true);
                CommonMethods.callAnActivityWithParameter(WaiterPageThreeAssessmentActivity.this,ScoreAssessmentActivity.class,"score",String.valueOf(score));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
