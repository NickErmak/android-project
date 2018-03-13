package com.senla.firstactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn, btnSignUp, btnAbout;
    private String androidUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = (Button) findViewById(R.id.btnMainSignIn);
        btnSignUp = (Button) findViewById(R.id.btnMainSignUp);
        btnAbout = (Button) findViewById(R.id.btnMainAbout);

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnAbout.setOnClickListener(this);

        androidUri = getString(R.string.main_url_about);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnMainSignIn:
                intent = new Intent(this, SignInActivity.class);
                break;
            case R.id.btnMainAbout:
                Uri adress = Uri.parse(androidUri);
                intent = new Intent(Intent.ACTION_VIEW, adress);
                break;
            case R.id.btnMainSignUp:
                intent = new Intent(this, RegActivity.class);
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
