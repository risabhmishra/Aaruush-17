package com.rm.dell.aaruush17;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sarthak on 15/7/17.
 */

public class AboutUsAdapter extends FragmentPagerAdapter{
    private String tabTitles[] = new String[]{"SRM University", "Legacy", "Tagline", "Theme", "Timeline", "Patrons"};

    public AboutUsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        /*
        if (position == 0) {
            return new AboutSrmFragment();
        } else if (position == 1) {
            return new AboutLegacyFragment();
        } else if(position == 2){
            return new AboutTaglineFragment();
        }else if(position == 3){
            return new AboutThemeFragment();
        }else {
            return new AboutTimlineFragment();
        }*/


        switch (position) {
            case 0:
                return new AboutSrmFragment();
            case 1:
                return new AboutLegacyFragment();
            case 2:
                return new AboutTaglineFragment();
            case 3:
                return new AboutThemeFragment();
            case 4:
                return new AboutTimlineFragment();
            case 5:
                return new patrons_fragment();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
