package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.AcceptJob;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

public class SeekerJobCompleteActivity extends AppCompatActivity {
    private Button btnClosedJob;
    private int position;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_job_complete);
        init();
    }

    private void init() {
        position = Integer.valueOf(getIntent().getStringExtra("position"));
        final Job job = CommonObjects.getGetOffersResponse().getJobs().get(position);
        btnClosedJob = (Button) findViewById(R.id.btnClosedJob);
        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(job.getProvider());
        btnClosedJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServer(job.getId(), "3");
            }
        });
    }

    private void callServer(String id, String status) {

        CommonMethods.showProgressDialog(SeekerJobCompleteActivity.this);
        AppHandlerNew.providerAcceptJob(id, status, new AppHandlerNew.AcceptJobListner() {
            @Override
            public void onAcceptJob(AcceptJob acceptJob) {
                switch (acceptJob.getMessage()) {
                    case "success":
                        Toast.makeText(SeekerJobCompleteActivity.this, "Job is Closed successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "failure":
                        Toast.makeText(SeekerJobCompleteActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(SeekerJobCompleteActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }
}
