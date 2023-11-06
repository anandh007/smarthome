package com.example.smarthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_order_more extends BaseAdapter {

    String[] pname,pimage,qty,amount;

    private final Context context;
    public Custom_view_order_more(Context appcontext, String[] pname, String[]pimage, String[]qty, String[]amount) {

        this.context = appcontext;
        this.pname = pname;
        this.pimage = pimage;
        this.qty = qty;
        this.amount = amount;


    }


    @Override
    public int getCount() {
        return qty.length;
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
            gridView = inflator.inflate(R.layout.custom_view_oreder_more, null);

        } else {
            gridView = (View) view;

        }
        TextView tname = (TextView) gridView.findViewById(R.id.opname);
        TextView tqty = (TextView) gridView.findViewById(R.id.opqty);
        TextView tprice = (TextView) gridView.findViewById(R.id.opprice);
        ImageView img3 = (ImageView) gridView.findViewById(R.id.opimg);



        tname.setText(pname[i]);
        tqty.setText("Total Quantity :"+qty[i]);
        tprice.setText("Amount :"+amount[i]);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + pimage[i];

        Picasso.with(context).load(url).into(img3);


        return gridView;
    }
}
