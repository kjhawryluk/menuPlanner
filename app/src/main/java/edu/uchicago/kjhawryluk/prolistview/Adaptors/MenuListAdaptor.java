package edu.uchicago.kjhawryluk.prolistview.Adaptors;

import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.WeeklyMenu;
import edu.uchicago.kjhawryluk.prolistview.R;

public class MenuListAdaptor extends RecyclerView.Adapter<MenuListAdaptor.WeeklyMenuViewHolder> {

    class WeeklyMenuViewHolder extends RecyclerView.ViewHolder {
        private final TextView mMenuDate;

        private WeeklyMenuViewHolder(View itemView) {
            super(itemView);
            mMenuDate = itemView.findViewById(R.id.menuDate);
        }
    }

    private final LayoutInflater mInflater;
    private List<WeeklyMenu> mMenus; // Cached copy of menus

    public MenuListAdaptor(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WeeklyMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.weekly_menu_item, parent, false);
        return new WeeklyMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeeklyMenuViewHolder holder, int position) {
        if (mMenus != null) {
            WeeklyMenu current = mMenus.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
            String strStartDate = formatter.format(current.getStartDate());
            holder.mMenuDate.setText(strStartDate);
        } else {
            // Covers the case of data not being ready yet.
            holder.mMenuDate.setText("No Menus");
        }
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