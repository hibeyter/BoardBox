package com.bmaxgame.dynamicbuton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class oyunyolu extends AppCompatActivity {
    //region tanımlamalar
    int satir = 25;
    int sutun = 4;
    List<Button> btnlar = new ArrayList<Button>();
    Button buttons[][] = new Button[satir][sutun];
    Context context = this;
    veritabani veri=new veritabani(context);
    //MediaPlayer muzik;
    //endregion
    @Override
    protected void onPause() {
        super.onPause();
       // muzik.release();
    }
    @Override
    protected void onStart() {
        super.onStart();
       /* muzik = MediaPlayer.create(this, R.raw.homemuzik);
        muzik.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                muzik.start();
            }
        });
        muzik.start();*/
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i2 = new Intent(oyunyolu.this, HomePage.class);
        startActivity(i2);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyunyolu);
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        yololustur(satir,sutun);
    }
    public void yololustur(int satir,int sutun) {


        int level =1;
        final MediaPlayer  click = MediaPlayer.create(this, R.raw.seslevel);
     //   final Animation animAlpha = AnimationUtils.loadAnimation(this,R.anim.anime);
        TableLayout table = (TableLayout) findViewById(R.id.oyuntablo);
        for (int row = 0; row < satir; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int col = 0; col < sutun; col++){
                final int FINAL_COL = col;
                final int FINAL_ROW = row;
                tableRow.setPadding(3,6,3,6);
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setTag(String.valueOf(level));
               // button.setBackgroundResource(R.drawable.homelevel3yildiz);
                button.setTextColor(getResources().getColor( R.color.altin));
                button.setTextSize(30);
              //  button.setText(String.valueOf(level));
               button.setGravity(Gravity.CENTER);
                level++;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            click.start();

                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
                btnlar.add(button);
            }
        }
        verieklemekontrol();
       gelecekresim();
    }
    public void gridButtonClicked(int col, int row) {
        Button button = buttons[row][col]; // gelen buton
        int tıklananlevel = Integer.parseInt(button.getTag().toString());
    // if (acikmi(tıklananlevel)){
           Intent i2 = new Intent(oyunyolu.this, Denek.class);
           i2.putExtra("anahtar",tıklananlevel);
           startActivity(i2);
           finish();
  // }else{
    //    Toast.makeText(oyunyolu.this, "Buraya girmeden önce ustalaşmalısın", Toast.LENGTH_SHORT).show();
   //   }
    }
    public boolean  acikmi (int level){
        List<String> asd = veri.degerioku();
        if (level!=1){
            int cekilendeger= Integer.parseInt(asd.get(level-2));
            if (cekilendeger>0){
                return true;
            }else  {
                return false;
            }
        }else {
            return true;
        }
    }
    public  void    verieklemekontrol (){
        int x = veri.getRowCount();
        if (x<100){
            for (Button btn:btnlar){
                veri.levelekle(Integer.parseInt(btn.getTag().toString()),0 );
            }
       /*  for (int i=71;i<100;i++){
             veri.levelekle(Integer.parseInt(btnlar.get(i).getTag().toString()),0 );
         }*/
        }
    }
    public void gelecekresim(){
        List<String> asd = veri.degerioku();
        for (Button btn : btnlar){
            int x = Integer.parseInt(btn.getTag().toString())-1;
            int a = Integer.parseInt(asd.get(x));
            if (a==1){
                btn.setText(String.valueOf(x+1));
                btn.setBackgroundResource(R.drawable.homelevelbiryildiz);
            }else if (a==2){
                btn.setText(String.valueOf(x+1));
                btn.setBackgroundResource(R.drawable.homelevelikiyildiz);
            }else if (a==3){
                btn.setText(String.valueOf(x+1));
                btn.setBackgroundResource(R.drawable.homelevel3yildiz);
            }else {
                int tıklananlevel = Integer.parseInt(btn.getTag().toString());
                if (((int)(tıklananlevel/10))==0)
                    btn.setBackgroundResource(R.drawable.homelevel);
                else if (((int)(tıklananlevel/10))==1)
                    btn.setBackgroundResource(R.drawable.homelevelbir);
                else if (((int)(tıklananlevel/10))==2)
                    btn.setBackgroundResource(R.drawable.homelevel2);
                else if (((int)(tıklananlevel/10))==3)
                    btn.setBackgroundResource(R.drawable.homelevel3);
                else if (((int)(tıklananlevel/10))==4)
                    btn.setBackgroundResource(R.drawable.homelevel4);
                else if (((int)(tıklananlevel/10))==5)
                    btn.setBackgroundResource(R.drawable.homelevel5);
                else if (((int)(tıklananlevel/10))==6)
                    btn.setBackgroundResource(R.drawable.homelevel6);
                else if (((int)(tıklananlevel/10))==7)
                    btn.setBackgroundResource(R.drawable.homelevel7);
                else if (((int)(tıklananlevel/10))==8)
                    btn.setBackgroundResource(R.drawable.homelevel8);
                else if (((int)(tıklananlevel/10))==9 ||((int)(tıklananlevel/10))==10)
                    btn.setBackgroundResource(R.drawable.homelevel9);
            }
        }
        int index=0;
        for (String degerler :asd){
            index++;
            if (degerler.equals("0")){
                btnlar.get(index-1).setBackgroundResource(R.drawable.butonlevelyesilkilit);
                break;
            }
        }
    }


}
