package com.hazacs.smdtabs;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by HaZa on 05-Apr-16.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter{

    public FragmentPageAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0:
                return new BuyFragment();
            case 1:
                return new SellFragment();
            case 2:
                return new ExchangeFragment();

            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
