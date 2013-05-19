package main.java.no.niths.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.twitter.Tweet;
import no.niths.android.R;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 10.05.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class TwitterListAdapter extends BaseAdapter {

    Context context;
    int layoutResourceId;
    List<Tweet> data = null;
    LayoutInflater inflater;
    ImageLoader imageLoader;

    public TwitterListAdapter(Context context, int layoutResourceId, List<Tweet> data, LayoutInflater inflater, MainApplication application) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.inflater = inflater;
        this.imageLoader = application.getImageLoader();
    }

    public List<Tweet> getData() {
        return data;
    }

    public void setData(List<Tweet> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TwitterHolder holder = null;

        if (row == null) {
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TwitterHolder();
            holder.txtUsername = (TextView) row.findViewById(R.id.twitter_text_username);
            holder.txtMessage = (TextView) row.findViewById(R.id.twitter_text_tweet);
            holder.imgTweet = (NetworkImageView) row.findViewById(R.id.twitter_profile_img);

            row.setTag(holder);
        } else {
            holder = (TwitterHolder) row.getTag();
        }

        Tweet tweet = data.get(position);
        holder.txtUsername.setText(tweet.username);
        holder.imgTweet.setImageUrl(tweet.image_url, imageLoader);
        holder.txtMessage.setText(tweet.message);


        return row;
    }

    class TwitterHolder {
        TextView txtUsername;
        TextView txtMessage;
        NetworkImageView imgTweet;
    }
}

