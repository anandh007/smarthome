package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
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

public class view_design_booking_history extends AppCompatActivity {
    ListView lv;
    String[] bookingtime,bookingdate,bookingstatus,totalamount,designnname,designimage,price,materialsused,intername,intercontact,designid,interlid;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_design_booking_history);
        lv=(ListView)findViewById(R.id.designbookinghistory);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_view_inter_designs_history";



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
                                bookingstatus=new String[js.length()];
                                totalamount=new String[js.length()];
                                designnname=new String[js.length()];
                                designimage=new String[js.length()];
                                price=new String[js.length()];
                                materialsused=new String[js.length()];
                                intername=new String[js.length()];
                                intercontact=new String[js.length()];
                                designid=new String[js.length()];
                                interlid=new String[js.length()];

                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);

                                    bookingdate[i]=u.getString("booking_date");
                                    bookingtime[i]=u.getString("booking_time");
                                    bookingstatus[i]=u.getString("booking_status");
                                    totalamount[i]=u.getString("total_amount");
                                    designnname[i]=u.getString("design_name");
                                    designimage[i]=u.getString("design_templates");
                                    price[i]=u.getString("design_price");
                                    materialsused[i]=u.getString("materials_used");
                                    intercontact[i]=u.getString("interior_name");
                                    intername[i]=u.getString("interior_phone");
                                    designid[i]=u.getString("design_id");
                                    interlid[i]=u.getString("interior_lid");


                                }

                                // ArrayAdapter<String> adpt=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,name);
                                lv.setAdapter(new custom_view_design_booking_history(getApplicationContext(),bookingdate,bookingtime,bookingstatus,totalamount,designnname,designimage,price,materialsused,intername,intercontact,designid,interlid));
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





