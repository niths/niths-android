package main.java.no.niths.domain.twitter;

import com.google.gson.annotations.SerializedName;

public class Tweet {

    @SerializedName("from_user")
    public String username;
    @SerializedName("text")
    public String message;
    @SerializedName("profile_image_url")
    public String image_url;

    public Tweet(String username, String message, String url) {
        this.username = username;
        this.message = message;
        this.image_url = url;
    }

}
