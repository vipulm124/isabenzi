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

import java.util.List;

public class ListItemClosedJobAdapter extends ArrayAdapter<Job> {
    private Context context;
    private int layout;
    private List<Job> list;


    public ListItemClosedJobAdapter(Context context, int layout, List<Job> list) {
        super(context, layout);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvTimeDate;
        TextView tvAmount;
        TextView tvBalance;
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
            holder.tvTimeDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.tvAmount = (TextView) convertView.findViewById(R.id.tvAmount);
            holder.tvBalance = (TextView) convertView.findViewById(R.id.tvBalance);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String dateTime =list.get(position).getFromDate();
        String[] separated = dateTime.split(" ");
        String date = separated[0];
//        String time = separated[1];
        holder.tvName.setText(list.get(position).getSeeker());
        holder.tvTimeDate.setText(date);
        holder.tvAmount.setText(list.get(position).getPerDay()+"/per day");
        return convertView;
    }
}



