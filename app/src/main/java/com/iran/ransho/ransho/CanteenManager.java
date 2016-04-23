package com.iran.ransho.ransho;

import android.content.ContentValues;
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

    public boolean updateCanteen(String id, String name, int rating) {
        Log.d("Database", "Update id=" + id + " name=" + name + " rating=" + rating);
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("rating", rating);
        canteenDB.update("canteen", cv, "id=" + id, null);

        /* String sql = "UPDATE canteen " +
                "SET name='" + name + "',rating=" + rating +
                "WHERE id=" + id;
        try {
            Log.d("Database", "Update id=" + id + " name=" + name + " rating=" + rating);
            canteenDB.execSQL(sql);
        } catch (Exception e) {
            return false;
        }*/
        return true;
    }

    public void insertCanteen(String name, int rating) {
        String sql = "INSERT INTO canteen (name,rating) " +
                "VALUES ('" + name + "', " + rating + ")";
        canteenDB.execSQL(sql);
    }

    public boolean deleteCanteen(String id) {
        String sql = "DELETE FROM canteen " +
                "WHERE id=" + id;
        try {
            Log.d("Database", "Delete id=" + id);
            canteenDB.execSQL(sql);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
