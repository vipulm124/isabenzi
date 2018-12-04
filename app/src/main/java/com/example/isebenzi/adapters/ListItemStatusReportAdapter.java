package com.example.isebenzi.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.isebenzi.R;
import com.example.isebenzi.business.objects.Job;
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.business.objects.StatusReport;

import java.util.ArrayList;
import java.util.List;

public class ListItemStatusReportAdapter extends ArrayAdapter<Job> {
    private Context context;
    private int layout;
    private List<Job> list;


    public ListItemStatusReportAdapter(Context context, int layout, List<Job> list) {
        super(context, layout);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvDate;
        TextView tvTime;
        TextView tvStatus;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflator = ((Activity) context).getLayoutInflater();
        if (convertView == null) {
            convertView = inflator.inflate(layout, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String[] parts = list.get(position).getFromDate().split(" ");
        String date = parts[0]; // 004
        String time = parts[1]; //
        holder.tvName.setText(list.get(position).getProvider());
        holder.tvDate.setText(date);
        holder.tvTime.setText(time);
        if (list.get(position).getStatus().equals("0"))
            holder.tvStatus.setText("Pending");
        else if (list.get(position).getStatus().equals("1"))
            holder.tvStatus.setText("Open");
        else if (list.get(position).getStatus().equals("2"))
            holder.tvStatus.setText("Open");
        else if (list.get(position).getStatus().equals("3"))
            holder.tvStatus.setText("Closed");
        return convertView;
    }
}



