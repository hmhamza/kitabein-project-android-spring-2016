package com.hazacs.smdtabs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Models.Exchange_book;

/**
 * Created by Muhammad Musa on 4/29/2016.
 */
public class ExchangeBookAdapter extends ArrayAdapter<Exchange_book> {
    Context c;
    int layoutFile;
    ArrayList<Exchange_book> data;
    public ExchangeBookAdapter(Context context, int resource, ArrayList<Exchange_book> objects) {

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

        TextView give_book_name = (TextView)row.findViewById(R.id.exchange_book_give_name_tv);
        give_book_name.setText(data.get(position).getGive_book_name());
        TextView receive_book_name = (TextView)row.findViewById(R.id.exchange_book_receive_name_tv);
        receive_book_name.setText(data.get(position).getReceive_book_name());

        TextView contact_name = (TextView)row.findViewById(R.id.exchange_book_contact_name_tv);
        contact_name.setText(data.get(position).getContact_name());
        TextView contact_no = (TextView)row.findViewById(R.id.exchange_book_contact_no_tv);
        contact_no.setText(data.get(position).getContact_no());

        TextView give_category = (TextView)row.findViewById(R.id.exchange_book_give_cat_tv);
        give_category.setText(data.get(position).getGive_category());
        TextView receive_category = (TextView)row.findViewById(R.id.exchange_book_receive_cat_tv);
        receive_category.setText(data.get(position).getReceive_category());
        TextView created_at = (TextView)row.findViewById(R.id.exchange_book_created_at_tv);
        created_at.setText(data.get(position).getCreated_at().toString());

        return row;
    }
}
