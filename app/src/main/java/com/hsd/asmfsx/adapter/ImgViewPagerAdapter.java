package com.hsd.asmfsx.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.hsd.asmfsx.view.fragment.ImgViewPagerFragment;

import java.util.List;

/**
 * Created by sun on 2016/12/12.
 */

public class ImgViewPagerAdapter extends FragmentPagerAdapter{
    private List<String> urls;

    public ImgViewPagerAdapter(FragmentManager fm, List<String> urls) {
        super(fm);
        this.urls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("url", urls.get(position));
        ImgViewPagerFragment viewPagerFragment = ImgViewPagerFragment.getInstance(bundle);
        return viewPagerFragment;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

}
