package com.hazacs.smdtabs;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.splunk.mint.Mint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class Login extends AppCompatActivity implements FacebookLoginFragment.fbEventListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the application environment
        Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);

        // TODO: Update with your API key
        Mint.initAndStartSession(this, "<API_KEY>");


        setContentView(R.layout.activity_login);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }

    @Override
    public void someEvent(String s) {
        Fragment fbLoginFrag = getFragmentManager().findFragmentById(R.id.facebook_login_fragment);
        Toast.makeText(Login.this, "In Login Activity: "+s, Toast.LENGTH_SHORT).show();
    }

    public void crashApp(View v){

        throw new RuntimeException("Bug Sense Testing");

    }

    public void signup_BtnClick(View v)
    {
        Intent RegisterForm = new Intent(this, SignUp.class);
        startActivityForResult(RegisterForm, 1);
    }
	
    public void signin_BtnClick(View v)
    {
        EditText email=(EditText)findViewById(R.id.login_email_et);
        EditText password=(EditText)findViewById(R.id.login_password_et);
        UserBackgroundTask user_async_task=new UserBackgroundTask(this,"login", (UserBackgroundTask.AsyncResponse) this);
        user_async_task.execute("login",email.getText().toString(),password.getText().toString());
    }
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String name = res.getString("name");
                    String password = res.getString("password");
                    String email = res.getString("email");
                    String cnic = res.getString("cnic");
                    String contactno = res.getString("contactno");
                    String address = res.getString("address");
                    UserBackgroundTask user_async_task=new UserBackgroundTask(this,"register", (UserBackgroundTask.AsyncResponse) this);
                    user_async_task.execute("register",name,password,email,cnic,contactno,address);
                }
                else if(resultCode==RESULT_CANCELED)
                {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
