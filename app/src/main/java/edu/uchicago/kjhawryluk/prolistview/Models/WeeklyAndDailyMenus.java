package edu.uchicago.kjhawryluk.prolistview.Models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeeklyAndDailyMenus {
    @Embedded
    private WeeklyMenu mWeeklyMenu;

    @Relation(parentColumn = "mId",
                entityColumn = "mMenuId")
    private List<DailyMenuAndIngredients> mDailyMenus;

    public WeeklyAndDailyMenus(Date startDate) {
        mWeeklyMenu = new WeeklyMenu(startDate);
        generateDailyMenus(startDate);
    }

    private void generateDailyMenus(Date startDate) {
        mDailyMenus = new ArrayList<>();
        Date dayDate = new Date(startDate.getTime());
        for (int i = 0; i < 7; i++) {
            mDailyMenus.add(new DailyMenuAndIngredients(mWeeklyMenu.getId(), dayDate));
            dayDate = addDays(dayDate, 1);
        }
    }

    /**
     * Adds a given number of days to a date.
     * https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public WeeklyMenu getWeeklyMenu() {
        return mWeeklyMenu;
    }

    public void setWeeklyMenu(WeeklyMenu weeklyMenu) {
        mWeeklyMenu = weeklyMenu;
    }

    public List<DailyMenuAndIngredients> getDailyMenus() {
        return mDailyMenus;
    }

    public void setDailyMenus(List<DailyMenuAndIngredients> dailyMenus) {
        mDailyMenus = dailyMenus;
    }
}
