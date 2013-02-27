package com.example.freespot.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	//Logging table
	public static final String TABLE_LOGGING = "logging";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_COSTS = "costs";
	public static final String COLUMN_TOTALCOSTS = "totalCosts";

	//Product table
	public static final String TABLE_PRODUCT = "product";
	public static final String PRODUCT_COLUMN_ID = "_id";
	public static final String PRODUCT_COLUMN_NAME = "name";
	public static final String PRODUCT_COLUMN_PRICE = "price";
	
	
	private static final String DATABASE_NAME = "loggings3.db";
	private static final int DATABASE_VERSION = 1;
	
	// Database creation sql statement table logging
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_LOGGING + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_TYPE
			+ " text not null, " + COLUMN_DATE 
			+ " text not null, " + COLUMN_TIME
			+ " text not null, " + COLUMN_COSTS
			+ " text not null, " + COLUMN_TOTALCOSTS +" text not null);";
	

	// Database creation sql statement table product
	private static final String DATABASE_CREATE_PRODUCT = "create table "
			+ TABLE_PRODUCT + "(" + PRODUCT_COLUMN_ID
			+ " integer primary key autoincrement, " + PRODUCT_COLUMN_NAME
			+ " text not null, " + PRODUCT_COLUMN_PRICE + " text not null);";


	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		database.execSQL(DATABASE_CREATE_PRODUCT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGGING);
		onCreate(db);
	}

}