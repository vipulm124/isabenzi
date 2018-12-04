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
import com.example.isebenzi.business.objects.Offer;
import com.example.isebenzi.business.objects.Provider;
import com.example.isebenzi.business.objects.SearchResult;
import com.example.isebenzi.loopj.android.image.SmartImageView;
import com.example.isebenzi.utils.CommonObjects;
import com.example.isebenzi.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ListItemSearchResultsAdapterAdapter extends ArrayAdapter<Provider> {
    private Context context;
    private int layout;
    private List<Provider> list;


    public ListItemSearchResultsAdapterAdapter(Context context, int layout, List<Provider> list) {
        super(context, layout);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvRateDay;
        TextView tvRateHour;
        TextView tvRating;
        SmartImageView ivProfilePic;
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
            holder.tvRateDay = (TextView) convertView.findViewById(R.id.tvRateDay);
            holder.tvRateHour = (TextView) convertView.findViewById(R.id.tvRateHour);
            holder.tvRating = (TextView) convertView.findViewById(R.id.tvRating);
            holder.ivProfilePic = (SmartImageView) convertView.findViewById(R.id.ivProfilePic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            if (CommonObjects.getUser().getPhoto() != null) {
                holder.ivProfilePic.setImageUrl(Constants.NETWORK_SERVICE_BASE_URL + list.get(position).getPhoto());
            }
            holder.tvName.setText(list.get(position).getFirstname() + " " + list.get(position).getLastname());
            holder.tvRateDay.setText("Rate/Day : " + list.get(position).getId());
            holder.tvRateHour.setText("Rate/Hour : " + list.get(position).getType());
            holder.tvRating.setText("Rating : " + list.get(position).getAverageRating());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
}



