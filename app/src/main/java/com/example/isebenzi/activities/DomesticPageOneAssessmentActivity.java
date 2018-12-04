package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import com.example.isebenzi.R;
import com.example.isebenzi.utils.CommonMethods;

public class DomesticPageOneAssessmentActivity extends AppCompatActivity {
    private Button btnNext;
    private RadioGroup rGQuestionOne;
    private RadioGroup rGQuestionTwo;
    private RadioGroup rGQuestionThree;
    private RadioGroup rGQuestionFour;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_page_one);
        init();

    }

    private void init() {
        btnNext = (Button) findViewById(R.id.btnNext);
        rGQuestionOne = (RadioGroup) findViewById(R.id.rGQuestionOne);
        rGQuestionTwo = (RadioGroup) findViewById(R.id.rGQuestionTwo);
        rGQuestionThree = (RadioGroup) findViewById(R.id.rGQuestionThree);
        rGQuestionFour = (RadioGroup) findViewById(R.id.rGQuestionFour);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedIdOne = rGQuestionOne.getCheckedRadioButtonId();
                int selectedIdTwo = rGQuestionTwo.getCheckedRadioButtonId();
                int selectedIdThree = rGQuestionThree.getCheckedRadioButtonId();
                int selectedIdFour = rGQuestionFour.getCheckedRadioButtonId();
                if (selectedIdOne == R.id.rbqOneC)
                    score++;
                if (selectedIdTwo == R.id.rbqTwoA)
                    score++;
                if (selectedIdThree == R.id.rbqThreeB)
                    score++;
                if (selectedIdFour == R.id.rbqFourA)
                    score++;
                CommonMethods.callAnActivityWithParameter(DomesticPageOneAssessmentActivity.this,DomesticPageTwoAssessmentActivity.class,"score",String.valueOf(score));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
