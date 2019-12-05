package com.example.batb;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.batb.adapters.MainPagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

public class ImageList extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_img);

        //list
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        //tab
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setTitles("IU", "V", "Queen", "Jeong");
        navigationTabStrip.setViewPager(viewPager);

    }

    public interface FragmentListener {
        void getCelebId(int a);
    }

}
