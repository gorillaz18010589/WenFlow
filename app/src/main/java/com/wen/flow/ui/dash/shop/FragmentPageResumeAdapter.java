package com.wen.flow.ui.dash.shop;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public  class FragmentPageResumeAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public FragmentPageResumeAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragmentPageResumeAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragmentList = list;
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
}
