package com.mojomoney.mojomoney;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(version =1, entities = {Entry.class})
public abstract class AppDatabase extends RoomDatabase {

    // ...Dao is a class annotated with @Dao.
    abstract public EntryDao EntryDao ();
    //abstract public AlphabeticDao AlphabeticDao ();
    //abstract public DateDao DateDao();
    //abstract public TypeDao TypeDao ();
    //abstract public AmountDao AmountDao ();




}

