package com.hazacs.smdtabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.hazacs.smdtabs.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by HaZa on 29-Apr-16.
 */

public class UserBackgroundTask extends AsyncTask<String,Void,String> {


    public interface AsyncResponse {
        void processFinish(String output);
    }

    String JSON_STRING;
    public AsyncResponse delegate = null;
    Context ctx;
    String ID;
    String method;
    ProgressDialog p;
    UserBackgroundTask(Context c, String m,AsyncResponse delegate)
    {
        this.delegate=delegate;
        this.ctx=c;
        this.method=m;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
	
    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate();
    }


    @Override
    protected void onPostExecute(String aparams){

        if(!(method.equals("get_buy_books")||method.equals("get_sell_books"))) {
            Intent i = new Intent(ctx, MainActivity.class);
            if (method == "login") {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ctx);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("ID", ID);
                editor.commit();
                i.putExtra("ID", ID);
                ctx.startActivity(i);
            }
            else
            {
                delegate.processFinish(aparams);
            }
        }
        else if(method.equals("get_buy_books")||method.equals("get_sell_books"))
        {
            delegate.processFinish(aparams);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        String json_url_buy_book="https://<URL>/getBuyBooksList.php";
        String json_url_sell_book="https://<URL>/getSellBooksList.php";
        String json_url_exchange_book="https://<URL>/getExchangeBooksList.php";
        String reg_url="https://<URL>/insert_user.php";
        String login_url="https://<URL>/login_user.php";
        String post_buy_book_url="https://<URL>/insert_buy_book.php";
        String post_sell_book_url="https://<URL>/insert_sell_book.php";
        String post_exchange_book_url="https://<URL>/insert_exchange_book.php";
        
        if(method.equals("register"))
        {
            String name=params[1];
            String password=params[2];
            String email=params[3];
            String cnic=params[4];
            String contactno=params[5];
            String address=params[6];
            try {

                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter BW=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+ "="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+ "="+URLEncoder.encode(password,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+ "="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("cnic","UTF-8")+ "="+URLEncoder.encode(cnic,"UTF-8")+"&"+
                        URLEncoder.encode("contactno","UTF-8")+ "="+URLEncoder.encode(contactno,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+ "="+URLEncoder.encode(address,"UTF-8");

                BW.write(data);
                BW.flush();
                BW.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();
                return "Registration Successful ...";

            } catch (MalformedURLException e) {
                return e.getMessage();
            } catch (ProtocolException e) {
                return e.getMessage();
            } catch (UnsupportedEncodingException e) {
                return e.getMessage();
                //return "3";
            } catch (IOException e) {
                return e.getMessage();
            }

        }
        else if(method.equals("login"))
        {

            String email=params[1];
            String password=params[2];

            URL url= null;
            try {
                url = new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter BW=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data=URLEncoder.encode("email","UTF-8")+ "="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+ "="+URLEncoder.encode(password,"UTF-8");

                BW.write(data);
                BW.flush();
                BW.close();
                OS.close();

                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader BR=new BufferedReader(new InputStreamReader(IS,"ISO-8859-1"));
                String response="",line="";
                while((line=BR.readLine())!=null)
                {
                    response+=line;
                }
                BR.close();
                IS.close();
                httpURLConnection.disconnect();
                ID=response;
                return response;

            } catch (MalformedURLException e) {
                return e.getMessage();
            } catch (ProtocolException e) {
                return e.getMessage();
            } catch (IOException e) {
                return e.getMessage();
            }

        }
        else if(method.equals("post_buy_book"))
        {
            String name=params[1];
            String contact_name=params[2];
            String contactno=params[3];
            String price=params[4];
            String category=params[5];
            String longitude=params[7];
            String latitude=params[6];
            String email=params[8];
            try {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
                String  session_id = sharedPref.getString("ID","0");
                URL url=new URL(post_buy_book_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter BW=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+ "="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("contact_name","UTF-8")+ "="+URLEncoder.encode(contact_name,"UTF-8")+"&"+
                        URLEncoder.encode("contact_no","UTF-8")+ "="+URLEncoder.encode(contactno,"UTF-8")+"&"+
                        URLEncoder.encode("price","UTF-8")+ "="+URLEncoder.encode(price,"UTF-8")+"&"+
                        URLEncoder.encode("longitude","UTF-8")+ "="+URLEncoder.encode(longitude,"UTF-8")+"&"+
                        URLEncoder.encode("latitude","UTF-8")+ "="+URLEncoder.encode(latitude,"UTF-8")+"&"+
                        URLEncoder.encode("user_id","UTF-8")+ "="+URLEncoder.encode(session_id,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+ "="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("category","UTF-8")+ "="+URLEncoder.encode(category,"UTF-8");

                BW.write(data);
                BW.flush();
                BW.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();



                return "Book posted successfully!";

            } catch (MalformedURLException e) {
                return e.getMessage();
            } catch (ProtocolException e) {
                return e.getMessage();
            } catch (UnsupportedEncodingException e) {
                return e.getMessage();
                //return "3";
            } catch (IOException e) {
                return e.getMessage();
            }

        }
        else if(method.equals("post_sell_book"))
        {
            String name=params[1];
            String contact_name=params[2];
            String contactno=params[3];
            String price=params[4];
            String category=params[5];
            try {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
                String  session_id = sharedPref.getString("ID","0");
                URL url=new URL(post_sell_book_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter BW=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+ "="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("contact_name","UTF-8")+ "="+URLEncoder.encode(contact_name,"UTF-8")+"&"+
                        URLEncoder.encode("contact_no","UTF-8")+ "="+URLEncoder.encode(contactno,"UTF-8")+"&"+
                        URLEncoder.encode("price","UTF-8")+ "="+URLEncoder.encode(price,"UTF-8")+"&"+
                        URLEncoder.encode("user_id","UTF-8")+ "="+URLEncoder.encode(session_id,"UTF-8")+"&"+
                        URLEncoder.encode("category","UTF-8")+ "="+URLEncoder.encode(category,"UTF-8");

                BW.write(data);
                BW.flush();
                BW.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                IS.close();



                return "Book posted successfully!";

            } catch (MalformedURLException e) {
                return e.getMessage();
            } catch (ProtocolException e) {
                return e.getMessage();
            } catch (UnsupportedEncodingException e) {
                return e.getMessage();
                //return "3";
            } catch (IOException e) {
                return e.getMessage();
            }

        }


        else if(method.equals("post_exchange_book"))
        {
            String give_name=params[1];
            String receive_name=params[2];
            String contact_name=params[3];
            String contactno=params[4];
            String give_category=params[5];
            String receive_category=params[6];
            try {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ctx);
                String  session_id = sharedPref.getString("ID","0");
                URL url=new URL(post_exchange_book_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter BW=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("give_name","UTF-8")+ "="+URLEncoder.encode(give_name,"UTF-8")+"&"+
                        URLEncoder.encode("receive_name","UTF-8")+ "="+URLEncoder.encode(receive_name,"UTF-8")+"&"+
                        URLEncoder.encode("contact_name","UTF-8")+ "="+URLEncoder.encode(contact_name,"UTF-8")+"&"+
                        URLEncoder.encode("contact_no","UTF-8")+ "="+URLEncoder.encode(contactno,"UTF-8")+"&"+
                        URLEncoder.encode("give_category","UTF-8")+ "="+URLEncoder.encode(give_category,"UTF-8")+"&"+
                        URLEncoder.encode("user_id","UTF-8")+ "="+URLEncoder.encode(session_id,"UTF-8")+"&"+
                        URLEncoder.encode("receive_category","UTF-8")+ "="+URLEncoder.encode(receive_category,"UTF-8");

                BW.write(data);
                BW.flush();
                BW.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader BR=new BufferedReader(new InputStreamReader(IS,"ISO-8859-1"));
                String response="",line="";
                while((line=BR.readLine())!=null)
                {
                    response+=line;
                }
                BR.close();
                IS.close();



                return response;

            } catch (MalformedURLException e) {
                return e.getMessage();
            } catch (ProtocolException e) {
                return e.getMessage();
            } catch (UnsupportedEncodingException e) {
                return e.getMessage();
                //return "3";
            } catch (IOException e) {
                return e.getMessage();
            }

        }
        else if(method.equals("get_buy_books"))
        {
            try {
                URL url=new URL(json_url_buy_book);

                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader BR=new BufferedReader(new InputStreamReader(IS));
                StringBuilder SB=new StringBuilder();
                while((JSON_STRING=BR.readLine())!=null)
                {
                    SB.append(JSON_STRING+"\n");
                }

                BR.close();
                IS.close();
                httpURLConnection.disconnect();
                return SB.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("get_sell_books"))
        {
            try {
                URL url=new URL(json_url_sell_book);

                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader BR=new BufferedReader(new InputStreamReader(IS));
                StringBuilder SB=new StringBuilder();
                while((JSON_STRING=BR.readLine())!=null)
                {
                    SB.append(JSON_STRING+"\n");
                }

                BR.close();
                IS.close();
                httpURLConnection.disconnect();
                return SB.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(method.equals("get_exchange_books"))
        {
            try {
                URL url=new URL(json_url_exchange_book);

                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader BR=new BufferedReader(new InputStreamReader(IS));
                StringBuilder SB=new StringBuilder();
                while((JSON_STRING=BR.readLine())!=null)
                {
                    SB.append(JSON_STRING+"\n");
                }

                BR.close();
                IS.close();
                httpURLConnection.disconnect();
                return SB.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "Done";
    }
}
