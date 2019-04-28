package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

@Dao
public interface WeeklyMenuDao {
    //CREATE
    @Insert
    void insert(WeeklyMenu weeklyMenu);

    //DELETE
    @Query("DELETE FROM weekly_menu_table")
    void deleteAll();

    //Read
    @Query("SELECT * from weekly_menu_table ORDER BY mStartDate desc")
    LiveData<List<WeeklyMenu>> getWeeklyMenus();

    //Read
    @Query("SELECT * from weekly_menu_table WHERE mId=:menuId ORDER BY mStartDate desc")
    LiveData<List<WeeklyMenu>> getWeeklyMenuById(int menuId);

    //Delete
    @Delete
    void delete(WeeklyMenu weeklyMenu);
}
