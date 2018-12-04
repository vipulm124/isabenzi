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
import com.example.isebenzi.business.objects.Rating;
import com.example.isebenzi.business.objects.Review;

import java.util.ArrayList;
import java.util.List;

public class ListItemReviewAdapter extends ArrayAdapter<Rating> {
    private Context context;
    private int layout;
    private List<Rating> list;


    public ListItemReviewAdapter(Context context, int layout, List<Rating> list) {
        super(context, layout);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private static class ViewHolder {
        TextView tvId;
        TextView tvReviewDesc;
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
            holder.tvId = (TextView) convertView.findViewById(R.id.tvId);
            holder.tvReviewDesc= (TextView) convertView.findViewById(R.id.tvReviewDesc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvId.setText(list.get(position).getSeeker());
        holder.tvReviewDesc.setText(list.get(position).getComment());
        return convertView;
    }
}



