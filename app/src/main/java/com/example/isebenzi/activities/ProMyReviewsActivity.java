package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemReviewAdapter;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.EditBankDetails;
import com.example.isebenzi.business.objects.GetRatingResponse;
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.business.objects.Review;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.util.ArrayList;

public class ProMyReviewsActivity extends AppCompatActivity {
    private ListView lvMyReviews;
    private ImageButton ibBack;
    private TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_reviews);
        init();
    }

    private void init() {
        lvMyReviews = (ListView) findViewById(R.id.lvReviews);
        ibBack = (ImageButton) findViewById(R.id.ibBack);
        tvName = (TextView) findViewById(R.id.tvName);
        if (CommonObjects.getUser().getFirstname()!=null&&CommonObjects.getUser().getLastname()!=null){
            String name=CommonObjects.getUser().getFirstname()+" "+CommonObjects.getUser().getLastname();
            tvName.setText(name);
        }
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProMyReviewsActivity.this.finish();
            }
        });
        callServer(CommonObjects.getUser().getId());
    }

    private void callServer(String id) {

        CommonMethods.showProgressDialog(ProMyReviewsActivity.this);
        AppHandlerNew.providergetRatings("1", new AppHandlerNew.GetRatingListner() {
            @Override
            public void onGetRating(GetRatingResponse getRatingResponse) {
                if (getRatingResponse != null){
                    lvMyReviews.setAdapter(new ListItemReviewAdapter(ProMyReviewsActivity.this, R.layout.listitem_review, getRatingResponse.getRatings()));
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }

}
