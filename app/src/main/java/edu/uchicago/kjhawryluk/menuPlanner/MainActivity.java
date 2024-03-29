package edu.uchicago.kjhawryluk.menuPlanner;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.uchicago.kjhawryluk.menuPlanner.ViewModels.WeeklyMenuListViewModel;
import edu.uchicago.kjhawryluk.menuPlanner.Adaptors.MenuListAdaptor;
import edu.uchicago.kjhawryluk.menuPlanner.Models.WeeklyMenu;
import edu.uchicago.kjhawryluk.menuPlanner.R;

public class MainActivity extends AppCompatActivity {
    private WeeklyMenuListViewModel mWeeklyMenuListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView weeklyMenuListRecycler = findViewById(R.id.weeklyMenuList);
        mWeeklyMenuListViewModel = ViewModelProviders.of(this).get(WeeklyMenuListViewModel.class);
        final MenuListAdaptor adapter = new MenuListAdaptor(this, mWeeklyMenuListViewModel);
        weeklyMenuListRecycler.setAdapter(adapter);
        weeklyMenuListRecycler.setLayoutManager(new LinearLayoutManager(this));

        mWeeklyMenuListViewModel.getAllMenus().observe(this, new Observer<List<WeeklyMenu>>() {
            @Override
            public void onChanged(@Nullable final List<WeeklyMenu> menus) {
                // Update the cached copy of the words in the adapter.
                adapter.setMenus(menus);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void createNewMenu() {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_create_new_menu);
        final CalendarView startDateCalendarView = dialog.findViewById(R.id.startDateCalendarView);

        //Update date when a new one is selected.
        startDateCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = new GregorianCalendar(year, month, dayOfMonth);
                startDateCalendarView.setDate(selectedDate.getTimeInMillis());
            }
        });
        Button newMenuButton = dialog.findViewById(R.id.newMenuButton);

        newMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeeklyMenu weeklyMenu = new WeeklyMenu(new Date(startDateCalendarView.getDate()));
                mWeeklyMenuListViewModel.insert(weeklyMenu);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "New menu created", Toast.LENGTH_SHORT);
                toast.show();
                dialog.dismiss();
            }
        });

        //Set up cancel button.
        Button buttonCancel = dialog.findViewById(R.id.cancelNewMenuButton);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.newMenuOption) {
            createNewMenu();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
