package edu.uchicago.kjhawryluk.prolistview;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.DAOs.DailyMenuDao;
import edu.uchicago.kjhawryluk.prolistview.DAOs.IngredientDao;
import edu.uchicago.kjhawryluk.prolistview.DAOs.WeeklyMenuDao;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

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


    public LiveData<List<DailyMenu>> getDailyMenus() {
        return mDailyMenus;
    }

    public LiveData<List<DailyMenu>> getDailyMenusById(int id) {
        return mDailyMenuDao.getDailyMenusById(id);
    }

//    public DailyMenu getDailyMenuById(int id) {
//        return mDailyMenuDao.getDailyMenuById(id);
//    }

    public LiveData<List<Ingredient>> getAllDailyIngredientsById(int id) {
        return mIngredientDao.getAllDailyIngredients(id);
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
        new InsertIngredientsAsyncTask(mIngredientDao).execute(ingredients);
    }

    public void insert(WeeklyMenu weeklyAndDailyMenu) {
        new InsertWeeklyAndDailyMenusAsyncTask(mWeeklyMenuDao).execute(weeklyAndDailyMenu);
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
            List<Ingredient> ingredientsToInsert = new ArrayList(Arrays.asList(params));
            mAsyncTaskDao.insert(ingredientsToInsert);
            return null;
        }
    }

    private static class GetDailyMenuAsyncTask extends AsyncTask<Integer, Void, DailyMenu> {

        private DailyMenuDao mAsyncTaskDao;

        GetDailyMenuAsyncTask(DailyMenuDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected DailyMenu doInBackground(Integer... integers) {
            return mAsyncTaskDao.getDailyMenuById(integers[0]);
        }
    }
}
