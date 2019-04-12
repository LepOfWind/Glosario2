package com.glind;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.glind.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    Spinner spin;
    TextView Español,leng,upda;
    int a;
    String z;
    Button Consulta;
    ImageButton act;
    TextView asd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //-----------------------------------------------------------------------------------------------------inicio
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            Toast.makeText(MainActivity.this, "Bienvenido / Bienvenida", Toast.LENGTH_SHORT).show();
            Aviso();
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
        Toast.makeText(MainActivity.this, "Bienvenido / Bienvenida", Toast.LENGTH_SHORT).show();

        //------------------------------------------------------------------------------------------------------fin
        setContentView(R.layout.activity_main);
        Segundo hilo = new Segundo();
        hilo.execute();
        //------------------------------------------------------------------------------------------------------inicio
        final Typeface Linotte=Typeface.createFromAsset(getAssets(),"fonts/Linotte-SemiBold.otf");
        Typeface Jellee=Typeface.createFromAsset(getAssets(),"fonts/Jellee-Roman.ttf");

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab); //fab button

        leng=(TextView)findViewById(R.id.leng);
        leng.setTypeface(Linotte);
        Español= (TextView) findViewById(R.id.textView7);
        Español.setTypeface(Linotte);
        spin = (Spinner) findViewById(R.id.spinner);
        upda = (TextView) findViewById(R.id.update);
        upda.setTypeface(Linotte);

        //le indicamos al fab button las acciones a realizar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start1();
            }

            private void start1() {
                Intent myIntent = new Intent(MainActivity.this,creditos.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        String[] idiomas = {"Ngäbe", "Guna", "Emberá"};
        Consulta=(Button) findViewById(R.id.button4);
        //act = (ImageButton) findViewById(R.id.sync1) ;
      //  asd = (TextView) findViewById(R.id.prueba);
        Consulta.setTypeface(Jellee);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_idioma, idiomas){

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Linotte);
                return v;
            }
        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
            View v =super.getDropDownView(position, convertView, parent);
            ((TextView) v).setTypeface(Linotte);
            ((TextView) v).setTextColor(Color.parseColor("#5e5e5e"));
            return v;
        }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        //----------------------------------------------------------------------------------------------------------fin
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                a = position;
                z = (String) spin.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizacion hilo1 = new Actualizacion();
                hilo1.execute();

            }
        });*/
    }

    private void Aviso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogStyle);
        builder.setTitle("¡Aviso Importante!");
        builder.setCancelable(false);
        builder.setMessage("Puede que la escritura y pronunciación de las frases cambien dentro de una misma región y no coincidan con las presentadas en esta aplicación.");
        builder.setPositiveButton("Entiendo", null);
        builder.show();
    }

    public final static String d = "";
    public void start(View view) {
        int s;
        Intent myIntent = new Intent(MainActivity.this, Categorias.class);
        switch (z) {
            case "Ngäbe":
                s=1;
                myIntent.putExtra(d, s);
                startActivity(myIntent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case "Guna":
                s=2;
                myIntent.putExtra(d, s);
                startActivity(myIntent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
            case "Emberá":
                s=3;
                myIntent.putExtra(d, s);
                startActivity(myIntent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }

    }
    public void prueba (View view){
        Intent myIntent = new Intent(MainActivity.this, frase.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }
    /*
    public void start1(View view) {
        Intent myIntent = new Intent(MainActivity.this,creditos.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }*/
    public void advertencia(View view){
        Aviso();
    }
    private class Segundo extends AsyncTask<Void,Integer,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... params) {
            DataBaseManager manager = new DataBaseManager(getBaseContext());
            manager.insertar();
            manager.insertardatos();

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
    private class Actualizacion extends AsyncTask<String,Integer,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection con = null;
            BufferedReader reader = null;
            String alo= "Nada";
            try {
                URL url = new URL("http://192.168.1.112/query.php");
                con = (HttpURLConnection) url.openConnection();
                con.connect();
                 alo = Integer.toString(con.getResponseCode());
               // if (con.getResponseCode() == con.HTTP_OK) {
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = ("");

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);

                    }
                    String Json = buffer.toString();

                    JSONObject Obj = new JSONObject(Json);
                    JSONArray obj2 = Obj.getJSONArray("frase");
                    JSONObject obj3 = obj2.getJSONObject(0);
                    String des = obj3.getString("url");
                    return des;


                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(JSONException e){
                    e.printStackTrace();
                }
                finally{
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                return alo;

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // if (s == "No"){
           //     asd.setText("Sin Conexión");
           // }
           // else{
         //   DataBaseManager query = new DataBaseManager(getBaseContext());
         //   query.act(s);
            asd.setText(s);}
       // }


    }
}




