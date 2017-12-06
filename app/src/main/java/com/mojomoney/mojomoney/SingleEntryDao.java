package com.mojomoney.mojomoney;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SingleEntryDao {

    @Query("SELECT * FROM entry WHERE id LIKE :id")
    Entry getSingleEntry(int id);

    @Query("SELECT betrag FROM entry")
    List<EntryAmount> loadAmount();

    @Query("SELECT * FROM entry WHERE name LIKE :name")
    Entry getSingleEntrybyName(String name);

    @Insert
    void insertSingleEntry(Entry entry);

    @Delete
    void deleteSingleEntry(Entry entry);

}
