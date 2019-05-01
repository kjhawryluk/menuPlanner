package edu.uchicago.kjhawryluk.prolistview.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.uchicago.kjhawryluk.prolistview.Models.DailyMenu;
import edu.uchicago.kjhawryluk.prolistview.Models.Ingredient;
import edu.uchicago.kjhawryluk.prolistview.R;
import edu.uchicago.kjhawryluk.prolistview.ViewModels.WeeklyMenuListViewModel;

public class DailyMenuAdaptor extends RecyclerView.Adapter<DailyMenuAdaptor.DailyMenuViewHolder> {

    class DailyMenuViewHolder extends RecyclerView.ViewHolder {
        private final EditText mDailyMenuIngredientNameEditText;
        private final EditText mDailyMenuIngredientQuantityEditText;
        private final Button mRemoveDailyIngredient;

        private DailyMenuViewHolder(View itemView) {
            super(itemView);
            mDailyMenuIngredientNameEditText = itemView.findViewById(R.id.dailyMenuIngredientNameEditText);
            mDailyMenuIngredientQuantityEditText = itemView.findViewById(R.id.dailyMenuIngredientQuantityEditText);
            mRemoveDailyIngredient = itemView.findViewById(R.id.removeDailyIngredient);
        }
    }

    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients; // Cached copy of menus
    private WeeklyMenuListViewModel mDailyMenuViewModel;

    public DailyMenuAdaptor(Context context, WeeklyMenuListViewModel dailyMenuViewHolder) {
        mInflater = LayoutInflater.from(context);
        mDailyMenuViewModel = dailyMenuViewHolder;
    }

    @Override
    public DailyMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.daily_menu_ingredient, parent, false);
        return new DailyMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DailyMenuViewHolder holder, int position) {
        if (mIngredients != null) {
            final Ingredient current = mIngredients.get(position);
            holder.mDailyMenuIngredientNameEditText.setText(current.getName());
            holder.mDailyMenuIngredientQuantityEditText.setText(String.valueOf(current.getQuantity()));

            // Update the ingredient name when it changes.
            holder.mDailyMenuIngredientNameEditText.setOnFocusChangeListener(new IngredientFocusListener(current, holder));
            holder.mDailyMenuIngredientQuantityEditText.setOnFocusChangeListener(new IngredientFocusListener(current, holder));

            //Delete it if the button is clicked.
            holder.mRemoveDailyIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDailyMenuViewModel.delete(current);
                }
            });
        }
    }

    private class IngredientFocusListener implements OnFocusChangeListener {
        Ingredient selectedIngredient;
        DailyMenuViewHolder holder;

        public IngredientFocusListener(Ingredient selectedIngredient, DailyMenuViewHolder holder) {
            this.selectedIngredient = selectedIngredient;
            this.holder = holder;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                selectedIngredient.setQuantity(getValidQuantity());
                selectedIngredient.setName(holder.mDailyMenuIngredientNameEditText.getText().toString());
                mDailyMenuViewModel.insert(selectedIngredient);
            }
        }

        /**
         * Parses the quantity. If it's not a number, it will change it to 1 and notify the user.
         * @return
         */
        private int getValidQuantity(){
            String quantity = holder.mDailyMenuIngredientQuantityEditText.getText().toString();
            int quantityValue = 1;
            try {
                quantityValue = Integer.parseInt(quantity);
            }catch (NumberFormatException e){
                Toast toast = Toast.makeText(mInflater.getContext(),
                        "Invalid Quantity.", Toast.LENGTH_LONG);
                toast.show();
                holder.mDailyMenuIngredientQuantityEditText.setText(quantity);
            }
           return quantityValue;
        }
    }





    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mIngredients has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();
        else return 0;
    }
}