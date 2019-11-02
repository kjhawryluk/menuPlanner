package edu.uchicago.kjhawryluk.menuPlanner;

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

import edu.uchicago.kjhawryluk.menuPlanner.DAOs.DailyMenuDao;
import edu.uchicago.kjhawryluk.menuPlanner.DAOs.IngredientDao;
import edu.uchicago.kjhawryluk.menuPlanner.DAOs.WeeklyMenuDao;
import edu.uchicago.kjhawryluk.menuPlanner.Models.DailyMenu;
import edu.uchicago.kjhawryluk.menuPlanner.Models.Ingredient;
import edu.uchicago.kjhawryluk.menuPlanner.Models.WeeklyMenu;

@Database(entities = {
        WeeklyMenu.class,
        DailyMenu.class,
        Ingredient.class}, version = 1)
public abstract class MenuDatabase extends RoomDatabase {
    public abstract WeeklyMenuDao weeklyMenuDao();

    public abstract DailyMenuDao dailyMenuDao();

    public abstract IngredientDao ingredientDao();

    private static volatile MenuDatabase INSTANCE;

    static MenuDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MenuDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MenuDatabase.class, "menu_database")
                            .fallbackToDestructiveMigration()
                            // .addCallback(sMenuDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static MenuDatabase.Callback sMenuDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
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
            cal.set(2019, 1, 1);
            Date startDate = cal.getTime();
            WeeklyMenu weeklyMenu = new WeeklyMenu(startDate);
            mWeeklyMenuDao.insertWeeklyMenuAndDailyMenus(weeklyMenu);
            final DailyMenu dayOne = weeklyMenu.getDailyMenus().get(0);

            List<Ingredient> ingredients = new ArrayList<Ingredient>() {
                {
                    add(new Ingredient(dayOne.getId(), "pizza"));
                    add(new Ingredient(dayOne.getId(), "chips"));
                }
            };
            mIngredientDao.insert(ingredients);
            dayOne.setTitle("Pizza!");
            dayOne.setIngredients(ingredients);
            mDailyMenuDao.update(dayOne);
            return null;
        }
    }
}


