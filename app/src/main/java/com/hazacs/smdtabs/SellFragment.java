package com.hazacs.smdtabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.Sell_book;

/**
 * Created by HaZa on 05-Apr-16.
 */
public class SellFragment extends Fragment implements UserBackgroundTask.AsyncResponse {

    String currentMethod;
    ArrayList<Sell_book> sell_book_array = new ArrayList<Sell_book>();
    SellBookAdapter sell_book_adapter = null;

    JSONArray sell_json_array;
    JSONObject sell_book_jsonObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sell_layout, container, false);
        initializeListView(view);

        return view;

    }

    public void initializeListView(View view)
    {
         currentMethod="get_sell_books";
         UserBackgroundTask get_sell_books_list=new UserBackgroundTask(getContext(),"get_sell_books",this);
         get_sell_books_list.execute("get_sell_books");




        AdapterView.OnItemClickListener sell_rowListener = new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                Sell_book info = sell_book_adapter.getItem(position);
                Intent i=new Intent(v.getContext(),ViewAd_Buy_Sell.class);
                i.putExtra("book_name",info.getBook_name());
                i.putExtra("category",info.getCategory());
                i.putExtra("contact_name",info.getContact_name());
                i.putExtra("contact_no",info.getContact_no());
                i.putExtra("created_at", info.getCreated_at());
                i.putExtra("price", info.getPrice());
                i.putExtra("id", info.getId());
                i.putExtra("user_id", info.getUser_id());
                startActivity(i);
            }
        };

        sell_book_adapter=new SellBookAdapter(getContext(),R.layout.sell_book_row,sell_book_array);
        ListView lv_sell = (ListView) view.findViewById(R.id.adsList1);
        lv_sell.setAdapter(sell_book_adapter);
        lv_sell.setOnItemClickListener(sell_rowListener);
    }

    public void makeSellJSONObjects(String json_str)
    {

        try {
            sell_book_jsonObject=new JSONObject(json_str);
            sell_json_array=sell_book_jsonObject.getJSONArray("server_response");
            Integer count=0;
            while(count<sell_json_array.length())
            {

                JSONObject BUY_JO=sell_json_array.getJSONObject(count);
                Sell_book item=new Sell_book(BUY_JO.getInt("id"),BUY_JO.getInt("user_id"),BUY_JO.getInt("image_path"),BUY_JO.getString("contact_name"),
                        BUY_JO.getString("contact_no"),BUY_JO.getString("name"),BUY_JO.getString("price"),
                        BUY_JO.getString("category"), BUY_JO.getString("created_at"));
                sell_book_array.add(item);
                count++;

                sell_book_adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void processFinish(String output) {
        if(currentMethod.equals("get_sell_books")) {
            makeSellJSONObjects(output);
        }
    }
}
