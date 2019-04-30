package edu.uchicago.kjhawryluk.prolistview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Adaptors.DailyMenuAdaptor;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.TypeConverters.DateConverter;
import edu.uchicago.kjhawryluk.prolistview.ViewModels.DailyMenuViewModel;

public class DailyMenuFragment extends Fragment {

    public static final String DAILY_MENU_ID = "DAILY_MENU_ID";
    public static final String DAILY_MENU_DATE = "DAILY_MENU_DATE";
    private DailyMenuViewModel mViewModel;
    private int mMenuId;
    private Date mDailyMenuDate;
    private Button mSeeWeeklyMenuButton;
    private TextView mDailyMenuDateValueTextView;
    private EditText mIngredientsToAddEditText;
    private Button mAddIngredientsButton;
    private RecyclerView mIngredientsRecycler;

    public DailyMenuFragment() {}

    public static DailyMenuFragment newInstance(int menuId, long dailyMenuDate) {
        DailyMenuFragment weeklyMenuFragment = new DailyMenuFragment();
        Bundle args = new Bundle();
        args.putInt(DAILY_MENU_ID, menuId);
        args.putLong(DAILY_MENU_DATE, dailyMenuDate);
        weeklyMenuFragment.setArguments(args);
        return weeklyMenuFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMenuId = getArguments().getInt(DAILY_MENU_ID);
            long menuDateTime = getArguments().getLong(DAILY_MENU_DATE);
            try {
                mDailyMenuDate = DateConverter.toDate(menuDateTime);
            } catch (IllegalArgumentException e) {
                Log.e("MENU_DATE ERROR",
                        "MENU_DATE value invalid. Received " + menuDateTime);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.daily_menu_fragment, container, false);

        mViewModel = ViewModelProviders.of(this,
                new DailyMenuViewModel(getActivity().getApplication(), mMenuId))
                .get(DailyMenuViewModel.class);

        mSeeWeeklyMenuButton = root.findViewById(R.id.seeWeeklyMenu);
        mDailyMenuDateValueTextView = root.findViewById(R.id.dailyMenuDateValueTextView);
        mIngredientsToAddEditText = root.findViewById(R.id.ingredientsToAdd);
        mAddIngredientsButton = root.findViewById(R.id.addIngredientsButton);
        mIngredientsRecycler = root.findViewById(R.id.dailyMenuIngredientList);

        // Format Static Top Of Page
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd, yyyy");
        String menuDateStr = formatter.format(mDailyMenuDate);
        mDailyMenuDateValueTextView.setText(menuDateStr);

//        mIngredientsToAddEditText.setOnFocusChangeListener(
//                new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        enableAddIngredientsButton(hasFocus);
//                    }
//                });

                mAddIngredientsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addIngredients();
                    }
                });

        mSeeWeeklyMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        //Bind Data to recycler.
        final DailyMenuAdaptor adapter = new DailyMenuAdaptor(container.getContext(), mViewModel);
        mIngredientsRecycler.setAdapter(adapter);
        mIngredientsRecycler.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mViewModel.getAllIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable final List<Ingredient> ingredients) {
                // Update the cached copy of the words in the adapter.
                adapter.setIngredients(ingredients);
            }
        });



        return root;
    }

    private void addIngredients() {
        String ingredientList = mIngredientsToAddEditText.getText().toString();
        String[] ingredientStrings = ingredientList.split(",");
        Ingredient[] ingredients = new Ingredient[ingredientStrings.length];
        for (int i = 0; i < ingredients.length; i++) {
            String ingredientName = ingredientStrings[i];
            ingredients[i] = new Ingredient(mMenuId, ingredientName);
        }
        mViewModel.insert(ingredients);
        mIngredientsToAddEditText.setText("");
    }

    private void enableAddIngredientsButton(boolean hasFocus) {
        if (!hasFocus) {
            if(mIngredientsToAddEditText.getText().toString().trim().length() > 0)
            {
                mAddIngredientsButton.setEnabled(true);
            } else{
                mAddIngredientsButton.setEnabled(false);
            }
        }
    }


}
