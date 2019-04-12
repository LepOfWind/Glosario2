package com.glind;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.glind.R;

import org.w3c.dom.Text;

public class frase extends AppCompatActivity  {

    Button Español;
    Button Idioma;
    TextView EspañolT;
    TextView EspTit;
    TextView IdiomaT;
    ImageView Imagen;
    TextView idiomamostrar;
    ImageButton img;
    ImageButton img2;
    FrameLayout vista;
    int l;
    Cursor c;

    @Override
    // // Atraves de un cursor se almacenan los datos obtenidos por un metodo de la clase DataBaseManager.
    // Despues se almacenan en un varias variables para poder visualizarse de manera ordenada.
    // Se reciben valores de la clase Listafrases como: Id de la frase  y el lenguaje indigena escogido por el usuario.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frase);
        //----------------------------------------------------------------------------------------- inicio
        Typeface Linotte=Typeface.createFromAsset(getAssets(),"fonts/Linotte-SemiBold.otf");
        Typeface Jellee=Typeface.createFromAsset(getAssets(),"fonts/Jellee-Roman.ttf");

       final Vibrator vi = (Vibrator) frase.this.getSystemService(Context.VIBRATOR_SERVICE); //vibrador


        // Español = (Button) findViewById(R.id.button);
        // Idioma = (Button) findViewById(R.id.button2);
        EspTit=(TextView) findViewById(R.id.textView2);
        EspTit.setTypeface(Jellee);
        EspañolT= (TextView) findViewById(R.id.textView3);
        EspañolT.setTypeface(Linotte);
        idiomamostrar= (TextView) findViewById(R.id.textView4);
        idiomamostrar.setTypeface(Jellee);
        IdiomaT = (TextView) findViewById(R.id.textView5) ;
        IdiomaT.setTypeface(Linotte);
        TextView Fra = (TextView) findViewById(R.id.fra);
        Fra.setTypeface(Linotte.DEFAULT_BOLD);
        //----------------------------------------------------------------------------------------- fin

         EspañolT= (TextView) findViewById(R.id.textView3);
        idiomamostrar= (TextView) findViewById(R.id.textView4);
        IdiomaT = (TextView) findViewById(R.id.textView5) ;
        Imagen = (ImageView) findViewById(R.id.imageView) ;
        img = (ImageButton) findViewById(R.id.imageButton);
        img2 = (ImageButton) findViewById(R.id.imageButton2);

       final String val = getIntent().getExtras().getString("valor");
        int h = getIntent().getIntExtra("valor2",0);
        String a= "",b = "", r="";

        Cursor c;
         final DataBaseManager query = new DataBaseManager(this);
        c = query.Queryfinal(val,h);

        String get = "";
        String español = "",ngabere = "",audio ="" ,audiongo = "",audiogun = "",audioemb = "",imagen = "", guna= "", embera = "";
        if (c!= null && c.moveToFirst()) {

            while (c.isAfterLast() == false) {
                if (h == 1) {
                    español = c.getString(c.getColumnIndex("text_spanish"));
                    ngabere = c.getString(c.getColumnIndex("text_ngabere"));
                    audio = c.getString(c.getColumnIndex("audio_spanish"));
                    audiongo = c.getString(c.getColumnIndex("audio_ngabere"));
                    imagen = c.getString(c.getColumnIndex("picture"));
                    get = c.getString(c.getColumnIndex("phrase_ID"));
                    String uso = c.getString(c.getColumnIndex("most_used"));

                    c.moveToNext();
                } else {
                    if (h == 2) {
                        español = c.getString(c.getColumnIndex("text_spanish"));
                        audio = c.getString(c.getColumnIndex("audio_spanish"));
                        imagen = c.getString(c.getColumnIndex("picture"));
                        guna = c.getString(c.getColumnIndex("text_guna"));
                        audiogun = c.getString(c.getColumnIndex("audio_guna"));
                        c.moveToNext();
                    } else {
                        if (h == 3) {
                            español = c.getString(c.getColumnIndex("text_spanish"));
                            audio = c.getString(c.getColumnIndex("audio_spanish"));
                            imagen = c.getString(c.getColumnIndex("picture"));
                            embera = c.getString(c.getColumnIndex("text_embera"));
                            audioemb = c.getString(c.getColumnIndex("audio_embera"));
                            c.moveToNext();
                        }
                    }
                }
            }
            if (h == 1) {
                c.moveToNext();
                a = ngabere;
                b = audiongo;
                r = "Ngabere";
            } else {
                if (h == 2) {
                    a = guna;
                    b = audiogun;
                    r = "Guna";
                } else {
                    if (h == 3) {
                        a = embera;
                        b = audioemb;
                        r = "Emberá";
                    }
                }
            }
            final String est = get;
         /*   new Thread(new Runnable() {
                public void run() {
                    query.updatequery(est);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(),est,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();*/
            idiomamostrar.setText(r);
        EspañolT.setText(español);
        IdiomaT.setText(a);
            //Concatenacion de un string que contiene el directorio de las imagenes y audios a mostrar.
       String ImagenS = "android.resource://" + this.getPackageName() + imagen;
        Imagen.setImageURI(Uri.parse(ImagenS));
       String EspañolA = "android.resource://" + this.getPackageName() + audio;
        String IdiomaA = "android.resource://" + this.getPackageName() + b;


            AudioManager amanager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
            if(amanager.getRingerMode() == AudioManager.RINGER_MODE_SILENT || amanager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE ){
                Toast toast1 = Toast.makeText(getApplicationContext(), "Verifique que el volumen sea adecuado", Toast.LENGTH_LONG);
                toast1.show();
            }


            //Llamada a Media Player para la reproduccion de los archivos de audio.
          final  MediaPlayer m = new MediaPlayer();

        try { m.setDataSource(this,Uri.parse( EspañolA)); } catch (Exception e) {}
       try { m.prepare(); } catch (Exception e) {
           Toast toast3 = Toast.makeText(getApplicationContext(), "Audio en español no disponible", Toast.LENGTH_LONG);
           toast3.show();
           img.setVisibility(View.INVISIBLE);
       }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vi.vibrate(50); //realiza una vibracion
                m.start();
            }
        });



        final  MediaPlayer m2 = new MediaPlayer();

        try { m2.setDataSource(this,Uri.parse(IdiomaA)); } catch (Exception e) {}
        try { m2.prepare(); } catch (Exception e) {
            Toast toast3 = Toast.makeText(getApplicationContext(), "Audio en "+r+" no disponible", Toast.LENGTH_LONG);
            toast3.show();
            img2.setVisibility(View.INVISIBLE);
        }
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vi.vibrate(50); //realiza una vibracion
                m2.start();
            }
        });

    }


    }
    private void Aviso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyle);
        builder.setTitle("Nota");
        builder.setCancelable(false);
        builder.setMessage("Tome en cuenta que algunas frases no cuentan con su pronunciación en todas las lenguas, pero estamos trabajando para añadirlas.");
        builder.setPositiveButton("Comprendo", null);
        builder.show();
    }

    public void advertencia(View view){
        Aviso();
    }
}

