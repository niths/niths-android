package main.java.no.niths.views.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import main.java.no.niths.domain.school.Location;
import main.java.no.niths.services.utils.GMapV2Direction;
import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by elotin on 19.05.13.
 */
public class NithsMapFragment extends SupportMapFragment {
    public final static String FRAGMENT_TAG = "SHOW_MAP_FRAGMENT";
    private static final int ZOOMLEVEL = 15;
    private static final int ZOOMDURATION = 1000; //1 seconds
    private static final GMapV2Direction G_MAP_V_2_DIRECTION = new GMapV2Direction();
    public static String LOCATION_KEY = "location_key";
    private final Gson gson = new Gson();
    private GoogleMap map;
    private Location location;
    private LocationClient locationClient;

    public NithsMapFragment() {
        super();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null && location != null)
            outState.putString(LOCATION_KEY, gson.toJson(location));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String locationString = null;
        locationString = getArguments().getString(LOCATION_KEY);

        if (savedInstanceState != null) {
            locationString = savedInstanceState.getString(LOCATION_KEY);
        }
        location = gson.fromJson(locationString, Location.class);


        map = getMap();
        //map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
        //map.animateCamera(CameraUpdateFactory.zoomTo(ZOOMLEVEL), ZOOMDURATION, null);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title(location.getPlace()));
        map.setMyLocationEnabled(true);

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.getMyLocation();
    }

    @Override
    public void onStart() {
        super.onStart();

        locationClient = new LocationClient(this.getActivity(), new GooglePlayServicesClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                Toast.makeText(getActivity(), "Connected", Toast.LENGTH_SHORT).show();
                new GetWalkingDirectionsTask().execute();
            }

            @Override
            public void onDisconnected() {
                Toast.makeText(getActivity(), "Disconnected. Please re-connect for walking instructions.",
                        Toast.LENGTH_SHORT).show();
            }
        }, new GooglePlayServicesClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Toast.makeText(getActivity(), "Connection to play failed",
                        Toast.LENGTH_SHORT).show();
            }
        }
        );
        locationClient.connect();
    }

    private class GetWalkingDirectionsTask extends AsyncTask<Void, Void, Document> {
        android.location.Location myLocation = locationClient.getLastLocation();
        private LatLng startPos;
        private LatLng endPos;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myLocation = locationClient.getLastLocation();
            if (myLocation != null) {
                startPos = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                endPos = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }

        @Override
        protected Document doInBackground(Void... voids) {
            if (myLocation != null) {
                return G_MAP_V_2_DIRECTION.getDocument(startPos, endPos, GMapV2Direction.MODE_WALKING);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);
            if (myLocation != null) {
                ArrayList<LatLng> directionPoint = G_MAP_V_2_DIRECTION.getDirection(document);
                PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);

                for (int i = 0; i < directionPoint.size(); i++) {
                    rectLine.add(directionPoint.get(i));
                }
                map.addPolyline(rectLine);
            }
        }
    }


}

