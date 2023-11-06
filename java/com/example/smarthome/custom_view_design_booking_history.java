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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_design_booking_history extends BaseAdapter {
    String[]bookingtime,bookingdate,bookingstatus,totalamount,designnname,designimage,price,materialsused,intername,intercontact,designid,interlid;

    private Context context;

    public custom_view_design_booking_history(Context appcontext,String[]bookingtime,String[]bookingdate,String[]bookingstatus,String[]totalamount,String[]designname,String[]designimage,String[]price,String[]materialsused,String[]intername,String[]intercontact,String[]designid,String[]interlid)
    {
        this.context=appcontext;
        this.bookingtime=bookingtime;
        this.bookingdate=bookingdate;
        this.bookingstatus=bookingstatus;
        this.totalamount=totalamount;
        this.designnname=designname;
        this.designimage=designimage;
        this.price=price;
        this.materialsused=materialsused;
        this.intername=intername;
        this.intercontact=intercontact;
        this.designid=designid;
        this.interlid=interlid;



    }
    @Override
    public int getCount() {

        return interlid.length;
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
            gridView=inflator.inflate(R.layout.custom_view_design_booking_history,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.bookingtime);
        TextView tv2=(TextView)gridView.findViewById(R.id.bookingdate2);

        TextView tv3=(TextView)gridView.findViewById(R.id.bookingstatus);
        TextView tv5=(TextView)gridView.findViewById(R.id.totalamount);

        TextView tv11=(TextView)gridView.findViewById(R.id.designname);
        TextView tv22=(TextView)gridView.findViewById(R.id.price);
        ImageView im=(ImageView) gridView.findViewById(R.id.designimage);
        TextView tv33=(TextView)gridView.findViewById(R.id.materialsused);
        TextView tv4=(TextView)gridView.findViewById(R.id.desiname);
        TextView tv55=(TextView)gridView.findViewById(R.id.desicontact);
        TextView bkp=(TextView)gridView.findViewById(R.id.bookingpayment3);
        bkp.setVisibility(View.INVISIBLE);
        Button btbuy=(Button) gridView.findViewById(R.id.Budgets);
        btbuy.setTag(i);

        btbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos= (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("toid",interlid[pos]);
                ed.commit();

                Intent ii = new Intent(context, Test.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);


            }
        });




        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv11.setTextColor(Color.BLACK);
        tv22.setTextColor(Color.BLACK);
        tv33.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv55.setTextColor(Color.BLACK);



        tv1.setText(bookingtime[i]);
        tv2.setText(bookingdate[i]);
        tv3.setText(bookingstatus[i]);

        tv5.setText(totalamount[i]+"Rs/-");


        tv11.setText(designnname[i]);
        tv22.setText(price[i]);
        tv33.setText("Material Used :"+materialsused[i]);
        tv4.setText(intername[i]);
        tv55.setText(intercontact[i]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+designimage[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}



