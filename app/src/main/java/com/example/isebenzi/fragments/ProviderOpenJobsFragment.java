package com.example.isebenzi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.util.ArrayList;

/**
 * Created by takhleeqmacpro on 11/29/16.
 */
public class ProviderOpenJobsFragment extends Fragment {
    private ListView lvOffers;
    public static String title="OPEN";
    private ListItemOffersAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_open, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init(getView());
        adapter.notifyDataSetChanged();
    }

    private void init(View view) {
        adapter = new ListItemOffersAdapter(getActivity(),R.layout.listitem_offers, CommonObjects.getOpenJobs());
        lvOffers = (ListView) view.findViewById(R.id.lvOffers);
        lvOffers.setAdapter(adapter);
        lvOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("position",position);
                CommonMethods.callFragmentWithParameter(new ProviderOpenJobDetailFragment(),R.id.flFrameLayoutOpen,bundle,0,0,getActivity());
            }
        });
        }
}