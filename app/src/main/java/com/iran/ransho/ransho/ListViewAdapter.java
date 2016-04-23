package com.iran.ransho.ransho;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Movie;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
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

        final CanteenContainer canteen = canteens.get(position);
        holder.txtTitle.setText(canteen.getName());
        holder.relativeLayout.setId(Integer.parseInt(canteen.getId()));
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("Row "+v.getId(),"Clicked!");
//            }
//        });
        holder.ratingBar.setRating(1.0f * canteen.getRatingStar() / 2.0f);
        // If player change rating bar update it to database still not work

//        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                if(fromUser)
//                {
//                    ChooseList.updateCanteen(canteen.getId(),canteen.getName(), (int)(rating * 2));
//                }
//            }
//        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this canteen?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ChooseList.deleteCanteen(canteen.getId());
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Canteen");

                // Set up the input
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);


                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(canteen.getName());
                layout.addView(input);

                final RatingBar ratingBar = new RatingBar(context);
                ratingBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                float stepSize = canteen.getRatingStar()/2.0f;
                ratingBar.setRating(stepSize);
                ratingBar.setNumStars(5);
                ratingBar.setStepSize(0.5f);

                layout.addView(ratingBar);

                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        int rate = (int) (ratingBar.getRating() * 2);
                        Log.i("Update Canteen", m_Text + " " + rate);
                        ChooseList.updateCanteen(canteen.getId(),m_Text, rate);

                        /*Set<String> tempStr = sp.getStringSet(canteenNames, new HashSet<String>());
                        tempStr.add(m_Text);
                        editor.putStringSet(canteenNames, tempStr);
                        editor.putInt(m_Text, rate);
                        editor.commit();*/
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
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
