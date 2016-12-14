package com.mysampleapp;

import java.io.Serializable;

/**
 * Created by mario_oliver93 on 12/4/16.
 */

public class Recipe implements Serializable {

    private String mTitle;
    private String[] mIngredients;
    private Integer[] ingThumbnails;
    private Integer recipeThumbnail;

    public Recipe(String title, Integer recipeThumbnail, String[] ingredients, Integer[] ingThumbnails){
        mTitle = title;
        mIngredients = ingredients;
        this.ingThumbnails = ingThumbnails;
        this.recipeThumbnail = recipeThumbnail;
    }

    public String getTitle() {
        return mTitle;
    }

    public String[] getIngredients() {
        return mIngredients;
    }

    public Integer[] getThumbnails(){
        return ingThumbnails;
    }

    public Integer getRecipeThumbnail(){
        return recipeThumbnail;
    }

}
