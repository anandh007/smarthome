package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class viewreply extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        Intent ins=new Intent(getApplicationContext(),Home.class);
        startActivity(ins);
    }

    String [] c_id,complaint,reply,date,status;
    ListView lvs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreply);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ins= new Intent(getApplicationContext(),send_complaint.class);
                startActivity(ins);
            }
        });

        lvs=(ListView) findViewById(R.id.lvs);



        final SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String hu=sh.getString("ip","");
        final String url = "http://" + hu + ":5000/viewcompalintsandroid";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


//                                 response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                JSONArray js= jsonObj.getJSONArray("data");

                                c_id=new String[js.length()];;
                                complaint=new String[js.length()];
                                reply=new String[js.length()];
                                date=new String[js.length()];
                                status=new String[js.length()];


//`date`,`user_lid`,`complaint`,`reply`,`status`

                                for(int i=0;i<js.length();i++)
                                {
                                    JSONObject u=js.getJSONObject(i);
                                    c_id[i]=u.getString("complaint_id");
                                    complaint[i]=u.getString("complaint");
                                    reply[i]=u.getString("reply");
                                    date[i]=u.getString("date");
                                    status[i]=u.getString("status");
                                }

                                lvs.setAdapter(new custom_viewreply(getApplicationContext(),c_id,complaint,reply,date,status));

                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
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
                params.put("lid", sh.getString("lid",""));
//                params.put("cid", sh.getString("selcid",""));



                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);    }
}