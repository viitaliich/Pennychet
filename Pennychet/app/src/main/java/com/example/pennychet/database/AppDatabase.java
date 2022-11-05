package com.example.pennychet.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Expense.class, AccumulatedExpense.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();
    public abstract AccumulatedExpenseDao accumulatedExpenseDao();
}
