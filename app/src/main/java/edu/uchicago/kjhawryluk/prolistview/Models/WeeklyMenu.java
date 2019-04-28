package edu.uchicago.kjhawryluk.prolistview.Models;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;

@Entity(tableName = "weekly_menu_table")
public class WeeklyMenu {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
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

    public WeeklyMenu(Date startDate) {
        mStartDate = startDate;
    }

}
