package com.thebiggestgame.mikejudgeapps.psubluescreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SwipeFragment extends Fragment {
    private ViewPager mViewPager;
    private AnnouncementPagerAdapter mPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_swipe, container, false);

        //this next line fixed my big problem with the pages not being redrawn on back button press
        //ChildFragmentManager works, but FragmentManager does not
        mPagerAdapter = new AnnouncementPagerAdapter(getChildFragmentManager());

        mViewPager = (ViewPager)v.findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        return v;
    }



}
