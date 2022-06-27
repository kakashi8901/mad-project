package com.foddie.food;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "RestroDataBase";
    public static final String USER_INFO_TABLE_NAME = "UserInfoTable";
    public static final String RESTRO_INFO_TABLE_NAME = "RestroInfoTable";
    public static final String REVIEW_INFO_TABLE_NAME = "ReviewInfoTable";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +USER_INFO_TABLE_NAME+
                "( " +
                Util.ID+" TEXT, " +
                Util.USER_ID+" TEXT, " +
                Util.USER_USER_NAME+" TEXT, " +
                Util.USER_EMAIL_NAME+" TEXT PRIMARY KEY, " +
                Util.USER_PASSWORD+" TEXT, " +
                Util.USER_TYPE+" TEXT ) ";

        String restro_sql = "CREATE TABLE " +RESTRO_INFO_TABLE_NAME+
                "( " +
                Util.RESTRO_ID+" TEXT PRIMARY KEY, " +
                Util.RESTRO_NAME+" TEXT, " +
                Util.RESTRO_SMALL_DESC+" TEXT, " +
                Util.RESTRO_DESC+" TEXT, " +
                Util.RESTRO_CREATE_BY+" TEXT, " +
                Util.RESTRO_CREATE_DATE+" TEXT) ";

        String review_sql = "CREATE TABLE " +REVIEW_INFO_TABLE_NAME+
                "( " +
                Util.REVIEW_ID+" TEXT PRIMARY KEY, " +
                Util.REVIEW_COMMENT+" TEXT, " +
                Util.REVIEW_RATING+" TEXT, " +
                Util.RESTRO_ID+" TEXT, " +
                Util.REVIEW_DATE+" TEXT, " +
                Util.REVIEW_GIVEN_BY_EMAIL+" TEXT, " +
                Util.REVIEW_GIVEN_BY+" TEXT) ";

        db.execSQL(sql);
        db.execSQL(restro_sql);
        db.execSQL(review_sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+USER_INFO_TABLE_NAME;
        String restro_sql = "DROP TABLE IF EXISTS "+RESTRO_INFO_TABLE_NAME;
        String review_sql = "DROP TABLE IF EXISTS "+REVIEW_INFO_TABLE_NAME;
        db.execSQL(sql);
        db.execSQL(restro_sql);
        db.execSQL(review_sql);
        onCreate(db);
    }

    public SQLiteDatabase getReadableObject()
    {
        return this.getReadableDatabase();
    }
}
