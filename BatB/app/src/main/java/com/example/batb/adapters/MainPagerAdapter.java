package com.example.batb.adapters;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.batb.HorizontalPagerFragment;
import com.example.batb.ImageList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<HorizontalPagerFragment> hpFragment = new ArrayList<HorizontalPagerFragment>();
    private final static int COUNT = 4;

    //Map<String,Integer> ChoiceCeleb = new HashMap<String, Integer>();
    /*
    public Integer ChoiceCeleb(String id){
        switch(id){
            case "chaeyong":    return 0;
            case "dahyeon":     return 1;
            case "jeongyeon":   return 2;
            case "jihyo":       return 3;
            case "mina":        return 4;
            case "momo":        return 5;
            case "nayeon":      return 6;
            case "sana":        return 7;
            case "tzuyu":
            default:            return 8;
        }
    }

     */

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        if(position == 0){
            hpFragment.add(new HorizontalPagerFragment());
            hpFragment.get(hpFragment.size() - 1).setId(0);
            return hpFragment.get(hpFragment.size() - 1);
        }else if( position == 1){
            hpFragment.add(new HorizontalPagerFragment());
            hpFragment.get(hpFragment.size() - 1).setId(1);
            return hpFragment.get(hpFragment.size() - 1);
        }else if( position == 2){
            hpFragment.add(new HorizontalPagerFragment());
            hpFragment.get(hpFragment.size() - 1).setId(2);
            return hpFragment.get(hpFragment.size() - 1);
        }else{
            hpFragment.add(new HorizontalPagerFragment());
            hpFragment.get(hpFragment.size() - 1).setId(3);
            return hpFragment.get(hpFragment.size() - 1);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    public int myFunc(int celebId) {
        return hpFragment.get(celebId).horizontalInfiniteCycleViewPager.getRealItem();
    }
}
