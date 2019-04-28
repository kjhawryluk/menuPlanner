package edu.uchicago.kjhawryluk.prolistview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyAndDailyMenus;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

public class WeeklyMenuViewModel extends AndroidViewModel {
    private MenuRepositiory mRepository;

    private LiveData<List<WeeklyMenu>> mAllMenus;

    public WeeklyMenuViewModel (Application application) {
        super(application);
        mRepository = new MenuRepositiory(application);
        mAllMenus = mRepository.getAllMenus();
    }

    LiveData<List<WeeklyMenu>> getAllMenus() { return mAllMenus; }

    public void insert(WeeklyAndDailyMenus menu) { mRepository.insert(menu); }
}
