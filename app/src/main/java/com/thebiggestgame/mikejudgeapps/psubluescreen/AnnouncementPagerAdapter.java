package com.thebiggestgame.mikejudgeapps.psubluescreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Judge on 3/3/15.
 */
public class AnnouncementPagerAdapter extends FragmentPagerAdapter {

    public AnnouncementPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int n) {
        if (n == 0)
            return ListFragment.newInstance(ListFragment.EVENTS);
        else
            return ListFragment.newInstance(ListFragment.CANCELLATIONS);
    }

    @Override
    public int getCount() {
        return 2;
    }

}
