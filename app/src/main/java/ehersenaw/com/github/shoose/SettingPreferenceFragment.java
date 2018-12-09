package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingPreferenceFragment extends PreferenceFragment {

    private OnFragmentInteractionListener mListener;
    Intent intent;

    SharedPreferences prefs;

    EditTextPreference IDPreference;
    EditTextPreference PWPreference;
    ListPreference genderPreference;
    EditTextPreference sizePreference;
    EditTextPreference phoneNumPreference;
    EditTextPreference e_MailPreference;
    EditTextPreference agePreference;
    EditTextPreference prefer_colorPreference;
    EditTextPreference prefer_brandPreference;
    EditTextPreference prefer_typePreference;


    public SettingPreferenceFragment() {
        // Required empty public constructor
    }

    public static SettingPreferenceFragment newInstance(String param1, String param2) {
        SettingPreferenceFragment fragment = new SettingPreferenceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preference);
        IDPreference = (EditTextPreference)findPreference("IDPreference");
        PWPreference = (EditTextPreference)findPreference("PWPreference");
        genderPreference = (ListPreference)findPreference("genderPreference");
        sizePreference = (EditTextPreference)findPreference("sizePreference");
        phoneNumPreference = (EditTextPreference)findPreference("phoneNumPreference");
        e_MailPreference = (EditTextPreference)findPreference("e_MailPreference");
        agePreference = (EditTextPreference)findPreference("agePreference");
        prefer_colorPreference = (EditTextPreference)findPreference("prefer_colorPreference");
        prefer_brandPreference = (EditTextPreference)findPreference("prefer_brandPreference");
        prefer_typePreference = (EditTextPreference)findPreference("prefer_typePreference");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (!prefs.getString("IDPreference", "").equals("")) {
            IDPreference.setSummary(prefs.getString("IDPreference", "Shoose"));
        }
        if (!prefs.getString("PWPreference", "").equals("")) {
            PWPreference.setSummary(prefs.getString("PWPreference", "Shoose"));
        }
        if (!prefs.getString("genderPreference", "").equals("")) {
            genderPreference.setSummary(prefs.getString("genderPreference", "Shoose"));
        }
        if (!prefs.getString("sizePreference", "").equals("")) {
            sizePreference.setSummary(prefs.getString("sizePreference", "Shoose"));
        }
        if (!prefs.getString("phoneNumPreference", "").equals("")) {
            phoneNumPreference.setSummary(prefs.getString("phoneNumPreference", "Shoose"));
        }
        if (!prefs.getString("e_MailPreference", "").equals("")) {
            e_MailPreference.setSummary(prefs.getString("e_MailPreference", "Shoose"));
        }
        if (!prefs.getString("agePreference", "").equals("")) {
            agePreference.setSummary(prefs.getString("agePreference", "Shoose"));
        }
        if (!prefs.getString("prefer_colorPreference", "").equals("")) {
            prefer_colorPreference.setSummary(prefs.getString("prefer_colorPreference", "Shoose"));
        }
        if (!prefs.getString("prefer_brandPreference", "").equals("")) {
            prefer_brandPreference.setSummary(prefs.getString("prefer_brandPreference", "Shoose"));
        }
        if (!prefs.getString("prefer_typePreference", "").equals("")) {
            prefer_typePreference.setSummary(prefs.getString("prefer_typePreference", "Shoose"));
        }
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
        //
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("IDPreference")) {
                IDPreference.setSummary(prefs.getString("IDPreference", "String ID"));
                Log.i("SharedPreferenceChange", "ID "+prefs.getString("IDPreference", "String ID"));
            }
            if (key.equals("PWPreference")) {
                PWPreference.setSummary(prefs.getString("PWPreference", "String PW"));
            }
            if (key.equals("genderPreference")) {
                genderPreference.setSummary(prefs.getString("genderPreference", "String gender"));
            }
            if (key.equals("sizePreference")) {
                sizePreference.setSummary(prefs.getString("sizePreference", "String size"));
            }
            if (key.equals("phoneNumPreference")) {
                phoneNumPreference.setSummary(prefs.getString("phoneNumPreference", "String phoneNum"));
            }
            if (key.equals("e_MailPreference")) {
                e_MailPreference.setSummary(prefs.getString("e_MailPreference", "String e_Mail"));
            }
            if (key.equals("agePreference")) {
                agePreference.setSummary(prefs.getString("agePreference", "String age"));
            }
            if (key.equals("prefer_colorPreference")) {
                prefer_colorPreference.setSummary(prefs.getString("prefer_colorPreference", "String prefer_color"));
            }
            if (key.equals("prefer_brandPreference")) {
                prefer_brandPreference.setSummary(prefs.getString("prefer_brandPreference", "String prefer_brand"));
            }
            if (key.equals("prefer_typePreference")) {
                prefer_typePreference.setSummary(prefs.getString("prefer_typePreference", "String prefer_type"));
            }
            // Source to adapt 2depth PreferenceScreen's internal occurred preference context to 2depth PreferenceScreen
            //((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
        }
    };

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText(R.string.hello_blank_fragment);
//        return textView;
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //
        //if (context instanceof OnFragmentInteractionListener) {
        //    mListener = (OnFragmentInteractionListener) context;
        //} else {
        //    throw new RuntimeException(context.toString()
        //            + " must implement OnFragmentInteractionListener");
        //}
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("detach", "DETACH");
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        ContentValues values = new ContentValues();
        values.clear();
        values.put("prefer_type", prefs.getString("prefer_typePreference", "Blah"));
        values.put("prefer_brand", prefs.getString("prefer_brandPreference", "Blah"));
        values.put("prefer_color", prefs.getString("prefer_colorPreference", "Blah"));
        values.put("age", Integer.parseInt(prefs.getString("agePreference", "0")));
        values.put("e_mail", prefs.getString("e_MailPreference", "Blah"));
        values.put("phone_number", prefs.getString("phoneNumPreference", "Blah"));
        values.put("size", prefs.getString("sizePreference", "Blah"));
        values.put("gender", prefs.getString("genderPreference", "Blah"));
        values.put("password", prefs.getString("PWPreference", "DEFAULTPASS"));
        values.put("ID", prefs.getString("IDPreference", "DEFAULTID"));
        mListener.MakeContentValues(values);

        Log.i("values", values.toString());

        MyProfileCustom mMyProfileCustom = new MyProfileCustom(values);
        mMyProfileCustom.execute();


        mListener = null;
    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            Log.i("onStart", "ONSTART");
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void MakeContentValues(ContentValues values);
    }
}

class MyProfileCustom extends AsyncTask<Void, Void, String> {

    private ContentValues values;
    private String url;
    MyProfileCustom(ContentValues values){
        this.values = values;
        this.url = "http://13.125.41.85:3000/api/profile";
    }
    @Override
    protected String doInBackground(Void... voids) {

        RequestHTTPURLConnection requestHTTPURLConnection = new RequestHTTPURLConnection();
        try {
            JSONObject jsonObject_response = requestHTTPURLConnection.request(url, values);
            try {
                return jsonObject_response.get("message").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e2) {
                /*
                while (jsonObject_response == null) {
                    try {
                        Log.i("DETACH_SLEEP", "SLEEPING....");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    return jsonObject_response.get("message").toString();
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                */
            }

        } catch (CustomizedException e) {
            // TODO: Handle HTTP codes
            if (e.msg.equals("INTERNAL_ERROR")) {
                // Server Error
                return e.msg;
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(final String message) {
        if (!TextUtils.isEmpty(message)) {
            if (message.equals("INTERNAL_ERROR")){
                Log.i("HTTP CODE", message);
            } else {
                Log.i("message", message);
            }
        } else {
            Log.i("message", "Empty message");
        }
    }

}