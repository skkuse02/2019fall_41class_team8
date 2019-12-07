package com.example.batb.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.example.batb.ImageList;
import com.example.batb.R;
import com.example.batb.utils.Utils;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.example.batb.utils.Utils.setupItem;

//get image
public class HorizontalPagerAdapter extends PagerAdapter {
    String tmpSign;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    //String packageName = this.getPackageName();

    List<Utils.LibraryObject> LIBRARIES = new ArrayList<Utils.LibraryObject>();

    private void getLoopImg(int k){

            for(int i = 0;i<99; i++){
                tmpSign = ImageList.celebName[k]+"_"+(i+1);
                int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
                if ( lid != 0) LIBRARIES.add(new Utils.LibraryObject(lid, ImageList.celebName[k]+i));
            }

/*
        if(celebId == 0){
            for(int i = 0;i<99; i++){
                tmpSign = ImageList.celebName[0]+(i+1);
                int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
                //LIBRARIES[i] = new Utils.LibraryObject(lid, "IU"+i);
                if ( lid != 0) LIBRARIES.add(new Utils.LibraryObject(lid, "IU"+i));
            }
        }else if(celebId == 1){
            for(int i = 0;i<99; i++){
                tmpSign = "v_"+(i+1);
                int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
                //LIBRARIES[i] = new Utils.LibraryObject(lid, "IU"+i);
                if ( lid != 0) LIBRARIES.add(new Utils.LibraryObject(lid, "V"+i));
            }
        }else if(celebId == 2){
            for(int i = 0;i<99; i++){
                tmpSign = "queen_"+(i+1);
                int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
                //LIBRARIES[i] = new Utils.LibraryObject(lid, "IU"+i);
                if ( lid != 0) LIBRARIES.add(new Utils.LibraryObject(lid, "QUEEN"+i));
            }
        }else if(celebId == 3){
            for(int i = 0;i<99; i++){
                tmpSign = "jeong_"+(i+1);
                int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
                //LIBRARIES[i] = new Utils.LibraryObject(lid, "IU"+i);
                if ( lid != 0) LIBRARIES.add(new Utils.LibraryObject(lid, "JEONG"+i));
            }
        }

 */
    }

    private int celebId;

    public HorizontalPagerAdapter(final Context context, final int celeb) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        celebId = celeb;
        getLoopImg(celebId);

    }

    @Override
    public int getCount() {
        //getLoopImg();
        return LIBRARIES.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        //getLoopImg();
        final View view;
        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, LIBRARIES.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}