package com.seek.hrm.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.seek.hrm.POJO.HistoryMeasure;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "measureapp.db";

    // Table name: Note.
    private static final String TABLE_NOTE = "Note";

    private static final String COLUMN_ID ="Id";
    private static final String COLUMN_TIME ="Time";
    private static final String COLUMN_RESULT = "Result";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy    HH:mm:ss");

    public SQLiteHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Script to create table.
        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TIME + " DATETIME,"
                + COLUMN_RESULT + " TEXT" + ")";
        // Execute script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);


        // Recreate
        onCreate(db);
    }
    public void addResult(HistoryMeasure historyMeasure) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, sdf.format(historyMeasure.getCreatedAt()));
        values.put(COLUMN_RESULT, historyMeasure.getResult());

        // Inserting Row
        db.insert(TABLE_NOTE, null, values);

        // Closing database connection
        db.close();
    }

    public List<HistoryMeasure> get(int size) throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        List <HistoryMeasure> historyMeasureList = new ArrayList<>();
        Cursor c = db.query(TABLE_NOTE,null,null,null,null,null,null,null);
        c.moveToLast();
        while(!c.isBeforeFirst()){
            int id = c.getInt(0);
            Date time = sdf.parse(c.getString(1));
            int result = Integer.parseInt(c.getString(2));
            //historyMeasureList.add(new HistoryMeasure(id,time,result));
            c.moveToPrevious();
            if(--size==0) break;
        }
        db.close();
        return historyMeasureList;
    }
    public boolean delete(int resultId) {
        SQLiteDatabase db = this.getReadableDatabase();
         boolean success =  db.delete(TABLE_NOTE, COLUMN_ID + " = " + resultId,null) > 0;
         db.close();
         return success;
    }



}