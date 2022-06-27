package com.foddie.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.foddie.food.models.RestrauntModel;
import com.foddie.food.models.ReviewModel;
import com.foddie.food.models.UserInfoModel;

import java.util.ArrayList;
import java.util.List;

class DBManager {
    private DatabaseHandler dbHandler;

    private Context context;

    private SQLiteDatabase mDatabase;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHandler = new DatabaseHandler(context);
        mDatabase = dbHandler.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHandler.close();
    }

    public void insert(UserInfoModel userInfoModel) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(Util.USER_ID, userInfoModel.getUserID());
        contentValue.put(Util.USER_EMAIL_NAME, userInfoModel.getmUserEmailId());
        contentValue.put(Util.USER_PASSWORD, userInfoModel.getmUserPassWord());
        contentValue.put(Util.USER_USER_NAME, userInfoModel.getmUserName());
        contentValue.put(Util.USER_TYPE, userInfoModel.getmUserType());
        contentValue.put(Util.ID, userInfoModel.getmUserId());
        mDatabase.insert(DatabaseHandler.USER_INFO_TABLE_NAME, null, contentValue);
    }

    public void insertRestraunt(RestrauntModel restrauntModel) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(Util.RESTRO_ID, restrauntModel.getmRestrauntId());
        contentValue.put(Util.RESTRO_NAME, restrauntModel.getmName());
        contentValue.put(Util.RESTRO_SMALL_DESC, restrauntModel.getmSmallDesc());
        contentValue.put(Util.RESTRO_DESC, restrauntModel.getmDesc());
        contentValue.put(Util.RESTRO_CREATE_BY, restrauntModel.getmCreateBy());
        contentValue.put(Util.RESTRO_CREATE_DATE, restrauntModel.getmCreatedDate());
        mDatabase.insert(DatabaseHandler.RESTRO_INFO_TABLE_NAME, null, contentValue);
    }

    public void insertReview(ReviewModel reviewModel) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(Util.REVIEW_ID, reviewModel.getReviewId());
        contentValue.put(Util.REVIEW_COMMENT, reviewModel.getReviewComment());
        contentValue.put(Util.RESTRO_ID, reviewModel.getRestrauntId());
        contentValue.put(Util.REVIEW_RATING, reviewModel.getReviewRating());
        contentValue.put(Util.REVIEW_GIVEN_BY, reviewModel.getReviewGivenBy());
        contentValue.put(Util.REVIEW_GIVEN_BY_EMAIL, reviewModel.getReviewGivenByEmail());
        contentValue.put(Util.REVIEW_DATE, reviewModel.getReviewDate());
        mDatabase.insert(DatabaseHandler.REVIEW_INFO_TABLE_NAME, null, contentValue);
    }

    public List<ReviewModel> getAllReviews(String restroId) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        List<ReviewModel> reviewModelList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHandler.REVIEW_INFO_TABLE_NAME + " WHERE " + Util.RESTRO_ID + " =? ", new String[]{restroId});
        if (cursor.moveToFirst()) {
            do {
                reviewModelList.add(createReviewComments(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reviewModelList;
    }


    private ReviewModel createReviewComments(Cursor cursor) {
        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setRestrauntId(cursor.getString(3));
        reviewModel.setReviewComment(cursor.getString(1));
        reviewModel.setReviewGivenBy(cursor.getString(6));
        reviewModel.setReviewGivenByEmail(cursor.getString(5));
        reviewModel.setReviewRating(cursor.getString(2));
        reviewModel.setReviewId(cursor.getString(0));
        reviewModel.setReviewDate(cursor.getString(4));
        return reviewModel;
    }

    public UserInfoModel getLoginCredintials(String userName) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        UserInfoModel userInfoModel = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHandler.USER_INFO_TABLE_NAME + " WHERE " + Util.USER_EMAIL_NAME + " = '" + userName + "'", null);
        if (cursor.moveToFirst()) {
            userInfoModel = createUserInfoMOdel(cursor);
        }
        cursor.close();
        return userInfoModel;
    }

    public List<RestrauntModel> getAllRestraunts() {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        List<RestrauntModel> mRestrauntList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHandler.RESTRO_INFO_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                mRestrauntList.add(createRestrauntData(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mRestrauntList;
    }

    private RestrauntModel createRestrauntData(Cursor cursor) {
        RestrauntModel restrauntModel = new RestrauntModel();
        restrauntModel.setmName(cursor.getString(1));
        restrauntModel.setmSmallDesc(cursor.getString(2));
        restrauntModel.setmDesc(cursor.getString(3));
        restrauntModel.setmCreateBy(cursor.getString(4));
        restrauntModel.setmCreatedDate(cursor.getString(5));
        restrauntModel.setmRestrauntId(cursor.getString(0));
        return restrauntModel;
    }

    private UserInfoModel createUserInfoMOdel(Cursor cursor) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUserID(cursor.getString(1));
        userInfoModel.setmUserEmailId(cursor.getString(3));
        userInfoModel.setmUserPassWord(cursor.getString(4));
        userInfoModel.setmUserName(cursor.getString(2));
        userInfoModel.setmUserType(cursor.getString(5));
        userInfoModel.setmUserId(cursor.getString(0));
        return userInfoModel;
    }

    public void updateUserInfo(UserInfoModel userInfoModel) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(Util.USER_ID, userInfoModel.getUserID());
        contentValue.put(Util.USER_EMAIL_NAME, userInfoModel.getmUserEmailId());
        contentValue.put(Util.USER_PASSWORD, userInfoModel.getmUserPassWord());
        contentValue.put(Util.USER_USER_NAME, userInfoModel.getmUserName());
        contentValue.put(Util.USER_TYPE, userInfoModel.getmUserType());
        contentValue.put(Util.ID, userInfoModel.getmUserId());
        sqLiteDatabase.update(DatabaseHandler.USER_INFO_TABLE_NAME, contentValue, Util.USER_EMAIL_NAME + " = '" + userInfoModel.getmUserEmailId() + "'", null);

    }

    public void updateReviews(ReviewModel reviewModel) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(Util.REVIEW_ID, reviewModel.getReviewId());
        contentValue.put(Util.REVIEW_COMMENT, reviewModel.getReviewComment());
        contentValue.put(Util.RESTRO_ID, reviewModel.getRestrauntId());
        contentValue.put(Util.REVIEW_RATING, reviewModel.getReviewRating());
        contentValue.put(Util.REVIEW_GIVEN_BY, reviewModel.getReviewGivenBy());
        contentValue.put(Util.REVIEW_DATE, reviewModel.getReviewDate());
        sqLiteDatabase.update(DatabaseHandler.REVIEW_INFO_TABLE_NAME, contentValue, Util.REVIEW_ID + " =?" , new String[]{reviewModel.getReviewId()});

    }

    public List<UserInfoModel> getAllUserList(String type) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        List<UserInfoModel> mUserList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHandler.USER_INFO_TABLE_NAME+" WHERE "+Util.USER_TYPE +" =?", new String[]{type});
        if (cursor.moveToFirst()) {
            do {
                mUserList.add(createUserInfoMOdel(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return mUserList;
    }

 /*   public List<ExpenseInfo> fetchExpenseInfoData()
    {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursorExpense = sqLiteDatabase.rawQuery("SELECT * FROM "+DatabaseHandler.TABLE_NAME, null);
        List<ExpenseInfo> mList = new ArrayList<>();
        if (cursorExpense.moveToFirst()) {
            do {
                mList.add(createEmployeeData(cursorExpense));
            } while (cursorExpense.moveToNext());
        }
        //closing the cursor
        cursorExpense.close();
        return mList;
    }*/

   /* public List<ExpenseInfo> fetchCompleteData(String date) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursorExpense = sqLiteDatabase.rawQuery("SELECT * FROM "+DatabaseHandler.TABLE_NAME+ " WHERE "+DatabaseHandler.DATE_FIELD+ "= ? ", new String[]{date});
        List<ExpenseInfo> mList = new ArrayList<>();
        if (cursorExpense.moveToFirst()) {
            do {
                mList.add(createEmployeeData(cursorExpense));
            } while (cursorExpense.moveToNext());
        }
        //closing the cursor
        cursorExpense.close();
        return mList;
    }*/

  /*  public List<ExpenseInfo> fetchCompleteData() {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursorExpense = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHandler.TABLE_NAME, null);
        List<ExpenseInfo> mList = new ArrayList<>();
        if (cursorExpense.moveToFirst()) {
            do {
                mList.add(createExpenseData(cursorExpense));
            } while (cursorExpense.moveToNext());
        }
        //closing the cursor
        cursorExpense.close();
        return mList;
    }

    private ExpenseInfo createExpenseData(Cursor cursorExpense) {
        ExpenseInfo expenseInfo = new ExpenseInfo();
        expenseInfo.setExpensedate(cursorExpense.getString(1));
        expenseInfo.setTotalIncome(cursorExpense.getString(2));
        return expenseInfo;
    }

    public List<ExpenseType> fetchCompleteExpenseBasedOnType(String expenseDate, String expenseType) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursorExpense = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseHandler.EXPENSE_INFO_TABLE_NAME + " WHERE " + DatabaseHandler.DATE_FIELD + "= ? " + " AND " + DatabaseHandler.EXPENSE_TYPE + "= ? ", new String[]{expenseDate, expenseType});
        List<ExpenseType> mList = new ArrayList<>();
        if (cursorExpense.moveToFirst()) {
            do {
                mList.add(createExpenseTypeData(cursorExpense));
            } while (cursorExpense.moveToNext());
        }
        //closing the cursor
        cursorExpense.close();
        return mList;
    }

    private ExpenseType createExpenseTypeData(Cursor cursorExpense) {
        ExpenseType expenseType = new ExpenseType();
        expenseType.setExpenseId(cursorExpense.getInt(0));
        expenseType.setExpenseName(cursorExpense.getString(1));
        expenseType.setExpenseAmount(cursorExpense.getInt(2));
        expenseType.setExpenseDate(cursorExpense.getString(3));
        expenseType.setCurrentTimeStamp(cursorExpense.getString(4));
        expenseType.setExpenseType(cursorExpense.getString(5));
        return expenseType;
    }

    public void insertExpenseType(ExpenseType expenseType) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.NAME_FIELD, expenseType.getExpenseName());
        contentValue.put(DatabaseHandler.VALUE_FIELD, expenseType.getExpenseAmount());
        contentValue.put(DatabaseHandler.DATE_FIELD, expenseType.getExpenseDate());
        contentValue.put(DatabaseHandler.CURRENT_TIMESTAMP, ""+Calendar.getInstance().getTimeInMillis());
        contentValue.put(DatabaseHandler.EXPENSE_TYPE, expenseType.getExpenseType());
        mDatabase.insert(DatabaseHandler.EXPENSE_INFO_TABLE_NAME, null, contentValue);
    }

    public void deleteData(ExpenseType expenseType) {
        SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + DatabaseHandler.EXPENSE_INFO_TABLE_NAME + " WHERE id = " + expenseType.getExpenseId());
    }

    public void updateExpense(ExpenseType expenseType) {
        String timeStamp = expenseType.getCurrentTimeStamp();
        SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHandler.NAME_FIELD, expenseType.getExpenseName());
        contentValue.put(DatabaseHandler.VALUE_FIELD, expenseType.getExpenseAmount());
        contentValue.put(DatabaseHandler.DATE_FIELD, expenseType.getExpenseDate());
        contentValue.put(DatabaseHandler.CURRENT_TIMESTAMP, ""+Calendar.getInstance().getTimeInMillis());
        contentValue.put(DatabaseHandler.EXPENSE_TYPE, expenseType.getExpenseType());
        sqLiteDatabase.update(DatabaseHandler.EXPENSE_INFO_TABLE_NAME,contentValue,DatabaseHandler.CURRENT_TIMESTAMP+" = "+timeStamp,null);
    }

    public String calculateAmount() {
        SQLiteDatabase sqLiteDatabase = dbHandler.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM("+ DatabaseHandler.VALUE_FIELD +") as Total FROM "+DatabaseHandler.EXPENSE_INFO_TABLE_NAME,null);
        if(cursor.moveToFirst())
            return String.valueOf( cursor.getInt(0));
        return "";
    }*/
    /*

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.SUBJECT, name);
        contentValues.put(DatabaseHandler.DESC, desc);
        int i = database.update(DatabaseHandler.TABLE_NAME, contentValues, DatabaseHandler._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHandler.TABLE_NAME, DatabaseHandler._ID + "=" + _id, null);
    }
*/

}
