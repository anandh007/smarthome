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

public class View_cart_store extends AppCompatActivity {
    String[]storename,ownername,lid,place,email,phone,image;
    ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart_store);
        ls=(ListView) findViewById(R.id.ls);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000/view_cart_stores";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONObject ks = new JSONObject(response);
                            String status = ks.getString("status");
                            if (status.equalsIgnoreCase("ok")) {


                                JSONArray ja= ks.getJSONArray("data");
                                lid=new String[ja.length()];
                                storename=new String[ja.length()];
                                ownername=new String[ja.length()];
                                place=new String[ja.length()];
                                email=new String[ja.length()];
                                phone=new String[ja.length()];
                                phone=new String[ja.length()];
                                image=new String[ja.length()];

//                              `shop_lid`,`shop_name`,`shop_phone`,`shop_email`,`shop_place`,`shop_district`,
                                for(int i=0;i<ja.length();i++)
                                {
                                    lid[i]= ja.getJSONObject(i).getString("shop_lid");
                                    storename[i]= ja.getJSONObject(i).getString("shop_name");
                                    ownername[i]= ja.getJSONObject(i).getString("shop_district");
                                    place[i]= ja.getJSONObject(i).getString("shop_place");
                                    phone[i]= ja.getJSONObject(i).getString("shop_phone");
                                    email[i]= ja.getJSONObject(i).getString("shop_email");

                                    image[i]= ja.getJSONObject(i).getString("shop_email");


                                }
                                Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
                                ls.setAdapter(new Custom_view_cart_store(getApplicationContext(), lid,storename,place,email,phone,ownername));





                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "not", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "hi"+ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
}