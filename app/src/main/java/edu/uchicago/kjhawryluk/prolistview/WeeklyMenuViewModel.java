package edu.uchicago.kjhawryluk.prolistview;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;

public class WeeklyMenuViewModel extends ViewModel implements ViewModelProvider.Factory {
    private MenuRepository mRepository;
    private int menuId;
    private LiveData<List<DailyMenu>> mAllDailyMenus;
    @NonNull
    private final Application mApplication;

    public WeeklyMenuViewModel(@NonNull Application application, int menuId) {
        this.menuId = menuId;
        mApplication = application;
        mRepository = new MenuRepository(application);
        mAllDailyMenus = mRepository.getDailyMenusById(menuId);
    }

    public LiveData<List<DailyMenu>> getDailyMenus() { return mAllDailyMenus; }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeeklyMenuViewModel(mApplication, menuId);
    }


}
