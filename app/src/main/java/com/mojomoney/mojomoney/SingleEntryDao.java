package com.mojomoney.mojomoney;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface SingleEntryDao {

    @Query("SELECT * FROM entry WHERE id LIKE :id")
    Entry getSingleEntry(int id);

    @Insert
    void insertSingleEntry(Entry entry);

    @Delete
    void deleteSingleEntry(Entry entry);

}
