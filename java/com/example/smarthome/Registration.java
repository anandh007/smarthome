package com.example.smarthome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9;
    ImageView iv;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ed1=(EditText) findViewById(R.id.username2);
        ed2=(EditText) findViewById(R.id.phone);
        ed3=(EditText) findViewById(R.id.email);
        ed4=(EditText) findViewById(R.id.place);
        ed5=(EditText) findViewById(R.id.district);
        ed6=(EditText) findViewById(R.id.post);
        ed7=(EditText) findViewById(R.id.pin1);
        ed8=(EditText) findViewById(R.id.password);
        ed9=(EditText) findViewById(R.id.confirmpassword);
        iv=(ImageView) findViewById(R.id.userphoto);
        btn=(Button) findViewById(R.id.register);
        btn.setOnClickListener(this);
        iv.setOnClickListener(this);

    }
    String path, atype, fname, attach, attatch1;
    byte[] byteArray = null;

    void showfilechooser(int string) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //getting all types of files

        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), string);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getApplicationContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                ////
                Uri uri = data.getData();

                try {
                    path = FileUtils.getPath(this, uri);

                    File fil = new File(path);
                    float fln = (float) (fil.length() / 1024);
                    atype = path.substring(path.lastIndexOf(".") + 1);


                    fname = path.substring(path.lastIndexOf("/") + 1);
//                    ed15.setText(fname);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                try {

                    File imgFile = new File(path);

                    if (imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv.setImageBitmap(myBitmap);

                    }


                    File file = new File(path);
                    byte[] b = new byte[8192];
                    Log.d("bytes read", "bytes read");

                    InputStream inputStream = new FileInputStream(file);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    int bytesRead = 0;

                    while ((bytesRead = inputStream.read(b)) != -1) {
                        bos.write(b, 0, bytesRead);
                    }
                    byteArray = bos.toByteArray();

                    String str = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                    attach = str;


                } catch (Exception e) {
                    Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }

                ///

            }
        }

    }



    @Override
    public void onClick(View view) {
        if(view==iv)
        {
showfilechooser(1);
        }
        else {
            String username2 = ed1.getText().toString();
            String phone = ed2.getText().toString();
            String email = ed3.getText().toString();
            String place = ed4.getText().toString();
            String district = ed5.getText().toString();
            String post = ed6.getText().toString();
            String pin1 = ed7.getText().toString();
            String password = ed8.getText().toString();



            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String hu = sh.getString("ip", "");
            String url = "http://" + hu + ":5000/and_reg";


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
                                    Toast.makeText(Registration.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),login.class));
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

                    params.put("name", username2);
                    params.put("phone", phone);
                    params.put("email", email);
                    params.put("place", place);
                    params.put("district", district);
                    params.put("post", post);
                    params.put("pin", pin1);
                    params.put("pass", password);
                    params.put("pic", attach);

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