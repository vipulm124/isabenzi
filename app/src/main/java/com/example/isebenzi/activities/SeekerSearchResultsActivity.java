package com.example.isebenzi.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemReviewAdapter;
import com.example.isebenzi.adapters.ListItemSearchResultsAdapterAdapter;
import com.example.isebenzi.business.objects.Review;
import com.example.isebenzi.business.objects.SearchResult;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.util.ArrayList;

public class SeekerSearchResultsActivity extends AppCompatActivity {
    private ListView lvProviders;
    private ImageButton ibBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_search_results);
        init();
    }

    private void init() {
        lvProviders = (ListView) findViewById(R.id.lvProviders);
        ibBack = (ImageButton) findViewById(R.id.ibBack);
        lvProviders.setAdapter(new ListItemSearchResultsAdapterAdapter(this,R.layout.listitem_search_resuls, CommonObjects.getProviders()));
        lvProviders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonMethods.callAnActivityWithParameter(SeekerSearchResultsActivity.this
                        ,SeekerSearchProviderDetailsActivity.class
                        ,"position",String.valueOf(position),"userLocalString",getIntent().getStringExtra("userLocalString"));
            }
        });
//        View footerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bottom_button, null, false);
//        lvProviders.addFooterView(footerView);
//        Button btnSendRequest = (Button) footerView.findViewById(R.id.btnSendRequest);
//        btnSendRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
