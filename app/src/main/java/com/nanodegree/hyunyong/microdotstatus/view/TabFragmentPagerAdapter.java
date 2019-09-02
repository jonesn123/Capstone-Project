package com.nanodegree.hyunyong.microdotstatus.view;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mFragmentTitles = new ArrayList<>();

    public TabFragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
        notifyDataSetChanged();
    }

    public void removeFragment(Fragment fragment) {
        int position = -1;
        for (int i = 0; i < mFragments.size(); i++) {
            if (fragment.equals(mFragments.get(i))) {
                position = i;
            }
        }
        if (position == -1) return;

        mFragments.remove(position);
        mFragmentTitles.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
