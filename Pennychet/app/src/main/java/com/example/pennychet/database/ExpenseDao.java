package com.example.pennychet.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Query("SELECT * FROM expense")
    List<Expense> getAll();

    @Query("SELECT * FROM expense WHERE category LIKE :ctg")
    List<Expense> getAllByCategory(String ctg);

    @Query("SELECT * FROM expense WHERE id IN (:expenseIds)")
    List<Expense> loadAllByIds(int[] expenseIds);

    @Query("SELECT * FROM expense WHERE category LIKE :first AND " +
            "account LIKE :last LIMIT 1")
    Expense findByName(String first, String last);

    @Insert
    void insertAll(Expense... expenses);

    @Delete
    void delete(Expense expense);
}
