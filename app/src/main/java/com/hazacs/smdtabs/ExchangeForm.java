package com.hazacs.smdtabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExchangeForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    public String give_category_str,receive_category_str;
    Spinner give_category = null;

    Spinner receive_category = null;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private GoogleMap googleMap;

    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_form);

        give_category = (Spinner) findViewById(R.id.give_category_exchange_et);
        receive_category = (Spinner) findViewById(R.id.receive_category_exchange_et);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        give_category.setAdapter(adapter1);
        give_category.setOnItemSelectedListener(this);
        receive_category.setAdapter(adapter1);
        receive_category.setOnItemSelectedListener(this);

        Button takePic = (Button) findViewById(R.id.takePicBtn);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

            googleMap.setMyLocationEnabled(true);

            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

                boolean isFirst=true;
                @Override
                public void onMyLocationChange(Location loc) {
                    
					lat=loc.getLatitude();
                    lng=loc.getLongitude();

                    LatLng temp=new LatLng(loc.getLatitude(), loc.getLongitude());
                    googleMap.clear();
                    googleMap.addMarker(new MarkerOptions().position(temp).title("It's Me!"));

                    if(isFirst) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));
                        isFirst=false;
                    }
                }
            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        TextView cat_tv=(TextView) view;
        switch (spinner.getId() )
        {
            case R.id.give_category_exchange_et:

                give_category_str= cat_tv.getText().toString();
                break;

            case R.id.receive_category_exchange_et:

                receive_category_str= cat_tv.getText().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Spinner spinner = (Spinner) parent;

        switch (spinner.getId() )
        {
            case R.id.give_category_exchange_et:

                give_category_str= "";
                break;

            case R.id.receive_category_exchange_et:

                receive_category_str="";
                break;

        }
    }


    public void post_exchange_book_btn_click(View v)
    {
        Intent intent = this.getIntent();
        EditText give_name = (EditText) findViewById(R.id.give_book_name_exchange_et);
        EditText receive_name = (EditText) findViewById(R.id.receive_book_name_exchange_et);
        EditText contact_name = (EditText) findViewById(R.id.contact_name_exchange_et);
        EditText contactno = (EditText) findViewById(R.id.contact_no_exchange_et);

        if(checkEditText(give_name) || checkEditText(contactno) ||checkEditText(contact_name) ||checkEditText(receive_name)   )
        {
            this.setResult(RESULT_CANCELED, intent);
            finish();
        }
        else
        {

            Bundle res=new Bundle();
            res.putString("give_name", give_name.getText().toString());
            res.putString("receive_name", receive_name.getText().toString());
            res.putString("contact_name", contact_name.getText().toString());
            res.putString("contactno", contactno.getText().toString());
            res.putString("give_category", give_category_str);
            res.putString("receive_category", receive_category_str);
            intent.putExtras(res);
            this.setResult(RESULT_OK, intent);
            finish();

        }
    }
    public boolean checkEditText(EditText e)
    {
        return e.getText().toString().equals("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            ImageView mImageView = (ImageView) findViewById(R.id.capturedImage);
            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
