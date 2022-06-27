package com.foddie.food.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoModel implements Parcelable
{
    private String mId;
    private String mUserName;
    private String mUserID;
    private String mUserType;
    private String mUserPassWord;
    private String mUserEmailId;

    public  UserInfoModel(){}
    public UserInfoModel(Parcel in) {
        mId = in.readString();
        mUserID = in.readString();
        mUserName = in.readString();
        mUserType = in.readString();
        mUserPassWord = in.readString();
        mUserEmailId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mUserID);
        dest.writeString(mUserName);
        dest.writeString(mUserType);
        dest.writeString(mUserPassWord);
        dest.writeString(mUserEmailId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfoModel> CREATOR = new Creator<UserInfoModel>() {
        @Override
        public UserInfoModel createFromParcel(Parcel in) {
            return new UserInfoModel(in);
        }

        @Override
        public UserInfoModel[] newArray(int size) {
            return new UserInfoModel[size];
        }
    };

    public String getmUserId() {
        return mId;
    }

    public void setmUserId(String mUserId) {
        this.mId = mUserId;
    }
    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getmUserType() {
        return mUserType;
    }

    public void setmUserType(String mUserType) {
        this.mUserType = mUserType;
    }

    public String getmUserPassWord() {
        return mUserPassWord;
    }

    public void setmUserPassWord(String mUserPassWord) {
        this.mUserPassWord = mUserPassWord;
    }

    public String getmUserEmailId() {
        return mUserEmailId;
    }

    public void setmUserEmailId(String mUserEmailId) {
        this.mUserEmailId = mUserEmailId;
    }
}
