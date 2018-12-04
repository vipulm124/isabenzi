package com.example.isebenzi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemOffersAdapter;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.Offer;

import java.util.ArrayList;

/**
 * Created by zain on 6/10/2017.
 */

public class SeekerDashboardFragment extends Fragment {
    private ListView lvOffers;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        setRetainInstance(false);
        lvOffers = (ListView) view.findViewById(R.id.lvOffers);
        lvOffers.setAdapter(new ListItemOffersAdapter(getActivity(),R.layout.listitem_offers,list()));
        return view;
    }
    private ArrayList<Job> list(){
        ArrayList<Job> list=new ArrayList<>();
        list.add(new Job());
        list.add(new Job());
        list.add(new Job());
        list.add(new Job());
        list.add(new Job());
        list.add(new Job());
        list.add(new Job());
        return list;
    }
}
