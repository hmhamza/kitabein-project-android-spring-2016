package com.hazacs.smdtabs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HaZa on 05-Apr-16.
 */
public class AdDataAdapter extends ArrayAdapter<AdData> {

    private Context context;
    private int layoutId;
    private ArrayList<AdData> data=new ArrayList<AdData>();

    public AdDataAdapter(Context c,int l,ArrayList<AdData> d) {
        super(c, l, d);
        data=d;
        //layoutId=l;
        context=c;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View row;
        if(convertView == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.list_view_row, parent, false);

        }
        else
        {
            row = (View) convertView;
        }


        TextView title = (TextView)row.findViewById(R.id.title);
        title.setText(data.get(position).getTitle());

        TextView desc = (TextView)row.findViewById(R.id.description);

        String cmnt=data.get(position).getDescription();

        if(cmnt.length()<45)
            desc.setText(data.get(position).getDescription()+"...");
        else
            desc.setText(data.get(position).getDescription().substring(0,44)+"...");

        return row;
    }

    @Override
    public int getCount(){
        if(data != null){
            return data.size();
        }
        else{
            return 0;
        }
    }
}
