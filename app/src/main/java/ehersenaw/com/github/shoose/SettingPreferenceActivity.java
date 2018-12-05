package ehersenaw.com.github.shoose;

import android.preference.PreferenceActivity;

import java.util.List;

public class SettingPreferenceActivity extends PreferenceActivity {
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_settingpreference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingPreferenceFragment.class.getName().equals(fragmentName);
    }

}
