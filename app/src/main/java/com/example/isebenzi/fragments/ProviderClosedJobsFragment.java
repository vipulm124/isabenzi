package com.example.isebenzi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isebenzi.R;
import com.example.isebenzi.adapters.ListItemClosedJobAdapter;
import com.example.isebenzi.business.handlers.AppHandlerNew;
import com.example.isebenzi.business.objects.AcceptJob;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.RequestPayment;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by takhleeqmacpro on 11/29/16.
 */
public class ProviderClosedJobsFragment extends Fragment {
    private ListView lvOffers;
    public static String title = "CLOSED";
    private ListItemClosedJobAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_jobs_closed, container, false);
        init(view,inflater);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void init(View view, LayoutInflater inflater) {
        adapter=new ListItemClosedJobAdapter(getActivity(), R.layout.listitem_offers_closed, CommonObjects.getClosedJobs());
        lvOffers = (ListView) view.findViewById(R.id.lvOffers);
        View footerView = inflater.inflate(R.layout.footer_layout_balance, null);
//        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout_balance, null, false);
        lvOffers.addFooterView(footerView);
        TextView tvBalance = (TextView) footerView.findViewById(R.id.tvBalance);
        tvBalance.setText("Balance : "+CommonObjects.getUser().getBalance());
        Button btnCheckOut = (Button) footerView.findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServer(CommonObjects.getUser().getId());
            }
        });
        lvOffers.setAdapter(adapter);

    }
    private void callServer(String id) {

        CommonMethods.showProgressDialog(getActivity());
        AppHandlerNew.requestPayment(id, new AppHandlerNew.RequestPaymentListner() {
            @Override
            public void onRequestPayment(RequestPayment requestPayment) {
                switch (requestPayment.getMessage()) {
                    case "success":
                        Toast.makeText(getActivity(), "Your Cash out request is forwarded to the admin", Toast.LENGTH_SHORT).show();
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