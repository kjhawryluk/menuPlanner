package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

@Dao
public abstract class WeeklyMenuDao {

    public void insertWeeklyMenuAndDailyMenus(WeeklyMenu weeklyMenuAndDailyMenus) {
        long weeklyMenuId = insert(weeklyMenuAndDailyMenus);
        weeklyMenuAndDailyMenus.setId((int) weeklyMenuId);
        List<DailyMenu> dailyMenus = weeklyMenuAndDailyMenus.getDailyMenus();
        // Make sure that daily menus have been created.
        if (dailyMenus != null) {
            for (int i = 0; i < dailyMenus.size(); i++) {
                dailyMenus.get(i).setMenuId((int) weeklyMenuId);
            }
            long[] ids = _insert(dailyMenus);
            for (int i = 0; i < ids.length; i++) {
                dailyMenus.get(i).setId(i);
            }
        }
    }

    @Insert
    abstract long[] _insert(List<DailyMenu> dailyMenus);

    //CREATE
    @Insert
    public abstract long insert(WeeklyMenu weeklyMenu);

    //DELETE
    @Query("DELETE FROM weekly_menu_table")
    public abstract void deleteAll();

    //Read
    @Query("SELECT * from weekly_menu_table ORDER BY mStartDate desc")
    public abstract LiveData<List<WeeklyMenu>> getWeeklyMenus();

    //Read
    @Query("SELECT * from weekly_menu_table WHERE mId=:menuId ORDER BY mStartDate desc")
    public abstract LiveData<WeeklyMenu> getWeeklyMenuById(int menuId);

    //Delete
    @Delete
    public abstract void delete(WeeklyMenu weeklyMenu);
}
