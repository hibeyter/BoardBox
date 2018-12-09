package com.bmaxgame.dynamicbuton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Fatma on 22.04.2018.
 */

public class veritabani extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;//database versiyon
    private static final String DATABASE_NAME = "sqlgame";//database adı
    private static final String TABLE_NAME = "leveller";
    private static final String level = "level";
    private static final String leveldegeri = "deger";



    public veritabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "("
                + level + " INTEGER PRIMARY KEY ,"
                + leveldegeri + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }
    public void levelekle(int gelenlevel,int deger ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(level, gelenlevel);
        values.put(leveldegeri,String.valueOf(deger) );
        db.insert(TABLE_NAME, null, values);
        db.close(); //Database Bağlantısını kapattık*/
    }

    public void levelguncelle(int gelenlevel, int deger){
        SQLiteDatabase db = this.getWritableDatabase();
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(leveldegeri,String.valueOf(deger) );
        // updating row
        db.update(TABLE_NAME, values,"level" + "=" + gelenlevel,null );
        db.close();
    }
    public int getRowCount() {
        // Tablodaki row sayısını geri döner.
        String countQuery = "SELECT  count(*) FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCount= db.rawQuery(countQuery, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
       db.close();
       return count;
    }

    public List<String> degerioku(){
      List<String> degerler = new ArrayList<String>();
      SQLiteDatabase db = this.getWritableDatabase();
      String kolonlar[] = {level,leveldegeri};
      Cursor c =db.query(TABLE_NAME,kolonlar,null,null,null,null,null);
      while ((c.moveToNext())){
            degerler.add(c.getString(1));
      }
      return degerler;

    }
    @Override
    public void onUpgrade(SQLiteDatabase sql, int arg1, int arg2) {
       /* String sorgu ="DROP TABLE IF EXISTS "+TABLE_NAME;
        sql.execSQL(sorgu);
        onCreate(sql);*/

    }


}
