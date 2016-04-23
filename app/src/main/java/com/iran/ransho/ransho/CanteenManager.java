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
    private SQLiteDatabase canteenDB;

    public CanteenManager(Context context) {
        try {
            canteenDB = context.openOrCreateDatabase("Ransho", Context.MODE_PRIVATE, null);
            canteenDB.execSQL("CREATE TABLE IF NOT EXISTS canteen (id INTEGER PRIMARY KEY , name VARCHAR , rating INTEGER(3))");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CanteenContainer> getCanteenList() {
        String query = "SELECT * FROM canteen";
        ArrayList<CanteenContainer> result = new ArrayList<CanteenContainer>();

        Cursor c = canteenDB.rawQuery(query, null);
        Log.i("Cursor Count", Integer.toString(c.getCount()));
        int nameIndex = c.getColumnIndex("name");
        int ratingIndex = c.getColumnIndex("rating");
        int idIndex = c.getColumnIndex("id");

        c.moveToFirst();
        int i = 0;
        while (c != null && c.getCount() > 0) {
            String name = c.getString(nameIndex);
            int rating = c.getInt(ratingIndex);
            String id = Integer.toString(c.getInt(idIndex));
            Log.i(i + " Canteen", id + " " + name + " " + rating);

            result.add(new CanteenContainer(id, name, rating));
            i++;
            if (!c.moveToNext()) break;

        }

        return result;
    }

    public void addCanteen(String name, int rating) {
        String sql = "INSERT INTO canteen (name,rating) VALUES ('" + name + "', " + rating + ")";
        canteenDB.execSQL(sql);
    }
}
