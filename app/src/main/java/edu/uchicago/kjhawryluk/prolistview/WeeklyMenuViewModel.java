package edu.uchicago.kjhawryluk.prolistview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

public class WeeklyMenuViewModel extends AndroidViewModel {
    private MenuRepository mRepository;

    private LiveData<List<WeeklyMenu>> mAllMenus;

    public WeeklyMenuViewModel (Application application) {
        super(application);
        mRepository = new MenuRepository(application);
        mAllMenus = mRepository.getAllMenus();
    }

    LiveData<List<WeeklyMenu>> getAllMenus() { return mAllMenus; }

    public void insert(WeeklyMenu menu) { mRepository.insert(menu); }
    public void delete(WeeklyMenu menu) { mRepository.delete(menu); }
}
