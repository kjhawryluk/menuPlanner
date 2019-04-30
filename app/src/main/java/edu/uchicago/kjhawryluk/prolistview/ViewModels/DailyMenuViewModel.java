package edu.uchicago.kjhawryluk.prolistview.ViewModels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.MenuRepository;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

public class DailyMenuViewModel extends ViewModel implements ViewModelProvider.Factory {
    private MenuRepository mRepository;
    private int dailyMenuId;
    private LiveData<List<Ingredient>> mAllIngredients;
    @NonNull
    private final Application mApplication;

    public DailyMenuViewModel(@NonNull Application application, int menuId) {
        dailyMenuId = menuId;
        mApplication = application;
        mRepository = new MenuRepository(application);
        mAllIngredients = mRepository.getAllDailyIngredientsById(menuId);
    }

    public LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    public void delete(Ingredient ingredient) { mRepository.delete(ingredient); }
    public void insert(Ingredient ingredient) { mRepository.insert(ingredient); }
    public void insert(Ingredient[] ingredient) { mRepository.insert(ingredient); }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DailyMenuViewModel(mApplication, dailyMenuId);
    }


}
