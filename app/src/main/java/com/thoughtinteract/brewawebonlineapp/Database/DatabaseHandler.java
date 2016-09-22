package com.thoughtinteract.brewawebonlineapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by AzaharSheikh on 21-09-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "brew_a_web_online";

    // product table name
    private static final String TABLE_PRODUCT = "product_list";

    // product Table Columns names
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_PRODUCT_TITLE = "product_title";
    private static final String KEY_PRODUCT_DETAILS = "product_details";
    private static final String KEY_PRODUCT_ADDRESS = "p_address";
    private static final String KEY_IMAGE_URL = "image_url";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCT + "("
                + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY," + KEY_PRODUCT_TITLE + " TEXT,"
                + KEY_PRODUCT_DETAILS + " TEXT," + KEY_PRODUCT_ADDRESS+" TEXT, "+KEY_IMAGE_URL+" TEXT, "+ ")";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);

        // Create tables again
        onCreate(db);
    }
}