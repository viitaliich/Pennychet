package com.example.pennychet.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category WHERE type LIKE :type")
    List<Category> getAllByType(String type);

    @Query("SELECT * FROM category WHERE name LIKE :ctg")
    Category getByName(String ctg);

    @Query("SELECT * FROM category WHERE year LIKE :year")
    List<Category> getAllByYear(int year);

    @Query("SELECT * FROM category WHERE month LIKE :month")
    List<Category> getAllByMonth(int month);

    @Insert
    void insertAll(Category... category);

    @Insert
    void insert(Category category);

    @Query("UPDATE category SET sum_month= :new_sum WHERE name LIKE :ctg")
    void updateSumMonth(double new_sum, String ctg);

    @Delete
    void deleteAll(Transaction... category);

    @Delete
    void delete(Transaction category);
}
