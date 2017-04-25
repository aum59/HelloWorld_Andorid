package com.anongsinbook.helloworld;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int x, y, z; // Save/Restore in Activity's instance state

    private TextView tvResult;
    private EditText editText1, editText2;
    private Button btnCalculate;
    private RadioGroup rgOperator;
    private CustomViewGroup viewGroup1, viewGroup2;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.protrait_only)) {
            // Fix screen orientation to Protrait
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        setContentView(R.layout.activity_welcome);

        //initInstances();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int heigth = size.y;

        FirebaseCrash.log("Activity created");
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        Toast.makeText(MainActivity.this,
                "Width = " + width + " , Heigth = " + heigth,
                Toast.LENGTH_SHORT).show();
    }

    private void initInstances() {

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        tvResult = (TextView) findViewById(R.id.tvResult);
        //tvResult.setMovementMethod(LinkMovementMethod.getInstance()); // use for goto WebPage when click www. .
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(this);
        rgOperator = (RadioGroup) findViewById(R.id.rgOperator);

        viewGroup1 = (CustomViewGroup) findViewById(R.id.viewGroup1);
        viewGroup2 = (CustomViewGroup) findViewById(R.id.viewGroup2);
        viewGroup1.setButtonText("Hello");
        viewGroup2.setButtonText("World");

        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12345) {
            if (resultCode == RESULT_OK) {
                // get data from data's extra
                Toast.makeText(MainActivity.this,
                        "Comment : " + data.getStringExtra("result"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCalculate) {
            int val1 = 0, val2 = 0, sum = 0;
            try {
                val1 = Integer.parseInt(editText1.getText().toString());
            } catch (NumberFormatException e) {

            }
            try {
                val2 = Integer.parseInt(editText2.getText().toString());
            } catch (NumberFormatException e) {

            }

            switch (rgOperator.getCheckedRadioButtonId()) {
                case R.id.rbPlus:
                    sum = val1 + val2;
                    break;
                case R.id.rbMinus:
                    sum = val1 - val2;
                    break;
                case R.id.rbMultiply:
                    sum = val1 * val2;
                    break;
                case R.id.rbDivide:
                    if (val2 != 0) sum = val1 / val2;
                    break;
                //Add case here
            }

            tvResult.setText(" = " + sum);

            Log.d("Calculate", "Calculate = " + sum);

            Toast.makeText(MainActivity.this,
                    "Result = " + sum,
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this,
                    SecondActivity.class);
            intent.putExtra("result", sum);

            //Playground
            Coordinate c1 = new Coordinate();
            c1.x = 5;
            c1.y = 10;
            c1.z = 20;
            Bundle bundle = new Bundle();
            bundle.putInt("x", c1.x);
            bundle.putInt("y", c1.y);
            bundle.putInt("z", c1.z);
            intent.putExtra("cBundle", bundle);

            //Serializable Lab Don't Used it's verry slow
            CoordinateSerializable c2 = new CoordinateSerializable();
            c2.x = 5;
            c2.y = 10;
            c2.z = 20;
            intent.putExtra("cSerializable", c2);

            //Parcelable
            CoordinateParcelable c3 = new CoordinateParcelable();
            c3.x = 5;
            c3.y = 10;
            c3.z = 20;
            intent.putExtra("cParcelable", c3);

            startActivityForResult(intent, 12345);
        }


    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_setting){
            Toast.makeText(MainActivity.this,
                    "Setting Menu",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onStart() { super.onStart(); }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onPause() { super.onPause(); }

    @Override
    protected void onStop() { super.onStop(); }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save thing(s) here
        //outState.putString("text",tvResult.getText().toString()); //Replace to android:freezesText="true" in Textview

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore thing(s) here
        //tvResult.setText(savedInstanceState.getString("text")); //Replace to android:freezesText="true" in Textview
    }
};
