package com.example.batb.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.batb.HorizontalPagerFragment;
import com.example.batb.ImageList;
import java.util.HashMap;
import java.util.Map;

public class MainPagerAdapter extends FragmentStatePagerAdapter {


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
    }

    @Override
    public int getCount() {
        return COUNT;
    }


}
