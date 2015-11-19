package com.bu.meet.client;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.util.BUMeetConstants;
import com.bu.meet.util.DisplayContactListUtil;

public class Room extends AppCompatActivity {

    private Contact currentUser = new Contact();
    private String roomName;
   // @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        WebView webView =(WebView) findViewById(R.id.webviewId);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            // Need to accept permissions to use the camera and audio
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Room.this.runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.getUserAgentString();
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.getUserAgentString();
        webSettings.setUserAgentString(BUMeetConstants.USER_AGENT_STRING);
        roomName = getIntent().getExtras().get(BUMeetConstants.ROOM_NAME).toString();
        webView.loadUrl(BUMeetConstants.MEET_URL+roomName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room, menu);
        return true;
    }
    private void onSignOutClicked() {
        // Clear the default account so that GoogleApiClient will not automatically
        // connect in the future.

        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(BUMeetConstants.SIGN_OUT, true);
        intent.putExtra(BUMeetConstants.LOGIN,false);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//            default:
//                return super.onOptionsItemSelected(item);
            case R.id.SignOut:
                //Toast.makeText(getApplicationContext(),item.toString(),Toast.LENGTH_SHORT).show();
                onSignOutClicked();
                break;
            case R.id.InviteFriends:
                SharedPreferences sharedpreferences = getSharedPreferences(BUMeetConstants.CURRENT_USER, Context.MODE_PRIVATE);
                currentUser.setEmailID(sharedpreferences.getString(BUMeetConstants.EMAIL,BUMeetConstants.NOT_FOUND));
                currentUser.setFirstName(sharedpreferences.getString(BUMeetConstants.FIRST_NAME, BUMeetConstants.NOT_FOUND));
                currentUser.setLastName(sharedpreferences.getString(BUMeetConstants.LAST_NAME, BUMeetConstants.NOT_FOUND));
                new DisplayContactListUtil(this).execute(currentUser,BUMeetConstants.FROM_ROOM,roomName);
        }
        return super.onOptionsItemSelected(item);
   }
}
