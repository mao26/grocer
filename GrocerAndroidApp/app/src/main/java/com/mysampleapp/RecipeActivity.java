package com.mysampleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mario_oliver93 on 12/4/16.
 */

public class RecipeActivity extends AppCompatActivity{

    private static final String RECIPE = "recipe";
    private Recipe currRecipe;
    private String[] ingTitles;
    private Integer[] ingThumbnails;
    AnimationDrawable rocketAnimation;

    @Override
    protected void onPause() {
        super.onPause();
        rocketAnimation.stop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ImageView image = (ImageView) findViewById(R.id.image_view_chef_cooking);
        image.setBackgroundResource(R.drawable.prep_now_chef);
        rocketAnimation = (AnimationDrawable) image.getBackground();
        rocketAnimation.start();

        currRecipe = (Recipe) getIntent().getSerializableExtra(RECIPE);
        ingTitles = currRecipe.getIngredients();
        ingThumbnails = currRecipe.getThumbnails();

        RecyclerView gridview = (RecyclerView) findViewById(R.id.recipeview_gridview);
        gridview.setLayoutManager(new GridLayoutManager(RecipeActivity.this, 3));
        gridview.setAdapter(new RecipeGridViewAdapter());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout cookNowButton = (LinearLayout) findViewById(R.id.cook_now_button_container);
        cookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecipeActivity.this, PrepNowActivity.class);
                i.putExtra(RECIPE, currRecipe);
                startActivity(i);
            }
        });
    }

    private class RecipeGridViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_thumbnail;
        private TextView tv_title;

        //changed the View itemview out of the constructor because I'm inflating a view
        public RecipeGridViewHolder(ViewGroup container) {
            super(getLayoutInflater().inflate(R.layout.list_item_recipe_gridview, container, false));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            iv_thumbnail = (ImageView) itemView.findViewById(R.id.recipeview_gridview_imageview);
            tv_title = (TextView) itemView.findViewById(R.id.recipeview_title_textview);
        }

        public void bindView(Integer thumb, String title) {
            iv_thumbnail.setImageResource(thumb);
            tv_title.setText(title);
        }
    }

    private class RecipeGridViewAdapter extends RecyclerView.Adapter<RecipeGridViewHolder>{

        @Override
        public RecipeGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecipeGridViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecipeGridViewHolder holder, int position) {
            Integer thumb = ingThumbnails[position];
            String title = ingTitles[position];
            holder.bindView(thumb, title);
        }

        @Override
        public int getItemCount() {
            return ingThumbnails.length;
        }
    }
}
