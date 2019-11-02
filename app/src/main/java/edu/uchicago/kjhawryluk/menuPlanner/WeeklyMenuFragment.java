package edu.uchicago.kjhawryluk.menuPlanner;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.menuPlanner.Adaptors.WeeklyMenuAdaptor;
import edu.uchicago.kjhawryluk.menuPlanner.Models.DailyMenu;
import edu.uchicago.kjhawryluk.menuPlanner.R;
import edu.uchicago.kjhawryluk.menuPlanner.TypeConverters.DateConverter;
import edu.uchicago.kjhawryluk.menuPlanner.ViewModels.WeeklyMenuListViewModel;

public class WeeklyMenuFragment extends Fragment {

    public static final String MENU_ID = "MENU_ID";
    public static final String MENU_DATE = "MENU_DATE";
    private WeeklyMenuListViewModel mViewModel;
    private Date mMenuDate;
    private int mMenuId;
    private Button shoppingListButton;
    private TextView menuDateValueTextView;
    private RecyclerView dailyMenuList;

    public WeeklyMenuFragment() {}

    public static WeeklyMenuFragment newInstance(int menuId, long menuDate) {
        WeeklyMenuFragment weeklyMenuFragment = new WeeklyMenuFragment();
        Bundle args = new Bundle();
        args.putInt(MENU_ID, menuId);
        args.putLong(MENU_DATE, menuDate);
        weeklyMenuFragment.setArguments(args);
        return weeklyMenuFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMenuId = getArguments().getInt(MENU_ID);
            long menuDateValue = getArguments().getLong(MENU_DATE);
            try {
                mMenuDate = DateConverter.toDate(menuDateValue);
            } catch (IllegalArgumentException e) {
                Log.e("MENU_DATE ERROR",
                        "MENU_DATE value invalid. Received " + menuDateValue);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.weekly_menu_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(WeeklyMenuListViewModel.class);

        shoppingListButton = root.findViewById(R.id.seeShoppingListButton);
        menuDateValueTextView = root.findViewById(R.id.menuDateValueTextView);
        dailyMenuList = root.findViewById(R.id.dailyMenuList);

        // Format Static Top Of Page
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
        String menuDateStr = formatter.format(mMenuDate);
        menuDateValueTextView.setText(menuDateStr);

        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShoppingList(v, mMenuId);
            }
        });


        //Bind Data to recycler.
        final WeeklyMenuAdaptor adapter = new WeeklyMenuAdaptor(container.getContext(), mViewModel);
        dailyMenuList.setAdapter(adapter);
        dailyMenuList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mViewModel.getDailyMenus().observe(WeeklyMenuFragment.this, new Observer<List<DailyMenu>>() {
            @Override
            public void onChanged(@Nullable final List<DailyMenu> menus) {
                // Update the cached copy of the words in the adapter.
                adapter.setMenus(menus);
            }
        });

        mViewModel.setWeeklyMenuId(mMenuId);

        return root;
    }

    private void openShoppingList(View view, int menuId) {
        ShoppingListFragment shoppingListFragment= ShoppingListFragment.newInstance(menuId);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentBody, shoppingListFragment)
                .addToBackStack(null)
                .commit();
    }
}
