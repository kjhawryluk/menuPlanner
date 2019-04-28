package edu.uchicago.kjhawryluk.prolistview;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import android.arch.persistence.room.Database;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.DAOs.DailyMenuAndIngredientsDao;
import edu.uchicago.kjhawryluk.prolistview.DAOs.DailyMenuDao;
import edu.uchicago.kjhawryluk.prolistview.DAOs.IngredientDao;
import edu.uchicago.kjhawryluk.prolistview.DAOs.WeeklyAndDailyMenusDao;
import edu.uchicago.kjhawryluk.prolistview.DAOs.WeeklyMenuDao;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenuAndIngredients;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyAndDailyMenus;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

@Database(entities = {
        WeeklyMenu.class,
        DailyMenu.class,
        Ingredient.class}, version = 1)
public abstract class MenuDatabase extends RoomDatabase {
    public abstract WeeklyMenuDao weeklyMenuDao();
    public abstract DailyMenuDao dailyMenuDao();
    public abstract IngredientDao ingredientDao();
//    public abstract DailyMenuAndIngredientsDao mDailyMenuAndIngredientsDao();
//    public abstract WeeklyAndDailyMenusDao mWeeklyAndDailyMenusDao();

    private static volatile MenuDatabase INSTANCE;

    static MenuDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MenuDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MenuDatabase.class, "menu_database")
                            .addCallback(sMenuDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static MenuDatabase.Callback sMenuDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WeeklyMenuDao mWeeklyMenuDao;
        private final DailyMenuDao mDailyMenuDao;
        private final IngredientDao mIngredientDao;

        PopulateDbAsync(MenuDatabase db) {
            mWeeklyMenuDao = db.weeklyMenuDao();
            mDailyMenuDao = db.dailyMenuDao();
            mIngredientDao = db.ingredientDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //  mDao.deleteAll();
            Calendar cal = Calendar.getInstance();
            cal.set(2019,1,1);
            Date startDate = cal.getTime();
            WeeklyAndDailyMenus weeklyMenu = new WeeklyAndDailyMenus();
            weeklyMenu.getWeeklyMenu().setStartDate(startDate);
            weeklyMenu.generateDailyMenus();
            List<Ingredient> ingredients = new ArrayList<Ingredient>(){
                {
                    add(new Ingredient("pizza"));
                    add(new Ingredient("chips"));
                }
            };
            DailyMenuAndIngredients dayOne = weeklyMenu.getDailyMenus().get(0);
            dayOne.getDailyMenu().setTitle("Pizza!");
            dayOne.setIngredients(ingredients);
           // mDao.insert(weeklyMenu);
            return null;
        }
    }
}


