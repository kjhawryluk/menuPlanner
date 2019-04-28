package edu.uchicago.kjhawryluk.prolistview.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenuAndIngredients;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;

@Dao
public abstract class DailyMenuAndIngredientsDao {
    @Insert
    protected abstract void insert(DailyMenu dailyMenu);

    @Insert
    protected abstract void insert(List<Ingredient> ingredients);

    @Transaction
    public void insert(DailyMenuAndIngredients dailyMenusAndIngredients){
        insert(dailyMenusAndIngredients.getDailyMenu());
        insert(dailyMenusAndIngredients.getIngredients());
    }

    @Query("SELECT * FROM daily_menu_table where mId =:dailyMenuId")
    public abstract LiveData<List<DailyMenuAndIngredients>> getDailyMenu(int dailyMenuId);
}
