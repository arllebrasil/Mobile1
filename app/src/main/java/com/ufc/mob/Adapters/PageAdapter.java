package com.ufc.mob.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ufc.mob.BlueFragment;
import com.ufc.mob.RedFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private int tabsQtd;
    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm);
        tabsQtd = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BlueFragment();
            case 1:
                return new RedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsQtd;
    }
}
