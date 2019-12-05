package com.example.batb.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.example.batb.HorizontalPagerFragment;
import com.example.batb.ImageList;
import com.example.batb.TwoWayPagerFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private ImageList.FragmentListener flistener;

    private final static int COUNT = 4;

    private final static int IU = 0;
    private final static int V = 1;
    private final static int Queen = 2;
    private final static int Jeong = 3;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
    }
    public int setID(int a){
        return a;
    }
    @Override
    public Fragment getItem(final int position) {
        Log.d("position", Integer.toString(position));
        if(position == 0){
            HorizontalPagerFragment.getID(0);
            return new HorizontalPagerFragment();
        }else if( position == 1){
            HorizontalPagerFragment.getID(1);
            return new HorizontalPagerFragment();
        }else if( position == 2){
            HorizontalPagerFragment.getID(2);
            return new HorizontalPagerFragment();
        }else{
            HorizontalPagerFragment.getID(3);
            return new HorizontalPagerFragment();
        }
        /*
        switch (position) {
            case IU:
                //HPF에 0보내기.
                HorizontalPagerFragment.getID(0);
                return new HorizontalPagerFragment();
            case V:
                HorizontalPagerFragment.getID(1);
                return new HorizontalPagerFragment();
            case Queen:
                HorizontalPagerFragment.getID(2);
                return new HorizontalPagerFragment();
            case Jeong:
                HorizontalPagerFragment.getID(3);
            default:
                return new HorizontalPagerFragment();
        }

         */
    }

    public void setFragmentListener(ImageList.FragmentListener listener){
        this.flistener = listener;
    }
    @Override
    public int getCount() {
        return COUNT;
    }


}
