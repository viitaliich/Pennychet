package com.example.pennychet.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccumulatedDateDao {
    @Query("SELECT * FROM accumulateddate")
    List<AccumulatedDate> getAll();

    @Query("SELECT * FROM accumulateddate WHERE type LIKE :type")
    List<AccumulatedDate> getAllByType(String type);

    @Query("SELECT * FROM accumulateddate WHERE date LIKE :date")
    AccumulatedDate getByDate(String date);

    @Insert
    void insertAll(AccumulatedDate... accumulatedDate);

    @Insert
    void insert(AccumulatedDate accumulatedDate);

    @Query("UPDATE accumulateddate SET sum= :new_sum WHERE date LIKE :date")
    void updateSum(double new_sum, String date);

    @Delete
    void deleteAll(AccumulatedDate... accumulatedDate);

    @Delete
    void delete(AccumulatedDate accumulatedDate);
}
