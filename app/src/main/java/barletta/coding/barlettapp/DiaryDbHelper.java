package barletta.coding.barlettapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DiaryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "diary.db";
    private static final String TABLE_NAME = "diary";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_USERID = "userid";
    private static final String COLUMN_PHOTOPATH = "photopath";

    public DiaryDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERID + " INTEGER, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PHOTOPATH + " TEXT);";

        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertDiary(diaryObject diary, int userID) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERID, userID);
        cv.put(COLUMN_TITLE, diary.getTitle());
        cv.put(COLUMN_DESCRIPTION, diary.getDescription());
        cv.put(COLUMN_PHOTOPATH, diary.getPhoto());

        db.insert(TABLE_NAME, null, cv);

        db.close();

    }

    public ArrayList<diaryObject> getListDiary(int userID) {
        diaryObject diaryAddList;
        ArrayList<diaryObject> diaryList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USERID + " = " + userID + ";";


        if (DatabaseUtils.queryNumEntries(db, TABLE_NAME, COLUMN_USERID + " = " + userID, null) > 0) {
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            do {
                diaryAddList = new diaryObject();
                diaryAddList.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                diaryAddList.setPhotoEncoded(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTOPATH)));
                diaryAddList.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                diaryAddList.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                diaryList.add(diaryAddList);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return diaryList;

    }

    public void deleteFromDiary(int idDelete){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = "+idDelete,null);
        db.close();
    }

    public String getNumberOfRow(int userID){
        String queryCount = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_USERID+" = "+userID+";";

        SQLiteDatabase db = getReadableDatabase();
        Cursor mCursosr = db.rawQuery(queryCount,null);

        String number = String.valueOf(mCursosr.getCount());
        return " ("+number+")";
    }

}
