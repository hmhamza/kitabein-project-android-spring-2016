package com.hazacs.smdtabs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);
        }

        public void submit_reg_user_btnClick(View v)
        {
            Intent intent = this.getIntent();
            EditText name = (EditText) findViewById(R.id.reg_name_et);
            EditText password = (EditText) findViewById(R.id.reg_password_et);
            EditText email = (EditText) findViewById(R.id.reg_email_et);
            EditText contactno = (EditText) findViewById(R.id.reg_contactno_et);
            EditText cnic = (EditText) findViewById(R.id.reg_cnic_et);
            EditText address = (EditText) findViewById(R.id.reg_address_et);

            if(checkEditText(address) || checkEditText(name) ||checkEditText(password) ||checkEditText(email) ||checkEditText(contactno) ||checkEditText(cnic) )
            {
                this.setResult(RESULT_CANCELED, intent);
                finish();
            }
            else
            {

                Bundle res=new Bundle();
                res.putString("name", name.getText().toString());
                res.putString("password", password.getText().toString());
                res.putString("email", email.getText().toString());
                res.putString("contactno", contactno.getText().toString());
                res.putString("cnic", cnic.getText().toString());
                res.putString("address", address.getText().toString());
                intent.putExtras(res);
                this.setResult(RESULT_OK, intent);
                finish();

            }
        }
        public boolean checkEditText(EditText e)
        {
            return e.getText().toString().equals("");
        }
    }
