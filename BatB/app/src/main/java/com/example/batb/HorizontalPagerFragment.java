package com.example.batb;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.batb.adapters.HorizontalPagerAdapter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;

public class HorizontalPagerFragment extends Fragment {
    //public static ArrayList<Integer> celebid = new ArrayList<Integer>();
    private int celebid = 0;
    public HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_horizontal, container, false);
    }
    public void setId(int s){
        celebid = s;
    }
    public int getCelebId() {
        return celebid;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        horizontalInfiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) view.findViewById(R.id.hicvp);

        //int cid = celebid.get(0);
        //celebid.remove(0);
        horizontalInfiniteCycleViewPager.setAdapter(new HorizontalPagerAdapter(getContext(), celebid));

    }
}