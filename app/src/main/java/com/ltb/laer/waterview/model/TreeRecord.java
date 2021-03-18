package com.ltb.laer.waterview.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TreeRecord implements Parcelable {
    private int id;
    private String time;//日期
    private int state;//状态
    private int num;//数目
    private String total;//总
    private String during;//周期

    public TreeRecord(int id, String time, int state, int num, String total, String during) {
        this.id = id;
        this.time = time;
        this.state = state;
        this.num = num;
        this.total = total;
        this.during = during;
    }

    public TreeRecord(String time, int state, int num, String total, String during) {
        this.time = time;
        this.state = state;
        this.num = num;
        this.total = total;
        this.during = during;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }



    public String getDuring() {
        return during;
    }

    public void setDuring(String during) {
        this.during = during;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.time);
        dest.writeInt(this.state);
        dest.writeInt(this.num);
        dest.writeString(this.total);
        dest.writeString(this.during);
    }

    protected TreeRecord(Parcel in) {
        this.id = in.readInt();
        this.time = in.readString();
        this.state = in.readInt();
        this.num = in.readInt();
        this.total = in.readString();
        this.during = in.readString();
    }

    public static final Creator<TreeRecord> CREATOR = new Creator<TreeRecord>() {
        @Override
        public TreeRecord createFromParcel(Parcel source) {
            return new TreeRecord(source);
        }

        @Override
        public TreeRecord[] newArray(int size) {
            return new TreeRecord[size];
        }
    };
}
