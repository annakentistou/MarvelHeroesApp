package com.codehub.marvelheroesapp.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.codehub.marvelheroesapp.Fragments.FirstFragment;
import com.codehub.marvelheroesapp.Fragments.SecondFragment;
import com.codehub.marvelheroesapp.Fragments.ThirdFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    private int tabsNumber;

    public TabsAdapter(@NonNull FragmentManager fm, int behavior,int tabs) {
        super(fm, behavior);
        this.tabsNumber = tabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();
            case 2:
                return new ThirdFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
