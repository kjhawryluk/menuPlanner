package edu.uchicago.kjhawryluk.prolistview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Adaptors.MenuListAdaptor;
import edu.uchicago.kjhawryluk.prolistview.Adaptors.WeeklyMenuAdaptor;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;
import edu.uchicago.kjhawryluk.prolistview.TypeConverters.DateConverter;

public class WeeklyMenuFragment extends Fragment {

    public static final String MENU_ID = "MENU_ID";
    public static final String MENU_DATE = "MENU_DATE";
    private WeeklyMenuViewModel mViewModel;
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

        mViewModel = ViewModelProviders.of(this,
                new WeeklyMenuViewModel(getActivity().getApplication(), mMenuId))
                .get(WeeklyMenuViewModel.class);

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
                Toast toast = Toast.makeText(container.getContext(),
                        "Opening Shopping List", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        //Bind Data to recycler.
        final WeeklyMenuAdaptor adapter = new WeeklyMenuAdaptor(container.getContext(), mViewModel);
        dailyMenuList.setAdapter(adapter);
        dailyMenuList.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mViewModel.getDailyMenus().observe(this, new Observer<List<DailyMenu>>() {
            @Override
            public void onChanged(@Nullable final List<DailyMenu> menus) {
                // Update the cached copy of the words in the adapter.
                adapter.setMenus(menus);
            }
        });

        return root;
    }


}
