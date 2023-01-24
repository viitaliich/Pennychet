package com.example.pennychet.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDao {
    @Query("SELECT * FROM account")
    List<Account> getAll();

    @Query("SELECT * FROM account WHERE name LIKE :account")
    List<Account> getByName(String account);

    @Insert
    void insertAll(Account... account);

    @Insert
    void insert(Account account);

    @Query("UPDATE account SET name= :new_name WHERE name LIKE :account")
    void updateName(String new_name, String account);

    @Query("UPDATE account SET init_sum= :new_sum WHERE name LIKE :account")
    void updateInitSum(double new_sum, String account);

    @Delete
    void deleteAll(Transaction... category);

    @Delete
    void delete(Transaction category);
}
