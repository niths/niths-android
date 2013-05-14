package main.java.no.niths.views.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.SessionToken;
import main.java.no.niths.domain.Student;
import main.java.no.niths.services.TokenBundle;
import main.java.no.niths.services.domain.StudentsServiceImpl;
import main.java.no.niths.services.domain.interfaces.StudentsService;
import main.java.no.niths.views.activities.superclasses.AbstractTokenConsumerActivity;
import no.niths.android.R;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 11.05.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class ProfileFragment extends Fragment {
    protected static final String TAG = ProfileFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private boolean destroyed = false;
    private Context context;
    private TextView name, email, description;
    private Button update;
    private Student user;
    AbstractTokenConsumerActivity activity;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (TextView) view.findViewById(R.id.profile_page_nametext);
        email = (TextView) view.findViewById(R.id.profile_page_emailtext);
        description = (TextView) view.findViewById(R.id.profile_page_descriptiontext);
        update = (Button) view.findViewById(R.id.profile_page_update_button);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(getActivity());
                input.setSingleLine(false);
                input.setLines(5);
                input.setMinLines(5);
                input.setText(description.getText());

                AlertDialog.Builder editable = new AlertDialog.Builder(getActivity())
                        .setTitle("Beskrivelse")
                        .setMessage("Endre din beskrivelse av deg selv")
                        .setView(input)
                        .setPositiveButton("Lagre", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String value = input.getText().toString();
                                Log.d("INPUT FROM VIEW", value);
                                user.setDescription(value);
                                new UpdateProfileTask().execute(user);
                            }
                        })
                        .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                editable.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateProfileTask().execute(user);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, null);
    }

    @Override
    public void onResume() {
        super.onResume();

        context = getActivity();
        activity = (AbstractTokenConsumerActivity) getActivity();
        if (activity.isOnline()){
            new FetchProfileTask().execute();
        }
    }

    private void showUserInfo(Student profile) {
        user = profile;
        name.setText(user.getFirstName());
        email.setText(user.getEmail());
        description.setText(user.getDescription());
    }

    public MainApplication getApplicationContext() {
        return (MainApplication) getActivity().getApplicationContext();
    }

    // ***************************************
    // Public methods
    // ***************************************
    public void showLoadingProgressDialog() {
        this.showProgressDialog("Loading. Please wait...");
    }

    public void showProgressDialog(CharSequence message) {
        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setIndeterminate(true);
        }

        this.progressDialog.setMessage(message);
        this.progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (this.progressDialog != null && !this.destroyed) {
            this.progressDialog.dismiss();
        }
    }

    // ***************************************
    // Private classes
    // ***************************************
    private class FetchProfileTask extends AsyncTask<Void, Void, Student> {

        private Exception exception;
        private String token;
        private String niths_token;
        private SharedPreferences preferences;
        private String niths_token_key;

        @Override
        protected void onPreExecute() {
            showProgressDialog("Fetching profile...");
            preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            niths_token_key = getApplicationContext().getString(R.string.niths_token_key);
            token = preferences.getString(getApplicationContext().getString(R.string.google_token_key), "none");

        }

        @Override
        protected Student doInBackground(Void... params) {

            Student student = null;
            try {
                RestTemplate template = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                SessionToken token1 = new SessionToken();
                token1.setToken(token);
                HttpEntity<SessionToken> entity = new HttpEntity<SessionToken>(token1, headers);

                template.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
                ResponseEntity<Student> result = template.postForEntity(context.getString(R.string.server_url) + "/auth/login", entity, Student.class);

                Log.d(TAG, result.getBody().toString());
                Log.d(TAG, result.getHeaders().get("session-token").get(0).toString());
                Log.d(TAG, result.getStatusCode().toString());
                niths_token = result.getHeaders().get("session-token").get(0).toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(niths_token_key, niths_token);
                editor.commit();
                Log.d("NITHS_KALL_HEADER", niths_token);
                student = result.getBody();

            } catch (Exception e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                exception = e;
            }
            return student;
        }

        @Override
        protected void onPostExecute(Student profile) {
            dismissProgressDialog();
            if (profile == null || exception != null) {
                Toast.makeText(context, "Something went wrong while fetching the profile : " + token, Toast.LENGTH_LONG).show();
            } else {
                user = profile;
                showUserInfo(profile);
            }
        }
    }

    private class UpdateProfileTask extends AsyncTask<Student, Void, Student> {

        TokenBundle bundle;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String sessiontoken = preferences.getString(getApplicationContext().getString(R.string.niths_token_key), null);
            if (sessiontoken != null) {
                bundle = new TokenBundle();
                bundle.setSessionToken(sessiontoken);
                bundle.setApplicationToken(getApplicationContext().getString(R.string.niths_application_token));
                bundle.setDeveloperToken(getApplicationContext().getString(R.string.niths_developer_token));
            }
        }

        @Override
        protected Student doInBackground(Student... students) {
            StudentsService service = new StudentsServiceImpl(bundle);
            students[0].setDescription("Oppdatert profuhuihilen");
            return service.updateStudent(students[0]);
        }

        @Override
        protected void onPostExecute(Student student) {
            super.onPostExecute(student);
            Toast.makeText(context, "Update successfull for student with mail: " + student.getEmail(), Toast.LENGTH_SHORT).show();
            showUserInfo(student);
        }
    }


}
