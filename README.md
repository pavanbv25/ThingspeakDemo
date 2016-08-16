# ThingspeakDemo
This repo demonstrates Thingspeak API calls like post,delete and plotting graph on Android App. We can Change the rate of data being pushed to cloud.
Let me walk you through each functionality one by one.

1) Posting sensor data continously for specific interval of time

Here I am genererating random number which I assuming as sensor data for time being.

Working: 

I need to run thread continously for speacific period of time. For this I have used threadScheduler. Below is the HTTP call for post.

 HttpPost httpPost = new HttpPost("https://api.thingspeak.com/update");

                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("api_key", "7K31F5L4RYM08DAA"));
                nameValuePair.add(new BasicNameValuePair("field1", String.valueOf(value)));
                


2) To Stop Post data.

Working: 

Every time when I click on Stop button from my main activity I am calling onDestroy method from MyService class.
Here I am Shutting down thread shceduler which was created in onStartCommand

3) Thingspeak Graphs

This was the interesting part of my coding because I need to embeeded thingspeak charts api in HTML and embedded that in webView Android API.

This is how we need to do.

String htmlstr = "<iframe width=\"450\" height=\"260\" style=\"border: 1px solid #cccccc;\" src=\"http://api.thingspeak.com/channels/142070/charts/1?width=450&height=260&results=60&dynamic=true\" ></iframe>";

4) Clearing out Graph

Working:

Here we need to call HTTP DELETE Restful API as we did for HTTP POST calls.

Note: Thingspeak have different API key's like read Key,Write Key and account Key. For Delete operation we need to use account key.

5) Changing frequency.

Working:

used one editText which will accept number type this data I have passed it to service class where I have started thread for specific internal of time

FYI: we have to use always external intent when passing data from main activity to service method.

  Intent intent = new Intent(getApplicationContext(),MyService.class);
                    intent.putExtra("entered_frequency",RateValue.getText().toString().trim());
                    startService(intent);



