package com.mysampleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/9/16.
 */

public class BundlesLobby extends AppCompatActivity {

    private static final String LOG_TAG = BundlesLobby.class.getSimpleName();
    private BundlesAdapter mBundlesAdapter;
    private static final String BUNDLE = "bundle";
    private SharedPreferences.Editor editor;
    private HashSet<String> recipeSets;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_lobby);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BundlesLab bundlesController = new BundlesLab(getApplicationContext());
        List<OneBundle> bundlesList = bundlesController.getBundles();
        mBundlesAdapter = new BundlesAdapter(bundlesList);
        recyclerView.setAdapter(mBundlesAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPref = BundlesLobby.this.getSharedPreferences("Personal recipes", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

//        String value = sharedPref.getString("Recipe", "empty");
//        Log.i(LOG_TAG, value);

        HashSet random = (HashSet) new HashSet<String>();
        recipeSets = (HashSet) sharedPref.getStringSet("recipeSets", random);
        if(recipeSets == null) recipeSets = new HashSet();
        Log.i(LOG_TAG, recipeSets.toString());

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
        Log.i(LOG_TAG, "recipe sets is : " + recipeSets.toString());
        editor.putStringSet("recipeSets", recipeSets);
        editor.commit();
    }



    private class BundlesHolder extends RecyclerView.ViewHolder {

        public TextView mTitleTextView;
        private OneBundle currBundle;
        private ImageView fridgeLikeButton;
        private boolean fridgeIconOn = false;

        public BundlesHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.recipeview_title_textview);
            fridgeLikeButton = (ImageView) itemView.findViewById(R.id.add_to_fridge_icon);
            fridgeLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(fridgeIconOn){
                        fridgeLikeButton.setImageResource(R.mipmap.fridge_icon_off);
                        fridgeIconOn = false;
                        recipeSets.remove(mTitleTextView.getText().toString());
                    } else {
                        fridgeLikeButton.setImageResource(R.mipmap.fridge_icon_on);
                        fridgeIconOn = true;
//                        editor.putStringSet("Recipe", newHighScore);
                        recipeSets.add(mTitleTextView.getText().toString());
                    }
                    Log.i(LOG_TAG, recipeSets.toString());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(BundlesLobby.this, RecipeLobby.class);
                    /**
                     * RecipeHolder constructor gets called first to return a view - happens in OnCreateViewHolder;
                     * then Adapter calls onBindViewHolder that gives us the current recipe. However, we could never
                     * have a view onscreen until it is created and bound so this dependency is fine
                     *
                     */
                    i.putExtra(BUNDLE, currBundle);
                    startActivity(i);
                }
            });
        }

        public void bindView(OneBundle bundle) {
            currBundle = bundle;
            mTitleTextView.setText(bundle.getTitle());
            for(String recipe: recipeSets){
                Log.i(LOG_TAG, "current reciple in list: " + recipe + "title : " + mTitleTextView.getText().toString());
                if(recipe.equals(mTitleTextView.getText().toString())){
                    fridgeLikeButton.setImageResource(R.mipmap.fridge_icon_on);
                    fridgeIconOn = true;
                }
            }
        }
    }

    private class BundlesAdapter extends RecyclerView.Adapter<BundlesHolder> {

        private List<OneBundle> mBundlesList;

        public BundlesAdapter(List<OneBundle> bundles) {
            mBundlesList = bundles;
        }

        @Override
        public BundlesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(BundlesLobby.this);
            View view = layoutInflater.inflate(R.layout.activity_recipe_lobby_item, parent, false);
            return new BundlesHolder(view);
        }

        @Override
        public void onBindViewHolder(BundlesHolder holder, int position) {
            OneBundle bundle = mBundlesList.get(position);
            holder.bindView(bundle);
        }

        @Override
        public int getItemCount() {
            return mBundlesList.size();
        }
    }
}