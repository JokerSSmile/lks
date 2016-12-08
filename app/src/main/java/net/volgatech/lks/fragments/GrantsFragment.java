package net.volgatech.lks.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.volgatech.lks.Adapter.TabAdapter;
import net.volgatech.lks.R;

public class GrantsFragment extends Fragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    TabLayout tabLayout = null;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.f_grants_fragment, container, false);

        tabLayout = (TabLayout) view.findViewById(R.id.grantsTabLayout);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.grantsTab)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.grantsPretendantTab)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabAdapter adapter = new TabAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager = (ViewPager) view.findViewById(R.id.grantsPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setOnTabSelectedListener(this);

        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        tabLayout.setScrollPosition(tab.getPosition(), 0f, true);
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        tabLayout.setScrollPosition(position, 0f, true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
