package com.ib.hunger.models;


public class fragment_notification_model {
    String title;
    String req;
    String date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public fragment_notification_model(){

    }

    public fragment_notification_model(String title, String req, String date) {
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
