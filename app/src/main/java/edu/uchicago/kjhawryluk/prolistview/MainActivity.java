package edu.uchicago.kjhawryluk.prolistview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Adaptors.MenuListAdaptor;
import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;

public class MainActivity extends AppCompatActivity {
    private WeeklyMenuViewModel mWeeklyMenuViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView weeklyMenuListRecycler = findViewById(R.id.weeklyMenuList);
        final MenuListAdaptor adapter = new MenuListAdaptor(this);
        weeklyMenuListRecycler.setAdapter(adapter);
        weeklyMenuListRecycler.setLayoutManager(new LinearLayoutManager(this));

        mWeeklyMenuViewModel = ViewModelProviders.of(this).get(WeeklyMenuViewModel.class);
        mWeeklyMenuViewModel.getAllMenus().observe(this, new Observer<List<WeeklyMenu>>() {
            @Override
            public void onChanged(@Nullable final List<WeeklyMenu> menus) {
                // Update the cached copy of the words in the adapter.
                adapter.setMenus(menus);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
//                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
//            }
  //      });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
//            mWordViewModel.insert(word);
//        } else {
//            Toast.makeText(
//                    getApplicationContext(),
//                    R.string.empty_not_saved,
//                    Toast.LENGTH_LONG).show();
//        }
    }
}
