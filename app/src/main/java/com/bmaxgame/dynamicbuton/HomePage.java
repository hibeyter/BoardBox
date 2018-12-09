package com.bmaxgame.dynamicbuton;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

public class HomePage extends AppCompatActivity {
   // MediaPlayer muzik;
    boolean volumesi=true;

    @Override
    protected void onPause() {
        super.onPause();
        //muzik.release();
    }

    @Override
    protected void onStart() {
        super.onStart();
   /*     muzik = MediaPlayer.create(this, R.raw.homemuzik);
        muzik.start();
        muzik.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                muzik.start();
            }
        });*/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MediaPlayer ses = MediaPlayer.create(this, R.raw.sesana);
        setContentView(R.layout.activity_home_page);
        final Button play = (Button) findViewById(R.id.oynabuton);
        final Button playsag = (Button) findViewById(R.id.sag);



        play.setEnabled(true);
         final Animation animAlpha = AnimationUtils.loadAnimation(this,R.anim.alpha);
        final Animation animbeta = AnimationUtils.loadAnimation(this,R.anim.anime);
        final Animation animteta = AnimationUtils.loadAnimation(this,R.anim.beta);
        //region TİMER İŞLEMLERİ
        final Thread timer = new Thread()
        {
            public void run(){
                try {
                    for (int i = 0; i < 500; i++)
                    {
                        sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                play.startAnimation(animbeta);
                                playsag.startAnimation(animAlpha);
                                //yazi.startAnimation(animbeta);
                            }
                        });
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

        //endregion      ;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setEnabled(false);
               // muzik.release();

                    ses.start();
                Intent i2 = new Intent(HomePage.this, oyunyolu.class);
                startActivity(i2);
                finish();

            }
        });
        playsag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ses.start();
                Intent i2 = new Intent(HomePage.this, yardim.class);
                startActivity(i2);

            }
        });

    }
}
