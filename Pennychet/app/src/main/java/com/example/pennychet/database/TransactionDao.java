package com.example.pennychet.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    List<Transaction> getAll();

    @Query("SELECT * FROM `transaction` WHERE type LIKE :type")
    List<Transaction> getAllByType(String type);

    @Query("SELECT * FROM `transaction` WHERE category LIKE :ctg")
    List<Transaction> getAllByCategory(String ctg);

    @Query("SELECT * FROM `transaction` WHERE account LIKE :acnt")
    List<Transaction> getAllByAccount(String acnt);

    @Query("SELECT * FROM `transaction` WHERE date LIKE :date")
    List<Transaction> getAllByDate(String date);

    @Insert
    void insertAll(Transaction... transaction);

    @Insert
    void insert(Transaction transaction);

    @Query("UPDATE `transaction` SET category= :new_ctg WHERE category LIKE :ctg")
    void updateCategory(String new_ctg, String ctg);

    @Query("UPDATE `transaction` SET account= :new_act WHERE category LIKE :ctg")
    void updateAccount(String new_act, String ctg);

    @Query("UPDATE `transaction` SET description= :new_descr WHERE category LIKE :ctg")
    void updateDescription(String new_descr, String ctg);

    @Query("UPDATE `transaction` SET sum= :new_sum WHERE category LIKE :ctg")
    void updateSum(double new_sum, String ctg);

    @Query("UPDATE `transaction` SET date= :new_date WHERE category LIKE :ctg")
    void updateDate(String new_date, String ctg);

    @Query("UPDATE `transaction` SET icon= :new_icon WHERE category LIKE :ctg")
    void updateIcon(int new_icon, String ctg);

    @Query("UPDATE `transaction` SET date= :new_color WHERE category LIKE :ctg")
    void updateColor(int new_color, String ctg);

    @Delete
    void deleteAll(Transaction... transaction);

    @Delete
    void delete(Transaction transaction);
}
