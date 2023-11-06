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

public class custom_view_custom_plan_budget_and_accept extends BaseAdapter {
    String[]customplanphoto,description,cuslanid,status;

    private Context context;

    public custom_view_custom_plan_budget_and_accept(Context appcontext,String[]ccustomplanphoto,String[]description,String[]cusplainid)
    {
        this.context=appcontext;
        this.customplanphoto=ccustomplanphoto;
        this.description=description;

        this.cuslanid=cusplainid;
//        this.bookingpayment=bookingpayment;
//        this.totalamount=totalamount;



    }
    @Override
    public int getCount() {

        return customplanphoto.length;
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
            gridView=inflator.inflate(R.layout.custom_view_custom_plan_budget_and_accept,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.description3);
        TextView tv2=(TextView)gridView.findViewById(R.id.archbudget);
        ImageView im=(ImageView) gridView.findViewById(R.id.customplanphoto);
        tv2.setVisibility(View.INVISIBLE);
        Button btbuy=(Button) gridView.findViewById(R.id.Budgets);

        btbuy.setTag(i);

        btbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos= (int) view.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("cusplanid",cuslanid[pos]);
                ed.commit();

                Intent ii = new Intent(context, View_budgets_and_accept.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);


            }
        });

//        TextView tv4=(TextView)gridView.findViewById(R.id.bookingpayment3);
//        TextView tv5=(TextView)gridView.findViewById(R.id.totalamount);


        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
//        tv3.setTextColor(Color.BLACK);
//        tv4.setTextColor(Color.BLACK);
//        tv5.setTextColor(Color.BLACK);


        tv1.setText(description[i]);
//        tv2.setText(archbudget[i]);
//        tv3.setText(materialsused[i]);
//        tv4.setText(bookingpayment[i]);
//        tv5.setText(totalamount[i]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+customplanphoto[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}








