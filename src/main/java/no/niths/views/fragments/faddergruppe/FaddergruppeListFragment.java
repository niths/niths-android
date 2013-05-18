package main.java.no.niths.views.fragments.faddergruppe;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Faddergruppe;
import main.java.no.niths.services.domain.school.FaddergruppeServiceImpl;
import main.java.no.niths.services.domain.school.interfaces.FaddergruppeService;
import main.java.no.niths.views.adapters.FaddergruppeListAdapter;
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
public class FaddergruppeListFragment extends ListFragment {

    List<Faddergruppe> faddergrupper;
    MainApplication application;
    FaddergruppeListAdapter adapter;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onStart() {
        super.onStart();
        application = (MainApplication) getActivity().getApplication();
        FaddergruppeService faddergruppeService = new FaddergruppeServiceImpl(application);
        faddergruppeService.getAll(new Response.Listener<List<Faddergruppe>>() {
                                       @Override
                                       public void onResponse(List<Faddergruppe> faddergruppes) {
                                           adapter.setData(faddergruppes);
                                           adapter.notifyDataSetChanged();
                                       }
                                   }, new Response.ErrorListener() {
                                       @Override
                                       public void onErrorResponse(VolleyError volleyError) {
                                           Log.e("VOLLEY_ERROR", volleyError.getLocalizedMessage(), volleyError);
                                       }
                                   }
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        faddergrupper = new ArrayList<Faddergruppe>();
        adapter = new FaddergruppeListAdapter(inflater.getContext(), R.layout.listview_item_row, faddergrupper, inflater);
        setListAdapter(adapter);

    }
}
