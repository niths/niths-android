package main.java.no.niths.domain.twitter;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by elotin on 18.05.13.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
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
