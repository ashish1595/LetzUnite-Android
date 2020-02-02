package com.letzunite.letzunite.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;

import com.letzunite.letzunite.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Deven Singh on 18 May, 2018.
 */
@Singleton
public class Resource {

    private final Resources resources;
    private final String packageName;

    @Inject
    public Resource(@ApplicationContext Context context){
        resources=context.getResources();
        packageName=context.getPackageName();
    }

    public int toColor(@ColorRes int colorId) {
        return resources.getColor(colorId);
    }

    public float toDimen(@DimenRes int dimenId) {
        return resources.getDimension(dimenId);
    }

    public int toInt(@IntegerRes int resId) {
        return resources.getInteger(resId);
    }

    public String toString(@StringRes int stringId) {
        return resources.getString(stringId);
    }

    public int toResourceId(String name, String defType) {
        return resources.getIdentifier(name,defType,packageName);
    }
}
