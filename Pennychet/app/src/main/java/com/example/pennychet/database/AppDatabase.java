package com.example.pennychet.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        Transaction.class,
        Category.class,
        Account.class,
        AccumulatedDate.class
}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
    public abstract CategoryDao categoryDao();
    public abstract AccountDao accountDao();
    public abstract AccumulatedDateDao accumulatedDateDao();
}
