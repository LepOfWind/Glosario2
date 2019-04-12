package com.glind;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;

import com.glind.R;

import java.util.ArrayList;

public class Listafrases extends AppCompatActivity  {
    ListView Listafrases;
    SearchView  mSearchView;
    Cursor c;
    ArrayList<String> names = new ArrayList<String>();
    @Override
    // Atraves de un cursor se almacenan los datos obtenidos por un metodo de la clase DataBaseManager.
    // Despues se almacenan en un listview para poder visualizarse de manera ordenada.
    // Se reciben valores de la clase Categorias como: el numero de sección o capitulo y el lenguaje indigena escogido por el usuario.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listafrases);

        final Typeface Linotte=Typeface.createFromAsset(getAssets(),"fonts/Linotte-SemiBold.otf");//--------------- agregado
        Typeface Jellee=Typeface.createFromAsset(getAssets(),"fonts/Jellee-Roman.ttf");
        TextView Lista = (TextView) findViewById(R.id.list);
        Lista.setTypeface(Linotte.DEFAULT_BOLD);
//--------------------------------------------------------------------------------------------------------------------------

        Listafrases = (ListView) findViewById(R.id.listView2);
        mSearchView = (SearchView) findViewById(R.id.searchView1);
        String val = getIntent().getExtras().getString("valor");
        final int y= getIntent().getIntExtra("valor2", 0);
        DataBaseManager query = new DataBaseManager(this);
        c = query.Queryfrases(val);
       //  String addData[] = new String[100];

        c.moveToFirst();
        while(c.isAfterLast() == false){
            names.add(c.getString(c.getColumnIndex("text_spanish")));
           // addData[c.getPosition()] = c.getString(c.getColumnIndex("text_spanish"));
            c.moveToNext();
        }
        /*final ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, c, new String[]{"text_spanish"}, new int[] {android.R.id.text1},0) {

            public View getView(int position, View convertView, ViewGroup parent) {
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text size 25 dip for ListView each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                tv.setTextColor(Color.parseColor("#5e5e5e"));
                tv.setTypeface(Linotte);

                // Return the view
                return view;
            }
        };
*/
       // final ArrayAdapter<String> labels = new ArrayAdapter<String>(getBaseContext(),
        //        android.R.layout.simple_list_item_1, names);

       final ArrayAdapter<String> labels = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, resol()){
           public View getView(int position, View convertView, ViewGroup parent) {

               View view = super.getView(position, convertView, parent);

               TextView tv = (TextView) view.findViewById(android.R.id.text1);

               tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
               tv.setTextColor(Color.parseColor("#5e5e5e"));
               tv.setTypeface(Linotte);

               return view;
           }
       };


        Listafrases.setAdapter(labels);
        Listafrases.setTextFilterEnabled(true);
        //Listafrases.setAdapter(adapter);
        // setupSearchView();


        mSearchView.setIconifiedByDefault(false);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("Buscar");

        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView searchText = (TextView) mSearchView.findViewById(id);
        searchText.setTypeface(Linotte);
        searchText.setTextColor(Color.parseColor("#5e5e5e"));

        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                labels.getFilter().filter(newText);
                return false;
            }
        });


        Listafrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a = position;
                Intent myIntent = new Intent(view.getContext(), frase.class);
                String j = labels.getItem(position);
             //   String j = c.getString(c.getColumnIndex("text_spanish"));
                myIntent.putExtra("valor", j);
                myIntent.putExtra("valor2", y);
                startActivity(myIntent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }});


        }

    private String[] resol (){
       return  names.toArray(new String[names.size()]);
    }

    }


