package com.kepler.resume.datebaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import com.kepler.resume.support.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * TODOs DAO object.
 *
 * @author itcuties
 */
public class UrlDAO {

    private final SQLiteDatabase db;
    private final SQLiteHelper dbHelper;

    public UrlDAO(Context context) {
        dbHelper = new SQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private UrlDAO(Fragment context) {
        dbHelper = new SQLiteHelper(context.getActivity());
        db = dbHelper.getWritableDatabase();
    }

    // Close the db
    public void close() {
        db.close();
    }

    /**
     * Create new TODO object
     */


    // ******************************URL SECTION ******************************* //
    public void insert_url(URLSetterGetter urlSetterGetter) {
        if (searchUrl_url_tbl(urlSetterGetter.getURL())) {
            update_url(urlSetterGetter.getURL(), 1);
            return;
        }
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", urlSetterGetter.getURL());
            // Insert into DB
            db.insert("url_tbl", null, contentValues);
        } catch (Exception e) {
//            Logger.printTrash(e);
        }
    }

    public ArrayList<URLSetterGetter> get_url(int status) {
        ArrayList<URLSetterGetter> urlSetterGetters = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from url_tbl where status=" + status, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int id = cursor.getInt(cursor
                        .getColumnIndex("id"));

                String url = cursor.getString(cursor
                        .getColumnIndex("url"));

                URLSetterGetter urlSetterGetter = new URLSetterGetter();
                urlSetterGetter.setId(id);
                urlSetterGetter.setURL(url);
                urlSetterGetters.add(urlSetterGetter);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return urlSetterGetters;
    }

    public boolean remove_url(int id) {
        return update_url(id, 0);
    }

    private boolean update_url(int id, int status) {
        ContentValues cv = new ContentValues();
        cv.put("status", status); //These Fields should be your String values of actual column names
        if (db.update("url_tbl", cv, "id=?", new String[]{String.valueOf(id)}) > 0)
            return true;
        return false;
    }

    private boolean update_url(String url, int status) {
        ContentValues cv = new ContentValues();
        cv.put("status", status); //These Fields should be your String values of actual column names
        if (db.update("url_tbl", cv, "url=?", new String[]{url}) > 0)
            return true;
        return false;
    }

    public boolean searchUrl_url_tbl(String url) {
        boolean isAvailable = false;
        String query = "select * from url_tbl where url = '"
                + url + "'";
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                isAvailable = true;
            }
        }
        c.close();
        return isAvailable;
    }

    public int count_url() {
        String countQuery = "SELECT  * FROM url_tbl";
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

}
