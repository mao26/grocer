package com.mysampleapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario_oliver93 on 12/9/16.
 */

public class BundlesLab {

    //    private static OneBundle sBundles;
    private List<OneBundle> mBundlesList;
    private Context context;

    public BundlesLab(Context context) {
        this.context = context;
        mBundlesList = testBundles();
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
