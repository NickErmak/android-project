package com.senla.calculatortoolbar.fragment;

import android.support.v4.app.Fragment;

import com.senla.calculatortoolbar.App;
import com.senla.calculatortoolbar.R;
import com.senla.calculatortoolbar.activity.MainActivity;

public class FragCalculated extends Fragment{

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_CALCULATED";
    public static final String FRAGMENT_TITLE = App.self.getString(R.string.frag_calculated);

    public static FragCalculated newInstance() {
        return new FragCalculated();
    }

    public FragCalculated(){}

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).onFragmentStart(FRAGMENT_TITLE, FRAGMENT_TAG);
    }
}
