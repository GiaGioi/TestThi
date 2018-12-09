package zedcode.giagioi.testthi.sqlDAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import zedcode.giagioi.testthi.database.Constant;
import zedcode.giagioi.testthi.database.DatabaseHelper;
import zedcode.giagioi.testthi.model.AddMap;

import static android.content.ContentValues.TAG;

public class AddMapDAO implements Constant {
    DatabaseHelper databaseHelper;

    public AddMapDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void insertMap(AddMap addMap) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_latitude, addMap.getLatitude());
        values.put(COLUMN_longitude, addMap.getLongitude());

        long id = db.insert(TABLE_NAME, null, values);

        Log.e("insertMaps", "insert : " + id);

        db.close();
    }

    public int updateMap(AddMap addMap) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("latitude", addMap.getLatitude());
        values.put("longitude", addMap.getLongitude());
        int result = db.update(TABLE_NAME, values, "=?", new
                String[]{String.valueOf(addMap.getLatitude())});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int deleteMap(String username) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "=?", new String[]{username});
        if (result == 0)
            return -1;
        return 1;
    }

    public AddMap getAddMap(String id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        List<AddMap> typeBookList = new ArrayList<>();
        AddMap ee = new AddMap(-34, 151);
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {

            ee.setLatitude(c.getInt(0));
            ee.setLongitude(c.getInt(1));

            typeBookList.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return ee;
    }

    public List<AddMap> getAllMap() {
        List<AddMap> mapsList = new ArrayList<>();

        String SELECT_ALL_Maps = "SELECT * FROM " + TABLE_NAME;

        Log.e("getAllMaps", SELECT_ALL_Maps);

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL_Maps, null);
        if (cursor != null && cursor.moveToFirst()) {


            do {
//                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                double lat = cursor.getDouble(cursor.getColumnIndex(COLUMN_latitude));
                double lng = cursor.getDouble(cursor.getColumnIndex(COLUMN_longitude));

                AddMap maps = new AddMap();
//                maps.setId(id);
                maps.setLatitude(lat);
                maps.setLongitude(lng);

                mapsList.add(maps);


            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();

        return mapsList;
    }

    public AddMap getmapsbyName(String map) {

        AddMap maps = null;

        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,
                new String[]{COLUMN_latitude, COLUMN_longitude},
                COLUMN_latitude + "=?",
                new String[]{map}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

//            String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            double lat = cursor.getDouble(cursor.getColumnIndex(COLUMN_latitude));
            double lng = cursor.getDouble(cursor.getColumnIndex(COLUMN_longitude));

            maps = new AddMap();
//            maps.setId(id);
            maps.setLatitude(lat);
            maps.setLongitude(lng);
        }

        return maps;
    }
}
