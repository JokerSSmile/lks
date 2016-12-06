package net.volgatech.lks.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.volgatech.lks.fragments.tabs.GrantsPretendantTab;
import net.volgatech.lks.fragments.tabs.GrantsTab;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new GrantsTab();
            case 1:
                return new GrantsPretendantTab();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }


}