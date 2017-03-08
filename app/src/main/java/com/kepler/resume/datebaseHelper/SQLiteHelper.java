package com.kepler.resume.datebaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kepler.resume.support.Params;

/**
 * Creates application database.
 *
 * @author itcuties
 */
class SQLiteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public SQLiteHelper(Context context) {
        // Databse: todos_db, Version: 1
        super(context, "kep_db", null, VERSION);
    }

    /**
     * Create simple table todos _id - key todo - todo text
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute create table SQL
        db.execSQL("CREATE TABLE url_tbl (id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT NOT NULL,status INTEGER DEFAULT 1);");
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", Params.URL_AMITKUMARJAISWAL);
        db.insert("url_tbl", null, contentValues);
    }


    /**
     * Recreates table
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        // DROP table
        db.execSQL("DROP TABLE IF EXISTS url_tbl");
        // Recreate table
        onCreate(db);
    }

}
