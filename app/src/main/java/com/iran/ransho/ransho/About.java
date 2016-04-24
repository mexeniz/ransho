package com.iran.ransho.ransho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class About extends AppCompatActivity {
    public void onCloseClicked (View view){
        Log.i("Close Button", "Clicked!");
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels ;
        int height = dm.heightPixels ;
        getWindow().setLayout((int)(width*0.8), (int)(height*0.6f));
    }
}
