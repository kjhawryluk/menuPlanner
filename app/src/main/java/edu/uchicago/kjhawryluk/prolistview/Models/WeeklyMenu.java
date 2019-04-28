package edu.uchicago.kjhawryluk.prolistview.Models;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import java.util.Date;

import edu.uchicago.kjhawryluk.prolistview.TypeConverters.DateConverter;

@Entity(tableName = "weekly_menu_table")
public class WeeklyMenu {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    @TypeConverters({DateConverter.class})
    private Date mStartDate;

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
    public WeeklyMenu() { }
    public WeeklyMenu(Date startDate) {
        mStartDate = startDate;
    }

}
