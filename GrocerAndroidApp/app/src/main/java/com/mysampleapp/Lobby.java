package com.mysampleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobile.user.IdentityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/3/16.
 */

public class Lobby extends AppCompatActivity {

    private static final String LOG_TAG = Lobby.class.getSimpleName();
    private String[] mMonths;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle = "QC";
    private String mDrawerTitle = "Drawer";
    private HashSet<String> recipeSets;
    private static final String BUNDLE = "bundle";

    /** The identity manager used to keep track of the current user account. */
    private IdentityManager identityManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // Obtain a reference to the mobile client. It is created in the Application class,
        // but in case a custom Application class is not used, we initialize it here if necessary.
        AWSMobileClient.initializeMobileClientIfNecessary(this);

        // Obtain a reference to the mobile client. It is created in the Application class.
        final AWSMobileClient awsMobileClient = AWSMobileClient.defaultMobileClient();

        // Obtain a reference to the identity manager.
        identityManager = awsMobileClient.getIdentityManager();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        setUpActivityDrawer(myToolbar);

        Button emptyFridgeButton = (Button) findViewById(R.id.empty_fridge_button);
        emptyFridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Lobby.this, BundlesLobby.class));
            }
        });

        SharedPreferences sharedPref = Lobby.this.getSharedPreferences("Personal recipes", Context.MODE_PRIVATE);
        HashSet<String> random = new HashSet<>();
        HashSet value = (HashSet) sharedPref.getStringSet("recipeSets", random);
        Log.i(LOG_TAG, value.toString());

        LinearLayout fridgeAddContainer = (LinearLayout) findViewById(R.id.lets_add_fridge_container);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lobby_list_view);
        recipeSets = (HashSet) sharedPref.getStringSet("recipeSets", random);
        if(recipeSets.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            fridgeAddContainer.setVisibility(View.GONE);
        } else{
            recyclerView.setVisibility(View.GONE);
            fridgeAddContainer.setVisibility(View.VISIBLE);
        }
        //this logic needs to get changed
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BundlesLab bundlesController = new BundlesLab(getApplicationContext(), new ArrayList<String>(recipeSets));
        List<OneBundle> bundlesList = bundlesController.getBundles();
        BundlesAdapter mBundlesAdapter = new BundlesAdapter(bundlesList);
        recyclerView.setAdapter(mBundlesAdapter);
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
                    Intent i = new Intent(Lobby.this, RecipeLobby.class);
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
            LayoutInflater layoutInflater = LayoutInflater.from(Lobby.this);
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

    private void setUpActivityDrawer(Toolbar myToolbar) {
        mMonths = getResources().getStringArray(R.array.drawer_menu);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(android.R.id.list);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                , mMonths));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                myToolbar, R.string.drawer_open, R.string.drawer_closer) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.user).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.user:
                showUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showUser() {
        Intent i = new Intent(Lobby.this, UserProfile.class);
        startActivity(i);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {

        if(position == 4) startActivity(new Intent(Lobby.this, MainActivity.class));
        if(position == 0){
            identityManager.signOut();
            // Start the sign-in activity. Do not finish this activity to allow the user to navigate back.
            startActivity(new Intent(this, SignInActivity.class));
        }
        if(position == 1) startActivity(new Intent(this, BundlesLobby.class));

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMonths[position] + " hello!");
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

}
