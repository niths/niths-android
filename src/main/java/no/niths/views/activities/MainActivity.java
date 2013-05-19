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
        if (isOnline()){
            refreshToken();
        }

        headers = getResources().getStringArray(R.array.side_menu_title);
        fragmentNames = getResources().getStringArray(R.array.side_menu_fragments);


        fragments = getFragmentsForNames(fragmentNames);
        sideMenu = (ListView)findViewById(R.id.left_drawer);
        sideMenuLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        sideMenuLayout.setDrawerListener(drawerToggle);


        sideMenu.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, headers));
        sideMenu.setOnItemClickListener(new DrawerItemClickListener());
        sideMenu.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        drawerToggle = new ActionBarDrawerToggle(
                this,
                sideMenuLayout,
                R.drawable.ic_drawer,
                R.string.side_menu_open_description,
                R.string.side_menu_close_description

        ){
            @Override
            public void onDrawerClosed(View drawerView) {
                //getActionBar().setTitle("NITHS");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle("Meny");
            }
        };

    }

    @Override
    protected void newTokenAquired() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private List<Fragment> getFragmentsForNames(String[] fragmentNames){
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < fragmentNames.length; i++) {
            try {
                fragmentList.add((Fragment) Class.forName(fragmentNames[i]).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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
            showFragment(selectedFragemnt, headers[position], headers[position]);
            sideMenu.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            sideMenu.setItemChecked(position, true);
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

    public void showFragment(Fragment fragment, String tag, String title){
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
        setTitle(title);
        sideMenuLayout.closeDrawer(sideMenu);
    }
}
