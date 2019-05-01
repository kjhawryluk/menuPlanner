package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;

@Dao
public abstract class DailyMenuDao {

    //CREATE
    @Insert
    public abstract long insert(DailyMenu dailyMenu);

    //DELETE
    @Query("DELETE FROM daily_menu_table")
    public abstract void deleteAll();

    //Read
    @Query("SELECT * from daily_menu_table WHERE mMenuId=:menuId ORDER BY mDate ASC")
    public abstract LiveData<List<DailyMenu>> getAllDailyMenusByWeeklyMenuId(int menuId);

    //Read
    @Query("SELECT * from daily_menu_table WHERE mId=:id ORDER BY mDate ASC")
    public abstract LiveData<List<DailyMenu>> getDailyMenuById(int id);

    //Read
    @Query("SELECT * from ingredient_table WHERE mDailyMenuId=:id ORDER BY mName ASC, mQuantity DESC")
    public abstract LiveData<List<Ingredient>>  getIngredientsById(int id);

    //Delete
    @Delete
    public abstract void delete(DailyMenu dailyMenu);

    @Update
    public abstract void update(DailyMenu dailyMenu);
}
