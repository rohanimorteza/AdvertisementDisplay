package com.example.mortrza.myadvertismentdispaly.SETTING;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingData {

    private Context context;

    public SettingData(Context context){
        this.context = context;
    }

    public void SetTextSize(int size){
        SharedPreferences shp = context.getSharedPreferences("AGAHI",Context.MODE_PRIVATE);
        SharedPreferences.Editor shpedit = shp.edit();
        shpedit.putInt("TEXTSIZE",size);
        shpedit.commit();
    }

    public int GetTextSize(){
        SharedPreferences shp = context.getSharedPreferences("AGAHI",Context.MODE_PRIVATE);
        return shp.getInt("TEXTSIZE",24);
    }


    public void SetTextSpaceLine(int size){
        SharedPreferences shp = context.getSharedPreferences("AGAHI",Context.MODE_PRIVATE);
        SharedPreferences.Editor shpedit = shp.edit();
        shpedit.putInt("TEXTSPACELINESIZE",size);
        shpedit.commit();
    }

    public int GetTextSpaceLine(){
        SharedPreferences shp = context.getSharedPreferences("AGAHI",Context.MODE_PRIVATE);
        return shp.getInt("TEXTSPACELINESIZE",2);
    }

    public void SetFONT(int font){
        SharedPreferences shp = context.getSharedPreferences("AGAHI",Context.MODE_PRIVATE);
        SharedPreferences.Editor shpedit = shp.edit();
        shpedit.putInt("FONT",font);
        shpedit.commit();
    }

    public int GetFONT(){
        SharedPreferences shp = context.getSharedPreferences("AGAHI",Context.MODE_PRIVATE);
        return shp.getInt("FONT",1);
    }

}
