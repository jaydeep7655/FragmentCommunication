package com.example.t186.fragmentcommunication.sqlitedatabase;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.t186.fragmentcommunication.sqliteentity.Result;

import java.util.ArrayList;

/**
 * Created by T186 on 4/11/2018.
 */

public class ProductDatabase {
    static String ID = "id";
    static String FIRSTNAME = "Firstname";
    static String LASTNAME = "Lastname";
    static String SKU = "Sku";
    static String EMAIL = "email";
    static String SUPPLIERNAME = "SupplierName";
    static String CITY = "city";
    static String TBL_DB = "product";
    static String TBL_DB_SUPPLIER = "supplier";
    DatabaseHelper helper;
    SQLiteDatabase database;
    long result = 0;

    Result resultEntity;

    ArrayList<String> alSupplierName = new ArrayList<String>();
    Activity activity;

    public ProductDatabase(Activity context) {
        activity = context;
        helper = new DatabaseHelper(context);
    }

    public void open()        throws SQLException {
        database = helper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public long addProduct(String firstname, String lastname, String sku, String email, String suppliername, String city) {
        result = 0;

        if (result == 0) {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FIRSTNAME, firstname);
            contentValues.put(LASTNAME, lastname);
            contentValues.put(SKU, sku);
            contentValues.put(EMAIL, email);
            contentValues.put(SUPPLIERNAME, suppliername);
            contentValues.put(CITY, city);

            result = database.insertWithOnConflict(TBL_DB, null, contentValues, 0);
            close();
        }

        return result;
    }

    public long addSuppliers(String suppliername) {
        result = 0;

        if (result == 0) {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SUPPLIERNAME, suppliername);

            result = database.insertWithOnConflict(TBL_DB_SUPPLIER, null, contentValues, 0);
            close();
        }

        return result;
    }

    public ArrayList<Result> selectAllSupplierName() {
        open();
        ArrayList<Result> alResult = new ArrayList<Result>();
        Log.d("Supplier Name Query ","SELECT " + SUPPLIERNAME + " FROM " + TBL_DB);
        Cursor c1 = database.rawQuery("SELECT " + SUPPLIERNAME + " FROM " + TBL_DB, null);

        if (c1.getCount() > 0) {
            c1.moveToFirst();
            do {
                resultEntity = new Result();
                resultEntity.setSupplierName(c1.getString(c1.getColumnIndex(SUPPLIERNAME)));
                alResult.add(resultEntity);

            } while (c1.moveToNext());
        }
        close();
        return alResult;
    }


    public ArrayList<String> selectSupplierName() {
        open();
        alSupplierName.clear();
        alSupplierName = new ArrayList<String>();
        Cursor c1 = database.rawQuery("SELECT  *  FROM " + TBL_DB_SUPPLIER, null);

        if (c1.getCount() > 0) {
            c1.moveToFirst();
            do {
                alSupplierName.add(c1.getString(c1.getColumnIndex(SUPPLIERNAME)));

            } while (c1.moveToNext());
        }
        close();
        return alSupplierName;
    }


    public ArrayList<Result> selectData() {
        open();
        ArrayList<Result> alResult = new ArrayList<Result>();
        Cursor c1 = database.rawQuery("SELECT  *  FROM " + TBL_DB, null);

        if (c1.getCount() > 0) {
            c1.moveToFirst();
            do {
                resultEntity = new Result();
                resultEntity.setFirstname(c1.getString(c1.getColumnIndex(FIRSTNAME)));
                resultEntity.setLastname(c1.getString(c1.getColumnIndex(LASTNAME)));
                resultEntity.setSku(c1.getString(c1.getColumnIndex(SKU)));
                resultEntity.setEmail(c1.getString(c1.getColumnIndex(EMAIL)));
                resultEntity.setSupplierName(c1.getString(c1.getColumnIndex(SUPPLIERNAME)));
                resultEntity.setCity(c1.getString(c1.getColumnIndex(CITY)));

                alResult.add(resultEntity);

            } while (c1.moveToNext());
        }
        close();
        return alResult;
    }

    public ArrayList<Result> selectDataBySupplierName(String name) {
        open();
        ArrayList<Result> alResult = new ArrayList<Result>();
        Cursor c1 = database.rawQuery("SELECT  *  FROM " + TBL_DB + " where "
                + SUPPLIERNAME + "='" + name + "'", null);

        if (c1.getCount() > 0) {
            c1.moveToFirst();
            do {
                resultEntity = new Result();
                resultEntity.setFirstname(c1.getString(c1.getColumnIndex(FIRSTNAME)));
                resultEntity.setLastname(c1.getString(c1.getColumnIndex(LASTNAME)));
                resultEntity.setSku(c1.getString(c1.getColumnIndex(SKU)));
                resultEntity.setEmail(c1.getString(c1.getColumnIndex(EMAIL)));
                resultEntity.setSupplierName(c1.getString(c1.getColumnIndex(SUPPLIERNAME)));
                resultEntity.setCity(c1.getString(c1.getColumnIndex(CITY)));

                alResult.add(resultEntity);

            } while (c1.moveToNext());
        }
        close();
        return alResult;
    }


    public long removeOfflineFavourite(String productId) {
        result = 0;
        open();

        String where = SKU + "='" + productId + "'";
        result = database.delete(TBL_DB, where, null);

        close();
        return result;
    }

    /**
     * Remove all the favourite product from offline database.
     *
     * @return return 0 because no whereClause has been passed to delete method.
     */

    public void deleteAllTable() {
        open();

        database.delete(TBL_DB, null, null);
        database.delete(TBL_DB_SUPPLIER, null, null);

        close();
    }
}
