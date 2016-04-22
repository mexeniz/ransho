package com.iran.ransho.ransho;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ASUS on 22/4/2559.
 */
public class CanteenManager {
    SQLiteDatabase canteenDB;

    public CanteenManager(Context context) {
        try {
            canteenDB = context.openOrCreateDatabase("Ransho", Context.MODE_PRIVATE, null);
            canteenDB.execSQL("CREATE TABLE IF NOT EXISTS canteen (id INTEGER PRIMARY KEY , name VARCHAR , rating INTEGER(3))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Canteen> getCanteenList() {
        String sql = "";
        ArrayList<Canteen> result = new ArrayList<Canteen>();

        Cursor c = canteenDB.rawQuery("SELECT * FROM canteen", null);
        Log.i("Cursor Count", Integer.toString(c.getCount()));
        int nameIndex = c.getColumnIndex("name");
        int ratingIndex = c.getColumnIndex("rating");
        int idIndex = c.getColumnIndex("id");

        c.moveToFirst();
        int i = 0;
        while (c != null) {
            String name = c.getString(nameIndex);
            int rating = c.getInt(ratingIndex);
            String id = Integer.toString(c.getInt(idIndex));
            Log.i(i + " Canteen", id + " " + name + " " + rating);

            result.add(new Canteen(id, name, rating));
            i++;
            if (!c.moveToNext()) break;

        }

        return result;
    }
}
