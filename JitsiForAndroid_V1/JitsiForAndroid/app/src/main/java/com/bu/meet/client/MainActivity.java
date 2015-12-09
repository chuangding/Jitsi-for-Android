package com.bu.meet.client;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.bu.meet.login.Register;
import com.bu.meet.util.BUMeetConstants;
import com.bu.meet.util.SignInUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

/**
 * @author Ganesh Seshank
 */
public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, ResultCallback<People.LoadPeopleResult> {

    Button bLogin;
    EditText etUsername,etPassword;
    TextView tvRegisterLink;
    GoogleApiClient mGoogleApiClient =null;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    private static final int RC_SIGN_IN =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText)findViewById(R.id.TFusername);
        etPassword = (EditText)findViewById(R.id.TFpassword);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();


        findViewById(R.id.sign_in_button).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

            if (v.getId() == R.id.sign_in_button) {
            onSignInClicked();
        }
    }

    private void onSignInClicked() {
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        mShouldResolve = true;
        mGoogleApiClient.connect();

        // Show a message to the user that we are signing in.
        // mStatus.setText(R.string.signing_in);
    }

    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        //Log.d(TAG, "onConnected:" + bundle);
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            if(extras.get(BUMeetConstants.SIGN_OUT)!=null && extras.getBoolean(BUMeetConstants.SIGN_OUT)){
                if(mGoogleApiClient.isConnected()){
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();

                    getIntent().putExtra(BUMeetConstants.SIGN_OUT,false);
                }
            }
        }
        mShouldResolve = false;
        System.out.println(mGoogleApiClient.isConnected());

        String personName = BUMeetConstants.EMPTY_STRING;
        String personPhoto= BUMeetConstants.EMPTY_STRING;
        String personGooglePlusProfile = BUMeetConstants.EMPTY_STRING;
        Person currentPerson = null;
        String currentPersonEmail = null;

        if(mGoogleApiClient.isConnected()) {


            System.out.println(Plus.PeopleApi.getCurrentPerson(mGoogleApiClient));

            Intent intent = new Intent(this,WelcomeActivity.class);
            Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                personPhoto = currentPerson.getImage().getUrl();
                personGooglePlusProfile = currentPerson.getUrl();
                currentPersonEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
            }
            intent.putExtra(BUMeetConstants.DISPLAY_NAME, personName);
            intent.putExtra(BUMeetConstants.PHOTO, personPhoto);
            intent.putExtra(BUMeetConstants.LOGIN, true);
            startActivity(intent);
        }
        // Show the signed-in UI
        //showSignedInUI();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        // Log.d(TAG, "onConnectionFailed:" + connectionResult);
        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    // Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                //showErrorDialog(connectionResult);
            }
        } else {
            // Show the signed-out UI
            //  showSignedOutUI();
        }
    }

    public void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.Blogin) {
            EditText a = (EditText) findViewById(R.id.TFusername);
            String str = a.getText().toString();
            EditText b = (EditText) findViewById(R.id.TFpassword);
            String pass = b.getText().toString();

            Contact user = new Contact();
            user.setEmailID(str);
            user.setPassword(pass);
            new SignInUtil(this).execute(user);
         }
            if (v.getId() == R.id.Bsignup) {
                Intent i = new Intent(MainActivity.this, Register.class);
                i.putExtra(BUMeetConstants.LOGIN, true);
                startActivity(i);
            }


    }
}
