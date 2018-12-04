package com.example.isebenzi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.isebenzi.R;
import com.example.isebenzi.activities.ProMyReviewsActivity;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.adapters.ListItemReviewAdapter;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.GetOffersResponse;
import com.example.isebenzi.business.objects.GetRatingResponse;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zain on 6/10/2017.
 */

public class ProviderDashboardFragment extends Fragment {
    private ListView lvOffers;
    private TextView tvError;
    private ProviderDashboardOfferDetailsFragment offerDetailsFragment = new ProviderDashboardOfferDetailsFragment();
    private ListItemOffersAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setRetainInstance(false);
        lvOffers = (ListView) view.findViewById(R.id.lvOffers);
        tvError = (TextView) view.findViewById(R.id.tvError);
        lvOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("position", String.valueOf(position));
                Log.d("**PositionSent",position+"");
                CommonMethods.callFragmentWithParameterNew(offerDetailsFragment, R.id.flFrame, bundle, 0, 0, getActivity());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Job offers");
        callServer(CommonObjects.getUser().getId());
    }

    private void callServer(final String id) {

        CommonMethods.showProgressDialog(getActivity());
        AppHandlerNew.providerGetJobs("0", id, new AppHandlerNew.GetJobsListner() {
            @Override
            public void onGetJobs(GetOffersResponse getOffersResponse) {
                if (getOffersResponse != null) {
                    List<Job> jobs = new ArrayList<>();
                    for (Job job : getOffersResponse.getJobs()) {
                        if (job.getStatus().equals("0") || job.getStatus().equals("1")) {
                            jobs.add(job);
                        }
                    }
                    CommonObjects.setGetOffersResponse(getOffersResponse);
                    CommonObjects.getGetOffersResponse().setJobs(jobs);
                    if (jobs.size() == 0)
                        tvError.setVisibility(View.VISIBLE);
                    else tvError.setVisibility(View.GONE);
                    adapter = new ListItemOffersAdapter(getActivity(), R.layout.listitem_offers, jobs);
                    lvOffers.setAdapter(adapter);
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter = null;
        lvOffers.setAdapter(null);
    }
}
