package main.java.no.niths.views.fragments.Events;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.domain.school.EventServiceImpl;
import main.java.no.niths.services.domain.school.interfaces.EventService;
import no.niths.android.R;

/**
 * Created by elotin on 19.05.13.
 */
public class ShowEventFragment extends Fragment {

    public final static String EVENT_ID_KEY = "event_id";
    private final Long id;
    EventService eventService;
    private Event eventShown;
    private TextView title, description, startTime, endTime;
    private Button showMapButton;

    public ShowEventFragment(Long id) {
        this.id = id;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eventService = new EventServiceImpl((MainApplication) getActivity().getApplication());
        eventService.getById(id, new Response.Listener<Event>() {
                    @Override
                    public void onResponse(Event event) {
                        eventShown = event;
                        showEvent();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("VOLLEY_ERROR", volleyError.getLocalizedMessage());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());title.setText(eventShown.getName());
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.event_detail_header_textview);
        description = (TextView) view.findViewById(R.id.event_detail_description_textview);
        startTime = (TextView) view.findViewById(R.id.event_detail_starttime_textview);
        endTime = (TextView) view.findViewById(R.id.event_detail_endtime_textview);
        showMapButton = (Button) view.findViewById(R.id.event_detail_showmap_button);
    }

    private void showEvent(){
        title.setText(eventShown.getName());
        description.setText(eventShown.getDescription());
        startTime.setText(eventShown.getStartTime().toString());
        endTime.setText(String.valueOf(eventShown.getEndTime().getDayOfMonth()) + " " + String.valueOf(eventShown.getEndTime().getHourOfDay()));
    }
}
