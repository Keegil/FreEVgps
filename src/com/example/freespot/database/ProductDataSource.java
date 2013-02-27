package com.example.freespot.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProductDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.PRODUCT_COLUMN_ID,
      MySQLiteHelper.PRODUCT_COLUMN_NAME, MySQLiteHelper.PRODUCT_COLUMN_PRICE };

  public ProductDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Product createProduct(String name, int price) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.PRODUCT_COLUMN_NAME, name);
    values.put(MySQLiteHelper.PRODUCT_COLUMN_PRICE, price);
    long insertId = database.insert(MySQLiteHelper.TABLE_PRODUCT, null,
        values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_PRODUCT,
        allColumns, MySQLiteHelper.PRODUCT_COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Product pro = cursorToPro(cursor);
    cursor.close();
    return pro;
  }

  
  public void deletePro(Product pro) {
    long id = pro.getId();
    System.out.println("Product deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_PRODUCT, MySQLiteHelper.PRODUCT_COLUMN_ID
        + " = " + id, null);
  }
  
  
  public void updatePro(long id, String name, int price) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.PRODUCT_COLUMN_NAME, name);
	    values.put(MySQLiteHelper.PRODUCT_COLUMN_PRICE, price);
	  open();
	  database.update(MySQLiteHelper.TABLE_PRODUCT, values, "_id=" + id, null);
	  close();
	  }


  public List<Product> getAllPros() {
    List<Product> pros = new ArrayList<Product>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_PRODUCT,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Product pro = cursorToPro(cursor);
      pros.add(pro);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return pros;
  }

  private Product cursorToPro(Cursor cursor) {
   Product pro = new Product();
    pro.setId(cursor.getLong(0));
    pro.setName(cursor.getString(1));
    pro.setPrice(cursor.getInt(2));
    return pro;
  }
} 