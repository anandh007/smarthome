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

public class custom_view_plan_booking_history extends BaseAdapter {
    String[]bookingdate,bookingtime,bookingpayment,description,planphoto,amount,archname,archcontact,planid,arclid,status;;
    private Context context;

    public custom_view_plan_booking_history(Context appcontext,String[]bookingdate,String[]bookingtime,String[]bookingpayment,String[]description,String[]planphoto,String[]amount,String[]archname,String[]archcontact,String[]planid,String[]arclid,String[]status)
    {
        this.context=appcontext;
        this.bookingdate=bookingdate;
        this.bookingtime=bookingtime;
        this.bookingpayment=bookingpayment;
        this.context=appcontext;
        this.description=description;
        this.planphoto=planphoto;
        this.amount=amount;
        this.archcontact=archcontact;
        this.archname=archname;
        this.planid=planid;
        this.arclid=arclid;
        this.status=status;



    }
    @Override
    public int getCount() {

        return planid.length;
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
            gridView=inflator.inflate(R.layout.custom_view_plan_booking_history,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.bookingdate1);
        TextView tv2=(TextView)gridView.findViewById(R.id.bookingtime1);
        TextView tv3=(TextView)gridView.findViewById(R.id.bookingpayment);
        TextView tv11=(TextView)gridView.findViewById(R.id.plandescription);
        TextView tv22=(TextView)gridView.findViewById(R.id.planamount);
        TextView tvname=(TextView)gridView.findViewById(R.id.arcname);
        TextView tvphone=(TextView)gridView.findViewById(R.id.archconatct);
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView2);
        Button btbuy=(Button) gridView.findViewById(R.id.btpalnbuy);

        btbuy.setTag(i);

        btbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos= (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("toid",arclid[pos]);
                ed.putString("productid",planid[pos]);
                ed.putString("amount",amount[pos]);
                ed.putString("type","plan");
                ed.commit();

                Intent ii = new Intent(context, Test.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);


            }
        });



        tv11.setTextColor(Color.BLACK);
        tv22.setTextColor(Color.BLACK);
        tvname.setTextColor(Color.BLACK);
        tvphone.setTextColor(Color.BLACK);
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv11.setText(description[i]);
        tv22.setText(amount[i]);
        tvname.setText(archname[i]);
        tv1.setText(bookingdate[i]);
        tv2.setText(bookingtime[i]);
        tv3.setText(bookingpayment[i]+"\n\n"+status[i]);
        tvphone.setText(archcontact[i]);




        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+planphoto[i];


        Picasso.with(context).load(url). into(im);


        return gridView;
    }
}



