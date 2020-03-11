package com.example.jugsoomarket.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.jugsoomarket.ViewModel.item;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(item item);

    @Update
    void update(item item);

    @Delete
    void delete(item item);

    @Query("DELETE FROM item_table")
    void deleteAllItems();

    @Query("SELECT * FROM item_table")
    LiveData<List<item>> getAllItems();
}

