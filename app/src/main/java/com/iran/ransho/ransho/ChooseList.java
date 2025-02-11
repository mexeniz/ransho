package com.iran.ransho.ransho;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import java.util.ArrayList;

public class ChooseList extends AppCompatActivity {

    private String m_Text = "";
    protected static ArrayList<CanteenContainer> canteens;
    protected static ListViewAdapter arrayAdapter;
    protected static CanteenManager canteenManager;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static final String canteenNames = "canteenNames";

    /*public void loadCanteens()
    {
        Set<String> names = sp.getStringSet(canteenNames, null);
        canteens = new ArrayList<>();
        if(names != null)
        {
            String[] nameArrays = names.toArray(new String[names.size()]);
            for(int i = 0; i < nameArrays.length; i++)
            {
                String name = nameArrays[i];
                int rating = sp.getInt(name, 0);
                CanteenContainer canteen = new CanteenContainer(name, rating);
                canteens.add(canteen);
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.listView);
        canteenManager = new CanteenManager(getApplicationContext());
        //Read data from SQLlite
        canteens = canteenManager.getCanteenList();
        arrayAdapter = new ListViewAdapter(this, R.layout.listview_item_row, canteens);
        listView.setAdapter(arrayAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                ratingBar.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                ratingBar.setNumStars(5);
                ratingBar.setStepSize(0.5f);
                layout.addView(ratingBar);

                builder.setView(layout);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        int rate = (int) (ratingBar.getRating() * 2);
                        Log.i("Add Canteen", m_Text + " " + ratingBar.getRating());
                        AddCanteen(m_Text, rate);

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
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void AddCanteen(String name, int score) {
        canteenManager.insertCanteen(name, score);
        canteens.clear();
        canteens.addAll(canteenManager.getCanteenList());
        arrayAdapter.notifyDataSetChanged();
    }
    public static void updateCanteen(String id,String name, int score) {
        canteenManager.updateCanteen(id,name, score);
        canteens.clear();
        canteens.addAll(canteenManager.getCanteenList());
        arrayAdapter.notifyDataSetChanged();
    }
    public static void deleteCanteen(String id) {
        canteenManager.deleteCanteen(id);
        canteens.clear();
        canteens.addAll(canteenManager.getCanteenList());
        arrayAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStop (){
        super.onStop();
        Log.i("ChooseList","Finish");
        MainActivity.setCanteens(this.canteens);
    }

}
