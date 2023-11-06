package com.example.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Custom_view_order_history extends BaseAdapter {
    private final Context context;
    String[] oid,storename,date,amount;


    public Custom_view_order_history(Context appcontext, String[] oid, String[] storename, String[] date, String[] amount) {

        this.context = appcontext;
        this.oid = oid;
        this.storename = storename;
        this.date = date;
        this.amount = amount;



    }


    @Override
    public int getCount() {
        return oid.length;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_view_oreder_history, null);

        } else {
            gridView = (View) view;

        }
        TextView tname = (TextView) gridView.findViewById(R.id.storenm);

        TextView tdate = (TextView) gridView.findViewById(R.id.orderdate);
        TextView tamount = (TextView) gridView.findViewById(R.id.orderamount);


        Button bts = (Button) gridView.findViewById(R.id.btordermore);
        bts.setTag(i);
        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("oid", oid[pos]);
                ed.commit();

                Intent ins = new Intent(context, View_order_histoy_more.class);
                ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ins);

            }
        });


        tname.setText(storename[i]);
        tdate.setText(date[i]);
        tamount.setText(amount[i]+"rs/-");

        return gridView;

    }
}