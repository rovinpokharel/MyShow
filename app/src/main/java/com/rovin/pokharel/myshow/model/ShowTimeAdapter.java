package com.rovin.pokharel.myshow.model;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rovin.pokharel.myshow.R;

import java.util.List;

/**
 * Created by Rovin on 8/11/2018.
 */

public class ShowTimeAdapter extends ArrayAdapter<Time> {
    public ShowTimeAdapter(@NonNull Context context, @NonNull List<Time> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Time time = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rowitem_show_time, parent, false);
        }

        TextView timeTV = (TextView) convertView.findViewById(R.id.row_showtime_time);
        timeTV.setText(time.getShowTime());
        return convertView;
    }
}
