package com.example.isebenzi.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by takhleeqmacpro on 12/26/16.
 */

public class CustomTabLayout extends TabLayout {
    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void setupWithViewPager(ViewPager viewPager)
    {
        super.setupWithViewPager(viewPager);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "Lato-Bold.ttf");

        if (typeface != null)
        {
            this.removeAllTabs();

            ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);

            PagerAdapter adapter = viewPager.getAdapter();

            for (int i = 0, count = adapter.getCount(); i < count; i++)
            {
                Tab tab = this.newTab();
                this.addTab(tab.setText(adapter.getPageTitle(i)));
                TextView view = (TextView) ((ViewGroup) slidingTabStrip.getChildAt(i)).getChildAt(1);
                view.setTypeface(typeface, Typeface.NORMAL);
            }
        }
    }
//    @Override
//    public void setTabsFromPagerAdapter(@NonNull PagerAdapter adapter) {
//        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.ttf");
//
//        this.removeAllTabs();
//
//        ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);
//
//        for (int i = 0, count = adapter.getCount(); i < count; i++) {
//            Tab tab = this.newTab();
//            this.addTab(tab.setText(adapter.getPageTitle(i)));
//            AppCompatTextView view = (AppCompatTextView) ((ViewGroup)slidingTabStrip.getChildAt(i)).getChildAt(1);
//            view.setTypeface(typeface, Typeface.NORMAL);
//        }
//    }
}