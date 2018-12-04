package com.example.isebenzi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.isebenzi.R;
import com.example.isebenzi.activities.SeekerCandidatesArrivedActivity;
import com.example.isebenzi.activities.SeekerCandidatesDetailActivity;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.adapters.ListItemStatusReportAdapter;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.GetOffersResponse;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.business.objects.StatusReport;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zain on 6/10/2017.
 */

public class SeekerStatusReportsFragment extends Fragment {
    private ListView lvStatusReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seeker_status_reports, container, false);
        lvStatusReport = (ListView) view.findViewById(R.id.lvStatusReport);
        lvStatusReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Job job = CommonObjects.getGetOffersResponse().getJobs().get(position);
                if (job.getStatus().equals("2")) {
                    CommonMethods.callAnActivityWithParameter(getActivity(), SeekerCandidatesArrivedActivity.class, "position", String.valueOf(position));
                } else
                    CommonMethods.callAnActivityWithParameter(getActivity(), SeekerCandidatesDetailActivity.class
                            , "position", String.valueOf(position));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        callServer(CommonObjects.getUser().getId());
    }

    private void callServer(String id) {

        CommonMethods.showProgressDialog(getActivity());
        AppHandlerNew.providerGetJobs(id, "0", new AppHandlerNew.GetJobsListner() {
            @Override
            public void onGetJobs(GetOffersResponse getOffersResponse) {
                if (getOffersResponse != null) {
                    CommonObjects.setGetOffersResponse(getOffersResponse);
//                    List<Job> jobs = new ArrayList<>();
//                    for (Job job:getOffersResponse.getJobs()){
//                        if (job.getStatus().equals("0")||job.getStatus().equals("1")){
//                            jobs.add(job);
//                        }
//                    }
                    lvStatusReport.setAdapter(new ListItemStatusReportAdapter(getActivity(), R.layout.listitem_status_report, getOffersResponse.getJobs()));
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }
}
