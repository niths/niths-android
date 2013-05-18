package main.java.no.niths.views.fragments.Events;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.domain.school.EventServiceWithVolley;
import main.java.no.niths.views.adapters.EventListAdapter;
import no.niths.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 14.05.13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class EventListFragment extends ListFragment {

    List<Event> faddergrupper;
    MainApplication application;
    EventListAdapter adapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onStart() {
        super.onStart();
        application = (MainApplication) getActivity().getApplication();
        //new FetchFaddergrupperTask().execute();
        EventServiceWithVolley eventServiceWithVolley = new EventServiceWithVolley((MainApplication) getActivity().getApplication());
        eventServiceWithVolley.getAll(new Response.Listener<List<Event>>() {
                                          @Override
                                          public void onResponse(List<Event> events) {
                                              adapter.getData().addAll(events);
                                              adapter.notifyDataSetChanged();
                                          }
                                      }, new Response.ErrorListener() {
                                          @Override
                                          public void onErrorResponse(VolleyError volleyError) {

                                          }
                                      }
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        faddergrupper = new ArrayList<Event>();
        adapter = new EventListAdapter(inflater.getContext(), R.layout.listview_item_row, faddergrupper, inflater);
        setListAdapter(adapter);

    }
}
