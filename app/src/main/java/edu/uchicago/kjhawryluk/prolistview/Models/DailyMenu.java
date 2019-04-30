package edu.uchicago.kjhawryluk.prolistview.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.TypeConverters.DateConverter;

//        foreignKeys = @ForeignKey(entity = WeeklyMenuFragment.class,
//        parentColumns = "mId",
//        childColumns = "mMenuId",
//        onDelete = CASCADE))
@Entity(tableName = "daily_menu_table")
public class DailyMenu {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer mId;
    @NonNull
    private Integer mMenuId;
    private String mTitle;
    private String mUrl;
    @TypeConverters({DateConverter.class})
    private Date mDate;
    @Ignore
    private List<Ingredient> mIngredients;

    public DailyMenu() {}

    public DailyMenu(Date date) {
        mDate = date;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Integer getMenuId() {
        return mMenuId;
    }

    public void setMenuId(Integer menuId) {
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

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }
}
