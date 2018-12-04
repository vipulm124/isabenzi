package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.utils.CommonMethods;

public class AssessmentSelectActivity extends AppCompatActivity {
    private Button btnNext;
    private RadioButton rbDomestic;
    private RadioButton rbGardener;
    private RadioButton rbWaiter;
    private String selectAssessment="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_assessments);
        init();

    }

    private void init() {
        btnNext = (Button) findViewById(R.id.btnNext);
        rbDomestic = (RadioButton) findViewById(R.id.rbDomestic);
        rbGardener = (RadioButton) findViewById(R.id.rbGardener);
        rbWaiter = (RadioButton) findViewById(R.id.rbWaiter);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (selectAssessment) {
                    case "domestic":
                        CommonMethods.callAnActivity(AssessmentSelectActivity.this, DomesticPageOneAssessmentActivity.class);
                        finish();
                        break;
                    case "gardener":
                        CommonMethods.callAnActivity(AssessmentSelectActivity.this, GardenerPageOneAssessmentActivity.class);
                        finish();
                        break;
                    case "waiter":
                        CommonMethods.callAnActivity(AssessmentSelectActivity.this, WaiterPageOneAssessmentActivity.class);
                        finish();
                        break;
                    case "":
                        Toast.makeText(AssessmentSelectActivity.this, "Please select your occupation", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbDomestic:
                if (checked) {
                    rbGardener.setChecked(false);
                    rbWaiter.setChecked(false);
                    selectAssessment = "domestic";
                }
                break;
            case R.id.rbGardener:
                if (checked) {
                    rbDomestic.setChecked(false);
                    rbWaiter.setChecked(false);
                    selectAssessment = "gardener";
                }
                break;
            case R.id.rbWaiter:
                if (checked) {
                    rbDomestic.setChecked(false);
                    rbGardener.setChecked(false);
                    selectAssessment = "waiter";
                }
                break;
        }
    }
}
