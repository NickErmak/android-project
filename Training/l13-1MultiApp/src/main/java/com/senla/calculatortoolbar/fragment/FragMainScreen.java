package com.senla.calculatortoolbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.senla.calculatortoolbar.App;
import com.senla.calculatortoolbar.R;
import com.senla.calculatortoolbar.activity.MainActivity;
import com.senla.calculatortoolbar.dialog.ExitDialogFragment;
import com.senla.calculatortoolbar.dialog.InfoDialogFragment;

public class FragMainScreen extends Fragment implements View.OnClickListener, ExitDialogFragment.OkHandler{

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG_MAIN";
    public static final String FRAGMENT_TITLE = App.self.getString(R.string.frag_main);

    public static FragMainScreen newInstance() {
        return new FragMainScreen();
    }

    public FragMainScreen() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Button) view.findViewById(R.id.main_btn_about_program)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.main_btn_about_author)).setOnClickListener(this);
        ((Button) view.findViewById(R.id.main_btn_exit)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_about_program:
                InfoDialogFragment.newInstance(getString(R.string.dialog_program_title), getString(R.string.dialog_program_message))
                        .show(getFragmentManager(), null);
                break;
            case R.id.main_btn_about_author:
                InfoDialogFragment.newInstance(getString(R.string.dialog_author_title), getString(R.string.dialog_author_message))
                        .show(getFragmentManager(), null);
                break;
            case R.id.main_btn_exit:
                ExitDialogFragment.newInstance().show(getChildFragmentManager(), null);
                break;
        }
    }

    @Override
    public void onOkPressed() {
        getActivity().finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).onFragmentStart(FRAGMENT_TITLE, FRAGMENT_TAG);
    }
}
