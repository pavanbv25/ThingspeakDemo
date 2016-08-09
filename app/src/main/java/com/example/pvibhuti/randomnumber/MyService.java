package com.example.pvibhuti.randomnumber;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MyService extends Service {

    int frequency=2;
    String getdata;

    ScheduledExecutorService scheduleTaskExecutor;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return mStartMode;
        Toast.makeText(this,"service started",Toast.LENGTH_LONG).show();
        Log.i(String.valueOf(getClass()), "Service method just started");


       /* if (intent.getAction().equals(MyService.class.getName())) {
            getdata = intent.getStringExtra("entered_frequency");
            frequency = Integer.parseInt(getdata);

        }*/

        getdata = intent.getStringExtra("entered_frequency");
        try {
            frequency = Integer.parseInt(getdata);
            Log.i("Data from intent", "" + frequency);
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                // Random Values from 0.0 to 1.0
                double value = Math.random();


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("https://api.thingspeak.com/update");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("api_key", "7K31F5L4RYM08DAA"));
                nameValuePair.add(new BasicNameValuePair("field1", String.valueOf(value)));

                //Encoding POST data
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //making POST request.
                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    // write response to log
                    Log.d("Http Post Response:", response.toString());
                } catch (ClientProtocolException e) {
                    // Log exception
                    e.printStackTrace();
                } catch (IOException e) {
                    // Log exception
                    e.printStackTrace();
                }

            }
        }, 0, frequency, TimeUnit.SECONDS);
        Log.i("Frequency value is:", ""+frequency);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Shutting down scheduler
        scheduleTaskExecutor.shutdown();
        Toast.makeText(this, "service Stopped", Toast.LENGTH_LONG).show();
        System.out.println("service is Stopped");
        Log.i(String.valueOf(getClass()), "Service method Stopping");

    }

}
