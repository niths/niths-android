package main.java.no.niths.views.fragments.Events;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import main.java.no.niths.MainApplication;
import main.java.no.niths.services.domain.school.EventServiceImpl;
import main.java.no.niths.services.domain.school.interfaces.EventService;
import no.niths.android.R;

/**
 * Created by elotin on 19.05.13.
 */
public class ShowEventFragment extends Fragment {

    EventService eventService;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eventService = new EventServiceImpl((MainApplication) getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment_layout, null);
    }


}
