package com.bu.meet.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bu.meet.R;
import com.bu.meet.model.Contact;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class FacebookFragment extends Fragment {

    private Contact user = new Contact();
    private LoginButton loginButton = null;

    private CallbackManager mCallbackManager;
   /* public FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {

        private ProfileTracker mProfileTracker;

        @Override
        public void onSuccess(LoginResult loginResult) {

System.out.println();
           *//* Intent intent = new Intent(getContext(),WelcomeActivity.class);
            intent.putExtra("login", true);
            intent.putExtra("f", "f");
            startActivity(intent);*//*

          //  new DownloadProfilePicAndRegisterUtil("facebook").execute(user, strProfilePic, this);


        }*/



       /* @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };
*/
    public FacebookFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        return inflater.inflate(R.layout.fragment_main, container, false);

    }

  /*  @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(BUMeetConstants.USER_FRIENDS,"email");
        loginButton.setFragment(this);
        loginButton.registerCallback(mCallbackManager, mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

}




