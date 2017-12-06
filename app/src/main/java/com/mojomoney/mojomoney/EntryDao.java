package com.mojomoney.mojomoney;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM entry")
    List<Entry> getAllEntries();

    @Insert
    void insertAll (Entry ...entries);

    @Delete
    void deleteAll (List<Entry> entries);


    //Test Merging
}