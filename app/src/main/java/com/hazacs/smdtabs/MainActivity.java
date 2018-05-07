package com.hazacs.smdtabs;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.plus.PlusShare;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    FragmentPageAdapter ft;

    public GoogleAnalytics googleAnalytics;
    public Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleAnalytics= GoogleAnalytics.getInstance(this);
        googleAnalytics.setLocalDispatchPeriod(1800);

        tracker=googleAnalytics.newTracker("<KEY>");
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

        Button postAd = (Button) findViewById(R.id.postAdBtn);
        postAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Google+ share dialog with attribution to your app.
                Intent loginIntent = new Intent(getBaseContext(), BookCategory.class);
                startActivity(loginIntent);
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Google+ share dialog with attribution to your app.
                Intent loginIntent=new Intent(getBaseContext(),Login.class);
                startActivity(loginIntent);
            }
        });


        viewPager = (ViewPager) findViewById(R.id.pager);
        ft = new FragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(ft);


        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagerTab);
        viewPagerTab.setViewPager(viewPager);

        ((TextView)viewPagerTab.getTabAt(0)).setText("Buy");
        ((TextView)viewPagerTab.getTabAt(1)).setText("Sell");
        ((TextView)viewPagerTab.getTabAt(2)).setText("Exchange");

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                                              @Override
                                              public void onPageScrolled(int i, float v, int i1) {

                                              }

                                              @Override
                                              public void onPageSelected(int i) {
                                              }

                                              @Override
                                              public void onPageScrollStateChanged(int i) {

                                              }
                                          }

        );

    }
}
