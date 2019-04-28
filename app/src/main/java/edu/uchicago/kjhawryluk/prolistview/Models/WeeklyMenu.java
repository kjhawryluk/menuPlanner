package edu.uchicago.kjhawryluk.prolistview.Models;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.TypeConverters.DateConverter;

@Entity(tableName = "weekly_menu_table")
public class WeeklyMenu {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    @TypeConverters({DateConverter.class})
    private Date mStartDate;

    @Ignore
    private List<DailyMenu> mDailyMenus;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }
    public WeeklyMenu() {}

    public WeeklyMenu(Date startDate) {
        mStartDate = startDate;
        generateDailyMenus();
    }

    public List<DailyMenu> getDailyMenus() {
        return mDailyMenus;
    }

    public void setDailyMenus(List<DailyMenu> dailyMenus) {
        mDailyMenus = dailyMenus;
    }

    private void generateDailyMenus() {
        if(mDailyMenus == null){
            mDailyMenus = new ArrayList<>();
        }

        Date dayDate = new Date(mStartDate.getTime());
        for (int i = 0; i < 7; i++) {
            mDailyMenus.add(new DailyMenu(dayDate));
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
    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
