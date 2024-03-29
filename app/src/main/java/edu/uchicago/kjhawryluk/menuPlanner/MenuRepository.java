package edu.uchicago.kjhawryluk.menuPlanner;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import edu.uchicago.kjhawryluk.menuPlanner.DAOs.DailyMenuDao;
import edu.uchicago.kjhawryluk.menuPlanner.DAOs.IngredientDao;
import edu.uchicago.kjhawryluk.menuPlanner.DAOs.WeeklyMenuDao;
import edu.uchicago.kjhawryluk.menuPlanner.Models.DailyMenu;
import edu.uchicago.kjhawryluk.menuPlanner.Models.Ingredient;
import edu.uchicago.kjhawryluk.menuPlanner.Models.WeeklyMenu;

public class MenuRepository {
    private WeeklyMenuDao mWeeklyMenuDao;
    private DailyMenuDao mDailyMenuDao;
    private IngredientDao mIngredientDao;

    private LiveData<List<WeeklyMenu>> mAllMenus;

    // This should only every be one. I need to check if I can change this from a list
    // to a single object
    private LiveData<List<DailyMenu>> mDailyMenus;
    private LiveData<List<Ingredient>> mIngredients;


    public MenuRepository(Application application) {
        MenuDatabase db = MenuDatabase.getDatabase(application);
        mWeeklyMenuDao = db.weeklyMenuDao();
        mDailyMenuDao = db.dailyMenuDao();
        mIngredientDao = db.ingredientDao();
        mAllMenus = mWeeklyMenuDao.getWeeklyMenus();
    }

    public LiveData<List<WeeklyMenu>> getAllMenus() {
        return mAllMenus;
    }

    public void setAllMenus(LiveData<List<WeeklyMenu>> allMenus) {
        mAllMenus = allMenus;
    }


    public LiveData<List<DailyMenu>> getAllDailyMenusByWeeklyMenuId(int id) {
        return mDailyMenuDao.getAllDailyMenusByWeeklyMenuId(id);
    }

    public LiveData<List<DailyMenu>> getDailyMenuById(int id) {
        return mDailyMenuDao.getDailyMenuById(id);
    }

    public LiveData<List<Ingredient>> getAllDailyIngredientsById(int id) {
        return mIngredientDao.getAllDailyIngredients(id);
    }

    public LiveData<List<Ingredient>> getShoppingListIngredients(int menuId) {
        return mIngredientDao.getShoppingListIngredients(menuId);
    }

    public void setDailyMenus(LiveData<List<DailyMenu>> dailyMenus) {
        mDailyMenus = dailyMenus;
    }


    public LiveData<List<Ingredient>> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(LiveData<List<Ingredient>> ingredients) {
        mIngredients = ingredients;
    }

    public void delete(WeeklyMenu weeklyAndDailyMenu) {
        new DeleteWeeklyAndDailyMenusAsyncTask(mWeeklyMenuDao).execute(weeklyAndDailyMenu);
    }

    public void delete(Ingredient ingredient) {
        new DeleteIngredientAsyncTask(mIngredientDao).execute(ingredient);
    }

    public void insert(Ingredient ingredient) {
        new InsertIngredientsAsyncTask(mIngredientDao).execute(ingredient);
    }

    public void insert(Ingredient[] ingredients) {
        for (Ingredient ingredient : ingredients) {
            new InsertIngredientsAsyncTask(mIngredientDao).execute(ingredient);
        }
    }

    public void insert(WeeklyMenu weeklyAndDailyMenu) {
        new InsertWeeklyAndDailyMenusAsyncTask(mWeeklyMenuDao).execute(weeklyAndDailyMenu);
    }

    public void insert(DailyMenu dailyMenu) {
        new InsertDailyMenuAsyncTask(mDailyMenuDao).execute(dailyMenu);
    }

    private static class InsertDailyMenuAsyncTask extends AsyncTask<DailyMenu, Void, Void> {

        private DailyMenuDao mAsyncTaskDao;

        InsertDailyMenuAsyncTask(DailyMenuDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final DailyMenu... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class InsertWeeklyAndDailyMenusAsyncTask extends AsyncTask<WeeklyMenu, Void, Void> {

        private WeeklyMenuDao mAsyncTaskDao;

        InsertWeeklyAndDailyMenusAsyncTask(WeeklyMenuDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeeklyMenu... params) {
            mAsyncTaskDao.insertWeeklyMenuAndDailyMenus(params[0]);
            return null;
        }
    }

    private static class DeleteWeeklyAndDailyMenusAsyncTask extends AsyncTask<WeeklyMenu, Void, Void> {

        private WeeklyMenuDao mAsyncTaskDao;

        DeleteWeeklyAndDailyMenusAsyncTask(WeeklyMenuDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeeklyMenu... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class DeleteIngredientAsyncTask extends AsyncTask<Ingredient, Void, Void> {

        private IngredientDao mAsyncTaskDao;

        DeleteIngredientAsyncTask(IngredientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ingredient... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class InsertIngredientsAsyncTask extends AsyncTask<Ingredient, Void, Void> {

        private IngredientDao mAsyncTaskDao;

        InsertIngredientsAsyncTask(IngredientDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ingredient... params) {
            params[0].setId((int) mAsyncTaskDao.insert(params[0]));
            return null;
        }
    }
}
