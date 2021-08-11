package com.ib.hunger.LoginAsReceiverFragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.ib.hunger.Helper.Contants;
import com.ib.hunger.Helper.PrefManager;
import com.ib.hunger.Network.VolleySingleton;
import com.ib.hunger.R;
import com.ib.hunger.activities.UpdateProfile;
import com.ib.hunger.loginasReceiverAdapters.lad_my_account_adapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

public class LoggedInAsDonorMyAccount extends Fragment implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_AND_LOCATION = 123;
    CircleImageView imageView;
    TextView lad_titleText,lad_emailText;
    String user_name,message,title,eemail,emobile,eaddress,eimage,city,locality,password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logged_in_as_receiver_my_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView options = view.findViewById(R.id.lad_options);
        TextView updateProfile = view.findViewById(R.id.lad_updateProfile);
        ImageButton picker = view.findViewById(R.id.lad_picker);
        imageView = view.findViewById(R.id.lad_ProfileImageView);
        lad_titleText = view.findViewById(R.id.lad_titleText);
        lad_emailText = view.findViewById(R.id.lad_emailText);


        user_name= PrefManager.getDStr(getActivity(), Contants.demail);


        getProfile();

        SharedPreferences sharedPreferences_lad_My_acc_get_take_pic_get = Objects.requireNonNull(getContext()).getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
        String imageUri_takePic = sharedPreferences_lad_My_acc_get_take_pic_get.getString("imageString_lad", "");
        Glide.with(getContext()).load(StringToBitMap(imageUri_takePic)).into(imageView);

        SharedPreferences sharedPreferences_lad_My_acc_get = Objects.requireNonNull(getContext()).getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
        String imageUri_pickFromGallery = sharedPreferences_lad_My_acc_get.getString("imageString_lad", "");
        Glide.with(getContext()).load(StringToBitMap(imageUri_pickFromGallery)).into(imageView);

        options.setLayoutManager(new LinearLayoutManager(getContext()));
        options.setAdapter(new lad_my_account_adapter(getContext(), dataList()));

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getContext()).startActivity(new Intent(getContext(), UpdateProfile.class));
            }
        });
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodRequiresTwoPermission();
            }
        });
    }

    private void getProfile() {

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

                            title = jsonObject1.getString("title");
                            eemail = jsonObject1.getString("email");
                            eimage = jsonObject1.getString("image");
                            emobile = jsonObject1.getString("mobile");
                            eaddress=jsonObject1.getString("address");
                            password=jsonObject1.getString("password");
                            city=jsonObject1.getString("city");
                            locality=jsonObject1.getString("address");



                            lad_emailText.setText(eemail);
                            lad_titleText.setText(title);
                            PrefManager.insertData(getActivity(), Contants.demail,eemail);
                            PrefManager.insertData(getActivity(), Contants.dfullname,title);
                            PrefManager.insertData(getActivity(), Contants.dmobile,emobile);
                            PrefManager.insertData(getActivity(), Contants.dimage,eimage);
                            PrefManager.insertData(getActivity(), Contants.daddress,eaddress);



                            if(!eimage.equals("")){
                                Picasso.get().load(Contants.image_url + eimage).into(imageView);

                            }else {
                                imageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
                            }



                        }
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
                data1.put("ngos_view_by_email", "1");
                data1.put("email",user_name);
                return data1;
            }

        };
        queue.add(stringRequest);

    }

    private List<String> dataList() {
        List<String> data = new ArrayList<>();
        data.add("Change Password");
        data.add("Contact Us");
        data.add("Terms & Conditions");
        data.add("Privacy Policy");
        data.add("Share");
        data.add("Manage Requests");
        data.add("Log out");
        return data;
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            // Already have permission, do the thing
            // ...

            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("Profile Photo")
                    .setMessage("Pick or Take a Photo")
                    .setNegativeButton("Pick a Photo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, 1);
                        }
                    })
                    .setPositiveButton("Take Photo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, 2);
                        }

                    });
            dialog.show().create();

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_externalStrorage_rationale),
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.

        }
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String imageUri = null;

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getContext()).getContentResolver(), selectedImage);
                imageUri = BitMapToString(bitmap);
                SharedPreferences sharedPreferences_lad_My_acc = getContext().getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences_lad_My_acc.edit();
                editor.putString("imageString_lad", imageUri);
                editor.apply();
                SharedPreferences sharedPreferences_lad_My_acc_get = Objects.requireNonNull(getContext()).getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
                String imageUri1 = sharedPreferences_lad_My_acc_get.getString("imageString_lad", "");
                Glide.with(getContext()).load(StringToBitMap(imageUri1)).into(imageView);

                updateimage(StringToBitMap(imageUri1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            SharedPreferences sharedPreferences_lad_My_acc_take_pic = getContext().getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences_lad_My_acc_take_pic.edit();
            editor.putString("imageString_lad", BitMapToString(photo));
            editor.apply();

            SharedPreferences sharedPreferences_lad_My_acc_get_take_pic_get = Objects.requireNonNull(getContext()).getSharedPreferences("Image_lad", Context.MODE_PRIVATE);
            String imageUri1 = sharedPreferences_lad_My_acc_get_take_pic_get.getString("imageString_lad", "");
            Glide.with(getContext()).load(StringToBitMap(imageUri1)).into(imageView);

            updateimage(StringToBitMap(imageUri1));

        }
    }

    private void updateimage(Bitmap stringToBitMap) {
        String serverurl = Contants.URL;

        StringRequest sr = new StringRequest(Request.Method.POST, serverurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject=new JSONObject(response);

                    String res=jsonObject.getString("result");
                    message=jsonObject.getString("message");
                    if(res.equals("success")) {
                        Toast.makeText(getActivity(),""+message,Toast.LENGTH_SHORT).show();
                        //  getProfile();

                    }else {
                        Toast.makeText(getActivity(),""+message,Toast.LENGTH_SHORT).show();

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
            public Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> data= new HashMap<String, String>();//to bind group of data
                data.put(Contants.accessKey,Contants.accessKeyValue);
                data.put("ngos_update_image","1");
                data.put("email",user_name);
                data.put("image",BitMapToString(stringToBitMap));
                return data;
            }
        };
        //TO add request to Volley
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(sr);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}