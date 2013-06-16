package main.java.no.niths.views.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Student;
import main.java.no.niths.services.auth.AuthService;
import main.java.no.niths.services.auth.AuthServiceImpl;
import main.java.no.niths.services.domain.school.StudentsServiceImpl;
import main.java.no.niths.services.domain.school.interfaces.StudentsService;
import main.java.no.niths.views.activities.superclasses.AbstractTokenConsumerActivity;
import no.niths.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 11.05.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class ProfileFragment extends Fragment {
    public final static String FRAGMENT_TAG = "SHOW_PROFILE_FRAGMENT";

    protected static final String TAG = ProfileFragment.class.getSimpleName();
    AbstractTokenConsumerActivity activity;
    private ProgressDialog progressDialog;
    private boolean destroyed = false;
    private Context context;
    private TextView name, email, description;
    private Button update;
    private Student user;
    private MainApplication application;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        application = (MainApplication) getActivity().getApplication();


    }

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
                            }
                        })
                        .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                update.setOnClickListener(new View.OnClickListener() {
                    StudentsService studentsService = new StudentsServiceImpl(application.getTokenBundle(), application.getRequestQueue(), application.getApplicationContext());
                    @Override
                    public void onClick(View view) {
                        studentsService.getById(29, new Response.Listener<Student>() {
                                    @Override
                                    public void onResponse(Student students) {
                                        showUserInfo(students);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Log.e("VOLLEY_ERROR", volleyError.getLocalizedMessage(), volleyError);
                                    }
                                }
                        );
                    }
                });
                editable.show();
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
        if (activity.isOnline()) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String token = preferences.getString(getApplicationContext().getString(R.string.google_token_key), "none");
            AuthService authService = new AuthServiceImpl(application);
            authService.login(token, new Response.Listener<Student>() {
                        @Override
                        public void onResponse(Student students) {
                            showUserInfo(students);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("VOLLEY_ERROR", volleyError.getLocalizedMessage(), volleyError);
                        }
                    }
            );
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
}
