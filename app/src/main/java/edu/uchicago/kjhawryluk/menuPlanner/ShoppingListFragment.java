package edu.uchicago.kjhawryluk.menuPlanner;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.menuPlanner.Adaptors.ShoppingListAdaptor;
import edu.uchicago.kjhawryluk.menuPlanner.Models.Ingredient;
import edu.uchicago.kjhawryluk.menuPlanner.R;
import edu.uchicago.kjhawryluk.menuPlanner.ViewModels.WeeklyMenuListViewModel;

public class ShoppingListFragment extends Fragment {

    public static final String WEEKLY_MENU_ID = "WEEKLY_MENU_ID";
    private WeeklyMenuListViewModel mViewModel;
    private int mMenuId;
    private Date mDailyMenuDate;
    private Button mSeeWeeklyMenuButton;
    private RecyclerView mShoppingListIngredients;

    public ShoppingListFragment() {
    }

    public static ShoppingListFragment newInstance(int menuId) {
        ShoppingListFragment weeklyMenuFragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        args.putInt(WEEKLY_MENU_ID, menuId);
        weeklyMenuFragment.setArguments(args);
        return weeklyMenuFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMenuId = getArguments().getInt(WEEKLY_MENU_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.shopping_list_fragment, container, false);

        mViewModel = ViewModelProviders.of(this).get(WeeklyMenuListViewModel.class);

        mSeeWeeklyMenuButton = root.findViewById(R.id.seeWeeklyMenuFromShoppingList);
        mSeeWeeklyMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        mShoppingListIngredients = root.findViewById(R.id.shoppingListIngredientList);


        //Bind Data to recycler.
        final ShoppingListAdaptor shoppingListAdaptor = new ShoppingListAdaptor(container.getContext(), mViewModel);
        mShoppingListIngredients.setAdapter(shoppingListAdaptor);
        mShoppingListIngredients.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mViewModel.getShoppingListIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable final List<Ingredient> ingredients) {
                // Update the cached copy of the words in the adapter.
                shoppingListAdaptor.setIngredients(ingredients);
            }
        });

        mViewModel.setWeeklyMenuId(mMenuId);
        return root;
    }


}
