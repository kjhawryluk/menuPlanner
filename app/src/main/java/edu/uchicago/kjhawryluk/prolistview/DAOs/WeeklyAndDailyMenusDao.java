package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyAndDailyMenus;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

@Dao
public abstract class WeeklyAndDailyMenusDao {
    @Insert
    protected abstract void insert(WeeklyMenu weeklyMenu);

    @Insert
    protected abstract void insert(List<DailyMenu> dailyMenus);

    @Transaction
    public void insert(WeeklyAndDailyMenus weeklyAndDailyMenus){
        insert(weeklyAndDailyMenus.getWeeklyMenu());
        insert(weeklyAndDailyMenus.getDailyMenus());
    }

    @Query("SELECT * FROM weekly_menu_table where mId =:menuId")
    public abstract LiveData<List<WeeklyAndDailyMenus>> getWeeklyMenu(int menuId);
}
