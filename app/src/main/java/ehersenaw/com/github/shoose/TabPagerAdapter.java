package ehersenaw.com.github.shoose;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    //Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                HomeFragment tabFragment1 = new HomeFragment();
                return tabFragment1;
            case 1:
                SearchFragment tabFragment2 = new SearchFragment();
                return tabFragment2;
            case 2:
                RecommendationFragment tabFragment3 = new RecommendationFragment();
                return tabFragment3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
