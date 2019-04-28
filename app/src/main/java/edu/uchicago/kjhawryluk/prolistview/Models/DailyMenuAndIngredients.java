package edu.uchicago.kjhawryluk.prolistview.Models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.TypeConverters.DateConverter;

public class DailyMenuAndIngredients {
    @Embedded
    private DailyMenu mDailyMenu;

//    @TypeConverters({DateConverter.class})
//    private Date mDailyMenuDate;
//
//    private int menuId;

    @Relation(parentColumn = "mId",
            entityColumn = "mDailyMenuId")
    private List<Ingredient> mIngredients;

    public DailyMenuAndIngredients(){
        mDailyMenu = new DailyMenu();
        mIngredients = new ArrayList<>();
    }

//    public DailyMenuAndIngredients(int menuId, Date mDailyMenuDate) {
//       // mDailyMenu = new DailyMenu(menuId, mDailyMenuDate);
//        mIngredients = new ArrayList<>();
//    }

    public DailyMenu getDailyMenu() {
        return mDailyMenu;
    }

    public void setDailyMenu(DailyMenu dailyMenu) {
        mDailyMenu = dailyMenu;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

//    public Date getDailyMenuDate() {
//        return mDailyMenuDate;
//    }
//
//    public void setDailyMenuDate(Date dailyMenuDate) {
//        mDailyMenuDate = dailyMenuDate;
//    }
//
//    public int getMenuId() {
//        return menuId;
//    }
//
//    public void setMenuId(int menuId) {
//        this.menuId = menuId;
//    }
}
