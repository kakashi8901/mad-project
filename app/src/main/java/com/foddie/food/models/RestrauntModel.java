package com.foddie.food.models;

import android.os.Parcel;
import android.os.Parcelable;

public class RestrauntModel implements Parcelable
{
    private String mRestrauntId;
    private String mRestrauntName;
    private String mName;
    private String mSmallDesc;
    private String mDesc;
    private String mCreateBy;
    private String mCreatedDate;

    public RestrauntModel()
    {}

    public RestrauntModel(Parcel in) {
        mRestrauntId = in.readString();
        mRestrauntName = in.readString();
        mName = in.readString();
        mSmallDesc = in.readString();
        mDesc = in.readString();
        mCreateBy = in.readString();
        mCreatedDate = in.readString();
    }

    public static final Creator<RestrauntModel> CREATOR = new Creator<RestrauntModel>() {
        @Override
        public RestrauntModel createFromParcel(Parcel in) {
            return new RestrauntModel(in);
        }

        @Override
        public RestrauntModel[] newArray(int size) {
            return new RestrauntModel[size];
        }
    };

    public String getmRestrauntName() {
        return mRestrauntName;
    }

    public void setmRestrauntName(String mRestrauntName) {
        this.mRestrauntName = mRestrauntName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSmallDesc() {
        return mSmallDesc;
    }

    public void setmSmallDesc(String mSmallDesc) {
        this.mSmallDesc = mSmallDesc;
    }

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmCreateBy() {
        return mCreateBy;
    }

    public void setmCreateBy(String mCreateBy) {
        this.mCreateBy = mCreateBy;
    }

    public String getmCreatedDate() {
        return mCreatedDate;
    }

    public void setmCreatedDate(String mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
    }

    public String getmRestrauntId() {
        return mRestrauntId;
    }

    public void setmRestrauntId(String mRestrauntId) {
        this.mRestrauntId = mRestrauntId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRestrauntId);
        dest.writeString(mRestrauntName);
        dest.writeString(mName);
        dest.writeString(mSmallDesc);
        dest.writeString(mDesc);
        dest.writeString(mCreateBy);
        dest.writeString(mCreatedDate);
    }
}
