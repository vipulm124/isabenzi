package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.isebenzi.R;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.loopj.android.image.SmartImageView;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.example.isebenzi.utils.Constants;

public class SeekerCandidatesDetailActivity extends AppCompatActivity {
    private Button btnConfirmCandidate;
    private SmartImageView ivProfilePic;
    private TextView tvName;
    private TextView tvRateDay;
    private TextView tvNoOfAppoinments;
    private TextView tvRating;
    private TextView tvTopName;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_candidates_details);
        init();
    }

    private void init() {
        position = Integer.valueOf(getIntent().getStringExtra("position"));
        final Job job = CommonObjects.getGetOffersResponse().getJobs().get(position);
        tvTopName = (TextView) findViewById(R.id.tvTopName);
        tvName = (TextView) findViewById(R.id.tvName);
        tvRateDay = (TextView) findViewById(R.id.tvRateDay);
        tvNoOfAppoinments = (TextView) findViewById(R.id.tvNoOfAppoinments);
        tvRating = (TextView) findViewById(R.id.tvRating);
        ivProfilePic = (SmartImageView) findViewById(R.id.ivProfilePic);
        btnConfirmCandidate = (Button) findViewById(R.id.btnConfirmCandidate);
        try {
            tvName.setText(job.getProvider());
            tvTopName.setText(job.getProvider() + "'s Detail");
            tvRateDay.setText("Rate/Day : " + job.getPerDay());
            tvRating.setText("Rating : " + job.getStatus());
            tvNoOfAppoinments.setText("Number of appointments :" + job.getStatus());
//            if (CommonObjects.getUser().getPhoto() != null) {
//                ivProfilePic.setImageUrl(Constants.NETWORK_SERVICE_BASE_URL + job.getProvider());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (job.getStatus().equals("0")||job.getStatus().equals("3")) {
            btnConfirmCandidate.setVisibility(View.GONE);
        } else btnConfirmCandidate.setVisibility(View.VISIBLE);

        btnConfirmCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.callAnActivityWithParameter(SeekerCandidatesDetailActivity.this, SeekerTransferCashActivity.class, "position", getIntent().getStringExtra("position"));
            }
        });
    }
}
