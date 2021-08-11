
package com.ib.hunger.activities;

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

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.material.textfield.TextInputLayout;
import com.ib.hunger.Helper.Contants;
import com.ib.hunger.Network.VolleySingleton;
import com.ib.hunger.R;
import com.ib.hunger.loginAsReceiverActivities.LoginAsDonor;
import com.ib.hunger.loginAsReceiverActivities.LoginasDonorCreateAccount;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {
    private TextView backtoLogin;
    TextInputLayout fname, email, mob, pass,address;
    Button createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        backtoLogin = findViewById(R.id.AlreadyloginTextview);
        createAcc = findViewById(R.id.createaccbutton);
        fname = findViewById(R.id.fullname);
        mob = findViewById(R.id.MobNo);
        email = findViewById(R.id.cemail);
        pass = findViewById(R.id.cpasswordField);
        address=findViewById(R.id.caddressField);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidPassword(pass.getEditText().getText().toString().trim()) && ValidEmail(email.getEditText().getText().toString().trim())) {

                }
               else   if ((ValidEmail(email.getEditText().getText().toString().trim()))) {
                    email.setError("");
                }
                else  if ((ValidPassword(pass.getEditText().getText().toString().trim()))) {
                    pass.setError("");
                }
                else if (!(ValidEmail(email.getEditText().getText().toString().trim()))) {
                    email.setError("Please enter a valid Email id");
                }
                else if (!(ValidPassword(pass.getEditText().getText().toString().trim()))) {
                    pass.setError("Password must contain 1 small letter, 1 Capital letter, 1 special character, 1 digit and 8 characters");
                }
                else if (fname.getEditText().getText().length() == 0) {
                    fname.setError("Field can't be Empty");
                }
                if (mob.getEditText().getText().length() == 0) {
                    mob.setError("Field can't be Empty");
                }
                if (address.getEditText().getText().length() == 0) {
                    address.setError("Field can't be Empty");
                }
                else
                {
                  signup(email.getEditText().getText().toString(),pass.getEditText().getText().toString(),fname.getEditText().getText().toString(),mob.getEditText().getText().toString(),address.getEditText().getText().toString());
                }
//                if (fname.getEditText().getText().length() != 0) {
//                    fname.setError("");
//                }
//                if (mob.getEditText().getText().length() != 0) {
//                    mob.setError("");
//                }
            }
        });
        backtoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void signup(String email, String pass, String fname, String mobile, String eaddress) {
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

                        startActivity(new Intent(CreateAccount.this, LoginActivity.class));
                        finish();


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
                data.put("customer_reg","1");
                data.put("fullname",fname);
                data.put("email",email);
                data.put("mobile",mobile);
                data.put("password",pass);
                data.put("address",eaddress);

                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);
    }


    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(CreateAccount.this, LoginActivity.class));
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreateAccount.this, LoginActivity.class));
        finish();
    }

    public boolean ValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public boolean ValidEmail(final String email) {

        Pattern pattern;
        Matcher matcher;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }
}