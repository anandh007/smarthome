package com.example.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class custom_view_nearest_shop  extends BaseAdapter {
    private final Context context;
    String[]shoplid,name,place,phone,email;

    public custom_view_nearest_shop(Context applicationContext, String[] shoplid, String[] name, String[] place, String[] phone, String[] email) {
    this.context=applicationContext;
    this.shoplid=shoplid;
    this.name=name;
    this.place=place;
    this.phone=phone;
    this.email=email;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custom_view_nearest_shop,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tvshopname=(TextView)gridView.findViewById(R.id.medshopname);
        TextView tvshopemail=(TextView)gridView.findViewById(R.id.medshopemail);
        TextView tvshopphone=(TextView)gridView.findViewById(R.id.medshopphone);
        TextView tvshopwebsite=(TextView)gridView.findViewById(R.id.medshopwebsite);
        Button im=(Button) gridView.findViewById(R.id.btmedicin);
        im.setTag(i);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("shoplid", shoplid[i]);
                ed.commit();


                Intent i = new Intent(context,view_furniture_and_add_to_cart.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        Button btrev=(Button) gridView.findViewById(R.id.btreview);
        btrev.setTag(i);
        btrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("shoplid", shoplid[i]);
                ed.commit();


                Intent i = new Intent(context,View_Store_Rating.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        tvshopname.setTextColor(Color.BLACK);


        tvshopname.setText(name[i]);
        tvshopemail.setText(email[i]);
        tvshopphone.setText(phone[i]);
        tvshopwebsite.setText(place[i]);


        return gridView;
    }
}
