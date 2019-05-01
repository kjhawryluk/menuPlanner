package edu.uchicago.kjhawryluk.prolistview.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Listeners.EditTextEnterListener;
import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.R;
import edu.uchicago.kjhawryluk.prolistview.ViewModels.WeeklyMenuListViewModel;

public class SelectedDailyMenuAdaptor extends RecyclerView.Adapter<SelectedDailyMenuAdaptor.DailyMenuViewHolder> {

    class DailyMenuViewHolder extends RecyclerView.ViewHolder {
        private final TextView mDailyMenuDateValueTextView;
        private final EditText mDailyMenuTitleEditText;
        private final EditText mDailyMenuURLEditText;

        private DailyMenuViewHolder(View itemView) {
            super(itemView);
            mDailyMenuDateValueTextView = itemView.findViewById(R.id.dailyMenuDateValueTextView);
            mDailyMenuTitleEditText = itemView.findViewById(R.id.dailyMenuTitleEditText);
            mDailyMenuURLEditText = itemView.findViewById(R.id.dailyMenuURLEditText);
        }
    }

    private final LayoutInflater mInflater;
    private List<DailyMenu> mDailyMenus; // Cached copy of menus
    private WeeklyMenuListViewModel mDailyMenuViewModel;

    public SelectedDailyMenuAdaptor(Context context, WeeklyMenuListViewModel dailyMenuViewHolder) {
        mInflater = LayoutInflater.from(context);
        mDailyMenuViewModel = dailyMenuViewHolder;
    }

    @Override
    public DailyMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.selected_daily_menu_item, parent, false);
        return new DailyMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DailyMenuViewHolder holder, int position) {
        if (mDailyMenus != null) {
            final DailyMenu current = mDailyMenus.get(position);
            SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy");
            String strStartDate = formatter.format(current.getDate());
            holder.mDailyMenuDateValueTextView.setText(strStartDate);
            holder.mDailyMenuTitleEditText.setText(current.getTitle());
            holder.mDailyMenuURLEditText.setText(current.getUrl());

            // Update the ingredient name when it changes.
            holder.mDailyMenuTitleEditText.setOnFocusChangeListener(new DailyMenuFocusListener(current, holder));
            holder.mDailyMenuURLEditText.setOnFocusChangeListener(new DailyMenuFocusListener(current, holder));

            holder.mDailyMenuTitleEditText.setOnKeyListener(new EditTextEnterListener(holder.mDailyMenuTitleEditText));
            holder.mDailyMenuURLEditText.setOnKeyListener(new EditTextEnterListener(holder.mDailyMenuURLEditText));


        }
    }

    private class DailyMenuFocusListener implements OnFocusChangeListener {
        DailyMenu selectedDailyMenu;
        DailyMenuViewHolder holder;

        public DailyMenuFocusListener(DailyMenu dailyMenu, DailyMenuViewHolder holder) {
            this.selectedDailyMenu = dailyMenu;
            this.holder = holder;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                selectedDailyMenu.setTitle(holder.mDailyMenuTitleEditText.getText().toString());
                selectedDailyMenu.setUrl(holder.mDailyMenuURLEditText.getText().toString());
                mDailyMenuViewModel.insert(selectedDailyMenu);
            }
        }
    }

    public void setDailyMenus(List<DailyMenu> dailyMenus) {
        mDailyMenus = dailyMenus;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mIngredients has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mDailyMenus != null)
            return mDailyMenus.size();
        else return 0;
    }
}