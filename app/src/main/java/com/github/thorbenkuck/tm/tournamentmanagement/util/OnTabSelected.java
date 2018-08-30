package com.github.thorbenkuck.tm.tournamentmanagement.util;

import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import butterknife.internal.ListenerClass;
import butterknife.internal.ListenerMethod;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@ListenerClass(targetType = "import android.support.design.widget.TabLayout",
setter = "addOnTabSelectedListener",
remover = "removeOnTabSelectedListener",
type = "import android.support.design.widget.TabLayout.OnTabSelectedListener",
callbacks = OnTabSelected.Callback.class)
public @interface OnTabSelected {

    @IdRes int[] value() default {View.NO_ID};

    Callback callback() default Callback.ON_TAB_SELECTED;

    enum Callback {

        @ListenerMethod(name = "onTabSelected", parameters = "android.support.design.widget.TabLayout.Tab") ON_TAB_SELECTED,

        @ListenerMethod(name = "onTabUnselected", parameters = "android.support.design.widget.TabLayout.Tab") ON_TAB_UNSELECTED,

        @ListenerMethod(name = "onTabReselected", parameters = "android.support.design.widget.TabLayout.Tab") ON_TAB_RESELECTED

    }

}
