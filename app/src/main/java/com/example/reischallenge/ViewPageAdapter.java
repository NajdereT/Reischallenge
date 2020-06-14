package com.example.reischallenge;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }
    private String tabTitles[] = new String[]{"Bestemming", "Hotels", "Vervoer"};

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentBestemmingen(); //ChildFragment1 at position 0
            case 1:
                return new FragmentHotels(); //ChildFragment2 at position 1
            case 2:
                return new FragmentVervoer(); //ChildFragment3 at position 2


        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 3; //three fragments
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];

    }
}