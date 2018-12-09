package com.bmaxgame.dynamicbuton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 29.04.2018.
 */

public class paraveritabani extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;//database versiyon
    private static final String DATABASE_NAME = "sqlgame2";//database adı
    private static final String TABLE_NAME = "paraTablosu";
    private static final String paraid = "paraid";
    private static final String paraTutari = "paraTutari";

    public paraveritabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "("
                + paraid + " INTEGER PRIMARY KEY ,"
                + paraTutari + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    public int paraGoster(){
        List<String> degerler = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        String kolonlar[] = {paraid,paraTutari};
        Cursor c =db.query(TABLE_NAME,kolonlar,null,null,null,null,null);
        while ((c.moveToNext())){
            degerler.add(c.getString(1));
        }
        return  Integer.parseInt(degerler.get(0));

    }


    public void veriEkle(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(paraid,1 );
        values.put(paraTutari,50 );
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public void paraArttir(int artacakMiktar){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer a=paraGoster();
        Integer b=a+artacakMiktar;
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(paraTutari,b);
        // updating row
        db.update(TABLE_NAME, values,"paraid" + "=" +1,null );
        db.close();
    }

    public void paraEksilt(int azaltilacakMiktar){
        SQLiteDatabase db = this.getWritableDatabase();
        Integer a=paraGoster();
        Integer b=a-azaltilacakMiktar;
        //Bu methodda ise var olan veriyi güncelliyoruz(update)
        ContentValues values = new ContentValues();
        values.put(paraTutari,b );
        // updating row
        db.update(TABLE_NAME, values,"paraid" + "=" +1,null );
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      /*  String sorgu ="DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(sorgu);
        onCreate(sqLiteDatabase);*/
    }

}