package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import com.example.isebenzi.R;
import com.example.isebenzi.utils.CommonMethods;

public class DomesticPageTwoAssessmentActivity extends AppCompatActivity {
    private Button btnNext;
    private RadioGroup rGQuestionOne;
    private RadioGroup rGQuestionTwo;
    private RadioGroup rGQuestionThree;
    private RadioGroup rGQuestionFour;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_page_two);
        init();

    }

    private void init() {
        btnNext = (Button) findViewById(R.id.btnNext);
        score = Integer.valueOf(getIntent().getStringExtra("score"));
        rGQuestionOne = (RadioGroup) findViewById(R.id.rGQuestionOne);
        rGQuestionTwo = (RadioGroup) findViewById(R.id.rGQuestionTwo);
        rGQuestionThree = (RadioGroup) findViewById(R.id.rGQuestionThree);
        rGQuestionFour = (RadioGroup) findViewById(R.id.rGQuestionFour);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIdOne = rGQuestionOne.getCheckedRadioButtonId();
                int selectedIdTwo = rGQuestionTwo.getCheckedRadioButtonId();
                int selectedIdThree = rGQuestionThree.getCheckedRadioButtonId();
                int selectedIdFour = rGQuestionFour.getCheckedRadioButtonId();
                if (selectedIdOne == R.id.rbqOneC)
                    score++;
                if (selectedIdTwo == R.id.rbqTwoB)
                    score++;
                if (selectedIdThree == R.id.rbqThreeA)
                    score++;
                if (selectedIdFour == R.id.rbqFourA)
                    score++;
                CommonMethods.callAnActivityWithParameter(DomesticPageTwoAssessmentActivity.this, DomesticPageThreeAssessmentActivity.class, "score", String.valueOf(score));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
