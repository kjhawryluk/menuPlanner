package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;

@Dao
public interface DailyMenuDao {
    //CREATE
    @Insert
    void insert(DailyMenu dailyMenu);

    //DELETE
    @Query("DELETE FROM daily_menu_table")
    void deleteAll();

    //Read
    @Query("SELECT * from daily_menu_table WHERE mMenuId=:menuId ORDER BY mDate ASC")
    LiveData<List<DailyMenu>> getAllDailyMenusByMenuId(int menuId);

    //Delete
    @Delete
    void delete(DailyMenu dailyMenu);
}
