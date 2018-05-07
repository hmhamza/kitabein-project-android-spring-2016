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

public class SellForm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String category_str;
    Spinner category = null;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private GoogleMap googleMap;

    double lat;
    double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_form);

        category = (Spinner) findViewById(R.id.book_category_sell_et);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);

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

    public boolean checkEditText(EditText e)
    {
        return e.getText().toString().equals("");
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TextView cat_tv=(TextView) view;
        category_str= cat_tv.getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        category_str="";
    }


    public void post_sell_book_btn_click(View v)
    {
        Intent intent = this.getIntent();
        EditText name = (EditText) findViewById(R.id.book_contact_name_sell_et);
        EditText contact_name = (EditText) findViewById(R.id.book_contact_name_sell_et);
        EditText contactno = (EditText) findViewById(R.id.book_contact_no_sell_et);
        EditText price = (EditText) findViewById(R.id.book_price_sell_et);

        if(checkEditText(name) || checkEditText(contactno) ||checkEditText(contact_name) ||checkEditText(price)   )
        {
            this.setResult(RESULT_CANCELED, intent);
            finish();
        }
        else
        {

            Bundle res=new Bundle();
            res.putString("name", name.getText().toString());
            res.putString("contact_name", contact_name.getText().toString());
            res.putString("contactno", contactno.getText().toString());
            res.putString("price", price.getText().toString());
            res.putString("category", category_str);
            intent.putExtras(res);
            this.setResult(RESULT_OK, intent);
            finish();

        }
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
