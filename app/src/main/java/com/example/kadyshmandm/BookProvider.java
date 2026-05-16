package com.example.kadyshmandm;

import android.content.ContentProvider;
import android.content.ContentUris;  // ← Обязательно!
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BookProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.kadyshmandm.provider";
    public static final String PATH_BOOKS = "books";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_BOOKS);

    private static final int BOOKS = 1;
    private static final int BOOK_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, PATH_BOOKS, BOOKS);
        uriMatcher.addURI(AUTHORITY, PATH_BOOKS + "/#", BOOK_ID);
    }

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            dbHelper = new DatabaseHelper(context);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        db = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);
        switch (match) {
            case BOOK_ID:
                selection = "_id=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            case BOOKS:
                cursor = db.query("books", projection, selection, selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("Неизвестный URI: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return "vnd.android.cursor.dir/vnd.example.book";
            case BOOK_ID:
                return "vnd.android.cursor.item/vnd.example.book";
            default:
                throw new IllegalArgumentException("Неизвестный URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        if (match != BOOKS) {
            throw new IllegalArgumentException("Невозможно вставить в: " + uri);
        }

        long id = db.insert("books", null, values);
        if (id > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int count;

        switch (match) {
            case BOOKS:
                count = db.delete("books", selection, selectionArgs);
                break;
            case BOOK_ID:
                String id = String.valueOf(ContentUris.parseId(uri));
                count = db.delete("books", "_id=?", new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Неизвестный URI: " + uri);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int count;

        switch (match) {
            case BOOKS:
                count = db.update("books", values, selection, selectionArgs);
                break;
            case BOOK_ID:
                String id = String.valueOf(ContentUris.parseId(uri));
                count = db.update("books", values, "_id=?", new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Неизвестный URI: " + uri);
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }
}