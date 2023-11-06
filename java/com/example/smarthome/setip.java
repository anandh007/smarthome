package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class setip extends AppCompatActivity implements View.OnClickListener {
    EditText et;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setip);
        et=(EditText) findViewById(R.id.ipedittext);
        et.setText("192.168.43.120");
        btn=(Button) findViewById(R.id.ipbutton);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (et.getText().toString().length() == 0) {
            et.setError("Missing");
        } else {


            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", et.getText().toString());
            ed.commit();

            Intent ii = new Intent(getApplicationContext(), login.class);
            startActivity(ii);

        }
    }
}