package com.iran.ransho.ransho;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
            holder.relativeLayout = (RelativeLayout) row.findViewById(R.id.rowLayout);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.ratingBar = (RatingBar)row.findViewById(R.id.ratingBar);
            holder.deleteButton = (ImageView)row.findViewById(R.id.deleteButton);

            row.setTag(holder);
        }
        else
        {
            holder = (CanteenHolder) row.getTag();
        }

        CanteenContainer canteen = canteens.get(position);
        holder.txtTitle.setText(canteen.getName());
        holder.relativeLayout.setId(Integer.parseInt(canteen.getId()));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Row "+v.getId(),"Clicked!");
            }
        });
        holder.ratingBar.setRating(canteen.getRatingStar() / 2.0f);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Delete Button","Clicked!");
            }
        });

        return row;
    }

    static class CanteenHolder
    {
        RelativeLayout relativeLayout;
        TextView txtTitle;
        RatingBar ratingBar;
        ImageView deleteButton ;
    }
}
