package edu.uchicago.kjhawryluk.prolistview;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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

public class MenuRepository {
    private WeeklyMenuDao mWeeklyMenuDao;
    private DailyMenuDao mDailyMenuDao;
    private IngredientDao mIngredientDao;
    private DailyMenuAndIngredientsDao mDailyMenuAndIngredientsDao;
    private WeeklyAndDailyMenusDao mWeeklyAndDailyMenusDao;

    private LiveData<List<WeeklyMenu>> mAllMenus;

    // This should only every be one. I need to check if I can change this from a list
    // to a single object
    private LiveData<List<WeeklyAndDailyMenus>> mWeeklyMenu;
    private LiveData<List<DailyMenu>> mDailyMenu;
    private LiveData<List<DailyMenuAndIngredients>> mDailyMenuAndIngredients;
    private LiveData<List<Ingredient>> mIngredients;


    MenuRepository(Application application) {
        MenuDatabase db = MenuDatabase.getDatabase(application);
        mWeeklyMenuDao = db.weeklyMenuDao();
        mDailyMenuDao = db.dailyMenuDao();
        mIngredientDao = db.ingredientDao();
//        mDailyMenuAndIngredientsDao = db.mDailyMenuAndIngredientsDao();
//        mWeeklyAndDailyMenusDao = db.mWeeklyAndDailyMenusDao();
        mAllMenus = mWeeklyMenuDao.getWeeklyMenus();
    }

    public LiveData<List<WeeklyMenu>> getAllMenus() {
        return mAllMenus;
    }

    public void setAllMenus(LiveData<List<WeeklyMenu>> allMenus) {
        mAllMenus = allMenus;
    }

    public LiveData<List<WeeklyAndDailyMenus>> getWeeklyMenu() {
        return mWeeklyMenu;
    }

    public void setWeeklyMenu(LiveData<List<WeeklyAndDailyMenus>> weeklyMenu) {
        mWeeklyMenu = weeklyMenu;
    }

    public LiveData<List<DailyMenu>> getDailyMenu() {
        return mDailyMenu;
    }

    public void setDailyMenu(LiveData<List<DailyMenu>> dailyMenu) {
        mDailyMenu = dailyMenu;
    }

    public LiveData<List<DailyMenuAndIngredients>> getDailyMenuAndIngredients() {
        return mDailyMenuAndIngredients;
    }

    public void setDailyMenuAndIngredients(LiveData<List<DailyMenuAndIngredients>> dailyMenuAndIngredients) {
        mDailyMenuAndIngredients = dailyMenuAndIngredients;
    }

    public LiveData<List<Ingredient>> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(LiveData<List<Ingredient>> ingredients) {
        mIngredients = ingredients;
    }

    public void insert (WeeklyAndDailyMenus weeklyAndDailyMenu) {
        new insertWeeklyAndDailyMenusAsyncTask(mWeeklyAndDailyMenusDao).execute(weeklyAndDailyMenu);
    }

    private static class insertWeeklyAndDailyMenusAsyncTask extends AsyncTask<WeeklyAndDailyMenus, Void, Void> {

        private WeeklyAndDailyMenusDao mAsyncTaskDao;

        insertWeeklyAndDailyMenusAsyncTask(WeeklyAndDailyMenusDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeeklyAndDailyMenus... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
