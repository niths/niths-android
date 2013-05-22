package main.java.no.niths.views.fragments.superclasses;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.services.domain.school.superclass.GenericCrudServiceOperator;
import no.niths.android.R;

import java.util.List;

/**
 * Created by elotin on 19.05.13.
 */
public abstract class RefreshableFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                item.setActionView(R.layout.actionbar_indeterminate_progress);
                refreshView(item);
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void refreshView(final MenuItem item);

}
