package com.mysampleapp;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/9/16.
 */

public class BundlesLab {

    //    private static OneBundle sBundles;
    private List<OneBundle> mBundlesList;
    private Context context;
    private static final String LOG_TAG = BundlesLab.class.getSimpleName();

    public BundlesLab(Context context) {
        this.context = context;
        mBundlesList = testBundles();
    }

    public BundlesLab(Context context, List<String> bundlesChosen) {
        this.context = context;
        mBundlesList = createFromList(bundlesChosen);
    }

    private List<OneBundle> createFromList(List<String> bundlesChosen) {
        List<OneBundle> testBundlesList = new ArrayList<>();
        for(String bundle : bundlesChosen){
            Log.i(LOG_TAG, bundle);
            if(bundle.equals("Basics Bundle")) testBundlesList.add(new BasicsBundle(context));
            else if(bundle.equals("Eggs Basic")) testBundlesList.add(new EggBundle(context));
        }
        return testBundlesList;
    }

    public List<OneBundle> getBundles() {
        return mBundlesList;
    }

    public OneBundle getBundle(String title) {
        for (OneBundle oneBundle : mBundlesList) {
            if (oneBundle.getTitle().equals(title)) {
                return oneBundle;
            }
        }
        return null;
    }

    private List<OneBundle> testBundles() {
        List<OneBundle> testBundlesList = new ArrayList<>();
        OneBundle basicsBundle = new BasicsBundle(context);
        OneBundle eggsBundle = new EggBundle(context);
        testBundlesList.add(basicsBundle);
        testBundlesList.add(eggsBundle);
        return testBundlesList;
    }

}
