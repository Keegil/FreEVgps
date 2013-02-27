package com.example.freespot.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoggingDataSource {
	

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_TYPE, 
			MySQLiteHelper.COLUMN_DATE,
			MySQLiteHelper.COLUMN_TIME,
			MySQLiteHelper.COLUMN_COSTS, 
			MySQLiteHelper.COLUMN_TOTALCOSTS };

	public LoggingDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Logging createLog(String type, String date, int time, int costs, int totalCosts) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TYPE, type);
		values.put(MySQLiteHelper.COLUMN_DATE, date);
		values.put(MySQLiteHelper.COLUMN_TIME, time);
		values.put(MySQLiteHelper.COLUMN_COSTS, costs);
		values.put(MySQLiteHelper.COLUMN_TOTALCOSTS, totalCosts);
		long insertId = database.insert(MySQLiteHelper.TABLE_LOGGING, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGGING,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Logging logging = cursorToLog(cursor);
		cursor.close();
		return logging;
	}

	public void deleteLog(Logging log) {
		long id = log.getId();
		System.out.println("Log deleted with id: " + id);
		database.delete(MySQLiteHelper.TABLE_LOGGING, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Logging> getAllLogs() {
		List<Logging> logs = new ArrayList<Logging>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGGING,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Logging log = cursorToLog(cursor);
			logs.add(log);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return logs;
	}

	// Getting single log
	public Logging getLog(int id) {

		Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGGING,allColumns, MySQLiteHelper.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
//	public Logging(int id, String type, String date, int time, int cost, int totalCosts) {
		Logging log = new Logging(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
		// return contact
		return log;
	}
	

	private Logging cursorToLog(Cursor cursor) {
		Logging log = new Logging();
		log.setId(cursor.getLong(0));
		log.setType(cursor.getString(1));
		log.setDate(cursor.getString(2));
		log.setTime(cursor.getInt(3));
		log.setCosts(cursor.getInt(4));
		log.setTotalCosts(cursor.getInt(5));
		return log;
	}
}