package edu.uchicago.kjhawryluk.prolistview.Models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.TypeConverters.DateConverter;

public class WeeklyAndDailyMenus {
    @Embedded
    private WeeklyMenu mWeeklyMenu;
//    @TypeConverters({DateConverter.class})
//    private Date mDate;
    @Relation(parentColumn = "mId",
                entityColumn = "mMenuId")
    private List<DailyMenuAndIngredients> mDailyMenus;

    public WeeklyAndDailyMenus() {
        //this.mDate = mDate;
        mWeeklyMenu = new WeeklyMenu();
        mDailyMenus = new ArrayList<>();
    }

//    public void generateDailyMenus() {
//        if(mDailyMenus == null){
//            mDailyMenus = new ArrayList<>();
//        }
//
//        Date dayDate = new Date(mWeeklyMenu.getStartDate().getTime());
//        for (int i = 0; i < 7; i++) {
//            mDailyMenus.add(new DailyMenuAndIngredients(mWeeklyMenu.getId(), dayDate));
//            dayDate = addDays(dayDate, 1);
//        }
//    }
//
//    /**
//     * Adds a given number of days to a date.
//     * https://stackoverflow.com/questions/428918/how-can-i-increment-a-date-by-one-day-in-java
//     *
//     * @param date
//     * @param days
//     * @return
//     */
//    public static Date addDays(Date date, int days) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(date);
//        cal.add(Calendar.DATE, days); //minus number would decrement the days
//        return cal.getTime();
//    }

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

//    public Date getDate() {
//        return mDate;
//    }
//
//    public void setDate(Date date) {
//        mDate = date;
//    }
}
