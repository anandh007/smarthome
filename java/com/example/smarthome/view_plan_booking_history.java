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

public class view_plan_booking_history extends AppCompatActivity {
    ListView lv4;
    String[] bookingdate,bookingtime,bookingpayment,description,planphoto,amount,archname,archcontact,planid,arclid,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_plan_booking_history);
        lv4=(ListView) findViewById(R.id.planbookinghistory);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_view_arch_paln_history";



        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("users");
                                bookingdate=new String[js.length()];
                                bookingtime=new String[js.length()];
                                bookingpayment=new String[js.length()];
                                description=new String[js.length()];
                                planphoto=new String[js.length()];
                                amount=new String[js.length()];
                                archcontact=new String[js.length()];
                                archname=new String[js.length()];
                                planid=new String[js.length()];
                                arclid=new String[js.length()];
                                status=new String[js.length()];

                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);

                                    bookingdate[i]=u.getString("booking_date");
                                    bookingtime[i]=u.getString("booking_time");
                                    bookingpayment[i]=u.getString("booking_payment");
                                    description[i]=u.getString("description");
                                    planphoto[i]=u.getString("plan");
                                    amount[i]=u.getString("amount");
                                    archname[i]=u.getString("arch_name");
                                    archcontact[i]=u.getString("arch_phone");
                                    planid[i]=u.getString("plan_id");
                                    arclid[i]=u.getString("arch_lid");
                                    status[i]=u.getString("booking_status");



                                }
//                                for(int i=0;i<js1.length();i++)
//                                {
//                                    JSONObject u=js1.getJSONObject(i);
//                                    rating[i]=u.getString("rating");
//
//                                }

                                // ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
                                lv4.setAdapter(new custom_view_plan_booking_history(getApplicationContext(),bookingdate,bookingtime,bookingpayment,description,planphoto,amount,archname,archcontact,planid,arclid,status));
                                // l1.setAdapter(new Custom(getApplicationContext(),gamecode,name,type,description,image,amount,status));
                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                String id=sh.getString("lid","");
                params.put("lid",id);
//                params.put("mac",maclis);

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




