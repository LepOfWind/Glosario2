package com.glind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.glind.R;

public class creditos extends AppCompatActivity  {
    HashMap<String, List<String>> S_category;
    List<String> S_list;
    ExpandableListView Exp_list;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        final Typeface Jellee=Typeface.createFromAsset(getAssets(),"fonts/Jellee-Roman.ttf");//---------------
        final Typeface Linotte=Typeface.createFromAsset(getAssets(),"fonts/Linotte-SemiBold.otf");
        TextView Cred = (TextView) findViewById(R.id.credi);
        Cred.setTypeface(Linotte.DEFAULT_BOLD);

        ExpandableListView Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        S_category = DataProvider.getInfo();
        final ArrayList<String> S_list = new ArrayList<String>(S_category.keySet());

        Adapter adapter = new Adapter(this, S_category, S_list){
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                ViewGroup tmp = (ViewGroup)super.getGroupView(groupPosition, isExpanded, convertView, parent);
                TextView tit = (TextView) tmp.findViewById(R.id.parent_txt);
                tit.setTypeface(Linotte);
                tit.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
                //tit.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);

                return tmp;
            }

        };
        Exp_list.setAdapter(adapter);
    }


}
