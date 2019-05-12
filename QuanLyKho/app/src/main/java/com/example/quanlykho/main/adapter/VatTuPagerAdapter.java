package com.example.quanlykho.main.adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class VatTuPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    public VatTuPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: {
                return "Kho";
            }
            case 1:{
                return "Vat Tu";
            }
            default:{
                return "Default";
            }
        }
    }
}
