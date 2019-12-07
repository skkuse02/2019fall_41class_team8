package com.example.batb.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView textView;
    //String packageName = this.getPackageName();

    List<Utils.LibraryObject> LIBRARIES = new ArrayList<Utils.LibraryObject>();

    private void getLoopImg(int k){

            for(int i = 0;i<99; i++){
                tmpSign = ImageList.celebName[k]+"_"+(i+1);
                int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
                if ( lid != 0) {
                    LIBRARIES.add(new Utils.LibraryObject(lid, ImageList.celebName[k]+i));
                    break;
                }
            }


    }

    private int celebId;
    private int celebAcc;

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