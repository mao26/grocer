package com.mysampleapp;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/4/16.
 */

public abstract class OneBundle implements Serializable {

    private Context mContext;
    private String title;

    public abstract List<Recipe> getRecipes();

    public abstract Recipe getRecipe(String title);

    public OneBundle(Context context, String title){
        context = context;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

//    private List<Recipe> testRecipes(){
//        List<Recipe> testRecipes = new ArrayList<>();
//        for(int i = 0; i < 100; i++){
//            String[] ingredients = new String[3];
//            for(int j = 0; j < 3; j++){
//                ingredients[0] = "Ingredient #" + j;
//            }
//            testRecipes.add(new Recipe("Recipe #" + i, ingredients));
//        }
//        return testRecipes;
//    }

}
