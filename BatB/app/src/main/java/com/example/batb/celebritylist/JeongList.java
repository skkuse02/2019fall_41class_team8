package com.example.batb.celebritylist;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.example.batb.R;
import com.example.batb.utils.Utils;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.example.batb.utils.Utils.setupItem;

//import com.example.batb.adapters.VerticalPagerAdapter;

public class JeongList extends Fragment {
    public static int celebid;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);
        Log.d("HPF - celebid",Integer.toString(celebid));
        horizontalInfiniteCycleViewPager.setAdapter(new HorizontalPagerAdapter(getContext(), 0));
    }
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

        private void getLoopImg(int a){
            for(int i = 0;i<99; i++){
                tmpSign = "jeong_"+(i+1);
                int lid = mContext.getResources().getIdentifier(tmpSign, "drawable", mContext.getPackageName());
                //LIBRARIES[i] = new Utils.LibraryObject(lid, "IU"+i);
                if ( lid != 0) LIBRARIES.add(new Utils.LibraryObject(lid, "JEONG"+i));
            }
        }
        public HorizontalPagerAdapter(final Context context, final int celeb) {
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
            getLoopImg(celeb);
        }
        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final View view;
            view = mLayoutInflater.inflate(R.layout.item, container, false);
            setupItem(view, LIBRARIES.get(position));
            container.addView(view);
            return view;
        }
        @Override
        public int getCount() {
            return LIBRARIES.size();
        }

        @Override
        public int getItemPosition(final Object object) {

            return POSITION_NONE;
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

}
