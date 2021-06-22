package com.seek.hrm.Fragments;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class Adapter  extends FragmentPagerAdapter {
    private String tabsList [] = {"HELP","MEASURE","HISTORY"};
    private HistoryMeasureFragment historyMeasureFragment;
    private MeasureFragment measureFragment;
    private HelpFragment helpFragment;
    public Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                helpFragment = new HelpFragment();
                return helpFragment;
            case 1:
                measureFragment = new MeasureFragment();
                return measureFragment;
            case 2:
                historyMeasureFragment = new HistoryMeasureFragment();
                return historyMeasureFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsList.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "HELP";
                break;
            case 1:
                title = "MEASURE";
                break;
            case 2:
                title = "HISTORY";
                break;
        }
        return title;
    }
}
