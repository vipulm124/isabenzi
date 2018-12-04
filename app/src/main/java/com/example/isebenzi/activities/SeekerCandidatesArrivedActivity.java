package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.isebenzi.R;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import static com.google.android.gms.R.id.text;

public class SeekerCandidatesArrivedActivity extends AppCompatActivity {
    private Button btnConfirmCandidate;
    private Button btnCall;
    private Button btnMessage;
    private TextView tvName;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_candidates_arrived);
        init();
    }

    private void init() {
        position = Integer.valueOf(getIntent().getStringExtra("position"));
        final Job job = CommonObjects.getGetOffersResponse().getJobs().get(position);
        btnConfirmCandidate = (Button) findViewById(R.id.btnConfirmCandidate);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnMessage = (Button) findViewById(R.id.btnMessage);
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText("Please confirm that" +job.getProvider()+ " has arrived");
        btnConfirmCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivityWithParameter(SeekerCandidatesArrivedActivity.this, SeekerJobCompleteActivity.class,"position",String.valueOf(position));
                finish();
            }
        });
    }

}
