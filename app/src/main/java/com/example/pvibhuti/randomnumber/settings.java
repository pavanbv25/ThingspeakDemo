package com.example.pvibhuti.randomnumber;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class settings extends Activity {

    EditText RateValue;
    int RateIntValue;
    String RateStrValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the view from activity_settings.xml
        setContentView(R.layout.activity_settings);

        // Get control of Change Frequency Button
        Button RateBtn= (Button)findViewById(R.id.submitrate);
        RateValue = (EditText)findViewById(R.id.editText);
        RateStrValue = RateValue.getText().toString().trim();



            RateBtn.setOnClickListener(new View.OnClickListener() {
                /**
                 * Called when a view has been clicked.
                 *
                 * @param v The view that was clicked.
                 */
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Value entered is "+RateValue.getText().toString().trim(),Toast.LENGTH_LONG).show();
                    Log.i("EditText value", RateValue.getText().toString().trim());

 /*                   Intent bi = new Intent("com.android.vending.billing.InAppBillingService.BIND");
                    bi.setPackage("com.android.vending");*/
/*

                    Intent serviceIntent = new Intent(MyService.class.getName());
                    serviceIntent.putExtra("entered_frequency",RateValue.getText().toString().trim());
                    getApplicationContext().startService(serviceIntent);
*/
                    Intent intent = new Intent(getApplicationContext(),MyService.class);
                    intent.putExtra("entered_frequency",RateValue.getText().toString().trim());
                    startService(intent);
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
