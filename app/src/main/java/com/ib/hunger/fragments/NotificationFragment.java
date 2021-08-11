package com.ib.hunger.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.ib.hunger.adapters.fragment_request_adapter;
import com.ib.hunger.models.fragment_notification_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    List<fragment_notification_model> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.notification_recyclerView);

        getNotifications();

    }

    private void getNotifications() {

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
                                String datetime = jsonObject1.getString("datetime");






                                fragment_notification_model uc = new fragment_notification_model();
                                uc.setId(id);
                                uc.setTitle(title);
                                uc.setReq(description);
                                uc.setDate(datetime);
                                arrayList.add(uc);



                        }


                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(new fragment_notification_adapter(getContext(),arrayList));


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
                data1.put("notifications_viewall", "1");
                return data1;
            }

        };
        queue.add(stringRequest);

    }

    private List<fragment_notification_model> dataList() {
        List<fragment_notification_model> arrayList = new ArrayList<>();

        arrayList.add(new fragment_notification_model("request1", "req", "8/4/21 11.53am"));
        arrayList.add(new fragment_notification_model("request1", "req", "8/4/21 11.53am"));
        arrayList.add(new fragment_notification_model("request1", "req", "8/4/21 11.53am"));
        arrayList.add(new fragment_notification_model("request1", "req", "8/4/21 11.53am"));
        arrayList.add(new fragment_notification_model("request1", "req", "8/4/21 11.53am"));
        arrayList.add(new fragment_notification_model("request1", "req", "8/4/21 11.53am"));
        return arrayList;
    }
}