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
                SearchFragment tabFragment1 = new SearchFragment();
                return tabFragment1;
            case 1:
                RecommendationFragment tabFragment2 = new RecommendationFragment();
                return tabFragment2;
            case 2:
                TournamentFragment tabFragment3 = new TournamentFragment();
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
