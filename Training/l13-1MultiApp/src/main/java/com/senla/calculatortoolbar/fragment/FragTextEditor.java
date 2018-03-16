package com.senla.calculatortoolbar.fragment;

import android.support.v4.app.Fragment;

import com.senla.calculatortoolbar.App;
import com.senla.calculatortoolbar.R;
import com.senla.calculatortoolbar.activity.MainActivity;

public class FragTextEditor extends Fragment {

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_TEXT_EDIT";
    public static final String FRAGMENT_TITLE = App.self.getString(R.string.frag_edit_note);

    public static FragTextEditor newInstance() {
        return new FragTextEditor();
    }

    public FragTextEditor() {}

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).onFragmentStart(FRAGMENT_TITLE, FRAGMENT_TAG);
    }
}
