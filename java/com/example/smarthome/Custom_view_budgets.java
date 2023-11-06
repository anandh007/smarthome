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

public class Custom_view_budgets extends BaseAdapter {
    String[]archname,archcontact,budget,bid,status;
    private Context context;

    public Custom_view_budgets(Context appcontext,String[]archname,String[]archcontact,String[]budget,String[]bid,String[]status)
    {
        this.context=appcontext;
        this.archname=archname;
        this.archcontact=archcontact;

        this.budget=budget;
        this.bid=bid;
        this.status=status;



    }
    @Override
    public int getCount() {

        return archname.length;
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
            gridView=inflator.inflate(R.layout.custom_view_budget,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.barch_name);
        TextView tv2=(TextView)gridView.findViewById(R.id.barch_conttact);
        TextView tvbudget=(TextView)gridView.findViewById(R.id.b_amount);


        Button btbuy=(Button) gridView.findViewById(R.id.btstatus);

        if(status[i].equalsIgnoreCase("pending")){
            btbuy.setEnabled(true);
        }
        else {
            btbuy.setEnabled(false);
            btbuy.setText(status[i]);
        }

        btbuy.setTag(i);

        btbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos= (int) view.getTag();
//                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
//                SharedPreferences.Editor ed = sh.edit();
//                ed.putString("cusplanid",cuslanid[pos]);
//                ed.commit();
//



                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                String hu = sh.getString("ip", "");
                String url = "http://" + hu + ":5000/and_view_custom_plan_budgets_accept";


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
                                        Toast.makeText(context, "Accceped", Toast.LENGTH_SHORT).show();
                                        Intent ii = new Intent(context, View_budgets_and_accept.class);
                                        ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(ii);



                                    }

                                    // }
                                    else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
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


                        params.put("bid", bid[pos]);
                        params.put("cusplid", sh.getString("cusplanid",""));

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

//        TextView tv4=(TextView)gridView.findViewById(R.id.bookingpayment3);
//        TextView tv5=(TextView)gridView.findViewById(R.id.totalamount);


        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tvbudget.setTextColor(Color.BLACK);
//        tv3.setTextColor(Color.BLACK);
//        tv4.setTextColor(Color.BLACK);
//        tv5.setTextColor(Color.BLACK);


        tv1.setText(archname[i]);
        tv2.setText(archcontact[i]);
        tvbudget.setText(budget[i]);
//        tv3.setText(materialsused[i]);
//        tv4.setText(bookingpayment[i]);
//        tv5.setText(totalamount[i]);





        return gridView;
    }
}
