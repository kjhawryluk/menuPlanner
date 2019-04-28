package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;

@Dao
public interface IngredientDao {
    //CREATE
    @Insert
    void insert(Ingredient ingredient);



    //DELETE
    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    //Read
    @Query("SELECT * from ingredient_table WHERE mDailyMenuId=:dailyMenuId ORDER BY mName ASC")
    LiveData<List<Ingredient>> getAllDailyIngredients(int dailyMenuId);

    //Delete
    @Delete
    void delete(Ingredient ingredient);
}
