package com.example.isebenzi.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.AcceptJob;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by takhleeqmacpro on 11/29/16.
 */
public class ProviderOpenJobDetailFragment extends Fragment {
    public static String title="OFFER";
    private TextView tvOccupation;
    private TextView tvSeekerName;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvAddress;
    private Button btnAcceptJob;
    private LinearLayout lLBackToOffer;
    private Job job;
    private Button btnArrived;
    private ImageButton ibLocation;
    private ImageButton ibCall;
    private ImageButton ibMessage;
    int position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_jobs_detail, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            position = bundle.getInt("position", 0);
        }
        init(view);
        lLBackToOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethods.removeFragment(ProviderOpenJobDetailFragment.this,0,0,getActivity());
//                CommonMethods.callFragment(new ProviderOpenJobsFragment(), R.id.flFrameLayoutOpen, 0, 0, getActivity());
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
        btnAcceptJob = (Button) view.findViewById(R.id.btnAcceptJob);
        btnArrived = (Button) view.findViewById(R.id.btnArrived);
        ibLocation = (ImageButton) view.findViewById(R.id.ibLocation);
        ibCall = (ImageButton) view.findViewById(R.id.ibCall);
        ibMessage = (ImageButton) view.findViewById(R.id.ibMessage);
        ibLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMap(job.getLatitude(),job.getLongitude());
            }
        });
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:" + job.getPerDay().trim() ;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });
        ibMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = job.getPerDay();
                String sms = "I have reached near your destination";

                try {

                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("sms_body", sms);
                    sendIntent.putExtra("address", phoneNo);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);

                } catch (Exception e) {
                    Toast.makeText(getActivity(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        try {
            job = CommonObjects.getOpenJobs().get(position);
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
            tvSeekerName.setText("Service Seeker name : " + job.getSeeker());
            tvDate.setText("Date : " + date);
            tvTime.setText("Time : " + time);
            Geocoder geocoder;
            List<Address> addresses;
            String address = null;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(Double.parseDouble(job.getLatitude()), Double.parseDouble(job.getLongitude()), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                address = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvAddress.setText("Address :" + address);
        }catch (Exception e ){
            e.printStackTrace();
        }
        btnArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Seeker has been notified",Toast.LENGTH_SHORT).show();
            }
        });
        btnAcceptJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                callServer(job.getId(),"3");
                Toast.makeText(getActivity(),"Job is completed wait for the seeker confirmation",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callMap(String lat,String lOng){
        String uri = "http://maps.google.com/maps?saddr=" +"&daddr=" + lat + "," + lOng;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }
    private void callServer(String id,String status) {

        CommonMethods.showProgressDialog(getActivity());
        AppHandlerNew.providerAcceptJob(id,status, new AppHandlerNew.AcceptJobListner() {
            @Override
            public void onAcceptJob(AcceptJob acceptJob) {
                switch (acceptJob.getMessage()) {
                    case "success":
                        Toast.makeText(getActivity(), "Job is completed successfully", Toast.LENGTH_SHORT).show();
                        List<Job> jobs=CommonObjects.getOpenJobs();
                        jobs.remove(position);
                        List<Job> closedJobs=CommonObjects.getClosedJobs();
                        closedJobs.add(jobs.get(position));
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

}