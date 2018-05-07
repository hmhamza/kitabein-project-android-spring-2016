package com.hazacs.smdtabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class BookCategory extends AppCompatActivity implements UserBackgroundTask.AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);
    }

    public void onRadioCategoryClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        Intent i = null;
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_buy:
                if (checked)
                    i=new Intent(this,BuyForm.class);
                    startActivityForResult(i,1);
                break;

            case R.id.radio_sell:
                if (checked)
                    i=new Intent(this,SellForm.class);
                    startActivityForResult(i,2);
                break;
            case R.id.radio_exchange:
                if (checked)
                   i=new Intent(this,ExchangeForm.class);
                   startActivityForResult(i,3);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String name = res.getString("name");
                    String contact_name = res.getString("contact_name");
                    String contactno = res.getString("contactno");
                    String price = res.getString("price");
                    String email = res.getString("email");
                    String category = res.getString("category");
                    Bitmap image=res.getParcelable("image");
                    Double lat=res.getDouble("latitude");
                    Double lng=res.getDouble("longitude");
                    Toast.makeText(getBaseContext(),
                            "Latitude: " + lat +
                                    " Longitude: " + lng,
                            Toast.LENGTH_SHORT).show();
                    UserBackgroundTask user_async_task=new UserBackgroundTask(this,"post_buy_book",  this);
                    user_async_task.execute("post_buy_book",name,contact_name,contactno,price,category,lat.toString(),lng.toString(),email);
                    finish();
                }
                else if(resultCode==RESULT_CANCELED)
                {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String name = res.getString("name");
                    String contact_name = res.getString("contact_name");
                    String contactno = res.getString("contactno");
                    String price = res.getString("price");
                    String category = res.getString("category");
                    double lat=res.getDouble("latitude");
                    double lng=res.getDouble("longitude");
                    Toast.makeText(getBaseContext(),
                            "Latitude: " + lat +
                                    " Longitude: " + lng,
                            Toast.LENGTH_SHORT).show();
                    UserBackgroundTask user_async_task=new UserBackgroundTask(this,"post_sell_book",  this);
                    user_async_task.execute("post_sell_book",name,contact_name,contactno,price,category);
                    finish();
                }
                else if(resultCode==RESULT_CANCELED)
                {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show();
                }
                break;


            case 3:
                if (resultCode == RESULT_OK) {
                    Bundle res = data.getExtras();
                    String give_name = res.getString("give_name");
                    String contact_name = res.getString("contact_name");
                    String contactno = res.getString("contactno");
                    String receive_name = res.getString("receive_name");
                    String give_category = res.getString("give_category");
                    String receive_category = res.getString("receive_category");
                    double lat=res.getDouble("latitude");
                    double lng=res.getDouble("longitude");
                    Toast.makeText(getBaseContext(),
                            "Latitude: " + lat +
                                    " Longitude: " + lng,
                            Toast.LENGTH_SHORT).show();

                    UserBackgroundTask user_async_task=new UserBackgroundTask(this,"post_exchange_book", this);
                    user_async_task.execute("post_exchange_book",give_name,receive_name,contact_name,contactno,give_category,receive_category);
                    finish();
                }
                else if(resultCode==RESULT_CANCELED)
                {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    public void processFinish(String output) {
        Toast.makeText(this, output, Toast.LENGTH_LONG).show();
    }
}
