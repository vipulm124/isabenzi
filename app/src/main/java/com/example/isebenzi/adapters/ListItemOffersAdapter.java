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

import java.util.ArrayList;
import java.util.List;

public class ListItemOffersAdapter extends ArrayAdapter<Job> {
    private Context context;
    private int layout;
    private List<Job> list;


    public ListItemOffersAdapter(Context context, int layout, List<Job> list) {
        super(context, layout);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvTimeDate;
    }

    @Override
    public int getCount() {
        try {
            return list.size();
        }catch (Exception e ){
            e.printStackTrace();
        }
        return 0;
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
            holder.tvTimeDate = (TextView) convertView.findViewById(R.id.tvTimeDate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(list.get(position).getSeeker());
        holder.tvTimeDate.setText(list.get(position).getFromDate());
        return convertView;
    }
}



