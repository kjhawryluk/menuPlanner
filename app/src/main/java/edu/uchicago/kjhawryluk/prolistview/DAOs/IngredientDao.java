package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;

@Dao
public interface IngredientDao {
    //CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Ingredient> ingredient);

    //DELETE
    @Query("DELETE FROM ingredient_table")
    void deleteAll();

    //Read
    @Query("SELECT * from ingredient_table WHERE mDailyMenuId=:dailyMenuId ORDER BY mName ASC")
    LiveData<List<Ingredient>> getAllDailyIngredients(int dailyMenuId);

    //Read
    @Query("SELECT ingredient_table.mId, ingredient_table.mDailyMenuId,ingredient_table.mName, " +
            "ingredient_table.mQuantity " +
            "FROM ingredient_table JOIN daily_menu_table ON mDailyMenuId = daily_menu_table.mId " +
            "WHERE daily_menu_table.mMenuId=:menuId and currentlyOwn = 0 " +
            "ORDER BY mName ASC, mQuantity DESC")
    LiveData<List<Ingredient>> getShoppingListIngredients(int menuId);

    //Delete
    @Delete
    void delete(Ingredient ingredient);
}
