package com.ib.hunger.models;


public class fragment_request_model {
    String title;
    String req;
    String date;
    String mobile;
    String address;
    String email;
    String id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



   public fragment_request_model(){

   }

    public fragment_request_model(String title, String req, String date) {
        this.title = title;
        this.req = req;
        this.date = date;
    }



    public String getTitle() {
        return title;
    }


    public String getReq() {
        return req;
    }


    public String getDate() {
        return date;
    }


}
