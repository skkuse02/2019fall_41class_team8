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
            /*
    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{

       new Utils.LibraryObject(
              R.drawable.iu_1,
                "IU1"
       ),
        new Utils.LibraryObject(
      R.drawable.iu_1,
       "IU1"
        )


    };
             */

    private void getLoopImg(){

        for(int i = 0;i<99; i++){
            tmpSign = "iu_"+(i+1);
            int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
            //LIBRARIES[i] = new Utils.LibraryObject(lid, "IU"+i);
            if ( lid != 0) LIBRARIES.add(new Utils.LibraryObject(lid, "IU"+i));
        }
    }



    private boolean mIsTwoWay;
    private int celebId;

    public HorizontalPagerAdapter(final Context context, final int celeb) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        //mIsTwoWay = isTwoWay;
        celebId = celeb;
        Log.d("celeb",Integer.toString(celebId));
        if(celebId == 0){
            for(int i = 0;i<99; i++){
                tmpSign = "iu_"+(i+1);
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
    }

    @Override
    public int getCount() {
       //getLoopImg();
        return mIsTwoWay ? 6 : LIBRARIES.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        if(celebId == 0){
            return 0;
        }else if(celebId == 1){
            return 1;
        }else if(celebId == 2){
            return 2;
        }else{
            return 3;
        }
        //return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        //getLoopImg();
        final View view;
        if (mIsTwoWay) {
            view = mLayoutInflater.inflate(R.layout.two_way_item, container, false);

            final VerticalInfiniteCycleViewPager verticalInfiniteCycleViewPager =
                    (VerticalInfiniteCycleViewPager) view.findViewById(R.id.vicvp);
            verticalInfiniteCycleViewPager.setAdapter(
                    new VerticalPagerAdapter(mContext)
            );
            verticalInfiniteCycleViewPager.setCurrentItem(position);
        } else {

            //여기서 아이템 바꾸기..?

            view = mLayoutInflater.inflate(R.layout.item, container, false);
            setupItem(view, LIBRARIES.get(position));
        }

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
