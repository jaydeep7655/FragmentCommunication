package com.example.t186.fragmentcommunication.sqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by T186 on 4/11/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "demo.sqlite";
    private static String DB_PATH;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/"
                + DATABASE_NAME;
//        DB_PATH = context.getFilesDir().getAbsolutePath().replace("files",
//                "databases")+File.separator + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);

    }

    public void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS 'product' (" +
                " 'id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'Firstname' TEXT," +
                " 'Lastname' TEXT," +
                " 'Sku' TEXT," +
                " 'email' TEXT," +
                " 'SupplierName' TEXT," +
                " 'city' TEXT" +
                ")");


        db.execSQL("CREATE TABLE IF NOT EXISTS 'supplier' (" +
                " 'SupplierName' TEXT PRIMARY KEY" + ")");
    }


    public void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE If EXISTS android_metadata");
        db.execSQL("DROP TABLE If EXISTS product");
        db.execSQL("DROP TABLE If EXISTS supplier");
    }
}
