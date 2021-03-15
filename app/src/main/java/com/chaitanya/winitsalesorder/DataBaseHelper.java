package com.chaitanya.winitsalesorder;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

public class DataBaseHelper {

    public static DataBaseHelper dataBaseHelper;
    private static DataBase db;
    private SQLiteDatabase mDb;
    public Context context;

    public DataBaseHelper(Context context) {
        this.context = context;
        db = new DataBase(context);

    }

    public static DataBaseHelper getInstance(Context mcontext) {

        if (dataBaseHelper == null) {

            dataBaseHelper = new DataBaseHelper(mcontext);
        }

        return dataBaseHelper;
    }

    public void open() {
        this.mDb = db.getReadableDatabase();
    }

    public Cursor getSku() {


        Cursor sku_cursor = null;
        try {
            sku_cursor = mDb.rawQuery("SELECT * FROM SKUAttributes ", null);
            /*Cursor dbCursor = mDb.query(table_name, null, null, null, null, null, null);
            String[] columnNames = dbCursor.getColumnNames();
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sku_cursor;
    }

    public Cursor getSkuprice(String Code) {
        //"SlabName" , "Price"
        Cursor cursor = null;
        try {
            cursor = mDb.rawQuery("Select skp.Id as code,skp.PriceLowerLimit as Pr,sk.Name as itemName from " +
                    "SKUPrice as skp INNER JOIN SKU as sk on skp.SKUCode = sk.Code where skp.SKUCode ='" + Code + "'", null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cursor;
    }

}
