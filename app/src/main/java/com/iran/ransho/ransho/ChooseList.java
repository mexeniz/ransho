package com.iran.ransho.ransho;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;

public class ChooseList extends AppCompatActivity {

    private String m_Text = "";
    ArrayList<CanteenContainer> canteens;
    ListViewAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.listView);
        canteens = new ArrayList();
        arrayAdapter = new ListViewAdapter(this, R.layout.listview_item_row, canteens);
        listView.setAdapter(arrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseList.this);
                builder.setTitle("New Canteen");

                // Set up the input
                LinearLayout layout = new LinearLayout(ChooseList.this);
                layout.setOrientation(LinearLayout.VERTICAL);


                final EditText input = new EditText(ChooseList.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                layout.addView(input);

                final RatingBar ratingBar = new RatingBar(ChooseList.this);
                ratingBar.setNumStars(5);
                ratingBar.setStepSize(0.5f);
                layout.addView(ratingBar);

                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        int rate = (int)(ratingBar.getRating() * 2);
                        AddCanteen(m_Text, rate);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void AddCanteen(String name, int score)
    {
        CanteenContainer canteenContainer = new CanteenContainer(name, score);
        canteens.add(canteenContainer);
        arrayAdapter.notifyDataSetChanged();
    }

}
