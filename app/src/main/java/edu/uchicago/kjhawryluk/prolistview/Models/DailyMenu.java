package edu.uchicago.kjhawryluk.prolistview.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "daily_menu_table")
//        foreignKeys = @ForeignKey(entity = WeeklyMenu.class,
//        parentColumns = "mId",
//        childColumns = "mMenuId",
//        onDelete = CASCADE))
public class DailyMenu {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    private int mMenuId;
    private String mTitle;
    private String mUrl;
    private Date mDate;

    public DailyMenu(int menuId, Date date) {
        mMenuId = menuId;
        mDate = date;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getMenuId() {
        return mMenuId;
    }

    public void setMenuId(int menuId) {
        mMenuId = menuId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }


}
