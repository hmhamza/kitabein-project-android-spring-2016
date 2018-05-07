package com.hazacs.smdtabs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Models.Sell_book;

/**
 * Created by Muhammad Musa on 4/29/2016.
 */
public class SellBookAdapter extends ArrayAdapter<Sell_book> {

    Context c;
    int layoutFile;
    ArrayList<Sell_book> data;
    public SellBookAdapter(Context context, int resource, ArrayList<Sell_book> objects) {
        super(context, resource, objects);
        c = context;
        layoutFile = resource;
        data = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v, row;
        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)c).getLayoutInflater();
            row = inflater.inflate(this.layoutFile, parent, false);
        }
        else
        {
            row = (View) convertView;
        }


        TextView book_name = (TextView)row.findViewById(R.id.sell_book_name_list_tv);
        book_name.setText(data.get(position).getBook_name());
        TextView contact_name = (TextView)row.findViewById(R.id.sell_contact_name_list_tv);
        contact_name.setText(data.get(position).getContact_name());
        TextView price = (TextView)row.findViewById(R.id.sell_book_price_list_tv);
        price.setText(data.get(position).getPrice());

        return row;
    }


}
