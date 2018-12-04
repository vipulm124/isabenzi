package com.example.isebenzi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.adapters.ViewPagerMyJobsAdaptor;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.GetOffersResponse;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;
import com.example.isebenzi.utils.CustomTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zain on 6/10/2017.
 */

public class MyJobsFragment extends Fragment {
    private CustomTabLayout tabs;
    private ViewPager viewpager;
    private Activity mActivity;
    private ViewPagerMyJobsAdaptor pagerMyJobsAdaptor;;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("My JOBS");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_jobs, container, false);
        tabs = (CustomTabLayout) view.findViewById(R.id.tabs);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        callServer(CommonObjects.getUser().getId());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.setTabTextColors(
                ContextCompat.getColor(mActivity, R.color.white),
                ContextCompat.getColor(mActivity, R.color.white)
        );
        tabs.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.tab_unsalected));
        tabs.setPadding(2, 2, 2, 2);
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
        tabs.setSelectedTabIndicatorHeight(0);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
//        ProviderOpenJobsFragment providerOpenJobsFragment= new ProviderOpenJobsFragment();
//        ProviderClosedJobsFragment providerClosedJobsFragment= new ProviderClosedJobsFragment();
//        ProviderClosedJobsFragment providerClosedJobsFragment= new ProviderClosedJobsFragment();

        pagerMyJobsAdaptor = new ViewPagerMyJobsAdaptor(getChildFragmentManager());
        // pagerMyJobsAdaptor.addFragment(providerOffersFragment, "OFFER");
//        pagerMyJobsAdaptor.addFragment(providerOpenJobsFragment, "OPEN");
//        pagerMyJobsAdaptor.addFragment(providerClosedJobsFragment, "CLOSED");
        viewPager.setAdapter(pagerMyJobsAdaptor);
        viewPager.setOffscreenPageLimit(0);
        setRetainInstance(false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        CommonObjects.setOfferJobs(null);
//        viewpager.setAdapter(null);
        super.onDestroy();
    }

    private void callServer(final String id) {
        CommonMethods.showProgressDialog(getActivity());
        AppHandlerNew.providerGetJobs("0", id, new AppHandlerNew.GetJobsListner() {
            @Override
            public void onGetJobs(GetOffersResponse getOffersResponse) {
                if (getOffersResponse != null) {
                    if (!getOffersResponse.getJobs().isEmpty()) {
                         List<Job> offerJobs = new ArrayList<>();
                         List<Job> operJobs = new ArrayList<>();
                         List<Job> closedJobs = new ArrayList<>();
                        CommonObjects.setGetOffersResponse(getOffersResponse);
                        for (Job job : getOffersResponse.getJobs()) {
                            switch (job.getStatus()) {
                                case "0":
                                case "1":
                                    offerJobs.add(job);
                                    break;
                                case "2":
                                    operJobs.add(job);
                                    break;
                                case "3":
                                    closedJobs.add(job);
                                    break;
                            }
                        }
                        CommonObjects.setOfferJobs(offerJobs);
                        CommonObjects.setOpenJobs(operJobs);
                        CommonObjects.setClosedJobs(closedJobs);
                        setupViewPager(viewpager);
                        tabs.setupWithViewPager(viewpager);
                    }
                }
                CommonMethods.hideProgressDialog();
            }
        });
    }
}
