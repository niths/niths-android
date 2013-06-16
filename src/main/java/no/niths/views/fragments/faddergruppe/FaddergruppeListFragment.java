package main.java.no.niths.views.fragments.faddergruppe;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ListView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import main.java.no.niths.MainApplication;
import main.java.no.niths.domain.school.Event;
import main.java.no.niths.domain.school.Faddergruppe;
import main.java.no.niths.services.domain.school.FaddergruppeServiceImpl;
import main.java.no.niths.services.domain.school.interfaces.FaddergruppeService;
import main.java.no.niths.services.domain.school.superclass.GenericCrudServiceOperator;
import main.java.no.niths.views.adapters.FaddergruppeListAdapter;
import main.java.no.niths.views.fragments.superclasses.RefreshableListFragment;
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
public class FaddergruppeListFragment extends RefreshableListFragment<Faddergruppe> {

    public final static String FRAGMENT_TAG = "SHOW_EVENT_FRAGMENT";
    List<Faddergruppe> faddergrupper;
    MainApplication application;
    FaddergruppeListAdapter adapter;
    View headerView;
    FaddergruppeService service;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        application = (MainApplication) getActivity().getApplication();
        service = new FaddergruppeServiceImpl(application.getTokenBundle(), application.getRequestQueue(), application.getApplicationContext());
        loadItems(new Response.Listener<List<Faddergruppe>>() {
            @Override
            public void onResponse(List<Faddergruppe> fadderGrupper) {
                adapter.setData(fadderGrupper);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setListAdapter(null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        faddergrupper = new ArrayList<Faddergruppe>();
        if (headerView == null) {
            headerView = getActivity().getLayoutInflater().inflate(R.layout.listview_item_header, null);
        }

        if (getListView().getHeaderViewsCount() == 0) {

            getListView().addHeaderView(headerView);
        }
        adapter = new FaddergruppeListAdapter(inflater.getContext(), R.layout.listview_item_row, faddergrupper, inflater);
        setListAdapter(adapter);
    }


    @Override
    protected void refreshView(final MenuItem item) {
        loadItems(new Response.Listener<List<Faddergruppe>>() {
            @Override
            public void onResponse(List<Faddergruppe> fadderGrupper) {
                adapter.setData(fadderGrupper);
                adapter.notifyDataSetChanged();
                item.setActionView(null);
            }
        });
    }

    @Override
    protected GenericCrudServiceOperator<Faddergruppe> getService() {
        return (GenericCrudServiceOperator<Faddergruppe>) service;
    }
}
