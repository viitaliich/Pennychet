package com.example.pennychet.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccumulatedExpenseDao {
    @Query("SELECT * FROM accumulatedexpense")
    List<AccumulatedExpense> getAll();

    @Query("SELECT * FROM accumulatedexpense WHERE category LIKE :ctg")
    AccumulatedExpense getByCategory(String ctg);

    @Query("SELECT * FROM accumulatedexpense WHERE id IN (:expenseIds)")
    List<AccumulatedExpense> loadAllByIds(int[] expenseIds);

    @Query("SELECT * FROM accumulatedexpense WHERE category LIKE :first AND " +
            "sum LIKE :last LIMIT 1")
    AccumulatedExpense findByName(String first, String last);

    @Query("UPDATE accumulatedexpense SET sum= :new_sum WHERE category LIKE :ctg")
    void updateSum(double new_sum, String ctg);

    @Insert
    void insertAll(AccumulatedExpense... accumulatedexpense);

    @Delete
    void delete(AccumulatedExpense accumulatedexpense);
}
