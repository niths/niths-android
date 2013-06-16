package main.java.no.niths.views.fragments.Events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import main.java.no.niths.domain.school.Event;
import main.java.no.niths.views.fragments.superclasses.RefreshableFragment;
import no.niths.android.R;

/**
 * Created by elotin on 28.05.13.
 */
public class MainEventFragment extends RefreshableFragment implements EventListHandler {

    LinearLayout detailsContainer, listContainer;
    ShowEventFragment showEventFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsContainer = (LinearLayout) view.findViewById(R.id.event_details_container);
        listContainer = (LinearLayout) view.findViewById(R.id.event_list_container);

        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction()
                .replace(R.id.event_list_container, new EventListFragment())
                .commit();
        if (detailsContainer != null){
            Bundle bundle = new Bundle();
            bundle.putLong(ShowEventFragment.EVENT_ID_KEY, 1);
            showEventFragment = new ShowEventFragment();
            showEventFragment.setArguments(bundle);
            manager.beginTransaction()
                    .replace(R.id.event_list_container, showEventFragment)
                    .commit();
        }
    }

    @Override
    protected void refreshView(MenuItem item) {

    }

    @Override
    public void EventItemClicked(Event event) {
        FragmentManager manager = getChildFragmentManager();
        if (showEventFragment != null){
            showEventFragment.showNewEvent(event.getId());
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong(ShowEventFragment.EVENT_ID_KEY, event.getId());
            Fragment fragment = new ShowEventFragment();
            fragment.setArguments(bundle);
            manager.beginTransaction()
                    .replace(R.id.event_fragment_container, fragment)
                    .commit();
        }
    }
}
