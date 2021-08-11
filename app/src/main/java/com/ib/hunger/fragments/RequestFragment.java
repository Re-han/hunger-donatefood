package com.ib.hunger.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.ib.hunger.Helper.Contants;
import com.ib.hunger.Network.VolleySingleton;
import com.ib.hunger.R;
import com.ib.hunger.adapters.fragment_request_adapter;
import com.ib.hunger.adapters.peopleAddr_adapter;
import com.ib.hunger.models.fragment_request_model;
import com.ib.hunger.models.peopleAddr_model;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestFragment extends Fragment {
    SwipeableRecyclerView recyclerView;
    List<fragment_request_model> arrayList=new ArrayList<>();
    String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.request_recyclerView);

//        recyclerView = view.findViewById(R.id.request_recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getRequest();




    }

    private void getRequest() {

        final RequestQueue queue = Volley.newRequestQueue(getActivity());
        String serverurl = Contants.URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String res = jsonObject.getString("result");

                    if (res.equals("success")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String description = jsonObject1.getString("description");
                            String email = jsonObject1.getString("email");
                            String mobile = jsonObject1.getString("mobile");
                            String datetime = jsonObject1.getString("datetime");
                            String address = jsonObject1.getString("address");


                            fragment_request_model uc = new fragment_request_model();
                            uc.setId(id);
                            uc.setTitle(title);
                            uc.setMobile(mobile);
                            uc.setEmail(email);
                            uc.setDate(datetime);
                            uc.setReq(description);
                            uc.setAddress(address);
                            arrayList.add(uc);



                        }


                        fragment_request_adapter fragment_request_adapter = new fragment_request_adapter(getContext(), arrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(fragment_request_adapter);
                        recyclerView.setListener(new SwipeLeftRightCallback.Listener() {
                            @Override
                            public void onSwipedLeft(int position) {

                                String serverurl = Contants.URL;
                                StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try{
                                            JSONObject jsonObject=new JSONObject(response);
                                            String res=jsonObject.getString("result");

                                            if(res.equals("success")) {
                                                message=jsonObject.getString("message");
                                               // Toast.makeText(getActivity(),""+message, Toast.LENGTH_SHORT).show();

                                                arrayList.remove(position);
                                                fragment_request_adapter.notifyDataSetChanged();
                                                Snackbar.make(recyclerView, "Item Deleted", Snackbar.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(getActivity(),""+message, Toast.LENGTH_SHORT).show();

                                            }


                                        } catch (Exception e) {
                                            Log.e("ERROR","Exception"+e.getMessage());
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.d("Main", "Error: " + error.getMessage());
                                        Log.d("Main", "" + error.getMessage() + "," + error.toString());

                                    }
                                }){
                                    @Override
                                    public Map<String, String> getParams() throws AuthFailureError {

                                        Map<String, String> data= new HashMap<String, String>();//to bind group of data
                                        data.put(Contants.accessKey,Contants.accessKeyValue);
                                        data.put("custmer_requests_delete_by_id","1");
                                        data.put("id", String.valueOf(position));
                                        return data;

                                    }
                                };
                                VolleySingleton.getInstance(getActivity()).addToRequestQueue(sr);





                            }

                            @Override
                            public void onSwipedRight(int position) {
                                String serverurl = Contants.URL;
                                StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try{
                                            JSONObject jsonObject=new JSONObject(response);
                                            String res=jsonObject.getString("result");

                                            if(res.equals("success")) {
                                                message=jsonObject.getString("message");
                                                // Toast.makeText(getActivity(),""+message, Toast.LENGTH_SHORT).show();

                                                arrayList.remove(position);
                                                fragment_request_adapter.notifyDataSetChanged();
                                                Snackbar.make(recyclerView, "Item Deleted", Snackbar.LENGTH_SHORT).show();

                                            }else {
                                                Toast.makeText(getActivity(),""+message, Toast.LENGTH_SHORT).show();

                                            }


                                        } catch (Exception e) {
                                            Log.e("ERROR","Exception"+e.getMessage());
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        VolleyLog.d("Main", "Error: " + error.getMessage());
                                        Log.d("Main", "" + error.getMessage() + "," + error.toString());

                                    }
                                }){
                                    @Override
                                    public Map<String, String> getParams() throws AuthFailureError {

                                        Map<String, String> data= new HashMap<String, String>();//to bind group of data
                                        data.put(Contants.accessKey,Contants.accessKeyValue);
                                        data.put("custmer_requests_delete_by_id","1");
                                        data.put("id", String.valueOf(position));
                                        return data;

                                    }
                                };
                                VolleySingleton.getInstance(getActivity()).addToRequestQueue(sr);
                            }
                        });

                        /*
                         * Additional attributes:
                         * */
                        recyclerView.setRightBg(R.color.red);
                        recyclerView.setRightImage(R.drawable.ic_baseline_delete_24);


                        recyclerView.setLeftBg(R.color.red);
                        recyclerView.setLeftImage(R.drawable.ic_baseline_delete_24);



                    }


                } catch (JSONException e) {


                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Main", "Error: " + error.getMessage());
                Log.d("Main", "" + error.getMessage() + "," + error.toString());


            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> data1 = new HashMap<String, String>();
                data1.put(Contants.accessKey, Contants.accessKeyValue);
                data1.put("customer_requests_viewall", "1");
                return data1;
            }

        };
        queue.add(stringRequest);

    }

//    private List<fragment_request_model> dataList() {
//        arrayList = new ArrayList<>();
//        arrayList.add(new fragment_request_model("request1", "req", "8/4/21 11.53am"));
//        arrayList.add(new fragment_request_model("request1", "req", "8/4/21 11.53am"));
//        arrayList.add(new fragment_request_model("request1", "req", "8/4/21 11.53am"));
//        arrayList.add(new fragment_request_model("request1", "req", "8/4/21 11.53am"));
//        arrayList.add(new fragment_request_model("request1", "req", "8/4/21 11.53am"));
//        arrayList.add(new fragment_request_model("request1", "req", "8/4/21 11.53am"));
//        return arrayList;
//    }


}