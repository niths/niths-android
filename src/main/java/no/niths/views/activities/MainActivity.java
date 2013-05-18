/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package main.java.no.niths.views.activities;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import main.java.no.niths.views.activities.superclasses.AbstractTokenConsumerActivity;
import main.java.no.niths.views.fragments.Mainpage;
import no.niths.android.R;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is based on the FacebookActivity by Roy Clarkson
 */
public class MainActivity extends AbstractTokenConsumerActivity {

    //ActionBar.Tab mainTab, profileTab, faddergruppeTab, eventsTab;
    private List<Fragment> fragments;
    private String[] fragmentNames;
    private String[] headers;
    private Context context;
    private ListView sideMenu;
    private DrawerLayout sideMenuLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activiy);
        context = this;

        //fragments = new ArrayList<Fragment>();
        //fragments.add(new Mainpage());
        //final ActionBar actionBar = getActionBar();
/*
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mainTab = actionBar.newTab().setText(getString(R.string.hovedside_tab_title)).setTabListener(new TabListener<Mainpage>(this, "mainpage", Mainpage.class));
        profileTab = actionBar.newTab().setText(getString(R.string.profil_tab_title)).setTabListener(new TabListener<ProfileFragment>(this, "profil", ProfileFragment.class));
        faddergruppeTab = actionBar.newTab().setText(getString(R.string.faddergrupper_tab_title)).setTabListener(new TabListener<FaddergruppeListFragment>(this, "faddergrupper", FaddergruppeListFragment.class));
        eventsTab = actionBar.newTab().setText(getString(R.string.events_tab_title)).setTabListener(new TabListener<EventListFragment>(this, "faddergrupper", EventListFragment.class));

        actionBar.addTab(mainTab);
        actionBar.addTab(profileTab);
        actionBar.addTab(faddergruppeTab);
        actionBar.addTab(eventsTab);
        */



        if (isOnline()){
            refreshToken();
        }

        headers = getResources().getStringArray(R.array.side_menu_title);
        fragmentNames = getResources().getStringArray(R.array.side_menu_fragments);

        try {
            fragments = getFragmentsForNames(fragmentNames);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        sideMenu = (ListView)findViewById(R.id.left_drawer);
        sideMenuLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                sideMenuLayout,
                R.drawable.ic_drawer,
                R.string.side_menu_open_description,
                R.string.side_menu_close_description

        ){
            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle("NITHS");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Meny");
            }
        };
        sideMenuLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        sideMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, headers));
        sideMenu.setOnItemClickListener(new DrawerItemClickListener());

    }

    @Override
    protected void newTokenAquired() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private List<Fragment> getFragmentsForNames(String[] fragmentNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < fragmentNames.length; i++) {
            fragmentList.add((Fragment) Class.forName(fragmentNames[i]).newInstance());
        }
        return fragmentList;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }

        private void selectItem(int position) {
            Fragment selectedFragemnt = fragments.get(position);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.fragment_continer, selectedFragemnt, headers[position])
                    .commit();
            sideMenu.setItemChecked(position, true);
            setTitle(headers[position]);
            sideMenuLayout.closeDrawer(sideMenu);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
