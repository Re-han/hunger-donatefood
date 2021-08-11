package com.ib.hunger.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
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

public class Ratings extends AppCompatActivity {
RatingBar appCompatRatingBar;
TextInputLayout rating_msg;
Button ratings_submit;
TextView error_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        appCompatRatingBar=findViewById(R.id.appCompatRatingBar);
        rating_msg=findViewById(R.id.rating_msg1);
        ratings_submit=findViewById(R.id.ratings_submit);
        error_message=findViewById(R.id.error_message);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        ratings_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              float rating =appCompatRatingBar.getRating();
              String message=rating_msg.getEditText().getText().toString();

              if(rating == 0.0){
                  error_message.setText("Rating should not be Empty");
              }
              else if(isEmpty(message)){
                  error_message.setText("Message should not be Empty");

              }else {
                  error_message.setText(" ");
                  //setrating(rating,message);
              }

            }
        });






    }

    private void setrating(float rating, String message) {
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
                data.put("","1");
                data.put("",""+rating);
                data.put("",""+message);
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