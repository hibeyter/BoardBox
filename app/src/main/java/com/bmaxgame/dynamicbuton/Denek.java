package com.bmaxgame.dynamicbuton;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Denek extends AppCompatActivity implements RewardedVideoAdListener  {

    //region DEĞİŞKENLER
    int reklamevent=0;
    int satir = 3;
    int sutun = 3;
    int hamle = 18;
    oyunyolu yol = new oyunyolu();
    List<Button> btnlar = new ArrayList<Button>();
    List<String> noktalar = new ArrayList<String>();
    Button buttons[][] = new Button[satir][sutun];
    int tiklananrow,tiklananco,falseet=0;
   // MediaPlayer ses ;
    int level ;
    boolean boxdegisme = true,ekhamlegelsinmi=true,abanozgelsinmi=true;
    TextView hamletext,leveltext,hamlegerialtext,boxdegistirtext,abanos,artiuchamle;
    private SharedPreferences referans;


    //endregion
    //region LEVEL HAMLE ADETLERİ
    int hamle1 = 9, hamle2=12,hamle3=15,hamle4=18,hamle5=18,hamle6=21,hamle7=21,hamle8=21,hamle9=24,hamle10=24,hamle11=27,hamle12=27,hamle13=30,
    hamle14=30,hamle15=30,hamle16=30, hamle17=30, hamle18=30,hamle19=30,hamle20=30,hamle21=30, hamle22=30,hamle23=33,hamle24=30,hamle25=30,
    hamle26=33,hamle27=33,hamle28=33,hamle29=33,hamle30=33,hamle31=36,hamle32=36,hamle33=36,hamle34=39,hamle35=39,hamle36=39,hamle37=42,hamle38=42,
    hamle39=42,hamle40=30,hamle41=33,hamle42=33,hamle43=33,hamle44=33,hamle45=33,hamle46=33,hamle47=33,hamle48=33,hamle49=33,hamle50=36,hamle51=36,
    hamle52=36,hamle53=39,hamle54=39,hamle55=39,hamle56=42,hamle57=42,hamle58=45,hamle59=45,hamle60=48,hamle61=48,hamle62=51,hamle63=51,hamle64=54,
    hamle65=54,hamle66=57,hamle67=57,hamle68=60,hamle69=60,hamle70=63,hamle71=63,hamle72=66,hamle73=66,hamle74=69,hamle75=69,hamle76=72,hamle77=75,
    hamle78=78,hamle79=81,hamle80=84,hamle81=87,hamle82=90,hamle83=93,hamle84=96,hamle85=99,hamle86=102,hamle87=105,hamle88=108,hamle89=111,hamle90=114,
    hamle91=117,hamle92=120,hamle93=123,hamle94=126,hamle95=129,hamle96=132,hamle97=135,hamle98=135,hamle99=135,hamle100=45;
    List<Integer> hamleler = new ArrayList<Integer>();
    public void ekle (){
        hamleler.add(0,hamle1);
        hamleler.add(1,hamle2);
        hamleler.add(2,hamle3);
        hamleler.add(3,hamle4);
        hamleler.add(4,hamle5);
        hamleler.add(5,hamle6);
    }

    //endregion
    public  boolean reklamgelsinmi(){
        Random x = new Random();
        int a = x.nextInt(5);
        if (a==1){
            return true;
        }else{
            return false;
        }

    }
    private void reklamCagir(){
    AdView adView1 = (AdView) this.findViewById(R.id.adViewbir);
    AdRequest adRequest = new AdRequest.Builder().build();
    adView1.loadAd(adRequest);

}
    private InterstitialAd gecisReklam;
    private AdRequest adRequest;
    private void gecisreklam(){
        adRequest = new AdRequest.Builder()
                .build();
        gecisReklam = new InterstitialAd(this);
        gecisReklam.setAdUnitId("ca-app-pub-5047443485754315/5182916487");
        gecisReklam.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }
            @Override
            public void onAdClosed(){
                loadGecisReklam();
            }
        });
        loadGecisReklam();

    }
    public void loadGecisReklam() {
        gecisReklam.loadAd(adRequest);
    }
    public void showGecisReklam() {
        if (gecisReklam.isLoaded()) {
            gecisReklam.show();
        }
    }

    public void kazan(){

        mRewardedVideoAd.loadAd(getString(R.string.ad_unit_id),
                new AdRequest.Builder().build());
    }
     RewardedVideoAd mRewardedVideoAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denek);
        MobileAds.initialize(getApplicationContext(),
                getString(R.string.admob_app_id));
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        kazan();

        reklamCagir();
        gecisreklam();
        MobileAds.initialize(getApplicationContext(),
                getString(R.string.admob_app_id));
        leveltext = (TextView) findViewById(R.id.leveltext);
        hamlegerialtext = (TextView) findViewById(R.id.hamlegerial);
        boxdegistirtext = (TextView) findViewById(R.id.boxdegistir);
        artiuchamle = (TextView) findViewById(R.id.artıuchamle);
        abanos = (TextView) findViewById(R.id.abanostext);
        referans= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        level=referans.getInt("kayitli_level",1);
        final Bundle gelenVeri=getIntent().getExtras();
        level = gelenVeri.getInt("anahtar",1);
        leveltext.setText(""+level);
        levelsorgu();
        final paraveritabani para=new paraveritabani(this);
        para.veriEkle();
        abanos.setText(String.valueOf(para.paraGoster()));
        //region EVENTLER
        hamlegerialtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int param=para.paraGoster();
                if(param>=4){
                    falseet=0;
                    hamlegerialtext.setEnabled(false);
                    boxdegistirtext.setEnabled(false);
                    gerial();
                    para.paraEksilt(4);
                    abanos.setText(String.valueOf(para.paraGoster()));
                }
                else Toast.makeText(Denek.this, "Yeterli ABANOZUNUZ YOK", Toast.LENGTH_LONG).show();

                falsemi();
            }
        });
        boxdegistirtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int param=para.paraGoster();
                if(param>=5){
                    boxdegistirtext.setEnabled(false);
                    hamlegerialtext.setEnabled(false);
                    hamle++;
                    falseet++;
                    enabledac();
                    boxdegisme= false;
                    para.paraEksilt(5);
                    abanos.setText(String.valueOf(para.paraGoster()));
                }
                else
                    Toast.makeText(Denek.this, "Yeterli ABANZUNUZ YOK", Toast.LENGTH_LONG).show();
                falsemi();
            }
        });
        artiuchamle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ekhamlegelsinmi){
                    if(mRewardedVideoAd.isLoaded()){
                        mRewardedVideoAd.show();
                        reklamevent=3;
                    }else{
                        Toast.makeText(Denek.this, "İnternet Bağlantınız ve Hızınızı Kontrol Ediniz", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if (gecisReklam.isLoaded()) {
                    Toast.makeText(getApplicationContext(), "Ek Hamle Daha Önce Alınmış", Toast.LENGTH_SHORT).show();
                    showGecisReklam();
                    }
                }
            }
        });
        abanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (abanozgelsinmi){

                      // gecisReklam.show();
                        if (mRewardedVideoAd.isLoaded()) {
                            mRewardedVideoAd.show();
                            reklamevent=1;
                        }

                    else{
                        Toast.makeText(Denek.this, "İnternet Bağlantınız ve Hızınızı Kontrol Ediniz", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (gecisReklam.isLoaded()) {
                        Toast.makeText(getApplicationContext(), "Ücretsiz Abanoz Daha Önce Alınmış", Toast.LENGTH_SHORT).show();
                        showGecisReklam();
                    }
                }
            }
        });
        //endregion


    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();

    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();

    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    public  void gerial(){
        hamle++;
        Button button = buttons[tiklananrow][tiklananco];
        button.setEnabled(true);
        gridButtonClicked(tiklananco,tiklananrow);
    }
    @Override

    public void onBackPressed() {
        if (reklamgelsinmi()){
            showGecisReklam();
        }
        //region ALERT DİALOG
        int width = (int)(getResources().getDisplayMetrics().widthPixels);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.37);
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Denek.this);
        View mView = getLayoutInflater().inflate(R.layout.cikiseminmisin, null);
        Button evet = (Button) mView.findViewById(R.id.btnevet) ;
        Button hayir = (Button) mView.findViewById(R.id.btnhayir) ;
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        dialog.getWindow().setLayout(width,height);
        evet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ses.release();
                Intent i2 = new Intent(Denek.this, oyunyolu.class);
                startActivity(i2);
                finish();
            }
        });
        hayir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    //endregion

    }
    public void populateButtons(int satir, int sutun) {
    //    showGecisReklam();
        ekhamlegelsinmi=true;
        btnlar.clear();
        hamlegerialtext.setEnabled(false);
        boxdegistirtext.setEnabled(false);
        falsemi();
        falseet=0;
        final MediaPlayer  click = MediaPlayer.create(this, R.raw.sesclick);
        hamletext= (TextView) findViewById(R.id.hamletext);
        leveltext=(TextView) findViewById(R.id.leveltext) ;
       leveltext.setText(String.valueOf(level));
        hamletext.setText(String.valueOf(hamle));
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        final Animation animAlpha = AnimationUtils.loadAnimation(this,R.anim.alpha);
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
                tableRow.setPadding(1,1,1,1);
                final Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setTag(String.valueOf(col)+String.valueOf(row));
                button.setBackgroundResource(R.drawable.xox);
                button.setText(String.valueOf(0));
                button.setTextColor(getResources().getColor( R.color.beyaz));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click.start();
                        v.startAnimation(animAlpha);
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                        tiklananco=FINAL_COL;
                        tiklananrow=FINAL_ROW;
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
                btnlar.add(button);

            }
        }
    }
    public  void renkver(Button a){
        String text = (String) a.getText();
        if (text.equals(String.valueOf(0))||text.equals(String.valueOf(2))){
            a.setBackgroundResource(R.drawable.sos);
            a.setText(String.valueOf(1));
        }
        else {
            a.setBackgroundResource(R.drawable.xox);
            a.setText(String.valueOf(0));
        }


    }
    public void setNoktalar(Button button){
        String gelentag = button.getTag().toString();
        int satir = Integer.parseInt(String.valueOf(gelentag.charAt(0)));
        int sutun = Integer.parseInt(String.valueOf(gelentag.charAt(1)));
        int x1, x2, x3, x4, x5, x6, x7, x8, x9, x11, x21, x31, x41, x51, x61, x71, x81, x91;
        noktalar.clear();
        x1 = satir; x11 = sutun; noktalar.add(String.valueOf(x1)+String.valueOf(x11));
        x2 = satir + 1; x21 = sutun + 1; noktalar.add(String.valueOf(x2)+String.valueOf(x21));
        x3 = satir + 1; x31 = sutun - 1; noktalar.add(String.valueOf(x3)+String.valueOf(x31));
        x4 = satir - 1; x41 = sutun - 1; noktalar.add(String.valueOf(x4)+String.valueOf(x41));
        x5 = satir - 1; x51 = sutun + 1; noktalar.add(String.valueOf(x5)+String.valueOf(x51));
        x6 = satir + 1; x61 = sutun; noktalar.add(String.valueOf(x6)+String.valueOf(x61));
        x7 = satir; x71 = sutun + 1; noktalar.add(String.valueOf(x7)+String.valueOf(x71));
        x8 = satir - 1; x81 = sutun; noktalar.add(String.valueOf(x8)+String.valueOf(x81));
        x9 = satir; x91 = sutun - 1; noktalar.add(String.valueOf(x9)+String.valueOf(x91));
    }
    public  void butonkapat(Button button){
        button.setEnabled(false);
        String x = (String) button.getText();
        if (x.equals(String.valueOf(1))){
            button.setBackgroundResource(R.drawable.sarimaymun);
            button.setText(String.valueOf(3));
        }else if (x.equals(String.valueOf(0))){
            button.setBackgroundResource(R.drawable.kirmizimaymun);
            button.setText(String.valueOf(2));
        }
    }
    public  void butonicidongu(){
        for (Button buton  : btnlar) {
            for (String nokta  : noktalar) {
                if (buton.getTag().toString().equals(nokta)){
                    renkver(buton);
                }
            }
        }
    }
    public boolean kazandimistring(){
        String kazandimi ="";
        for (Button btn: btnlar){
            if (btn.getText().equals(String.valueOf(1)) || (btn.getText().equals(String.valueOf(3)))){
                kazandimi+="s";
            }else{
                kazandimi+="k";
            }
        }
        int deger = 0;
        for (int i = 0; i< (satir*sutun); i++ ){
            if (  String.valueOf(kazandimi.charAt(i)).equals("s")  ){
                deger++;
            }
        }
        if (deger==satir*sutun){
            return true;
        }else return false;
    }
    public void gridButtonClicked(int col, int row) {
        if (falseet<2){
            falseet++;
        }else {
            hamlegerialtext.setEnabled(true);
        }
        boxdegistirtext.setEnabled(true);
        int width = (int)(getResources().getDisplayMetrics().widthPixels);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.37);
        hamletext= (TextView) findViewById(R.id.hamletext);
        Button button = buttons[row][col]; // gelen buton
        if (boxdegisme){
            if (hamle>0) {
                enabledac();
                setNoktalar(button);
                butonicidongu();
                butonkapat(button);
                kazanımkontrol();
            }
        }else {
                hamlegerialtext.setEnabled(false);
                boxdegistirtext.setEnabled(false);
                boxdegistir(button);
                boxdegisme = true;
                kazanımkontrol();
        }
        falsemi();

    }
    public void  eableddegistir(Object... gelenintler){

        for(Object i : gelenintler){
            Button x = btnlar.get(((int)i)-1);
            x.setEnabled(false);
            if (x.getText().equals(String.valueOf(0))){
                x.setBackgroundResource(R.drawable.kirmizimaymun);
                x.setText(String.valueOf(2));
            }else{
                x.setBackgroundResource(R.drawable.sarimaymun);
                x.setText(String.valueOf(3));
            }

        }

    }
    public   void kazanımkontrol(){
        int width = (int)(getResources().getDisplayMetrics().widthPixels);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.34);
        if (kazandimistring()) {
            for (Button btn : btnlar) {
                btn.setEnabled(true);
            }
            veritabani veri = new veritabani(this);
            paraveritabani para=new paraveritabani(this);
            int yildizim=Integer.parseInt(veri.degerioku().get(level-1));
            level++;
            if (yildizbak()==1){
               final MediaPlayer yildiz = MediaPlayer.create(this, R.raw.tekyildiz);
                yildiz.start();
                if (yildizim==0){
                    para.paraArttir(2);
                }
                veri.levelguncelle(level-1,1);
                //region ALERT
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Denek.this);
                View mView = getLayoutInflater().inflate(R.layout.tekyildiz, null);
                Button nextlevel = (Button) mView.findViewById(R.id.teknextlevel) ;
                Button home = (Button) mView.findViewById(R.id.tekbtnhome) ;
                Button againt =(Button) mView.findViewById(R.id.tekagain);
                mBuilder.setView(mView);
                mBuilder.setCancelable(false);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                nextlevel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        yildiz.release();
                        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                        table.removeAllViews();
                        levelsorgu();
                        dialog.cancel();
                      /*  ses.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ses.start();
                            }
                        });
                        ses.start();*/
                    }
                });
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        yildiz.release();
                        Intent i2 = new Intent(Denek.this, oyunyolu.class);
                        startActivity(i2);

                    }
                });
                againt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        level--;
                        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                        table.removeAllViews();
                       /* ses.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ses.start();
                            }
                        });
                        ses.start();*/
                        levelsorgu();
                        dialog.cancel();
                    }
                });
                //endregion
            }
            else if (yildizbak()==2){
                final MediaPlayer yildiz = MediaPlayer.create(this, R.raw.ikiyildiz);
                yildiz.start();
                if (yildizim==0){
                    para.paraArttir(4);
                }
                veri.levelguncelle(level-1,2);
               //region ALERT
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Denek.this);
                View mView = getLayoutInflater().inflate(R.layout.ikiyildiz, null);
                Button nextlevel = (Button) mView.findViewById(R.id.ikinextlevel) ;
                Button home = (Button) mView.findViewById(R.id.ikibtnhome) ;
                Button againt =(Button) mView.findViewById(R.id.ikiagain);
                mBuilder.setView(mView);
                mBuilder.setCancelable(false);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                nextlevel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        yildiz.release();
                        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                        table.removeAllViews();
                        levelsorgu();
                        dialog.cancel();
                      /*  ses.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ses.start();
                            }
                        });
                        ses.start();*/
                    }
                });
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        yildiz.release();
                        Intent i2 = new Intent(Denek.this, oyunyolu.class);
                        startActivity(i2);

                    }
                });
                againt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        level--;
                        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                        table.removeAllViews();
                       /* ses.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ses.start();
                            }
                        });
                        ses.start();*/
                        levelsorgu();
                        dialog.cancel();
                    }
                });
                //endregion
            }else {
                final MediaPlayer yildiz = MediaPlayer.create(this, R.raw.ucyildiz);
                yildiz.start();
                if (yildizim==0){
                    para.paraArttir(6);
                }
                veri.levelguncelle(level-1,3);
                //region ALERT
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Denek.this);
                View mView = getLayoutInflater().inflate(R.layout.ucyildiz, null);
                Button nextlevel = (Button) mView.findViewById(R.id.ucnextlevel) ;
                Button home = (Button) mView.findViewById(R.id.ucbtnhome) ;
                Button againt =(Button) mView.findViewById(R.id.ucagain);
                mBuilder.setView(mView);
                mBuilder.setCancelable(false);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                dialog.getWindow().setLayout(width,height);
                nextlevel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        yildiz.release();
                        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                        table.removeAllViews();
                        levelsorgu();
                        dialog.cancel();
                       /* ses.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ses.start();
                            }
                        });
                        ses.start();*/
                    }
                });
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        yildiz.release();
                        Intent i2 = new Intent(Denek.this, oyunyolu.class);
                        startActivity(i2);
                    }
                });
                againt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        level--;
                        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                        table.removeAllViews();
                     /*   ses.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ses.start();
                            }
                        });
                        ses.start();*/
                        levelsorgu();
                        dialog.cancel();
                    }
                });
                //endregion
            }
            int param=para.paraGoster();
            abanos.setText(String.valueOf(param));
        }
        else{
            hamle--;
            hamletext.setText(String.valueOf(hamle));
            if (hamle==0){
                final MediaPlayer kayip = MediaPlayer.create(this, R.raw.kayipmuzik);
                kayip.start();
                for (Button btn : btnlar) {
                    btn.setEnabled(false);
                }
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Denek.this);
                View mView = getLayoutInflater().inflate(R.layout.kaybettiniz, null);
                Button artıbes = (Button) mView.findViewById(R.id.artıbesbtn);
                Button home = (Button) mView.findViewById(R.id.kayipbtnhome);
                Button kayipagain = (Button) mView.findViewById(R.id.kayiptekraroyna);
                mBuilder.setView(mView);
                mBuilder.setCancelable(false);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                height = (int)(getResources().getDisplayMetrics().heightPixels*0.34);
                dialog.getWindow().setLayout(width, height);
                artıbes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        paraveritabani para=new paraveritabani(Denek.this);
                        int param = para.paraGoster();
                        if (param>=3){
                            hamle += 5;
                            para.paraEksilt(3);
                            hamletext.setText(String.valueOf(hamle));
                            abanos.setText(String.valueOf(para.paraGoster()));
                            enabledac();
                            dialog.cancel();

                        }else{
                            Toast.makeText(Denek.this, "Yeterli Abanozunuz Yok", Toast.LENGTH_SHORT).show();
                        }
                        kayip.release();
                       /* if(mRewardedVideoAd.isLoaded()){
                            mRewardedVideoAd.show();
                            reklamevent=5;
                            dialog.cancel();
                        }else{
                            Toast.makeText(Denek.this, "İnternet Bağlantınız ve Hızınızı Kontrol Ediniz", Toast.LENGTH_SHORT).show();
                            dialog.show();
                        }
                        for (Button btn : btnlar) {
                            btn.setEnabled(true);
                        }*/


                    }
                });
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                        Intent i2 = new Intent(Denek.this, oyunyolu.class);
                        startActivity(i2);
                    }
                });
                kayipagain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                        table.removeAllViews();
                        levelsorgu();
                        dialog.cancel();
                        if (reklamgelsinmi()){
                            showGecisReklam();
                        }
                    }
                });
            }
        }

              //endregion
    }
    public   void enabledac(){

        for (Button x : btnlar){
            if (!x.isEnabled()){
                x.setEnabled(true);
                if (x.getText().equals(String.valueOf(2))){
                    x.setBackgroundResource(R.drawable.xox);
                    x.setText(String.valueOf(0));
                }else  if (x.getText().equals(String.valueOf(3))) {
                    x.setBackgroundResource(R.drawable.sos);
                    x.setText(String.valueOf(1));
                }
            }
        }
    }
    public void boxdegistir(Button gelenbuton){
        String gelenstring = (String) gelenbuton.getText();
        if (gelenstring.equals(String.valueOf(0))){
            gelenbuton.setBackgroundResource(R.drawable.sos);
            gelenbuton.setText(String.valueOf(1));
        }else{
            gelenbuton.setBackgroundResource(R.drawable.xox);
            gelenbuton.setText(String.valueOf(0));
        }
    }
    public void degistir (Object... gelenintler){
        for (Object x: gelenintler){
            int a =((int) x)-1;
            String xyz =(String) btnlar.get(a).getText();
            if (xyz.equals(String.valueOf(3))){
                btnlar.get(a).setBackgroundResource(R.drawable.kirmizimaymun);
                btnlar.get(a).setText(String.valueOf(2));
            }else if(xyz.equals(String.valueOf(2))){
                btnlar.get(a).setBackgroundResource(R.drawable.sarimaymun);
                btnlar.get(a).setText(String.valueOf(3));
            }else  if(xyz.equals(String.valueOf(1))){
                btnlar.get(a).setBackgroundResource(R.drawable.xox);
                btnlar.get(a).setText(String.valueOf(0));
            }else if(xyz.equals(String.valueOf(0))){
                btnlar.get(a).setBackgroundResource(R.drawable.sos);
                btnlar.get(a).setText(String.valueOf(1));
            }
        }
    }
    public  void enabledrandomcu(int gelenrandom ){
        Random rsayi = new Random();
        for (int i = 0; i < gelenrandom; i++)
        {
            int y = rsayi.nextInt(btnlar.size());
            eableddegistir(y+1);
        }
    }
    public  void   randomcu (int gelenrandom){
        Random rsayi = new Random();
        for (int i = 0; i < gelenrandom; i++)
        {
            int y = rsayi.nextInt(btnlar.size());
            String x= (String) btnlar.get(y).getText();

            if(x.equals(String.valueOf(1))){
                btnlar.get(y).setBackgroundResource(R.drawable.xox);
                btnlar.get(y).setText(String.valueOf(0));
            }else if(x.equals(String.valueOf(0))){
                btnlar.get(y).setBackgroundResource(R.drawable.sos);
                btnlar.get(y).setText(String.valueOf(1));
            }
        }
    }
    public void falsemi (){
        if (hamlegerialtext.isEnabled()){
            hamlegerialtext.setBackgroundColor(getResources().getColor( R.color.beyaz));
        }else {
            hamlegerialtext.setBackgroundColor(getResources().getColor( R.color.kirmizi));
        }
        if (boxdegistirtext.isEnabled()){
            boxdegistirtext.setBackgroundColor(getResources().getColor( R.color.beyaz));
        }else {
            boxdegistirtext.setBackgroundColor(getResources().getColor( R.color.kirmizi));
        }
    }
    //region LEVELLER
    public  int   yildizbak(){
        int hamlem;
        switch (level-1){
            case  1:
                hamlem=hamle1/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  2:
                hamlem=hamle2/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  3:
                hamlem=hamle3/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  4:
                hamlem=hamle4/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  5:
                hamlem=hamle5/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  6:
                hamlem=hamle6/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  7:
                hamlem=hamle7/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  8:
                hamlem=hamle8/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  9:
                hamlem=hamle9/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  10:
                hamlem=hamle10/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  11:
                hamlem=hamle11/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  12:
                hamlem=hamle12/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  13:
                hamlem=hamle13/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  14:
                hamlem=hamle14/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  15:
                hamlem=hamle15/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  16:
                hamlem=hamle16/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  17:
                hamlem=hamle17/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  18:
                hamlem=hamle18/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  19:
                hamlem=hamle19/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  20:
                hamlem=hamle20/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  21:
                hamlem=hamle21/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  22:
                hamlem=hamle22/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  23:
                hamlem=hamle23/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  24:
                hamlem=hamle24/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  25:
                hamlem=hamle25/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  26:
                hamlem=hamle26/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  27:
                hamlem=hamle27/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  28:
                hamlem=hamle28/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  29:
                hamlem=hamle29/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  30:
                hamlem=hamle30/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  31:
                hamlem=hamle31/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  32:
                hamlem=hamle32/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  33:
                hamlem=hamle33/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  34:
                hamlem=hamle34/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  35:
                hamlem=hamle35/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  36:
                hamlem=hamle36/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  37:
                hamlem=hamle37/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  38:
                hamlem=hamle38/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  39:
                hamlem=hamle39/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  40:
                hamlem=hamle40/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  41:
                hamlem=hamle41/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  42:
                hamlem=hamle42/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  43:
                hamlem=hamle43/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  44:
                hamlem=hamle44/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  45:
                hamlem=hamle45/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  46:
                hamlem=hamle46/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  47:
                hamlem=hamle47/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  48:
                hamlem=hamle48/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  49:
                hamlem=hamle49/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  50:
                hamlem=hamle50/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  51:
                hamlem=hamle51/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  52:
                hamlem=hamle52/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  53:
                hamlem=hamle53/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  54:
                hamlem=hamle54/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  55:
                hamlem=hamle55/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  56:
                hamlem=hamle56/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  57:
                hamlem=hamle57/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  58:
                hamlem=hamle58/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else {
                    return 3;
                }
            case  59:
                hamlem=hamle59/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  60:
                hamlem=hamle60/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  61:
                hamlem=hamle61/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  62:
                hamlem=hamle62/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  63:
                hamlem=hamle63/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  64:
                hamlem=hamle64/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  65:
                hamlem=hamle65/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  66:
                hamlem=hamle66/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  67:
                hamlem=hamle67/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  68:
                hamlem=hamle69/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  69:
                hamlem=hamle69/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  70:
                hamlem=hamle70/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  71:
                hamlem=hamle71/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  72:
                hamlem=hamle72/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  73:
                hamlem=hamle73/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  74:
                hamlem=hamle74/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  75:
                hamlem=hamle75/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  76:
                hamlem=hamle76/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  77:
                hamlem=hamle77/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  78:
                hamlem=hamle78/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  79:
                hamlem=hamle79/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  80:
                hamlem=hamle80/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  81:
                hamlem=hamle81/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  82:
                hamlem=hamle82/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  83:
                hamlem=hamle83/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  84:
                hamlem=hamle84/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  85:
                hamlem=hamle85/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  86:
                hamlem=hamle86/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  87:
                hamlem=hamle87/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  88:
                hamlem=hamle88/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  89:
                hamlem=hamle89/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  90:
                hamlem=hamle90/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  91:
                hamlem=hamle91/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  92:
                hamlem=hamle92/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  93:
                hamlem=hamle93/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  94:
                hamlem=hamle94/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  95:
                hamlem=hamle95/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  96:
                hamlem=hamle96/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  97:
                hamlem=hamle97/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  98:
                hamlem=hamle98/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  99:
                hamlem=hamle99/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            case  100:
                hamlem=hamle100/3;
                if (hamle<=hamlem){
                    return 1;
                }else if(hamle>hamlem&hamle<hamlem*2){
                    return 2;
                }else{
                    return 3;
                }
            default:
                return 1;
        }

    }
    public void levelsorgu(){
       switch (level){
           case 1:
               hamle=hamle1;
               populateButtons(3,3);
            //   eableddegistir(5);
               break;
           case 2:
               hamle=hamle2;
               level2();
               break;
           case 3:
               hamle=hamle3;
               level3();
               break;
           case 4:
               hamle=hamle4;
               level4();
               break;
           case 5:
               hamle=hamle5;
               level5();
               break;
           case 6:
               hamle=hamle6;
               level6();
               break;
           case 7:
               hamle=hamle7;
               level7();
               break;
           case 8:
               hamle=hamle8;
               level8();
               break;
           case 9:
               hamle=hamle9;
               level9();
               break;
           case 10:
               hamle=hamle10;
               level10();
               break;
           case 11:
               hamle=hamle11;
               level11();
               break;
           case 12:
               hamle=hamle12;
               level12();
               break;
           case 13:
               hamle=hamle13;
               level13();
               break;
           case 14:
               hamle=hamle14;
               level14();
               break;
           case 15:
               hamle=hamle15;
               level15();
               break;
           case 16:
               hamle=hamle16;
               level16();
               break;
           case 17:
               hamle=hamle17;
               level17();
               break;
           case 18:
               hamle=hamle18;
               level18();
               break;
           case 19:
               hamle=hamle19;
               level19();
               break;
           case 20:
               hamle=hamle20;
               level20();
               break;
           case 21:
               hamle=hamle21;
               level21();
               break;
           case 22:
               hamle=hamle22;
               level22();
               break;
           case 23:
               hamle=hamle23;
               level23();
               break;
           case 24:
               hamle=hamle24;
               level24();
               break;
           case 25:
               hamle=hamle25;
               level25();
               break;
           case 26:
               hamle=hamle26;
               level26();
               break;
           case 27:
               hamle=hamle27;
               level27();
               break;
           case 28:
               hamle=hamle28;
               level28();
               break;
           case 29:
               hamle=hamle29;
               level29();
               break;
           case 30:
               hamle=hamle30;
               level30();
               break;
           case 31:
               hamle=hamle31;
               level31();
               break;
           case 32:
               hamle=hamle32;
               level32();
               break;
           case 33:
               hamle=hamle33;
               level33();
               break;
           case 34:
               hamle=hamle34;
               level34();
               break;
           case 35:
               hamle=hamle35;
               level35();
               break;
           case 36:
               hamle=hamle36;
               level36();
               break;
           case 37:
               hamle=hamle37;
               level37();
               break;
           case 38:
               hamle=hamle38;
               level38();
               break;
           case 39:
               hamle=hamle39;
               level39();
               break;
           case 40:
               hamle=hamle40;
               level40();
               break;
           case 41:
               hamle=hamle41;
               level41();
               break;
           case 42:
               hamle=hamle42;
               level42();
               break;
           case 43:
               hamle=hamle43;
               level43();
               break;
           case 44:
               hamle=hamle44;
               level44();
               break;
           case 45:
               hamle=hamle45;
               level45();
               break;
           case 46:
               hamle=hamle46;
               level46();
               break;
           case 47:
               hamle=hamle47;
               level47();
               break;
           case 48:
               hamle=hamle48;
               level48();
               break;
           case 49:
               hamle=hamle49;
               level49();
               break;
           case 50:
               hamle=hamle50;
               level50();
               break;
           case 51:
               hamle=hamle51;
               level51();
               break;
           case 52:
               hamle=hamle52;
               level52();
               break;
           case 53:
               hamle=hamle53;
               level53();
               break;
           case 54:
               hamle=hamle54;
               level54();
               break;
           case 55:
               hamle=hamle55;
               level55();
               break;
           case 56:
               hamle=hamle56;
               level56();
               break;
           case 57:
               hamle=hamle57;
               level57();
               break;
           case 58:
               hamle=hamle58;
               level58();
               break;
           case 59:
               hamle=hamle59;
               level59();
               break;
           case 60:
               hamle=hamle60;
               level60();
               break;
           case 61:
               hamle=hamle61;
               level61();
               break;
           case 62:
               hamle=hamle62;
               level62();
               break;
           case 63:
               hamle=hamle63;
               level63();
               break;
           case 64:
               hamle=hamle64;
               level64();
               break;
           case 65:
               hamle=hamle65;
               level65();
               break;
           case 66:
               hamle=hamle66;
               level66();
               break;
           case 67:
               hamle=hamle67;
               level67();
               break;
           case 68:
               hamle=hamle68;
               level68();
               break;
           case 69:
               hamle=hamle69;
               level69();
               break;
           case 70:
               hamle=hamle70;
               level70();
               break;
           case 71:
               hamle=hamle71;
               level71();
               break;
           case 72:
               hamle=hamle72;
               level72();
               break;
           case 73:
               hamle=hamle73;
               level73();
               break;
           case 74:
               hamle=hamle74;
               level74();
               break;
           case 75:
               hamle=hamle75;
               level75();
               break;
           case 76:
               hamle=hamle76;
               level76();
               break;
           case 77:
               hamle=hamle77;
               level77();
               break;
           case 78:
               hamle=hamle78;
               level78();
               break;
           case 79:
               hamle=hamle79;
               level79();
               break;
           case 80:
               hamle=hamle80;
               level80();
               break;
           case 81:
               hamle=hamle81;
               level81();
               break;
           case 82:
               hamle=hamle82;
               level82();
               break;
           case 83:
               hamle=hamle83;
               level83();
               break;
           case 84:
               hamle=hamle84;
               level84();
               break;
           case 85:
               hamle=hamle85;
               level85();
               break;
           case 86:
               hamle=hamle86;
               level86();
               break;
           case 87:
               hamle=hamle87;
               level87();
               break;
           case 88:
               hamle=hamle88;
               level88();
               break;
           case 89:
               hamle=hamle89;
               level89();
               break;
           case 90:
               hamle=hamle90;
               level90();
               break;
           case 91:
               hamle=hamle91;
               level91();
               break;
           case 92:
               hamle=hamle92;
               level92();
               break;
           case 93:
               hamle=hamle93;
               level93();
               break;
           case 94:
               hamle=hamle94;
               level94();
               break;
           case 95:
               hamle=hamle95;
               level95();
               break;
           case 96:
               hamle=hamle96;
               level96();
               break;
           case 97:
               hamle=hamle97;
               level97();
               break;
           case 98:
               hamle=hamle98;
               level98();
               break;
           case 99:
               hamle=hamle99;
               level99();
               break;
           case 100:
               hamle=hamle100;
               level100();
               break;
           default:
               break;
       }
    }
    public void level2(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        eableddegistir(5);
       //2 5 2
    }
    public void level3(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(3,5,7);
        // 1 9
    }
    public void level4(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,5,8);
        // 7 6
    }
    public void level5(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,3,4,5,6,7,9);
        // 2 8 1 3 9 7

    }
    public void level6(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,4,9);
        // 1 3 7
    }
    public void level7(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(3,5,8);
    }
    public void level8(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,7);
        // 3 9 2 8  5
    }
    public void level9(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1);
        // 3 7 9 6 8 3 7
    }
    public void level10(){
        satir=3;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,9);

        // 6 4 9 1 5 2 8
    }
    public void level11(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        veritabani veri = new veritabani(this);
        int yildizim=Integer.parseInt(veri.degerioku().get(level-1));
        if (yildizim==0)
            Toast.makeText(this, "Yeni Zorluk Seviyesine Geçildi", Toast.LENGTH_LONG).show();
        // 2 11

    }
    public void level12(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        eableddegistir(2,11);
       // 1 11 1  2
    }
    public void level13(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,11);
        // 4 6 7 9  8 5 2 11
    }
    public void level14(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,3,10,12);
        // 7 9 6 4 2 11
    }
    public void level15(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,12,5,8);
        // 6 7
    }
    public void level16(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,3,5,8,10,12);
        // 5 8 12 3 10 1
    }
    public void level17(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,2,6,8,10);
        //12 8 4 2 11
    }
    public void level18(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,2,4,6,7,9,11,12);
        //9 4  2 11
    }
    public void level19(){
        satir=4;
        sutun=3;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,3,4,5,8,9,10,12);
        //9 4 3 10 2 11
    }
    public void level20(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        eableddegistir(1,4,13,16);
        veritabani veri = new veritabani(this);
        int yildizim=Integer.parseInt(veri.degerioku().get(level-1));
        if (yildizim==0)
            Toast.makeText(this, "Yeni Zorluk Seviyesine Geçildi", Toast.LENGTH_LONG).show();
        //5  4  16 13 5 1
    }
    public void level21(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,2,3,5,6,7,10,11,12,14,15,16);
        // 2 15 1 4 13 16

    }
    public void level22(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(3,6,8,9,11,14);
        //6 11 4 13
    }
    public void level23(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,4,5,6,11,12,13,15);
        // 1 16 7 10 6 11 1 4 13 16
    }
    public void level24(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,2,3,5,8,9,12,14,15,16);
        //6 11 1 4 13 16
    }
    public void level25(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(6,7,8,9,10,11);
        // 6 11 2 15  1 4 13 16
    }
    public void level26(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,2,7,10,12,13,16);
        // 5 15 10 1 4 13 16
    }
    public void level27(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,4,6,7,8,9,10,11,13,16);
        // 2 7 10 15  1 4 13 16
    }
    public void level28(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,3,5,6,7,8,9,10,11,12,14,15);
        // 6,7 10 11
    }
    public void level29(){
        satir=4;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,2,4,8,9,13,15,16);
        // 2 15 8 9 1 4 13 16
    }
    public void level30(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        eableddegistir(1,2,3,4,5,8,9,12,13,16,17,20);
        veritabani veri = new veritabani(this);
        int yildizim=Integer.parseInt(veri.degerioku().get(level-1));
        if (yildizim==0)
            Toast.makeText(this, "Yeni Zorluk Seviyesine Geçildi", Toast.LENGTH_LONG).show();
        // 19 8 5 19 20 17

    }
    public void level31(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,5,9,13,17,4,8,12,16,20);
        // 6 18  7 19 5 8 17 20
    }
    public void level32(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,4,13,16);
        // 6,7,10,11,5,8,17,20
    }
    public void level33(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,4,9,12,17,20);
        // 6,7,10,11,14,15,5,8,17,20
    }
    public void level34(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,6,9,12,15,19);
        // 4,7,14,17,5,8,17,20
    }
    public void level35(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(9,12);
        // 3,6,14,18,6,7,5,8,17,20
    }
    public void level36(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(3,4,5,6,15,16,17,18);
        // 8,12,9,13,5,8,17,20
    }
    public void level37(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,4,6,7,10,11,14,15,17,20);
        // 9 12 6 18 7 19 20 17 8 5
    }
    public void level38(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,4,5,12,13,15,18,19,20);
        // 2,5,7,10,12,15,5,8,17,20
    }
    public void level39(){
        satir=5;
        sutun=4;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,2,3,5,6,7,9,12,14,15,16,18,19,20);
        // 6,15,5,8,17,20
    }
    public void level40(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        veritabani veri = new veritabani(this);
        int yildizim=Integer.parseInt(veri.degerioku().get(level-1));
        if (yildizim==0)
            Toast.makeText(this, "Yeni Zorluk Seviyesine Geçildi", Toast.LENGTH_LONG).show();
       eableddegistir(1,2,3,4,5,6,10,11,15,16,20,21,25,24,23,22);
        // 6 11 7 10 1 16
    }
    public void level41(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(3,7,9,11,13,15,17,19,23);
        // 1 7 13 19 20 6 9 24 21
    }
    public void level42(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,3,5,6,8,10,12,13,14,16,17,19,20,21,22,24,25);
        // 17 19 8 5 10 5 21 9 24
    }
    public void level43(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,5,6,8,10,11,14,16,17,19,20,23,24,25);
        // 4 24 8 12 6 9 21  14
    }
    public void level44(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,9,7);
    }
    public void level45(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(3,14,19,24);
    }
    public void level46(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,7,14,22,25);
    }
    public void level47(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(2,4,6,8,10,12);
    }
    public void level48(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,3,5,7,9,11,13);
    }
    public void level49(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(21,14,18,7,13,6,9,11);
    }
    public void level50(){
        satir=5;
        sutun=5;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(19, 8 );
    }
    public void level51(){
        satir=6;
        sutun=6;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(22,10,23,25);

    }
    public void level52(){
        satir=6;
        sutun=6;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(11,9,23,27,13,25);
    }
    public void level53(){
        satir=6;
        sutun=6;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(4,33,3,30,10,35,31,17);
    }
    public void level54(){
        satir=6;
        sutun=6;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(23,29,15,10,28,13,1,8,27,30);
    }
    public void level55(){
        satir=6;
        sutun=6;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(22,12,11,25,18,16,22,13,31,10,20);
    }
    public void level56(){
        satir=6;
        sutun=6;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(32,16,14,12,30,32,11,12,9,21,31,23,9,18,5,30);
    }
    public void level57(){
        satir=6;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(22,36,22,25);
    }
    public void level58(){
        satir=6;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(19, 8, 13, 23, 4 ,11, 14, 8 ,14);
    }
    public void level59(){
        satir=6;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(12,15,14,9,6,28,31,9,34,24,35,41);
    }
    public void level60(){
        satir=6;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(15);
    }
    public void level61(){
        satir=7;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(9,27,16,36);
    }
    public void level62(){
        satir=7;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(14,4,36,19,5,17,5,35);
    }
    public void level63(){
        satir=7;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(37,21,2,45,41,30,44,42,45,1,2,40);
    }
    public void level64(){
        satir=7;
        sutun=7;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(22,19,28,32,14,41,1,15,34,11,15,27,38,38,33,27);
    }
    public void level65(){
        satir=7;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(45,8,40,24);
    }
    public void level66(){
        satir=7;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(13,3,53,52,55,13,28);
    }
    public void level67(){
        satir=7;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(48,21,30,44,54,15,13,33,34);
    }
    public void level68(){
        satir=7;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(8,11,37,3,29,42,41,20,35,16,25,10,53);
    }
    public void level69(){
        satir=7;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(29,11,19,26,14,15,29,32,16,18,44,40,47,28,7,15,29);
    }
    public void level70(){
        satir=7;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(23,8,52,49,1,33,7,48,47,49,25,44,48,23,14,35,13,22,36,22,54,40,52);
    }
    public void level71(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(39,58,46,9,24,29,49);
    }
    public void level72(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(50,31,25,29,40,31,54,44,1,52,35);
    }
    public void level73(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(42,41,18,5,3,29,24,55,60,28,19,31,48,28,36);
}
    public void level74(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(9,40,52,29,30,16,9,60,15,13,24,17,54,49,21,30,26,45,35);
    }
    public void level75(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(53,2,53,54,7,10,41,14,37,24,3,45,3,55,9,21,15,36,63,33,51,56);
    }
    public void level76(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(39,49,32,24,48,52,11,27,44,23,13,34,8,57,2,39,54,18,48,62,21,12,11,54);
    }
    public void level77(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(37,28,58,53,50,5,42,24,1,49,50,53,38,47,58,32,13,63,14,2,13,32,6,31,18,63,8);
    }
    public void level78(){
        satir=8;
        sutun=8;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(17,31,44,7,17,19,32,24,19,62,62,7,5,15,14,25,63,34,35,30,20,42,27,19,49,22,19,18,21,45,50);
    }
    public void level79(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(28,42,15,51,15,32);
    }
    public void level80(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(59,24,7,64,10,37,50,62,62);
    }
    public void level81(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(70,53,37,8,6,37,42,34,10,23,60);
    }
    public void level82(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(45,41,46,13,5,2,10,64,27,46,68,59,47,20,5);
    }
    public void level83(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(67,56,24,25,50,53,62,5,5,69,69,62,17,9,35,54,18);
    }
    public void level84(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(59,32,27,56,63,65,1,23,50,39,43,69,41,46,61,14,60,19,1,33);
    }
    public void level85(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(44,58,67,53,36,42,18,1,7,43,56,16,5,8,63,40,40,68,3,25,54,56,52);
    }
    public void level86(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(23,24,47,54,59,39,63,26,7,63,11,7,64,30,21,25,43,71,42,56,52,2,55,50,37,35,21,64);
    }
    public void level87(){
        satir=8;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(31,11,48,43,67,25,32,23,12,58,40,19,39,55,6,52,57,61,7,60,4,14,63,7,69,19,22,34,30,34,50);
    }
    public void level88(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(10,52,70);
    }
    public void level89(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(72,37,26,28,66,41,51);
    }
    public void level90(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(33,61,55,11,37,26,20,66,25,42,64);
    }
    public void level91(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(36,33,32,21,10,42,43,22,5,51,68,50);
    }
    public void level92(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(80,21,44,66,76,41,17,78,56,63,32,57,33,22,80);
    }
    public void level93(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(15,36,3,19,77,32,34,10,14,38,59,53,63,41,15,68,23,51,65,77);
    }
    public void level94(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(79,72,9,67,9,36,6,53,31,19,43,49,3,14,46,3,73,16,13,80,65,67,3,35);
    }
    public void level95(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(74,34,4,24,37,68,36,34,73,51,39,69,44,53,77,53,32,44,24,19,57,80,61,63,50,5,7,51);
    }
    public void level96(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(40,6,37,75,4,7,73,57,1,13,42,15,18,44,25,5,33,72,4,23,73,14,79,12,26,71,3,35,16,61,60,52);
    }
    public void level97(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(1,15,32,77,74,47,18,63,65,65,59,59,22,63,58,12,43,21,9,25,74,80,56,12,80,59,46,17,10,33,16);
    }
    public void level98(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(16,37,11,76,78,39,13,43,64,39,79,17,41,9,66,49,61,7,18,29,26,70,53,36,16,60,49,74,16,37,30,44,22,3,1);
    }
    public void level99(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(11,57,12,3,2,15,65,66,65,5,55,5,33,55,12,80,51,24,41,50,15,42,37,36,25,52,8,67,51,62,58,56,33,21,60,27,54,55,35,74,45);
    }
    public void level100(){
        satir=9;
        sutun=9;
        buttons = new Button[satir][sutun];
        populateButtons(satir,sutun);
        degistir(16,13,2,56,79,34,79,1,3,7,31,70,21,56,68,24,53,19,24,53,73,35,68,69,15,33,23,5,58,45,57,76,76,14,80,78,75,20,32,62,5,48,23,58,46);
    }

    //endregion

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(this, "Ödül Reklamları Yüklendi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
       if(hamle<5 & reklamevent==5){
           int width = (int)(getResources().getDisplayMetrics().widthPixels);
           int height = (int)(getResources().getDisplayMetrics().heightPixels*0.34);
           final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Denek.this);
           View mView = getLayoutInflater().inflate(R.layout.kaybettiniz, null);
           Button artıbes = (Button) mView.findViewById(R.id.artıbesbtn);
           Button home = (Button) mView.findViewById(R.id.kayipbtnhome);
           Button kayipagain = (Button) mView.findViewById(R.id.kayiptekraroyna);
           mBuilder.setView(mView);
           mBuilder.setCancelable(false);
           final AlertDialog dialog = mBuilder.create();
           dialog.show();
           height = (int)(getResources().getDisplayMetrics().heightPixels*0.34);
           dialog.getWindow().setLayout(width, height);
           artıbes.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(mRewardedVideoAd.isLoaded()){
                       mRewardedVideoAd.show();
                       reklamevent=5;
                       dialog.cancel();
                   }else{
                       Toast.makeText(Denek.this, "İnternet Bağlantınız ve Hızınızı Kontrol Ediniz", Toast.LENGTH_SHORT).show();
                          dialog.show();
                   }
                   for (Button btn : btnlar) {
                       btn.setEnabled(true);
                   }

               }
           });
           home.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (reklamgelsinmi()){
                       showGecisReklam();
                   }
                   Intent i2 = new Intent(Denek.this, oyunyolu.class);
                   startActivity(i2);
               }
           });
           kayipagain.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
                   table.removeAllViews();
                       /* ses.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                ses.start();
                            }
                        });
                        ses.start();*/
                   levelsorgu();
                   dialog.cancel();
                   if (reklamgelsinmi()){
                       showGecisReklam();
                   }
               }
           });
       }
        kazan();
        reklamevent=0;
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        if(reklamevent==1){
            paraveritabani para=new paraveritabani(Denek.this);
            para.paraArttir(3);
            abanos.setText(String.valueOf(para.paraGoster()));
            abanozgelsinmi=false;
        }
        else if(reklamevent==3){
            hamle=hamle+3;
            hamletext.setText(String.valueOf(hamle));
            ekhamlegelsinmi=false;
        }
        else if (reklamevent==5){
            hamle += 5;
            hamletext.setText(String.valueOf(hamle));

        }


    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
       // Toast.makeText(Denek.this, "Hatalı", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted() {


    }




}
