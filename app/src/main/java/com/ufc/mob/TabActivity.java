package com.ufc.mob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.ufc.mob.Adapters.PageAdapter;

public class TabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        TabLayout tabs = findViewById(R.id.tabLayout);
        TabItem aba1 = findViewById(R.id.aba1);
        TabItem aba2 = findViewById(R.id.aba2);

        final ViewPager pager = findViewById(R.id.viewPager);
        PagerAdapter pagAdapter = new PageAdapter(getSupportFragmentManager(),tabs.getTabCount());
        pager.setAdapter(pagAdapter);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}