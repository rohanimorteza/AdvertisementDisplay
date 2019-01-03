package com.example.mortrza.myadvertismentdispaly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


import com.example.mortrza.myadvertismentdispaly.ADV.Agahi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class dbHandler extends SQLiteOpenHelper {

    private static String DBNAME = "agahidatabase.db";
    private static String DBPATH;
    private static String TBL_ADV="tbl_adv";
    Context cntx;
    SQLiteDatabase db;

    public dbHandler(@Nullable Context context) {
        super(context, DBNAME, null, 1);
        cntx = context;
        DBPATH = context.getCacheDir().getPath()+"/"+DBNAME;
    }

    public void open(){

        if(DbExist()){
            try{
                File f = new File(DBPATH);
                db = SQLiteDatabase.openDatabase(DBPATH,null,SQLiteDatabase.OPEN_READWRITE);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            if (CopyDb()){
                open();
            }
        }
    }

    public boolean DbExist(){
        File f = new File(DBPATH);
        if (f.exists()){
            return true;
        }else {
            return false;
        }
    }

    public boolean CopyDb(){

        try {

            FileOutputStream out = new FileOutputStream(DBPATH);
            InputStream in = cntx.getAssets().open(DBNAME);

            byte[] buffer = new byte[1024];
            int ch;

            while ((ch=in.read(buffer))>0){
                out.write(buffer,0,ch);
            }

            out.flush();
            out.close();
            in.close();

            return true;
        }catch (Exception e){

            return false;
        }
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public List<Agahi> display(){
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_adv ",null);
        cursor.moveToFirst();
        List<Agahi> agahiList = new ArrayList<>();
        do{
            Agahi agahi = new Agahi();
            agahi.setId(cursor.getString(0));
            agahi.setTitle(cursor.getString(1));
            agahi.setDescription(cursor.getString(2));
            agahi.setAx(cursor.getBlob(3));
            agahi.setCat(cursor.getString(4));
            agahiList.add(agahi);
        }while (cursor.moveToNext());
        return agahiList;
    }

    public List<Agahi> display(String a)  {
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_adv where title like '%"+a+"%'",null);
        cursor.moveToFirst();
        List<Agahi> agahiList = new ArrayList<>();
        do{
            Agahi agahi = new Agahi();
            agahi.setId(cursor.getString(0));
            agahi.setTitle(cursor.getString(1));
            agahi.setDescription(cursor.getString(2));
            agahi.setAx(cursor.getBlob(3));
            agahi.setCat(cursor.getString(4));
            agahiList.add(agahi);
        }while (cursor.moveToNext());
        return agahiList;
    }

    public boolean isDisplay(String a){
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_adv where title like '%"+a+"%'",null);
        cursor.moveToFirst();
        return cursor.getCount()>0;
    }

    public Agahi display2(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM "+TBL_ADV+" where id="+id,null);
        cursor.moveToFirst();

            Agahi agahi = new Agahi();
            agahi.setId(cursor.getString(0));
            agahi.setTitle(cursor.getString(1));
            agahi.setDescription(cursor.getString(2));
            agahi.setAx(cursor.getBlob(3));
            agahi.setCat(cursor.getString(4));
            agahi.setLike(cursor.getInt(5));

        return agahi;
    }

    public boolean isLike(String id){
        Cursor cursor = db.rawQuery("SELECT * FROM "+TBL_ADV+" where fav=1 and id="+id,null);
        cursor.moveToFirst();

        return cursor.getCount()>0;
    }

    public void change_like(int a,String id){
        //db.rawQuery("UPDATE "+TBL_ADV+" set fav="+a+" where id="+id,null);
        ContentValues cv = new ContentValues();
        cv.put("fav",a);
        db.update(TBL_ADV,cv,"id="+id,null);

    }

    public String get_Cat_Name(int id){
        Cursor cursor = db.rawQuery("SELECT name_cat FROM tbl_cat where id_cat="+id,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public String get_Cat_Cat(int id){
        Cursor cursor = db.rawQuery("SELECT cat FROM tbl_adv where id="+id,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public int Cat_count(){
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_cat ",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public int ADV_count(){
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_adv ",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public int Like_count(){
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_adv where fav=1",null);
        cursor.moveToFirst();
        return cursor.getCount();
    }

    public List<Agahi> display_Like(){
        Cursor cursor = db.rawQuery("SELECT * FROM tbl_adv where fav=1",null);
        cursor.moveToFirst();
        List<Agahi> agahiList = new ArrayList<>();
        do{
            Agahi agahi = new Agahi();
            agahi.setId(cursor.getString(0));
            agahi.setTitle(cursor.getString(1));
            agahi.setDescription(cursor.getString(2));
            agahi.setAx(cursor.getBlob(3));
            agahiList.add(agahi);
        }while (cursor.moveToNext());
        return agahiList;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
