package com.example.kadyshmandm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "app_data.db";
    private static final int DB_VERSION = 2;

    // === Таблица items (Практика 5) ===
    private static final String TABLE_ITEMS = "items";
    private static final String COL_ITEM_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_DESCRIPTION = "description";
    private static final String COL_VALUE = "value";
    private static final String COL_CATEGORY = "category";

    // === Таблица books (Практика 6 - Content Provider) ===
    private static final String TABLE_BOOKS = "books";
    private static final String COL_BOOK_ID = "_id";
    private static final String COL_BOOK_TITLE = "title";
    private static final String COL_AUTHOR = "author";
    private static final String COL_YEAR = "year";
    private static final String COL_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createItemsTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_VALUE + " REAL, " +
                COL_CATEGORY + " TEXT)";
        db.execSQL(createItemsTable);

        String createBooksTable = "CREATE TABLE " + TABLE_BOOKS + " (" +
                COL_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_BOOK_TITLE + " TEXT, " +
                COL_AUTHOR + " TEXT, " +
                COL_YEAR + " INTEGER, " +
                COL_PRICE + " REAL)";
        db.execSQL(createBooksTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }

    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, item.getTitle());
        cv.put(COL_DESCRIPTION, item.getDescription());
        cv.put(COL_VALUE, item.getValue());
        cv.put(COL_CATEGORY, item.getCategory());
        long result = db.insert(TABLE_ITEMS, null, cv);
        db.close();
        return result != -1;
    }

    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ITEMS, null);
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getString(4)
                );
                list.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Item findItem(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS,
                new String[]{COL_ITEM_ID, COL_TITLE, COL_DESCRIPTION, COL_VALUE, COL_CATEGORY},
                COL_TITLE + "=?", new String[]{title}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Item item = new Item(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );
            cursor.close();
            db.close();
            return item;
        }
        if (cursor != null) cursor.close();
        db.close();
        return null;
    }

    public boolean updateItem(int id, String newTitle, String newDescription, double newValue, String newCategory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, newTitle);
        cv.put(COL_DESCRIPTION, newDescription);
        cv.put(COL_VALUE, newValue);
        cv.put(COL_CATEGORY, newCategory);
        int result = db.update(TABLE_ITEMS, cv, COL_ITEM_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ITEMS, COL_ITEM_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }


    public boolean addBook(String title, String author, int year, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOK_TITLE, title);
        cv.put(COL_AUTHOR, author);
        cv.put(COL_YEAR, year);
        cv.put(COL_PRICE, price);
        long result = db.insert(TABLE_BOOKS, null, cv);
        db.close();
        return result != -1;
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOKS, null);
    }

    public Cursor findBook(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BOOKS, null, COL_BOOK_TITLE + "=?",
                new String[]{title}, null, null, null);
    }

    public boolean updateBook(int id, String title, String author, int year, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_BOOK_TITLE, title);
        cv.put(COL_AUTHOR, author);
        cv.put(COL_YEAR, year);
        cv.put(COL_PRICE, price);
        int result = db.update(TABLE_BOOKS, cv, COL_BOOK_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    public boolean deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOKS, COL_BOOK_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}