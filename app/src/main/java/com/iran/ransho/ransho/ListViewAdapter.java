package com.iran.ransho.ransho;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 19/04/2016.
 */
public class ListViewAdapter extends ArrayAdapter<CanteenContainer> {

    private AppCompatActivity activity;
    Context context;
    int layoutResourceId;
    List<CanteenContainer> canteens;

    public ListViewAdapter(Context context, int resource, List<CanteenContainer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.canteens = objects;
    }

    @Override
    public CanteenContainer getItem(int position)
    {
        return canteens.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CanteenHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CanteenHolder();
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.ratingBar = (RatingBar)row.findViewById(R.id.ratingBar);

            row.setTag(holder);
        }
        else
        {
            holder = (CanteenHolder) row.getTag();
        }

        CanteenContainer canteen = canteens.get(position);
        holder.txtTitle.setText(canteen.getName());
        holder.txtTitle.setId(Integer.parseInt(canteen.getId()));
        holder.ratingBar.setRating(canteen.getRatingStar() / 2.0f);

        return row;
    }

    static class CanteenHolder
    {
        TextView txtTitle;
        RatingBar ratingBar;
        ImageButton editButton ;
    }
}
