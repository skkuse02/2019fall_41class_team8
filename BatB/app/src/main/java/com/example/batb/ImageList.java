package com.example.batb;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.LayoutInflaterCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager.widget.ViewPager;

import com.example.batb.adapters.MainPagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import org.w3c.dom.Text;

public class ImageList extends AppCompatActivity{

    public static String[] celebName = new String[4];
    public static int[] accuracy = new int[4];
    private TextView textView;
    public ImageView imgv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_img);

        textView = (TextView)findViewById(R.id.text_similarity);

        String result = getIntent().getStringExtra("result");
        String[] arr1 = result.split("&");
        for(int i=0;i<4;i++){
            String[] arr2 = arr1[i].split("#");
            celebName[i]=arr2[0]; accuracy[i] = Integer.parseInt(arr2[1]);
        }

        textView.setText("99%");

        celebName[0] = "tzuyu"; celebName[1] = "mina"; celebName[2] = "sana"; celebName[3] = "momo";

        //list
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        //tab
        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setTitles(celebName[0],celebName[1], celebName[2], celebName[3]);
        navigationTabStrip.setViewPager(viewPager);

    }
    public void clickMethod(View view){
        Toast toast = Toast.makeText(getApplicationContext(),"다람쥐",Toast.LENGTH_SHORT);
        toast.show();
    }
}
