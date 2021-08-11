package com.ib.hunger.LoginAsReceiver_Models;

import android.graphics.drawable.Drawable;

public class Feedback_model {
    Drawable profile;
    String name, visited, review, date;
    float ratingBar;



    public Feedback_model(Drawable profile, String name, String visited, String review, String date, float ratingBar) {
        this.profile = profile;
        this.name = name;
        this.visited = visited;
        this.review = review;
        this.date = date;
        this.ratingBar = ratingBar;

    }

    public Drawable getProfile() {
        return profile;
    }

    public String getName() {
        return name;
    }

    public String getVisited() {
        return visited;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }


    public float getRating() {
        return ratingBar;
    }
}
