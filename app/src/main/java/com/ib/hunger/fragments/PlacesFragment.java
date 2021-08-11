package com.ib.hunger.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ib.hunger.Helper.Contants;
import com.ib.hunger.R;
import com.ib.hunger.adapters.fragment_notification_adapter;
import com.ib.hunger.adapters.peopleAddr_adapter;
import com.ib.hunger.models.fragment_notification_model;
import com.ib.hunger.models.peopleAddr_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlacesFragment extends Fragment {
    RecyclerView peopleRecycler;
    androidx.appcompat.widget.SearchView searchView;
    List<peopleAddr_model> list = new ArrayList<>();
    peopleAddr_adapter addr_adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        peopleRecycler = view.findViewById(R.id.peoplesAddr);
        searchView = view.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addr_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                addr_adapter.getFilter().filter(newText);
                return false;
            }
        });

        viewngos();





    }

    private void viewngos() {

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
                            String mobile = jsonObject1.getString("mobile");
                            String email = jsonObject1.getString("email");
                            String password = jsonObject1.getString("password");
                            String address = jsonObject1.getString("address");
                            String city = jsonObject1.getString("city");
                            String locality = jsonObject1.getString("locality");
                            String image = jsonObject1.getString("image");


                            peopleAddr_model uc = new peopleAddr_model();
                            uc.setId(id);
                            uc.setTitle(title);
                            uc.setMobile(mobile);
                            uc.setEmail(email);
                            uc.setPassword(password);
                            uc.setAddress(address);
                            uc.setCity(city);
                            uc.setLocality(locality);
                            uc.setImage(image);

                            list.add(uc);



                        }


                        peopleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        addr_adapter = new peopleAddr_adapter(getContext(), list);
                        peopleRecycler.setAdapter(addr_adapter);



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
                data1.put("ngos_viewall", "1");
                return data1;
            }

        };
        queue.add(stringRequest);

    }

//    private List<peopleAddr_model> datalist() {
//        list.add(new peopleAddr_model("Ngo", getResources().getString(R.string.sample_text), "secbad", getResources().getString(R.string.sample_text)));
//        list.add(new peopleAddr_model("Ngo", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("Ngo", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("Ngo", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("Ngo", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("Ngo", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("abo", getResources().getString(R.string.sample_text), "secbad", getResources().getString(R.string.sample_text)));
//        list.add(new peopleAddr_model("bao", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("abc", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("def", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("lol", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        list.add(new peopleAddr_model("Ngo", "10-57-88, maredpally", "secbad", "its an nog for old age"));
//        return list;
//    }
}