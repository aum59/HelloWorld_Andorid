package com.anongsinbook.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    int sum = 0;
    private TextView tvSecondResult;
    private Button btnOk;
    private EditText editTextSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.protrait_only)) {
            // Fix screen orientation to Protrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        sum = intent.getIntExtra("result", 0);

        Bundle bundle = intent.getBundleExtra("cBundle");
        int x = bundle.getInt("x");
        int y = bundle.getInt("y");
        int z = bundle.getInt("z");

        //CoordinateSerializable c2 = (CoordinateSerializable)  //Don't Used it verry slow
        //        intent.getSerializableExtra("cSerializable"); //Don't Used it verry slow

        //CoordinateParcelable c3 = intent.getParcelableExtra("cPraceleble");

        initInstances();
    }

    private void initInstances() {

        tvSecondResult = (TextView) findViewById(R.id.tvSecondResult);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(SecondActivity.this);
        editTextSecond = (EditText) findViewById(R.id.editTextSecond);

        tvSecondResult.setText("Result = " + sum);

    }

    @Override
    public void onClick(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", editTextSecond.getText().toString());
        setResult(RESULT_OK, returnIntent);
        if (v == btnOk) {
            finish();
        }
    }
}
