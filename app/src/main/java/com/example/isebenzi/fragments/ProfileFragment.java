package com.example.isebenzi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.adapters.ViewPagerDashboardAdaptor;
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.utils.CustomTabLayout;

import java.util.ArrayList;

/**
 * Created by zain on 6/10/2017.
 */

public class ProfileFragment extends Fragment {
    private CustomTabLayout tabs;
    private ViewPager viewpager;
    private ViewPagerDashboardAdaptor pagerDashboardAdaptor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tabs = (CustomTabLayout) view.findViewById(R.id.tabs);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
//        getActivity().view(viewpager);
        tabs.addTab(tabs.newTab());
        tabs.addTab(tabs.newTab());
        tabs.setTabTextColors(
                ContextCompat.getColor(getActivity(), R.color.white),
                ContextCompat.getColor(getActivity(), R.color.white)
        );
        tabs.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.tab_unsalected));
        tabs.setPadding(2,2,2,2);
        tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        tabs.setSelectedTabIndicatorHeight(0);
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ProviderProfileFragment providerProfileFragment=new ProviderProfileFragment();
        ProviderAvailibilityFragment providerAvailibilityFragment=new ProviderAvailibilityFragment();
        pagerDashboardAdaptor = new ViewPagerDashboardAdaptor(getChildFragmentManager());
        pagerDashboardAdaptor.addFragment(providerProfileFragment, "PROFILE");
        pagerDashboardAdaptor.addFragment(providerAvailibilityFragment, "AVAILABILITY");
        viewPager.setAdapter(pagerDashboardAdaptor);
        viewPager.setOffscreenPageLimit(0);
        setRetainInstance(false);
    }
}
