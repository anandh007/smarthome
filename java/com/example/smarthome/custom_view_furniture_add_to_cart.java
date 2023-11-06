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

public class custom_view_furniture_add_to_cart extends BaseAdapter {

    String[]name,material,price,image,pid;
    private Context context;

    public custom_view_furniture_add_to_cart(Context appcontext,String[]planphoto,String[]amount,String[]description,String[]image,String[]pid)
    {
        this.context=appcontext;
        this.name=description;
        this.material=planphoto;
        this.price=amount;
        this.image=image;
        this.pid=pid;



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
            gridView=inflator.inflate(R.layout.custom_view_furniture_add_to_cart,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.furname);
        TextView tv2=(TextView)gridView.findViewById(R.id.materials);
        ImageView im=(ImageView) gridView.findViewById(R.id.furnitureimage);
        TextView tv3=(TextView) gridView.findViewById(R.id.furprice);
        Button addbtn=(Button) gridView.findViewById(R.id.addbtn);
        addbtn.setTag(i);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();

                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("image",image[pos]);
                ed.putString("name",name[pos]);
                ed.putString("price",price[pos]);
                ed.putString("pid",pid[pos]);
                ed.commit();

                Intent ii = new Intent(context, add_to_cart.class);
                ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ii);



            }
        });

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(material[i]);
        tv3.setText(price[i]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+image[i];


        Picasso.with(context).load(url). into(im);

        return gridView;
    }
}





