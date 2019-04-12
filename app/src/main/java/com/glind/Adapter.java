package com.glind;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.glind.R;

import java.util.HashMap;
import java.util.List;

public class Adapter extends BaseExpandableListAdapter{
    private Context ctx;
    private HashMap<String, List<String>> Category;
    private List<String> List;

    public Adapter(Context ctx, HashMap<String, List<String>> S_Category, List<String> S_List )
    {
        this.ctx = ctx;
        this.Category = S_Category;
        this.List = S_List;

    }

    @Override
    public Object getChild(int parent, int child) {


        return Category.get(List.get(parent)).get(child);
    }

    @Override
    public long getChildId(int parent, int child) {
        // TODO Auto-generated method stub
        return child;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertview, ViewGroup parentview)
    {
        String child_title =  (String) getChild(parent, child);
        if(convertview == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.child_layout, parentview,false);
        }

        TextView child_textview = (TextView) convertview.findViewById(R.id.child_txt);
        child_textview.setText(child_title);
        child_textview.setTextSize(12);


        return convertview;
    }

    @Override
    public int getChildrenCount(int arg0) {

        return Category.get(List.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0) {
        // TODO Auto-generated method stub
        return List.get(arg0);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return List.size();
    }

    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertview, ViewGroup parentview) {

        String group_title = (String) getGroup(parent);
        if(convertview == null)
        {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.parent_layout, parentview,false);
        }
        TextView parent_textview = (TextView) convertview.findViewById(R.id.parent_txt);
        //parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setTextSize(11);
        parent_textview.setText(group_title);
        return convertview;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}