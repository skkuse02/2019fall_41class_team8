package com.example.batb.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.batb.ImageList;
import com.example.batb.celebritylist.IuList;
import com.example.batb.celebritylist.JeongList;
import com.example.batb.celebritylist.QueenList;
import com.example.batb.celebritylist.VList;

public class MainPagerAdapter extends FragmentStatePagerAdapter {


    private final static int COUNT = 4;


    public Fragment ChoiceCeleb(String id){
        switch(id){
            case "IU":
                return new IuList();
            case "V":
                return new VList();
            case "Queen":
                return new QueenList();
            case "Jeong":
                return new JeongList();
            default:
                return new QueenList();
        }
    }

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(final int position) {
        if(position == 0){
            return ChoiceCeleb( ImageList.celebName[0]);
        }else if( position == 1){
            return ChoiceCeleb( ImageList.celebName[1]);
        }else if( position == 2){
            return ChoiceCeleb( ImageList.celebName[2]);
        }else{
            return ChoiceCeleb( ImageList.celebName[3]);
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }


}
