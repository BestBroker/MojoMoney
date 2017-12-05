package com.mojomoney.mojomoney;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.File;

@Entity
public class Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "betrag")
    private String mBetrag;

    @ColumnInfo(name = "datum")
    private String mDatum;

    @ColumnInfo(name = "timeStamp")
    private String mTimeStamp;

    @ColumnInfo(name = "path")
    private String mPath;

    public Entry(String name, String betrag, String datum, String timeStamp, String path){
        mName = name;
        mBetrag = betrag;
        mDatum = datum;
        mTimeStamp = timeStamp;
        mPath = path;
    }

    void deleteImage() {

        File imgFile = new File(mPath);
        try {
            imgFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return mName;
    }

    public String getBetrag() {
        return mBetrag;
    }

    public String getDatum() {
        return mDatum;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return mPath;
    }



    public void setmName(String name) {
        mName = name;
    }

    public void setmBetrag(String betrag) {
        mBetrag = betrag;
    }

    public void setmDatum(String datum) {
        mDatum = datum;
    }

    public void setmTimeStamp(String timeStamp) {
        mTimeStamp = timeStamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }
}
