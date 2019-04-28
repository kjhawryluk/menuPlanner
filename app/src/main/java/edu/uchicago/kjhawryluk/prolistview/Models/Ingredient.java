package edu.uchicago.kjhawryluk.prolistview.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "ingredient_table")
//        foreignKeys = @ForeignKey(entity = DailyMenu.class,
//        parentColumns = "mId",
//        childColumns = "mDailyMenuId",
//        onDelete = CASCADE))
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;
    private int mDailyMenuId;
    private String mName;
    private int mQuantity;
    private boolean currentlyOwn;

    public Ingredient(String name) {
        mName = name;
        // By default assume they need 1 of an ingredient.
        mQuantity = 1;
        currentlyOwn = false;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public boolean isCurrentlyOwn() {
        return currentlyOwn;
    }

    public void setCurrentlyOwn(boolean currentlyOwn) {
        this.currentlyOwn = currentlyOwn;
    }




}
