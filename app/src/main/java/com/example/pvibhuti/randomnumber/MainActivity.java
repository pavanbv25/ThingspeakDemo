package com.example.pvibhuti.randomnumber;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Random;


public class MainActivity extends Activity {

    // Need handler for callbacks to the UI thread
    final Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        // onClick listner of start button
        Button StartBtn = (Button) findViewById(R.id.start);
        StartBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), MyService.class));
            }
        });


        // onClick listner of Stop button
        Button StopBtn = (Button) findViewById(R.id.stop);

        StopBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), MyService.class));

            }
        });

        // OnClick Listner for Clear button
        Button ClearBtn = (Button) findViewById(R.id.clear);

        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HttpClient httpClient = new DefaultHttpClient();
                HttpDelete httpDelete = new HttpDelete("https://api.thingspeak.com/channels/142070/feeds?api_key=89YBJ2056FDPDCYF");
                httpDelete.setHeader("content-type", "application/json");

                //making Delete request.
                try {
                    HttpResponse response = httpClient.execute(httpDelete);
                    String respStr = EntityUtils.toString(response.getEntity());
                    Log.d("Http Delete Response:", respStr.toString());
                    Toast.makeText(getApplicationContext(),"Cleared Graph",Toast.LENGTH_LONG).show();

                } catch (ClientProtocolException e) {
                    // Log exception
                    e.printStackTrace();
                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        // Locate the button in activity_main.xml
        Button SettingsBtn = (Button) findViewById(R.id.settings);

        // Capture button clicks
        SettingsBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent SettingIntent =  new Intent(MainActivity.this,settings.class);
                startActivity(SettingIntent);

            }


        });

        // Locate the button in activity_main.xml
        Button ChartsBtn = (Button) findViewById(R.id.charts);

        // Capture button clicks
        ChartsBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent ChartsIntent =  new Intent(MainActivity.this, Chats.class);
                startActivity(ChartsIntent);

            }


        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
