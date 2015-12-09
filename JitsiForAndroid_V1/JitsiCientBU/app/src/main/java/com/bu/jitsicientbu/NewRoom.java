package com.bu.jitsicientbu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Seshank on 10/11/2015.
 */
public class NewRoom extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
       // findViewById(R.id.createRoom).setOnClickListener(this);

        /*WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl("https://ec2-54-148-48-185.us-west-2.compute.amazonaws.com/");*/
    }

}
