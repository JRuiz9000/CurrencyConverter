//Database Handler , made in reference to Professor Tsai's code
//Changed variable names and records to best fit my project
//Does not properly run within activities as app crashes, within specific code in each multiple activity
//Those sections are commented out in code
package com.deitel.finalproject.jruiz.finalproject;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "convertedCurrencyRecords.db";
    public static final String TABLE_PRODUCTS = "Currency Records";
    //public static final String COLUMN_ID = "_id";
    public static final String COLUMN_AMOUNT = "Initial Amount";
    public static final String COLUMN_CONCURRENCY = "Con Currency";

    //We need to pass database information along to superclass
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_AMOUNT + " DOUBLE," + COLUMN_CONCURRENCY + " DOUBLE );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addProduct(Products product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONCURRENCY, product.get_conCurrency());
        values.put(COLUMN_AMOUNT, product.get_amount());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteProduct(double amount){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_AMOUNT+ "='" + amount + "';");
    }

    // this is goint in record_TextView in the Main activity.
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Amount")) != null) {
                //dbString += recordSet.getString(recordSet.getColumnIndex("productname"));
                dbString += recordSet.getString(0);
                dbString += ",   ";
                //dbString += recordSet.getString(recordSet.getColumnIndex("price"));
                dbString += recordSet.getString(1);
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        //dbString += "\n";

        db.close();
        return dbString;
    }

    public String viewToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT Converted Currency FROM " + TABLE_PRODUCTS + " GROUP BY Amount;";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Amount")) != null) {
                // dbString += recordSet.getString(recordSet.getColumnIndex("Amount"));
                dbString += recordSet.getString(0);
                dbString += ",   ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Con Currency"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

}
