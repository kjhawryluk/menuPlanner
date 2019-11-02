package edu.uchicago.kjhawryluk.menuPlanner.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.uchicago.kjhawryluk.menuPlanner.Listeners.EditTextEnterListener;
import edu.uchicago.kjhawryluk.menuPlanner.ViewModels.WeeklyMenuListViewModel;
import edu.uchicago.kjhawryluk.menuPlanner.Models.Ingredient;
import edu.uchicago.kjhawryluk.menuPlanner.R;

public class DailyMenuAdaptor extends RecyclerView.Adapter<DailyMenuAdaptor.DailyMenuViewHolder> {

    class DailyMenuViewHolder extends RecyclerView.ViewHolder {
        private final EditText mDailyMenuIngredientNameEditText;
        private final EditText mDailyMenuIngredientQuantityEditText;
        private final CheckBox mCurrentOwnCheckBox;
        private final Button mRemoveDailyIngredient;

        private DailyMenuViewHolder(View itemView) {
            super(itemView);
            mDailyMenuIngredientNameEditText = itemView.findViewById(R.id.dailyMenuIngredientNameEditText);
            mDailyMenuIngredientQuantityEditText = itemView.findViewById(R.id.dailyMenuIngredientQuantityEditText);
            mCurrentOwnCheckBox = itemView.findViewById(R.id.currentOwnCheckBox);
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
            holder.mCurrentOwnCheckBox.setChecked(current.isCurrentlyOwn());
            // Update the ingredient name when it changes.
            holder.mDailyMenuIngredientNameEditText.setOnFocusChangeListener(new IngredientChangeListener(current, holder));
            holder.mDailyMenuIngredientQuantityEditText.setOnFocusChangeListener(new IngredientChangeListener(current, holder));

            holder.mDailyMenuIngredientNameEditText.setOnKeyListener(new EditTextEnterListener(holder.mDailyMenuIngredientNameEditText));
            holder.mDailyMenuIngredientQuantityEditText.setOnKeyListener(new EditTextEnterListener(holder.mDailyMenuIngredientQuantityEditText));
            holder.mCurrentOwnCheckBox.setOnCheckedChangeListener(new IngredientChangeListener(current, holder));

            //Delete it if the button is clicked.
            holder.mRemoveDailyIngredient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDailyMenuViewModel.delete(current);
                }
            });
        }
    }

    private class IngredientChangeListener implements OnFocusChangeListener, CheckBox.OnCheckedChangeListener {
        Ingredient selectedIngredient;
        DailyMenuViewHolder holder;

        public IngredientChangeListener(Ingredient selectedIngredient, DailyMenuViewHolder holder) {
            this.selectedIngredient = selectedIngredient;
            this.holder = holder;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                saveIngredientChanges();
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            saveIngredientChanges();
        }

        private void saveIngredientChanges() {
            selectedIngredient.setQuantity(getValidQuantity());
            selectedIngredient.setName(holder.mDailyMenuIngredientNameEditText.getText().toString());
            selectedIngredient.setCurrentlyOwn(holder.mCurrentOwnCheckBox.isChecked());
            mDailyMenuViewModel.insert(selectedIngredient);
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