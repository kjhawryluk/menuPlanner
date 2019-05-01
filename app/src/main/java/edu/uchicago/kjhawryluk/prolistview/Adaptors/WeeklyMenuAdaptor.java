package edu.uchicago.kjhawryluk.prolistview.Adaptors;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.DailyMenuFragment;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.R;
import edu.uchicago.kjhawryluk.prolistview.ViewModels.WeeklyMenuListViewModel;
import edu.uchicago.kjhawryluk.prolistview.WeeklyMenuFragment;

public class WeeklyMenuAdaptor extends RecyclerView.Adapter<WeeklyMenuAdaptor.WeeklyMenuViewHolder> {

    class WeeklyMenuViewHolder extends RecyclerView.ViewHolder {
        private final TextView mDayOfWeekTextView;
        private final TextView mDailyMenuTitleTextView;
        private final LinearLayout mDailyMenuItem;
        private WeeklyMenuViewHolder(View itemView) {
            super(itemView);
            mDayOfWeekTextView = itemView.findViewById(R.id.dayOfWeekTextView);
            mDailyMenuTitleTextView = itemView.findViewById(R.id.dailyMenuTitleTextView);
            mDailyMenuItem = itemView.findViewById(R.id.dailyMenuItem);
        }
    }

    private final LayoutInflater mInflater;
    private List<DailyMenu> mMenus; // Cached copy of menus
    private WeeklyMenuListViewModel mWeeklyMenuListViewModel;

    public WeeklyMenuAdaptor(Context context, WeeklyMenuListViewModel weeklyMenuListViewModel)
    {
        mInflater = LayoutInflater.from(context);
        mWeeklyMenuListViewModel = weeklyMenuListViewModel;
    }

    @Override
    public WeeklyMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.daily_menu_item, parent, false);
        return new WeeklyMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeeklyMenuViewHolder holder, int position) {
        if (mMenus != null) {
            final DailyMenu current = mMenus.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String strDate = formatter.format(current.getDate());
            holder.mDayOfWeekTextView.setText(strDate);

            if(current.getTitle() != null){
                holder.mDailyMenuTitleTextView.setText(current.getTitle());
            }
            holder.mDailyMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDailyMenu(view, current.getId(), current.getDate().getTime());
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.mDailyMenuTitleTextView.setText("Daily Menus Are Missing!");
        }
    }

    private void openDailyMenu(View view, int dailyMenuId, long dailyMenuDateTime) {
        DailyMenuFragment dailyMenuFragment= DailyMenuFragment.newInstance(dailyMenuId, dailyMenuDateTime);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentBody, dailyMenuFragment)
                .addToBackStack(null)
                .commit();
    }


    public void setMenus(List<DailyMenu> menus){
        mMenus = menus;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mMenus has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMenus != null)
            return mMenus.size();
        else return 0;
    }
}