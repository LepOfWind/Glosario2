package com.glind;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.glind.R;

import java.util.ArrayList;

import static android.view.KeyEvent.KEYCODE_BACK;

public class Categorias extends AppCompatActivity {
        ListView Categorias;
        TextView Secciones;
        Cursor c;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_categorias);

        final Typeface Linotte=Typeface.createFromAsset(getAssets(),"fonts/Linotte-SemiBold.otf");//-----------inicio
        Typeface Jellee=Typeface.createFromAsset(getAssets(),"fonts/Jellee-Roman.ttf");
        Secciones = (TextView) findViewById(R.id.secc);
        Secciones.setTypeface(Linotte.DEFAULT_BOLD);
        Categorias = (ListView) findViewById (R.id.lista);//----------------------------------------------------fin

        Intent intent = getIntent();
        Bundle get = intent.getExtras();
        final int ValorIdioma = intent.getIntExtra(MainActivity.d, 0);
       DataBaseManager query = new DataBaseManager(this);
        c = query.Query();
        c.moveToFirst();
        while(c.isAfterLast() == false){
            c.moveToNext();
        }
          ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c, new String[]{"section_name"}, new int[] {android.R.id.text1},0) {
              public View getView(int position, View convertView, ViewGroup parent){
                  View view = super.getView(position, convertView, parent);
                  TextView tv = (TextView) view.findViewById(android.R.id.text1);

                  tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                  tv.setTextColor(Color.parseColor("#5e5e5e"));
                  tv.setTypeface(Linotte);
                  return view;
              }
          };

              Categorias.setAdapter(adapter);
        Categorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               int a = position;
                Intent myIntent = new Intent(view.getContext(), Listafrases.class);
                String j = c.getString(c.getColumnIndex("section_ID"));
                myIntent.putExtra("valor", j);
                myIntent.putExtra("valor2", ValorIdioma);
                startActivity(myIntent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }
        } );


    }

}
