package com.example.smarthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_purchase_history extends BaseAdapter {

    String[] productname,productimage,amount,bookingdate;
    private Context context;

    public custom_view_purchase_history(Context appcontext,String[]productname,String[]productimage,String[]bookingdate,String[]amount)
    {
        this.context=appcontext;
        this.productname=productname;
        this.productimage=productimage;
        this.amount=amount;
        this.bookingdate=bookingdate;



    }
    @Override
    public int getCount() {

        return productimage.length;
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
            gridView=inflator.inflate(R.layout.custom_view_purchase_history,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.productname);
        TextView tv2=(TextView)gridView.findViewById(R.id.amount1);
        TextView tv3=(TextView)gridView.findViewById(R.id.bookingdate) ;
        ImageView im=(ImageView) gridView.findViewById(R.id.productimage);


        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(productname[i]);
        tv2.setText(amount[i]);
        tv3.setText(bookingdate[i]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+productimage[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}






