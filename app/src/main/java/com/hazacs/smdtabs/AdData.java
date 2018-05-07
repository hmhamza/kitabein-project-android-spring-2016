package com.hazacs.smdtabs;

/**
 * Created by HaZa on 05-Apr-16.
 */
public class AdData {

    String title;
    String description;

    public AdData(String t,String d){
        title=t;
        description=d;

    }

    public String getTitle(){
        return title;

    }

    public String getDescription(){
        return description;

    }
}
