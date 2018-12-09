package ehersenaw.com.github.shoose;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.List;

public class SettingPreferenceActivity extends PreferenceActivity implements SettingPreferenceFragment.OnFragmentInteractionListener{
    Intent intent;
    private static int SN;
    private static String Token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        Log.i("Current intent pkg", intent.toString());
        if(null != intent.getAction()) {
            Log.i("Action", intent.getAction());
        } else {
            Log.i("Bundle_INTENT", intent.getExtras().toString());
            this.SN = intent.getExtras().getInt("SN");
            Log.i("Init intent SN", String.valueOf(SN));
            this.Token = intent.getExtras().getString("Token");
            Log.i("Init intent Token", Token);
        }
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.headers_settingpreference, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return SettingPreferenceFragment.class.getName().equals(fragmentName);
    }


    @Override
    public void MakeContentValues(ContentValues values) {
        try {
            Log.i("Put value: SN", String.valueOf(this.SN));
            values.put("SN", this.SN);
            Log.i("Put value: Token", this.Token);
            values.put("Cookie", this.Token);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}