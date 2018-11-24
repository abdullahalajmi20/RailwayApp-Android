package com.sn.railway.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sn.railway.Base_Activity;
import com.sn.railway.R;
import com.sn.railway.fragment.OrderHistoryFragment;
import com.sn.railway.fragment.SearchFragment;
import com.sn.railway.fragment.MapFragment;
import com.sn.railway.fragment.SettingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends Base_Activity {

    @Bind(R.id.viewPagerTab)
    SmartTabLayout viewPagerTab;

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        final ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        actionBar.setDisplayHomeAsUpEnabled(false);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.app_name));

        final LayoutInflater inflater = LayoutInflater.from(viewPagerTab.getContext());

        final Resources res = viewPagerTab.getContext().getResources();

        viewPagerTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {

                ImageView icon = (ImageView) inflater.inflate(R.layout.custom_tab_icon, container, false);
                switch (position) {
                    case 0:
                        icon.setImageDrawable(res.getDrawable(R.drawable.home));
                        break;
                    case 1:
                        icon.setImageDrawable(res.getDrawable(R.drawable.search));
                        break;
                    case 2:
                        icon.setImageDrawable(res.getDrawable(R.drawable.order));
                        break;
                    case 3:
                        icon.setImageDrawable(res.getDrawable(R.drawable.settings));
                        break;
                }
                return icon;
            }
        });
        FragmentPagerItems pages = new FragmentPagerItems(this);

        pages.add(FragmentPagerItem.of(getString(R.string.app_name), MapFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.search), SearchFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.order), OrderHistoryFragment.class));
        pages.add(FragmentPagerItem.of(getString(R.string.setting), SettingFragment.class));


        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPagerTab.setViewPager(viewPager);
        actionBar.setTitle(getString(R.string.app_name));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                hideKeyboard(viewPager);
                switch (position) {
                    case 0:
                        actionBar.setTitle(getString(R.string.app_name));
                        break;
                    case 1:
                        actionBar.setTitle(getString(R.string.search));
                        break;
                    case 2:
                        actionBar.setTitle(getString(R.string.orderhistory));
                        break;
                    case 3:
                        actionBar.setTitle(getString(R.string.setting));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}