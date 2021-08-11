package com.ib.hunger.loginAsReceiverActivities;

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
import com.ib.hunger.Helper.PrefManager;
import com.ib.hunger.Network.VolleySingleton;
import com.ib.hunger.R;
import com.ib.hunger.activities.LoginActivity;
import com.ib.hunger.activities.MainActivity;

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

import static android.text.TextUtils.isEmpty;

public class LoginAsDonor extends AppCompatActivity {

    TextView createAccount, forgotPass;
    Button LoginButton;
    TextInputLayout email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_as_receiver);
        LoginButton = findViewById(R.id.lad_loginButton);
        email = findViewById(R.id.loginasdonoremail);
        pass = findViewById(R.id.loginasdonorpasswordField);
        createAccount = findViewById(R.id.loginasdonorSignupTextview);
        forgotPass = findViewById(R.id.loginasdonorforgotPassword);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String demail=email.getEditText().getText().toString().trim();
                String dpass=pass.getEditText().getText().toString().trim();




                if (isEmpty(demail)) {
                    email.setError("Fields can't be Empty");
                }
                else  if (isEmpty(dpass)) {
                    pass.setError("Fields can't be Empty");
                }
                else if (!(ValidEmail(email.getEditText().getText().toString().trim()))) {
                    email.setError("Please enter a valid Email id");
                }
                else  if (!(ValidPassword(pass.getEditText().getText().toString().trim()))) {
                    pass.setError("Password must contain 1 small letter, 1 Capital letter, 1 special character, 1 digit and 8 characters");
                }
                else
                {
                    donorlogin(demail,dpass);

                }
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAsDonor.this, LoginAsDonorForgotPassword.class));
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAsDonor.this, LoginasDonorCreateAccount.class));
                finish();
            }
        });

    }

    private void donorlogin(String email, String password) {
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
                        // startHomeActivity();

                        startActivity(new Intent(LoginAsDonor.this, MainActivity_LoggedInAsDonor.class));
                        finish();

                        PrefManager.insertDData(LoginAsDonor.this, Contants.demail,email);


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
                data.put("ngos_login","1");
                data.put("email",email);
                data.put("password",password);
                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(sr);

    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(LoginAsDonor.this, LoginActivity.class));
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginAsDonor.this, LoginActivity.class));
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