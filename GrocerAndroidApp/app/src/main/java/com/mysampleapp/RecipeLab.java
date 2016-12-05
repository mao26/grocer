package com.mysampleapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/4/16.
 */

public class RecipeLab {

    private static RecipeLab sRecipeLab;
    private List<Recipe> mRecipeList;
    private Context mContext;

    public static RecipeLab get(Context context){
        if(sRecipeLab == null)
            sRecipeLab = new RecipeLab(context);
        return sRecipeLab;
    }

    private RecipeLab(Context context){
        context = context;
        mRecipeList = testRecipes();

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

    private List<Recipe> testRecipes(){
        List<Recipe> testRecipes = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            String[] ingredients = new String[3];
            for(int j = 0; j < 3; j++){
                ingredients[0] = "Ingredient #" + j;
            }
            testRecipes.add(new Recipe("Recipe #" + i, ingredients));
        }
        return testRecipes;
    }

}
