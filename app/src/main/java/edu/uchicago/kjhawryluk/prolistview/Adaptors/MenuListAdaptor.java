package edu.uchicago.kjhawryluk.prolistview.Adaptors;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;
import edu.uchicago.kjhawryluk.prolistview.R;
import edu.uchicago.kjhawryluk.prolistview.WeeklyMenuFragment;
import edu.uchicago.kjhawryluk.prolistview.ViewModels.WeeklyMenuListViewModel;

public class MenuListAdaptor extends RecyclerView.Adapter<MenuListAdaptor.WeeklyMenuViewHolder> {

    class WeeklyMenuViewHolder extends RecyclerView.ViewHolder {
        private final TextView mMenuDate;
        private final ImageButton mDeleteButton;
        private final LinearLayout mWeeklyMenuItem;
        private WeeklyMenuViewHolder(View itemView) {
            super(itemView);
            mMenuDate = itemView.findViewById(R.id.menuDate);
            mDeleteButton = itemView.findViewById(R.id.removeMenu);
            mWeeklyMenuItem = itemView.findViewById(R.id.weeklyMenuItem);
        }
    }

    private final LayoutInflater mInflater;
    private List<WeeklyMenu> mMenus; // Cached copy of menus
    private WeeklyMenuListViewModel mWeeklyMenuListViewModel;

    public MenuListAdaptor(Context context, WeeklyMenuListViewModel weeklyMenuListViewModel)
    {
        mInflater = LayoutInflater.from(context);
        mWeeklyMenuListViewModel = weeklyMenuListViewModel;
    }

    @Override
    public WeeklyMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.weekly_menu_item, parent, false);
        return new WeeklyMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeeklyMenuViewHolder holder, int position) {
        if (mMenus != null) {
            final WeeklyMenu current = mMenus.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
            String strStartDate = formatter.format(current.getStartDate());
            holder.mMenuDate.setText(strStartDate);
            holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWeeklyMenuListViewModel.delete(current);
                }
            });
            holder.mWeeklyMenuItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openWeeklyMenu(view, current.getId(), current.getStartDate().getTime());
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.mMenuDate.setText("No Menus");
        }
    }

    private void openWeeklyMenu(View view, int menuId, long date) {
        WeeklyMenuFragment weeklyMenuFragment= WeeklyMenuFragment.newInstance(menuId, date);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentBody, weeklyMenuFragment)
                .addToBackStack(null)
                .commit();
    }

    public void setMenus(List<WeeklyMenu> menus){
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