package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class add_to_cart extends AppCompatActivity implements View.OnClickListener {
    TextView tvname;
    ImageView img6;
    EditText ed1;
    Button btadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        tvname=(TextView) findViewById(R.id.pname);
        img6=(ImageView) findViewById(R.id.imageView6);
        ed1=(EditText) findViewById(R.id.edqty);
        btadd=(Button) findViewById(R.id.btcart);
        btadd.setOnClickListener(this);


        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String na=sh.getString("name","");
        String price=sh.getString("price","");
        tvname.setText(na+"\n\n"+price+"rs/-");

        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + sh.getString("image","");

        Picasso.with(getApplicationContext()).load(url).into(img6);
    }

    @Override
    public void onClick(View view) {
        String qty=ed1.getText().toString();
        SharedPreferences sh2= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//        int totalqty= Integer.parseInt(sh2.getString("qty",""));
//        int chooseqty= Integer.parseInt(qty);
        if(qty.length()==0){
            ed1.setError("Missing.....");
        }

        else {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/add_to_cart";


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

                                    Toast.makeText(add_to_cart.this, "Added", Toast.LENGTH_SHORT).show();

                                    Intent ins = new Intent(getApplicationContext(), view_furniture_and_add_to_cart.class);
                                    startActivity(ins);


                                }


                                // }
                                else {
                                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
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


                    params.put("lid", sh.getString("lid", ""));
                    params.put("qty", qty);
                    params.put("pid", sh.getString("pid", ""));
                    params.put("slid", sh.getString("shoplid", ""));

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
    }

}
