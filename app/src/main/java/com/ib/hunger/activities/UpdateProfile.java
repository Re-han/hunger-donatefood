package com.ib.hunger.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.ib.hunger.Helper.Contants;
import com.ib.hunger.Helper.PrefManager;
import com.ib.hunger.Network.VolleySingleton;
import com.ib.hunger.R;
import com.ib.hunger.fragments.MyAccountFragment;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.text.TextUtils.isEmpty;

public class UpdateProfile extends AppCompatActivity {
TextInputEditText fname,fmobile,faddress;
Button submitButton;
TextView errormessage;
String user_name,fullname,saddress,mobile,ename,emobile,eaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        fname=findViewById(R.id.fname);
        fmobile=findViewById(R.id.fmob);
        faddress=findViewById(R.id.faddress);
        submitButton=findViewById(R.id.submitButton);
        errormessage=findViewById(R.id.error_message);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        user_name= PrefManager.getStr(getApplicationContext(), Contants.email);
        fullname= PrefManager.getStr(getApplicationContext(), Contants.fullname);
        saddress= PrefManager.getStr(getApplicationContext(), Contants.address);
        mobile= PrefManager.getStr(getApplicationContext(), Contants.mobile);

        if((fullname != null && !fullname.isEmpty() && !fullname.equals("null"))) {
            fname.setText(fullname);
        }

        if((saddress != null && !saddress.isEmpty() && !saddress.equals("null"))) {
            faddress.setText(saddress);
        }

        if((mobile != null && !mobile.isEmpty() && !mobile.equals("null"))) {
            fmobile.setText(mobile);

        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ename=fname.getText().toString();
                 emobile=fmobile.getText().toString();
                 eaddress=faddress.getText().toString();

                if(isEmpty(ename)){
                   errormessage.setText("Name should be Empty");
                }
                else if(isEmpty(emobile)){
                   errormessage.setText("Mobile number should be Empty");

                }
                else if(isEmpty(eaddress)){
                    errormessage.setText("Address should be Empty");

                }else {
                    errormessage.setText("");

                    updateprofile(ename,emobile,eaddress);

                }
            }
        });
    }

    private void updateprofile(String name, String mobile, String address) {

        String serverurl = Contants.URL;
        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject=new JSONObject(response);
                    String res=jsonObject.getString("result");
                    String message=jsonObject.getString("message");

                    if(res.equals("success")) {
                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();

                        PrefManager.insertData(getApplicationContext(), Contants.fullname,name);
                        PrefManager.insertData(getApplicationContext(), Contants.mobile,mobile);
                        PrefManager.insertData(getApplicationContext(), Contants.address,address);
                    }else {
                        Toast.makeText(getApplicationContext(),""+message,Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {
                    Log.e("ERROR","Exception"+e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   VolleyLog.d("Main", "Error: " + error.getMessage());
                //   Log.d("Main", "" + error.getMessage() + "," + error.toString());



                if(error instanceof NoConnectionError){
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = null;
                    if (cm != null) {
                        activeNetwork = cm.getActiveNetworkInfo();
                    }
                    if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
                        Toast.makeText(getApplicationContext(), "Server is not connected to internet.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Your device is not connected to internet.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
                        || (error.getCause().getMessage() != null
                        && error.getCause().getMessage().contains("connection"))){
                    Toast.makeText(getApplicationContext(), "Your device is not connected to internet.",
                            Toast.LENGTH_SHORT).show();
                } else if (error.getCause() instanceof MalformedURLException){
                    Toast.makeText(getApplicationContext(), "Bad Request.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                        || error.getCause() instanceof JSONException
                        || error.getCause() instanceof XmlPullParserException){
                    Toast.makeText(getApplicationContext(), "Parse Error (because of invalid json or xml).",
                            Toast.LENGTH_SHORT).show();
                } else if (error.getCause() instanceof OutOfMemoryError){
                    Toast.makeText(getApplicationContext(), "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
                }else if (error instanceof AuthFailureError){
                    Toast.makeText(getApplicationContext(), "server couldn't find the authenticated request.",
                            Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server is not responding.", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
                        || error.getCause() instanceof ConnectTimeoutException
                        || error.getCause() instanceof SocketException
                        || (error.getCause().getMessage() != null
                        && error.getCause().getMessage().contains("Connection timed out"))) {
                    Toast.makeText(getApplicationContext(), "Connection timeout error",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An unknown error occurred.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }){
            @Override
            public Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> data= new HashMap<String, String>();//to bind group of data
                //to insert data from edit feilds into table feilds
                data.put(Contants.accessKey,Contants.accessKeyValue);
                data.put("custmer_update_profile","1");
                data.put("fullname",name);
                data.put("mobile",mobile);
                data.put("address",address);
                data.put("email",user_name);


                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}