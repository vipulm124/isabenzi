package com.example.isebenzi.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.activities.ProBankingDetailActivity;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.AcceptJob;
import com.example.isebenzi.business.objects.GetOffersResponse;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.R.attr.defaultValue;

/**
 * Created by zain on 6/10/2017.
 */

public class ProviderDashboardOfferDetailsFragment extends Fragment {
    private LinearLayout lLBackToOffer;
    private TextView tvOccupation;
    private TextView tvSeekerName;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvAddress;
    private TextView tvRating;
    private Button btnAcceptJob;
    private TextView tvDailyRate;
    private TextView tvHourlyRate;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_detail_screen, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = Integer.valueOf(bundle.getString("position"));
            Log.d("**PositionGet",position+"");
        }
        init(view);
        lLBackToOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.removeFragment(ProviderDashboardOfferDetailsFragment.this,0,0,getActivity());
                CommonMethods.callFragment(new ProviderDashboardFragment(), R.id.flFrame, 0, 0, getActivity());
            }
        });
        return view;
    }

    private void init(View view) {
        lLBackToOffer = (LinearLayout) view.findViewById(R.id.lLBackToOffer);
        tvOccupation = (TextView) view.findViewById(R.id.tvOccupation);
        tvSeekerName = (TextView) view.findViewById(R.id.tvSeekerName);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvAddress = (TextView) view.findViewById(R.id.tvAddress);
        tvRating = (TextView) view.findViewById(R.id.tvRating);
        btnAcceptJob = (Button) view.findViewById(R.id.btnAcceptJob);
        tvDailyRate = (TextView) view.findViewById(R.id.tvDailyRate);
        tvHourlyRate = (TextView) view.findViewById(R.id.tvHourlyRate);
        Log.d("**Position",position+"");
        final Job job = CommonObjects.getGetOffersResponse().getJobs().get(position);
        try {
//        if (job.getStatus().equals("1")) {
//            btnAcceptJob.setVisibility(View.GONE);
//        } else {
//            btnAcceptJob.setVisibility(View.VISIBLE);
//        }
            String dateTime = job.getFromDate();
            String[] separated = dateTime.split(" ");
            String date = separated[0];
            String time = separated[1];
            tvOccupation.setText("Skills required : " + job.getOccupation());
            tvSeekerName.setText("Service Seeker name: " + job.getSeeker());
            tvDate.setText("Start Date : " + date);
            tvTime.setText("Start Time : " + time);
            tvDailyRate.setText("Daily Rate : " + job.getPerDay());
            tvHourlyRate.setText("Hourly Rate : " + job.getPerHour());
            Geocoder geocoder;
            List<Address> addresses;
            String address = null;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(job.getLatitude()), Double.parseDouble(job.getLongitude()), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                address = addresses.get(0).getAddressLine(0);
                tvAddress.setText("Address :"+address);
            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btnAcceptJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServer(job.getId(),"1");
            }
        });
    }
    private void callServer(String id,String status) {

        CommonMethods.showProgressDialog(getActivity());
        AppHandlerNew.providerAcceptJob(id,status, new AppHandlerNew.AcceptJobListner() {
            @Override
            public void onAcceptJob(AcceptJob acceptJob) {
                switch (acceptJob.getMessage()) {
                    case "success":
                        Toast.makeText(getActivity(), "Job is accepted successfully", Toast.LENGTH_SHORT).show();
                        CommonMethods.removeFragment(ProviderDashboardOfferDetailsFragment.this,0,0,getActivity());
                        CommonMethods.callFragment(new ProviderDashboardFragment(), R.id.flFrame, 0, 0, getActivity());
//                        List<Job> jobs=CommonObjects.getOfferJobs();
//                        jobs.remove(position);
                        getActivity().onBackPressed();
                        break;
                    case "failure":
                        Toast.makeText(getActivity(), "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
                        break;
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Job offers");
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        CommonMethods.removeFragment(this,0,0,getActivity());
//        getActivity().onBackPressed();
//
//    }
}
