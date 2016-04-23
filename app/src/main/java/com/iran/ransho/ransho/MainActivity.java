package com.iran.ransho.ransho;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private CanteenManager canteenManager;

    public void clickRandom(View view)
    {
        TextView textView = (TextView) findViewById(R.id.resultText);

        if(canteens.size() == 0)
        {
            textView.setText("No Canteen in List");
            return;
        }

        Random rand = new Random();
        sumAllRate = sumAllRate();
        int randomNumber = rand.nextInt(sumAllRate) + 1;
        int cumulative = 0;
        for(int i = 0; i < canteens.size() ; i++)
        {
            cumulative += canteens.get(i).getRatingStar() + 1;
            if(randomNumber <= cumulative)
            {
                textView.setText(canteens.get(i).getName());
                return;
            }
        }
    }

    private static ArrayList<CanteenContainer> canteens;
    private int sumAllRate;

    /*public void loadCanteens()
    {
        SharedPreferences sp = getSharedPreferences("Canteens", Context.MODE_PRIVATE);
        Set<String> names = sp.getStringSet(ChooseList.canteenNames, null);
        canteens = new ArrayList<>();
        sumAllRate = 0;
        if(names != null)
        {
            String[] nameArrays = names.toArray(new String[names.size()]);
            for(int i = 0; i < nameArrays.length; i++)
            {
                String name = nameArrays[i];
                int rating = sp.getInt(name, 0);
                CanteenContainer canteen = new CanteenContainer(name, rating);
                canteens.add(canteen);

                sumAllRate += rating;
            }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        canteenManager = new CanteenManager(getApplicationContext());
        canteens = canteenManager.getCanteenList();
        sumAllRate = sumAllRate();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
            Intent i = new Intent(MainActivity.this, ChooseList.class);
            startActivity(i);
        }else if(id == R.id.action_about){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int sumAllRate(){
        int result = 0 ;
        if(canteens != null)
        {
            for(int i = 0; i < canteens.size(); i++)
            {
                CanteenContainer canteen = canteens.get(i);
                int rating = canteen.getRatingStar();
                result += rating + 1;
            }
        }
        Log.d("Sum All Rate" ,""+sumAllRate);
        return result;
    }

    public static void setCanteens(ArrayList<CanteenContainer> canteens){
        MainActivity.canteens = canteens;
    }
}
