package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_cart_product extends AppCompatActivity implements View.OnClickListener {
    String []svid,pimg,pname,pquty;
    TextView total;
    Button btpurchase;
    ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_product);
        ls=(ListView) findViewById(R.id.ls);
        total=(TextView)findViewById(R.id.totam);
        btpurchase=(Button) findViewById(R.id.btpurchase);
        btpurchase.setOnClickListener(this);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000/view_cart_product";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONObject ks = new JSONObject(response);
                            String status = ks.getString("status");
                            if (status.equalsIgnoreCase("ok")) {

                                String totamt=ks.getString("totalamount");
                                total.setText(totamt);


                                JSONArray ja= ks.getJSONArray("data");
                                svid=new String[ja.length()];
                                pname=new String[ja.length()];
                                pquty=new String[ja.length()];

                                pimg=new String[ja.length()];
                                for(int i=0;i<ja.length();i++)
                                {
                                    svid[i]= ja.getJSONObject(i).getString("cart_id");
                                    pname[i]= ja.getJSONObject(i).getString("product_name");
                                    pquty[i]= ja.getJSONObject(i).getString("total_count");
                                    pimg[i]= ja.getJSONObject(i).getString("product_photo");
                                }
                                ls.setAdapter(new Custom_view_cartproduct(getApplicationContext(),svid,pname,pquty,pimg));
                            }
                            else
                            {

                            }


                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                params.put("slid",sh.getString("storelid",""));
                params.put("lid",sh.getString("lid",""));





                return params;
            }
        };
        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

    }

    @Override
    public void onClick(View view) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("totalamount",total.getText().toString());
        ed.commit();
        startActivity(new Intent(getApplicationContext(),product_cart_payyment.class));
    }
}