package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.AddJobResponse;
import com.example.isebenzi.business.objects.Provider;
import com.example.isebenzi.business.objects.UserLocal;
import com.example.isebenzi.loopj.android.image.SmartImageView;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.example.isebenzi.utils.Constants;
import com.google.gson.Gson;

public class SeekerSearchProviderDetailsActivity extends AppCompatActivity {
    private Button btnConfirmCandidate;
    private SmartImageView ivProfilePic;
    private TextView tvName;
    private TextView tvRateDay;
    private TextView tvNoOfAppoinments;
    private TextView tvRating;
    private int position;
    private ImageButton ibBack;
    private UserLocal userLocal;
    private TextView tvTopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_search_provider_details);
        init();
    }

    private void init() {
        Gson gson = new Gson();
        position = Integer.valueOf(getIntent().getStringExtra("position"));
        String userLocalString = getIntent().getStringExtra("userLocalString");
        userLocal = gson.fromJson(userLocalString, UserLocal.class);
        final Provider provider = CommonObjects.getProviders().get(position);
        btnConfirmCandidate = (Button) findViewById(R.id.btnConfirmCandidate);
        tvName = (TextView) findViewById(R.id.tvName);
        ibBack = (ImageButton) findViewById(R.id.ibBack);
        tvRateDay = (TextView) findViewById(R.id.tvRateDay);
        tvNoOfAppoinments = (TextView) findViewById(R.id.tvNoOfAppoinments);
        tvRating = (TextView) findViewById(R.id.tvRating);
        ivProfilePic = (SmartImageView) findViewById(R.id.ivProfilePic);
        tvTopName = (TextView) findViewById(R.id.tvTopName);
        try {
            tvName.setText(provider.getFirstname() + " " + provider.getLastname());
            tvTopName.setText(provider.getFirstname() + "'s Detail");
            tvRateDay.setText("Rate/Day : " + provider.getId());
            tvRating.setText("Rating : " + provider.getAverageRating());
            tvNoOfAppoinments.setText("Number of appointments :" + provider.getStatus());
            if (CommonObjects.getUser().getPhoto() != null) {
                ivProfilePic.setImageUrl(Constants.NETWORK_SERVICE_BASE_URL + provider.getPhoto());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnConfirmCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServer(provider);
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callServer(Provider provider) {
        userLocal.setProvider(provider.getId());
        userLocal.setStatus("0");
        CommonMethods.showProgressDialog(SeekerSearchProviderDetailsActivity.this);
        AppHandlerNew.seekerAddJob(userLocal, new AppHandlerNew.AddJobListener() {
            @Override
            public void onAddJob(AddJobResponse addJobResponse) {
                switch (addJobResponse.getMessage()) {
                    case "success":
                        Toast.makeText(SeekerSearchProviderDetailsActivity.this, "Job is added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "failure":
                        Toast.makeText(SeekerSearchProviderDetailsActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(SeekerSearchProviderDetailsActivity.this, "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }

}
