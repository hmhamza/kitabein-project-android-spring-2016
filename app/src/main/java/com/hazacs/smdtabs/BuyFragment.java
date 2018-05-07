package com.hazacs.smdtabs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.Buy_book;

/**
 * Created by HaZa on 05-Apr-16.
 */
 
public class BuyFragment extends Fragment implements UserBackgroundTask.AsyncResponse {
	
    String currentMethod;
    final ArrayList<Buy_book> buy_book_array = new ArrayList<Buy_book>();
    com.hazacs.smdtabs.BuyBookAdapter buy_book_adapter = null;

    String json_string_buy;
    JSONArray buy_json_array;
    JSONObject buy_book_jsonObject;

    private SwipeRefreshLayout swipeContainer;    

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.buy_layout, container, false);
        initializeListView(view);
        
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerRCContacts);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initializeListView(view);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        return view;
    }

    public void initializeListView(View view)
    {
        currentMethod="get_buy_books";
        UserBackgroundTask get_buy_books_list=new UserBackgroundTask(getContext(),"get_buy_books", this);
        get_buy_books_list.execute("get_buy_books");

        AdapterView.OnItemClickListener buy_rowListener = new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                Buy_book info = buy_book_adapter.getItem(position);
                Intent i=new Intent(v.getContext(),ViewAd_Buy_Sell.class);
                i.putExtra("book_name",info.getBook_name());
                i.putExtra("category",info.getCategory());
                i.putExtra("contact_name",info.getContact_name());
                i.putExtra("contact_no",info.getContact_no());
                i.putExtra("created_at", info.getCreated_at());
                i.putExtra("price", info.getPrice());
                i.putExtra("id", info.getId());
                i.putExtra("user_id", info.getUser_id());
                i.putExtra("longitude", info.getLongitude());
                i.putExtra("latitude", info.getLatitude());
                i.putExtra("email", info.getEmail());
                startActivity(i);
            }
        };

        buy_book_adapter=new BuyBookAdapter(getContext(),R.layout.buy_book_row,buy_book_array);
        ListView lv_buy = (ListView) view.findViewById(R.id.adsList);
        lv_buy.setAdapter(buy_book_adapter);
        lv_buy.setOnItemClickListener(buy_rowListener);

        buy_book_adapter.notifyDataSetChanged();
    }

    public void makeBuyJSONObjects(String json_str) {

        try {
            buy_book_jsonObject=new JSONObject(json_str);
            buy_json_array=buy_book_jsonObject.getJSONArray("server_response");
            Integer count=0;
            while(count<buy_json_array.length()) {

                JSONObject BUY_JO=buy_json_array.getJSONObject(count);
                Buy_book item=new Buy_book(BUY_JO.getInt("id"),BUY_JO.getInt("user_id"),BUY_JO.getString("contact_name"),
                        BUY_JO.getString("contact_no"),BUY_JO.getString("name"),BUY_JO.getString("price"),
                        BUY_JO.getString("category"),BUY_JO.getInt("image_path"), BUY_JO.getString("created_at"),BUY_JO.getString("email"),BUY_JO.getDouble("longitude"),BUY_JO.getDouble("latitude"));
                buy_book_array.add(item);
                count++;

                buy_book_adapter.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void processFinish(String output) {
        if(currentMethod.equals("get_buy_books")) {
            makeBuyJSONObjects(output);
        }
    }
}
