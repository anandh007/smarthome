package com.example.smarthome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class View_order_histoy_more extends AppCompatActivity {
String []pname,pimage,qty,amount;
ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_histoy_more);
        ls=(ListView) findViewById(R.id.ls);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000/user_view_order_sub";

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


                                JSONArray ja= ks.getJSONArray("data");
                                pname=new String[ja.length()];
                                pimage=new String[ja.length()];
                                qty=new String[ja.length()];
                                amount=new String[ja.length()];
//                                `product_name`,`product_photo`,`total_count`,`product_price`
                                for(int i=0;i<ja.length();i++)
                                {
                                    pname[i]= ja.getJSONObject(i).getString("product_name");
                                    pimage[i]= ja.getJSONObject(i).getString("product_photo");
                                    qty[i]= ja.getJSONObject(i).getString("total_count");
                                    amount[i]= ja.getJSONObject(i).getString("product_price");

                                }

                                ls.setAdapter(new Custom_view_order_more(getApplicationContext(), pname,pimage,qty,amount));





                            }
                            else
                            {

                            }


                        } catch (Exception ex) {

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
                params.put("oid",sh.getString("oid",""));


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
}