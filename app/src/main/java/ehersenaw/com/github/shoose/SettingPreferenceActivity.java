package ehersenaw.com.github.shoose;

import android.net.Uri;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

import java.util.List;

public class SettingPreferenceActivity extends PreferenceActivity{
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_settingpreference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingPreferenceFragment.class.getName().equals(fragmentName);
    }
    /*
    @Override
    public void onAttachFragment(PreferenceFragment fragment) {
        if (fragment instanceof SettingPreferenceFragment) {
            SettingPreferenceFragment settingPreferenceFragment = (SettingPreferenceFragment) fragment;
            settingPreferenceFragment.setOnFragmentInteractionListener(this);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    */
}
