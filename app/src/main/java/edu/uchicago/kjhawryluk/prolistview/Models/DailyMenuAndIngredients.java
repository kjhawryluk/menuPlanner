package edu.uchicago.kjhawryluk.prolistview.Models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyMenuAndIngredients {
    @Embedded
    private DailyMenu mDailyMenu;

    @Relation(parentColumn = "mId",
            entityColumn = "mDailyMenuId")
    private List<Ingredient> mIngredients;

    public DailyMenuAndIngredients(int menuId, Date date) {
        mDailyMenu = new DailyMenu(menuId, date);
        mIngredients = new ArrayList<>();
    }

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
}
