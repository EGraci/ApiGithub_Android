package com.example.apigithub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterMenu extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentName = new ArrayList<>();

    public AdapterMenu(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    public void setFragment(Fragment frag, String name){
        fragmentList.add(frag);
        fragmentName.add(name);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentName.get(position);
    }
}
