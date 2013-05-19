package main.java.no.niths.views.fragments.Events;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.domain.school.EventServiceImpl;
import main.java.no.niths.services.domain.school.interfaces.EventService;
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

    List<Event> events;
    MainApplication application;
    EventListAdapter adapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Event selectedEvent = events.get(position);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new ShowEventFragment(selectedEvent.getId());
        Bundle fragmentData = new Bundle();
        fragmentData.putLong("id", selectedEvent.getId());
        fragment.setArguments(fragmentData);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, "show_event")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        application = (MainApplication) getActivity().getApplication();
        EventService eventService = new EventServiceImpl((MainApplication) getActivity().getApplication());
        eventService.getAll(new Response.Listener<List<Event>>() {
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

        events = new ArrayList<Event>();
        adapter = new EventListAdapter(inflater.getContext(), R.layout.listview_item_row, events, inflater);
        setListAdapter(adapter);

    }
}
