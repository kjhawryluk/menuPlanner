package edu.uchicago.kjhawryluk.prolistview.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.MenuRepository;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

public class WeeklyMenuListViewModel extends AndroidViewModel {
    private MenuRepository mRepository;

    private LiveData<List<WeeklyMenu>> mAllMenus;
    private LiveData<List<DailyMenu>> mAllDailyMenus;
    private LiveData<List<DailyMenu>> mSelectedDailyMenu;
    private LiveData<List<Ingredient>> mDailyMenuIngredients;
    private LiveData<List<Ingredient>> mShoppingListIngredients;
    private MutableLiveData<Integer> filterLiveDataByWeeklyMenuId = new MutableLiveData<>();
    private MutableLiveData<Integer> filterLiveDataByDailyMenuId = new MutableLiveData<>();


    public WeeklyMenuListViewModel(Application application) {
        super(application);
        mRepository = new MenuRepository(application);
        mAllMenus = mRepository.getAllMenus();
        mAllDailyMenus = Transformations.switchMap(filterLiveDataByWeeklyMenuId,
                weeklyMenuId -> mRepository.getAllDailyMenusByWeeklyMenuId(weeklyMenuId));
        mShoppingListIngredients = Transformations.switchMap(filterLiveDataByWeeklyMenuId,
                weeklyMenuId -> mRepository.getShoppingListIngredients(weeklyMenuId));
        mDailyMenuIngredients = Transformations.switchMap(filterLiveDataByDailyMenuId,
                dailyMenuId -> mRepository.getAllDailyIngredientsById(dailyMenuId));
        mSelectedDailyMenu = Transformations.switchMap(filterLiveDataByDailyMenuId,
                dailyMenuId -> mRepository.getDailyMenuById(dailyMenuId));
    }

    public void delete(Ingredient ingredient) {
        mRepository.delete(ingredient);
    }

    public void insert(Ingredient ingredient) {
        mRepository.insert(ingredient);
    }

    public void insert(Ingredient[] ingredient) {
        mRepository.insert(ingredient);
    }

    public LiveData<List<Ingredient>> getDailyMenuIngredients() {
        return mDailyMenuIngredients;
    }

    public LiveData<List<WeeklyMenu>> getAllMenus() {
        return mAllMenus;
    }

    public LiveData<List<DailyMenu>> getDailyMenus() {
        return mAllDailyMenus;
    }

    public void setWeeklyMenuId(Integer weeklyMenuId) {
        this.filterLiveDataByWeeklyMenuId.setValue(weeklyMenuId);
    }

    public void setDailyMenuId(Integer dailyMenuId) {
        filterLiveDataByDailyMenuId.setValue(dailyMenuId);
    }

    public LiveData<List<DailyMenu>> getAllDailyMenus() {
        return mAllDailyMenus;
    }

    public LiveData<List<Ingredient>> getShoppingListIngredients() {
        return mShoppingListIngredients;
    }

    public MutableLiveData<Integer> getFilterLiveDataByWeeklyMenuId() {
        return filterLiveDataByWeeklyMenuId;
    }

    public MutableLiveData<Integer> getFilterLiveDataByDailyMenuId() {
        return filterLiveDataByDailyMenuId;
    }

    public void insert(WeeklyMenu menu) {
        mRepository.insert(menu);
    }

    public void delete(WeeklyMenu menu) {
        mRepository.delete(menu);
    }
}
