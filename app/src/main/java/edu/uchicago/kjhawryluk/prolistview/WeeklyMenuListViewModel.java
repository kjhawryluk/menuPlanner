package edu.uchicago.kjhawryluk.prolistview;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

public class WeeklyMenuListViewModel extends AndroidViewModel {
    private MenuRepository mRepository;

    private LiveData<List<WeeklyMenu>> mAllMenus;

    public WeeklyMenuListViewModel(Application application) {
        super(application);
        mRepository = new MenuRepository(application);
        mAllMenus = mRepository.getAllMenus();
    }

    LiveData<List<WeeklyMenu>> getAllMenus() { return mAllMenus; }

    public void insert(WeeklyMenu menu) { mRepository.insert(menu); }
    public void delete(WeeklyMenu menu) { mRepository.delete(menu); }
}
