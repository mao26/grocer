package com.mysampleapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/13/16.
 */

public class BasicsBundle extends OneBundle {

    private List<Recipe> mRecipeList;

    public BasicsBundle(Context context) {
        super(context, "Basics Bundle");
        mRecipeList = createBasicsPackage();
    }

    private Integer[] mThumbIds = {
            R.mipmap.egg_tn, R.mipmap.butter_tn,
            R.mipmap.milk_tn, R.mipmap.sugar_tn,
            R.mipmap.olive_oil_tn, R.mipmap.flour_tn,
            R.mipmap.salt_tn, R.mipmap.water_tn,
    };

    private String[] ingredients = {
            "Eggs", "Butter", "Milk", "Sugar", "Olive Oil", "Flour", "Salt", "Sugar"
    };

    private String[] prepInstructionsForHardBoiledEggs = {
            "Take out the number of eggs you want to cook!", "Fill up pot with water up to a height that covers the eggs by 1 inch.",
            "Bring a pot of water to a boil over high heat",
    };

    private List<Recipe> createBasicsPackage(){
        List<Recipe> onePackage_Basics = new ArrayList<>();
        //pass a list of items and a list of bitimages
        //so pass the list of basic images that already have been created for basics
        //do the same thing for the eggs package that you are about to create
        onePackage_Basics.add(new Recipe("Basics",new Integer(R.mipmap.basics_tn), ingredients, mThumbIds, prepInstructionsForHardBoiledEggs));
        return onePackage_Basics;
    }

    public List<Recipe> getRecipes(){
        return mRecipeList;
    }

    public Recipe getRecipe(String title){
        for (Recipe recipe : mRecipeList){
            if(recipe.getTitle().equals(title)){
                return recipe;
            }
        }
        return null;
    }

}
