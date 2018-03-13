package com.senla.sum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    Button btnSum;
    TextView tvSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        btnSum = (Button) findViewById(R.id.btnSum);
        tvSum = (TextView) findViewById(R.id.textViewSum);

        View.OnClickListener oclBtnSum = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tvSum.setText(String.valueOf(getSum()));
                } catch (NumberFormatException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.error), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0 );
                    toast.show();
                }
            }
        };
        btnSum.setOnClickListener(oclBtnSum);
    }

    private int getSum() {
        int num1Value = Integer.parseInt(num1.getText().toString());
        int num2Value = Integer.parseInt(num2.getText().toString());
        return num1Value + num2Value;
    }
}
