package ehersenaw.com.github.shoose;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingPreferenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingPreferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingPreferenceFragment extends PreferenceFragment {
    // TODO: Rename parameter arguments, choose names that match

    private OnFragmentInteractionListener mListener;

    SharedPreferences prefs;

    EditTextPreference IDPreference;
    EditTextPreference PWPreference;
    ListPreference genderPreference;
    EditTextPreference sizePreference;
    EditTextPreference phoneNumPreference;
    EditTextPreference e_MailPreference;

    String message;
    String result;

    public SettingPreferenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingPreferenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingPreferenceFragment newInstance(String param1, String param2) {
        SettingPreferenceFragment fragment = new SettingPreferenceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: This is from blog, edit to real one
        addPreferencesFromResource(R.xml.settings_preference);
        IDPreference = (EditTextPreference)findPreference("IDPreference");
        PWPreference = (EditTextPreference)findPreference("PWPreference");
        genderPreference = (ListPreference)findPreference("genderPreference");
        sizePreference = (EditTextPreference)findPreference("sizePreference");
        phoneNumPreference = (EditTextPreference)findPreference("phoneNumPreference");
        e_MailPreference = (EditTextPreference)findPreference("e_MailPreference");
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if (!prefs.getString("IDPreference", "").equals("")) {
            IDPreference.setSummary(prefs.getString("IDPreference", "KaTalk"));
        }
        if (!prefs.getString("PWPreference", "").equals("")) {
            PWPreference.setSummary(prefs.getString("PWPreference", "KaTalk"));
        }
        if (!prefs.getString("genderPreference", "").equals("")) {
            genderPreference.setSummary(prefs.getString("genderPreference", "KaTalk"));
        }
        if (!prefs.getString("sizePreference", "").equals("")) {
            sizePreference.setSummary(prefs.getString("sizePreference", "KaTalk"));
        }
        if (!prefs.getString("phoneNumPreference", "").equals("")) {
            phoneNumPreference.setSummary(prefs.getString("phoneNumPreference", "KaTalk"));
        }
        if (!prefs.getString("e_MailPreference", "").equals("")) {
            e_MailPreference.setSummary(prefs.getString("e_MailPreference", "KaTalk"));
        }

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
        //
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("IDPreference")) {
                IDPreference.setSummary(prefs.getString("IDPreference", "String 1"));
            }
            if (key.equals("PWPreference")) {
                PWPreference.setSummary(prefs.getString("PWPreference", "String 2"));
            }
            if (key.equals("genderPreference")) {
                genderPreference.setSummary(prefs.getString("genderPreference", "String 3"));
            }
            if (key.equals("sizePreference")) {
                sizePreference.setSummary(prefs.getString("sizePreference", "String 4"));
            }
            if (key.equals("phoneNumPreference")) {
                phoneNumPreference.setSummary(prefs.getString("phoneNumPreference", "String 5"));
            }
            if (key.equals("e_MailPreference")) {
                e_MailPreference.setSummary(prefs.getString("e_MailPreference", "String 6"));
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    /*
    public void setOnFragmentInteractionListener(Activity activity) {
        mListener = activity;
    }
    */

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
        mListener = null;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
