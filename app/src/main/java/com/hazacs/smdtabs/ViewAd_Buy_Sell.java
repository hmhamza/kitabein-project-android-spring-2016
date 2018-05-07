package com.hazacs.smdtabs;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.plus.PlusShare;

public class ViewAd_Buy_Sell extends AppCompatActivity {

    private GoogleMap googleMap;
    String bookName;
    String contact_no;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ad__buy__sell);


        Bundle res=getIntent().getExtras();
        contact_no=res.getString("contact_no");
        email=res.getString("email");
        bookName=res.getString("book_name");
        TextView name=(TextView)findViewById(R.id.bookName);
        name.setText(bookName);

        TextView cat=(TextView)findViewById(R.id.category);
        cat.setText(res.getString("category"));

        TextView contactName=(TextView)findViewById(R.id.contactName);
        contactName.setText(res.getString("contact_name"));

        TextView contactNo=(TextView)findViewById(R.id.contactNo);
        contactNo.setText(contact_no);

        TextView createdAt=(TextView)findViewById(R.id.createdAt);
        createdAt.setText(res.getString("created_at"));

        TextView price=(TextView)findViewById(R.id.price);
        price.setText(res.getString("price"));

        Button googleShareButton = (Button) findViewById(R.id.google1);
        googleShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the Google+ share dialog with attribution to your app.
                Intent shareIntent = new PlusShare.Builder(getBaseContext())
                        .setType("text/plain")
                        .setText("Title: "+bookName)
                        .getIntent();

                startActivityForResult(shareIntent, 0);
            }
        });


        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            LatLng temp=new LatLng(res.getDouble("longitude"),res.getDouble("latitude"));
            Toast.makeText(ViewAd_Buy_Sell.this, String.valueOf(res.getDouble("latitude"))+"    Long: "+String.valueOf(res.getDouble("longitude")), Toast.LENGTH_SHORT).show();
            googleMap.addMarker(new MarkerOptions().position(temp).title("Kitabein"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openSMS(View v){

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:"));
        sendIntent.setData(Uri.parse("smsto:" + contact_no));
        startActivity(sendIntent);
    }

    public void openCall(View v){

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+contact_no));
        startActivity(intent);
    }

    public void openEmail(View v){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Ktaabein Ad");
        startActivity(Intent.createChooser(intent, ""));
    }
}
