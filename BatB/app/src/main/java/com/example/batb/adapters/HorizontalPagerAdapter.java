package com.example.batb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.example.batb.R;
import com.example.batb.utils.Utils;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import static com.example.batb.utils.Utils.setupItem;

//get image
public class HorizontalPagerAdapter extends PagerAdapter {
    private final Utils.LibraryObject[] LIBRARIES = new Utils.LibraryObject[]{
            new Utils.LibraryObject(
                    R.drawable.iu_one,
                    "IU1"
            ),
            new Utils.LibraryObject(
                    R.drawable.iu_two,
                    "IU2"
            ),
            new Utils.LibraryObject(
                    R.drawable.v_one,
                    "V1"
            ),
            new Utils.LibraryObject(
                    R.drawable.v_two,
                    "V2"
            ),
            new Utils.LibraryObject(
                    R.drawable.queen_one,
                    "퀸연아1"
            ),
            new Utils.LibraryObject(
                    R.drawable.queen_two,
                    "퀸연아2"
            ),
            new Utils.LibraryObject(
                    R.drawable.queen_god,
                    "연아갓"
            )

    };

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private boolean mIsTwoWay;

    public HorizontalPagerAdapter(final Context context, final boolean isTwoWay) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mIsTwoWay = isTwoWay;
    }

    @Override
    public int getCount() {
        return mIsTwoWay ? 6 : LIBRARIES.length;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
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
            view = mLayoutInflater.inflate(R.layout.item, container, false);
            setupItem(view, LIBRARIES[position]);
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
