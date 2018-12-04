package com.example.isebenzi.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.isebenzi.fragments.ProviderClosedJobsFragment;
import com.example.isebenzi.fragments.ProviderOffersFragment;
import com.example.isebenzi.fragments.ProviderOpenJobsFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerMyJobsAdaptor extends FragmentPagerAdapter {
//    private final List<Fragment> mFragmentList = new ArrayList<>();
//    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerMyJobsAdaptor(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) return new ProviderOffersFragment();
        if(position==1) return new ProviderOpenJobsFragment();
        if(position==2) return new ProviderClosedJobsFragment();
        return new ProviderClosedJobsFragment();
//        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

//    public void addFragment(Fragment fragment, String title) {
//        mFragmentList.add(fragment);
//        mFragmentTitleList.add(title);
//    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)  return ProviderOffersFragment.title;
        if(position==1)  return ProviderOpenJobsFragment.title;
        if(position==2)  return ProviderClosedJobsFragment.title;
        return ProviderClosedJobsFragment.title;
//        return mFragmentTitleList.get(position);
    }
}