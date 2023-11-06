package com.example.smarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Custom_view_cartproduct extends BaseAdapter {
    String[]svid,pname,pquty,pimg;

    private final Context context;
    public Custom_view_cartproduct(Context appcontext, String[] svid, String[]pname, String[]pquty, String[]pimg) {

        this.context = appcontext;
        this.svid = svid;
        this.pname = pname;
        this.pquty = pquty;
        this.pimg = pimg;

    }


    @Override
    public int getCount() {
        return svid.length;
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
            gridView = inflator.inflate(R.layout.custom_view_cart_product, null);

        } else {
            gridView = (View) view;

        }
        TextView tname = (TextView) gridView.findViewById(R.id.cpname);
        TextView tqty = (TextView) gridView.findViewById(R.id.cpqty);

        ImageView img3 = (ImageView) gridView.findViewById(R.id.imageView8);


        ImageView bts = (ImageView) gridView.findViewById(R.id.imgdelete);
        bts.setTag(i);
        bts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor ed = sh.edit();
                ed.putString("svid",svid[pos]);
                ed.commit();





                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/user_delete_cart";


                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                                        Intent ins = new Intent(context, View_cart_product.class);
                                        ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(ins);


                                    }


                                    // }
                                    else {
                                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();


                        params.put("svid", svid[i]);

                        return params;
                    }
                };

                int MY_SOCKET_TIMEOUT_MS = 100000;

                postRequest.setRetryPolicy(new DefaultRetryPolicy(
                        MY_SOCKET_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);



            }
        });


        tname.setText(pname[i]);
        tqty.setText(pquty[i]);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + pimg[i];

        Picasso.with(context).load(url).into(img3);


        return gridView;
    }

}
