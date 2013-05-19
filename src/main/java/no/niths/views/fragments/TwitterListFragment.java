package main.java.no.niths.views.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.twitter.NITHTwitterFetcher;
import main.java.no.niths.domain.twitter.Tweet;
import main.java.no.niths.domain.twitter.TwitterWrapper;
import main.java.no.niths.views.adapters.TwitterListAdapter;
import no.niths.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elotin on 18.05.13.
 */
public class TwitterListFragment extends ListFragment {
    List<Tweet> tweets;
    MainApplication application;
    TwitterListAdapter adapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onStart() {
        super.onStart();
        application = (MainApplication) getActivity().getApplication();
        //new FetchFaddergrupperTask().execute();
        NITHTwitterFetcher twitterFetcher = new NITHTwitterFetcher(1, new Response.Listener<TwitterWrapper>() {
            @Override
            public void onResponse(TwitterWrapper tweet) {
                adapter.getData().addAll(tweet.getTweets());
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        RequestQueue queue = application.getRequestQueue();
        queue.add(twitterFetcher);
        queue.start();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        tweets = new ArrayList<Tweet>();
        adapter = new TwitterListAdapter(inflater.getContext(), R.layout.twitter_listview_row, tweets, inflater, (MainApplication) getActivity().getApplication());
        setListAdapter(adapter);

    }
}
