package main.java.no.niths.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import main.java.no.niths.MainApplication;
import main.java.no.niths.views.activities.MainActivity;
import no.niths.android.R;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.connect.GoogleConnectionFactory;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 11.05.13
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class Mainpage extends Fragment {
    protected static final String TAG = MainActivity.class.getSimpleName();

    private ConnectionRepository connectionRepository;

    private GoogleConnectionFactory connectionFactory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View mainView = inflater.inflate(R.layout.google_activity_layout, null);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainApplication application = (MainApplication) getActivity().getApplication();


    }

}
