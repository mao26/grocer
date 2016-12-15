package com.mysampleapp;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/13/16.
 */

public class EggBundle extends OneBundle {

    private List<Recipe> mRecipeList;

    public EggBundle(Context context) {
        super(context, "Eggs Basic");
        mRecipeList = createEggsPackage();
    }

    @Override
    public List<Recipe> getRecipes() {
        return mRecipeList;
    }

    @Override
    public Recipe getRecipe(String title){
        for (Recipe recipe : mRecipeList){
            if(recipe.getTitle().equals(title)){
                return recipe;
            }
        }
        return null;
    }

    private Integer[] mThumbIds = {
            R.mipmap.hard_boiled_eggs_tn, R.mipmap.soft_boiled_egg_tn,
            R.mipmap.sunny_side_egg_tn, R.mipmap.scrambled_egg_tn,
    };

    private String[] titles = {
            "Hard Boiled Eggs", "Soft Boiled Eggs", "Sunny Side Up Eggs", "Scrambled Eggs"
    };

    private Integer[] ingThumbs = {
            R.mipmap.egg_tn, R.mipmap.butter_tn,
            R.mipmap.milk_tn,
            R.mipmap.olive_oil_tn,
            R.mipmap.salt_tn, R.mipmap.water_tn,
    };

    private String[] ingredients = {"Egg", "Butter", "Milk", "Olive Oil", "Salt", "Water"};

    private List<Recipe> createEggsPackage(){
        List<Recipe> onePackage_Basics = new ArrayList<>();
        //pass a list of items and a list of bitimages
        //so pass the list of basic images that already have been created for basics
        //do the same thing for the eggs package that you are about to create
        for(int i = 0; i < titles.length; i++) {
            Integer recipeThumb = mThumbIds[i];
            onePackage_Basics.add(new Recipe(titles[i], recipeThumb, ingredients, ingThumbs));
        }
        return onePackage_Basics;
    }

}
