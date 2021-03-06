package main.java.no.niths.views.fragments.Events;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.gson.Gson;

import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.services.domain.school.EventServiceImpl;
import main.java.no.niths.services.domain.school.interfaces.EventService;
import main.java.no.niths.views.fragments.NithsMapFragment;
import main.java.no.niths.views.fragments.superclasses.RefreshableFragment;
import no.niths.android.R;

/**
 * Created by elotin on 19.05.13.
 */
public class ShowEventFragment extends RefreshableFragment {

    public final static String EVENT_ID_KEY = "event_id";
    public final static String FRAGMENT_TAG = "SHOW_EVENT_FRAGMENT";
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    EventService eventService;
    private Long id;
    private Event eventShown;
    private TextView title, description, startTime, endTime, place;
    private Button showMapButton;

    public ShowEventFragment(Long id) {
        this.id = id;
    }

    public ShowEventFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null && id != null)
            outState.putLong("id", id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
        id = getArguments().getLong("id");
        if (savedInstanceState != null) {
            id = savedInstanceState.getLong("id");
        }
        MainApplication application = (MainApplication) getActivity().getApplication();

        eventService = new EventServiceImpl(application.getTokenBundle(), application.getRequestQueue(), application.getApplicationContext());
        eventService.getById(id, new Response.Listener<Event>() {
                    @Override
                    public void onResponse(Event event) {
                        if (event != null) {
                            eventShown = event;
                            showEvent();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("VOLLEY_ERROR", volleyError.getLocalizedMessage());
                    }
                }
        );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.event_fragment_layout_detail, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.event_detail_header_textview);
        description = (TextView) view.findViewById(R.id.event_detail_description_textview);
        startTime = (TextView) view.findViewById(R.id.event_detail_starttime_textview);
        endTime = (TextView) view.findViewById(R.id.event_detail_endtime_textview);
        place = (TextView) view.findViewById(R.id.event_detail_location_text);
        showMapButton = (Button) view.findViewById(R.id.event_detail_showmap_button);

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (servicesConnected()) {
                    showMapLocation();
                }
            }
        });
    }

    private void showMapLocation() {
        Fragment mapFragment = new NithsMapFragment();
        Bundle data = new Bundle();
        Gson gson = new Gson();
        String serializedLocation = gson.toJson(eventShown.getLocation());
        data.putString(NithsMapFragment.LOCATION_KEY, serializedLocation);
        mapFragment.setArguments(data);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mapFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showEvent() {
        title.setText(eventShown.getName());
        description.setText(eventShown.getDescription());
        startTime.setText(eventShown.getStartTime().toString());
        endTime.setText(String.valueOf(eventShown.getEndTime().getDayOfMonth()) + " " + String.valueOf(eventShown.getEndTime().getHourOfDay()));
        place.setText(eventShown.getLocation().getPlace());
    }

    /*
     * Handle results returned to the FragmentActivity
     * by Google Play services
     */
    @Override
    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {

            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
            /*
             * If the result code is Activity.RESULT_OK, try
             * to connect again
             */
                switch (resultCode) {
                    case Activity.RESULT_OK:
                    /*
                     * Try the request again
                     */

                        break;
                }

        }
    }

    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(getActivity());
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Get the error code
            int errorCode = resultCode;
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                    errorCode,
                    getActivity(),
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment =
                        new ErrorDialogFragment();
                // Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                // Show the error dialog in the DialogFragment
                errorFragment.show(getFragmentManager(),
                        "Location Updates");
            }
        }
        return false;
    }

    @Override
    protected void refreshView(final MenuItem item) {
        item.setActionView(R.layout.actionbar_indeterminate_progress);
        title.setText("loading");
        showNewEvent(id);
    }

    public void showNewEvent(final Long eventId) {
        title.setText("loading");
        eventService.getById(eventId, new Response.Listener<Event>() {
            @Override
            public void onResponse(Event event) {
                showEvent();
            }
        }, null);
    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
