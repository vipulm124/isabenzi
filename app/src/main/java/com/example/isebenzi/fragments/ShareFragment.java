package com.example.isebenzi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.isebenzi.R;

/**
 * Created by zain on 6/10/2017.
 */

public class ShareFragment extends Fragment {
    private ListView lvOffers;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        return view;
    }
}
