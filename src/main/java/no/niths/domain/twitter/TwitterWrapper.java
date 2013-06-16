package main.java.no.niths.domain.twitter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TwitterWrapper {

    @SerializedName("results")
    private List<Tweet> tweets;

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
