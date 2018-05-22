package com.tm_synchronizer.ecobinmobileappv10.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.Html;

import com.tm_synchronizer.ecobinmobileappv10.R;

public class StatisticsFragment extends Fragment {

    private ViewPager viewPager;
    TextView cycle_count, days_label;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private TextView[] dots;
    SmartFragmentStatePagerAdapter adapterViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        cycle_count = rootView.findViewById(R.id.cycle_count);
        days_label = rootView.findViewById(R.id.days_label);

        viewPager = rootView.findViewById(R.id.sliding_charts);
        dotsLayout = rootView.findViewById(R.id.layoutDots);

        layouts = new int[]{
                R.layout.fragment_temperature_chart,
                R.layout.fragment_humidity_chart,
                R.layout.fragment_methane_chart};

        addBottomDots(0);

        adapterViewPager = new SmartFragmentStatePagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        return rootView;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        private int NUM_ITEMS = 3;

        public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return TemperatureFragment.newInstance(0, "Page  1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return MethaneFragment.newInstance(1,"Page 2");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return HumidityFragment.newInstance(2, "Page  3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }
}