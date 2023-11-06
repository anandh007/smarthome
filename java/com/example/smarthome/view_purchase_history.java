package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
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

public class view_purchase_history extends AppCompatActivity {
    String[]oid,storename,amount,date;
    ListView ls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purchase_history);
        ls=(ListView) findViewById(R.id.ls);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000/user_view_order";

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
                                oid=new String[ja.length()];
                                storename=new String[ja.length()];
                                date=new String[ja.length()];
                                amount=new String[ja.length()];
                                for(int i=0;i<ja.length();i++)
                                {
                                    oid[i]= ja.getJSONObject(i).getString("order_main_id");
                                    storename[i]= ja.getJSONObject(i).getString("shop_name");
                                    date[i]= ja.getJSONObject(i).getString("order_date");
                                    amount[i]= ja.getJSONObject(i).getString("total_amount");

                                }

                                ls.setAdapter(new Custom_view_order_history(getApplicationContext(), oid,storename,date,amount));





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







