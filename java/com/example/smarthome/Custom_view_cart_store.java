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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Custom_view_cart_store extends BaseAdapter {
    private final Context context;
    String[] lid, storename, place, email, phone, image, ownername;


    public Custom_view_cart_store(Context appcontext, String[] lid, String[] storename, String[] place, String[] email, String[] phone, String[] ownername) {

        this.context = appcontext;
        this.lid = lid;
        this.storename = storename;
        this.place = place;

        this.ownername = ownername;
        this.email = email;
        this.phone = phone;


    }


    @Override
    public int getCount() {
        return lid.length;
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
            gridView = inflator.inflate(R.layout.custom_view_cart_stores, null);

        } else {
            gridView = (View) view;

        }
        TextView tname = (TextView) gridView.findViewById(R.id.store);
        TextView towner = (TextView) gridView.findViewById(R.id.owner);
        TextView tplace = (TextView) gridView.findViewById(R.id.place);
        TextView temail = (TextView) gridView.findViewById(R.id.email);
        TextView tphone = (TextView) gridView.findViewById(R.id.phone);
        ImageView img3 = (ImageView) gridView.findViewById(R.id.imageView3);


        Button bts = (Button) gridView.findViewById(R.id.btproduct);
        bts.setTag(i);
        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("storelid", lid[pos]);
                ed.commit();

                Intent ins = new Intent(context, View_cart_product.class);
                ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ins);

            }
        });


        tname.setText(storename[i]);
        towner.setText(ownername[i]);
        tphone.setText(phone[i]);
        temail.setText(email[i]);
        tplace.setText(place[i]);

//        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
//        String ip = sh.getString("ip", "");
//        String url = "http://" + ip + ":5000" + image[i];
//
//        Picasso.with(context).load(url).into(img3);


        return gridView;

    }
}
