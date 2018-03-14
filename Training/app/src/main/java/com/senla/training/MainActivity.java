package com.senla.training;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Fragment2.OnSomeEventListener {

    private CheckBox mCbStack;
    private Fragment frag1, frag2;
    private FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCbStack = (CheckBox) findViewById(R.id.cb_stack);
        frag1 = new Fragment1();
        frag2 = new Fragment2();

        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frame_frag2, frag2);
        fTrans.commit();
    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add:
                fTrans.add(R.id.frame_frag2, frag1);
                fTrans.commit();
                break;
            case R.id.btn_remove:
                fTrans.remove(frag1);
                fTrans.commit();
                break;
            case R.id.btn_replace:
                fTrans.replace(R.id.frame_frag2, frag2);
                fTrans.commit();
                break;
            case R.id.btn_find:
                findFragmentsFromActivity();
                break;
        }
        if (mCbStack.isChecked()) {
            fTrans.addToBackStack(null);
        }
    }

    private void findFragmentsFromActivity() {
        Fragment findedFrag1 = getFragmentManager().findFragmentById(R.id.frag1);
        TextView tv = (TextView) findedFrag1.getView().findViewById(R.id.tv_frag1);
        tv.setText("fragment_1 founded!");

        Fragment findedFrag2 = getFragmentManager().findFragmentById(R.id.frame_frag2);
        tv = (TextView) findedFrag2.getView().findViewById(R.id.tv_frag2);
        tv.setText("fragment_2 founded!");
    }

    @Override
    public void someEvent(String s) {
        Fragment frag1 = getFragmentManager().findFragmentById(R.id.frag1);
        TextView tv = (TextView) frag1.getView().findViewById(R.id.tv_frag1);
        tv.setText(s);
    }
}
