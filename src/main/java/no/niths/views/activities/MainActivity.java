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

import android.accounts.*;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import main.java.no.niths.views.activities.superclasses.AbstractTokenConsumerActivity;
import main.java.no.niths.views.fragments.faddergruppe.FaddergruppeFragment;
import main.java.no.niths.views.listeners.TabListener;
import main.java.no.niths.views.fragments.Mainpage;
import main.java.no.niths.views.fragments.ProfileFragment;
import no.niths.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is based on the FacebookActivity by Roy Clarkson
 */
public class MainActivity extends AbstractTokenConsumerActivity {

    ActionBar.Tab mainTab, profileTab, faddergruppeTab;
    private List<Fragment> fragments;
    private AccountManager accountManager;
    private Account account;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activiy);
        context = this;

        fragments = new ArrayList<Fragment>();
        fragments.add(new Mainpage());
        final ActionBar actionBar = getActionBar();

        if (isOnline()){
            refreshToken();
        }

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mainTab = actionBar.newTab().setText(getString(R.string.hovedside_tab_title)).setTabListener(new TabListener<Mainpage>(this, "mainpage", Mainpage.class));
        profileTab = actionBar.newTab().setText(getString(R.string.profil_tab_title)).setTabListener(new TabListener<ProfileFragment>(this, "profil", ProfileFragment.class));
        faddergruppeTab = actionBar.newTab().setText(getString(R.string.profil_tab_title)).setTabListener(new TabListener<FaddergruppeFragment>(this, "faddergrupper", FaddergruppeFragment.class));

        actionBar.addTab(mainTab);
        actionBar.addTab(profileTab);
        actionBar.addTab(faddergruppeTab);
    }

    @Override
    protected void newTokenAquired() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
