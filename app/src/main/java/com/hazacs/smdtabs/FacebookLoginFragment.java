package com.hazacs.smdtabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class FacebookLoginFragment extends Fragment {

    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mCallback=new FacebookCallback<LoginResult>(){

        private ProfileTracker mProfileTracker;

        public void onSuccess(LoginResult loginResult) {
            if(Profile.getCurrentProfile() == null) {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        fbLoginEvent.someEvent(profile2.getFirstName());
                        mProfileTracker.stopTracking();
                    }
                };
                mProfileTracker.startTracking();
            }
            else {
                Profile profile = Profile.getCurrentProfile();
                fbLoginEvent.someEvent(profile.getName());
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "CANCEL", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
        }
    };


    public FacebookLoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager=CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook_login, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        LoginButton loginButton=(LoginButton) view.findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public interface fbEventListener {
        public void someEvent(String s);
    }

    fbEventListener fbLoginEvent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fbLoginEvent = (fbEventListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }
}
