package edu.uchicago.kjhawryluk.menuPlanner.Adaptors;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import edu.uchicago.kjhawryluk.menuPlanner.ViewModels.WeeklyMenuListViewModel;
import edu.uchicago.kjhawryluk.menuPlanner.Models.Ingredient;
import edu.uchicago.kjhawryluk.menuPlanner.R;

public class ShoppingListAdaptor extends RecyclerView.Adapter<ShoppingListAdaptor.ShoppingListViewHolder> {

    class ShoppingListViewHolder extends RecyclerView.ViewHolder {
        private final TextView mShoppingListIngredientNameTextView;
        private final TextView mShoppingListIngredientQuantityTextView;
        private final CheckBox mPurchasedCheckBox;

        private ShoppingListViewHolder(View itemView) {
            super(itemView);
            mShoppingListIngredientNameTextView = itemView.findViewById(R.id.shoppingListIngredientNameTextView);
            mShoppingListIngredientQuantityTextView = itemView.findViewById(R.id.shoppingListIngredientQuantityTextView);
            mPurchasedCheckBox = itemView.findViewById(R.id.purchasedCheckBox);
        }
    }

    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients; // Cached copy of menus
    private WeeklyMenuListViewModel mDailyMenuViewModel;

    public ShoppingListAdaptor(Context context, WeeklyMenuListViewModel dailyMenuViewHolder) {
        mInflater = LayoutInflater.from(context);
        mDailyMenuViewModel = dailyMenuViewHolder;
    }

    @Override
    public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.shopping_list_ingredient, parent, false);
        return new ShoppingListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
        if (mIngredients != null) {
            final Ingredient current = mIngredients.get(position);
            holder.mShoppingListIngredientNameTextView.setText(current.getName());
            holder.mShoppingListIngredientQuantityTextView.setText(String.valueOf(current.getQuantity()));
            //in some cases, it will prevent unwanted situations
            holder.mPurchasedCheckBox.setOnCheckedChangeListener(null);

            //if true, your checkbox will be selected, else unselected
            holder.mPurchasedCheckBox.setChecked(current.isCurrentlyOwn());


            //Cross out ingredient.
            if (current.isCurrentlyOwn()) {
                holder.mShoppingListIngredientNameTextView.setPaintFlags(
                        holder.mShoppingListIngredientNameTextView.getPaintFlags()
                                | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.mShoppingListIngredientNameTextView.setPaintFlags(
                        holder.mShoppingListIngredientNameTextView.getPaintFlags()
                                & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
            // Update the ingredient name when it changes.
            holder.mPurchasedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // Changes the owned status upon checking and above handles crossing
                // out the item.
                current.setCurrentlyOwn(isChecked);
                mDailyMenuViewModel.insert(current);
            });

        } else {
            holder.mShoppingListIngredientNameTextView.setText("No Ingredients");
        }
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
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