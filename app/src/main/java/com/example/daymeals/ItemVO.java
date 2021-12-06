package com.example.daymeals;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemVO implements Parcelable {
    int id;
    int imgResId;
    String name;
    String mats;
    String detail;

    public ItemVO(int id, int imgResId, String name, String mats, String detail){
        this.id = id;
        this.imgResId = imgResId;
        this.name = name;
        this.mats = mats;
        this.detail = detail;
    }

    protected ItemVO(Parcel in) {
        id = in.readInt();
        imgResId = in.readInt();
        name = in.readString();
        mats = in.readString();
        detail = in.readString();
    }

    public static final Creator<ItemVO> CREATOR = new Creator<ItemVO>() {
        @Override
        public ItemVO createFromParcel(Parcel in) {
            return new ItemVO(in);
        }

        @Override
        public ItemVO[] newArray(int size) {
            return new ItemVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(imgResId);
        parcel.writeString(name);
        parcel.writeString(mats);
        parcel.writeString(detail);
    }
}
