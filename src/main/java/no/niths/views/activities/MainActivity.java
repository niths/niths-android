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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
import main.java.no.niths.MainApplication;
import main.java.no.niths.exceptions.NoNITHMailFoundException;
import main.java.no.niths.views.listeners.TabListener;
import main.java.no.niths.views.activities.superclasses.AbstractAsyncActivity;
import main.java.no.niths.views.fragments.Mainpage;
import main.java.no.niths.views.fragments.ProfileFragment;
import no.niths.android.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is based on the FacebookActivity by Roy Clarkson
 */
public class MainActivity extends AbstractAsyncActivity {

    ActionBar.Tab mainTab;
    ActionBar.Tab profileTab;
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


        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mainTab = actionBar.newTab().setText(getString(R.string.hovedside_tab_title)).setTabListener(new TabListener<Mainpage>(this, "mainpage", Mainpage.class));
        profileTab = actionBar.newTab().setText(getString(R.string.profil_tab_title)).setTabListener(new TabListener<ProfileFragment>(this, "mainpage", ProfileFragment.class));

        actionBar.addTab(mainTab);
        actionBar.addTab(profileTab);
    }

    @Override
    protected void onStart() {
        super.onStart();
        accountManager = AccountManager.get(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("TOKEN", preferences.getString(getString(R.string.niths_token_key), "null"));

        if (account == null){
            checkAccounts();
            refreshToken();
        }

        Log.d("Account", account.name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK){
            refreshToken();
        }
    }

    public boolean checkAccounts()
            throws NoNITHMailFoundException {
        ArrayList<Account> nithAccounts = new ArrayList<Account>();

        for (Account account : accountManager.getAccounts()) {
            if (account.name.matches("^\\w+@nith.no$")) {
                nithAccounts.add(account);
            }
        }

        final int size = nithAccounts.size();
        boolean success = false;

        if (account != null) {
            success = true;
        } else if (size > 1) {
            promptAccount(nithAccounts);
        } else if (size == 1) {
            account = nithAccounts.get(0);
            success = true;
        } else {
            throw new NoNITHMailFoundException();
        }

        return success;
    }

    public void refreshToken() {
        accountManager.invalidateAuthToken(getString(R.string.account_type), MainApplication.token);
        getToken();
    }

    public void getToken() {
        accountManager.getAuthToken(
                account,                     // Account retrieved using getAccountsByType()
                "oauth2:https://www.googleapis.com/auth/userinfo.email",            // Auth scope
                true,                           // Your activity
                new OnTokenAcquired(),
                null);
    }

    /**
     *
     * @param nithAccounts The accounts to choose from
     * @return The name which was chosen
     */
    public void promptAccount(final ArrayList<Account> nithAccounts) {
        final String[] nithAccountNames = new String[nithAccounts.size()];
        for (int i = 0; i < nithAccountNames.length; i++) {
            nithAccountNames[i] =nithAccounts.get(i).name;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_account);
        builder.setItems(nithAccountNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                for (String accountName : nithAccountNames) {
                    if (accountName.equals(nithAccountNames[which])) {
                        account = nithAccounts.get(which);
                        Toast.makeText(context, context.getString(
                                R.string.account_chosen),
                                Toast.LENGTH_LONG).show();
                        refreshToken();
                    }
                }
            }
        });
        builder.create();
        builder.show();
    }

    public Account getAccount() {
        return account;
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> bundleAccountManagerFuture) {
            try {
                Bundle result = bundleAccountManagerFuture.getResult();
                String token = result.getString(AccountManager.KEY_AUTHTOKEN);
                Intent launch = (Intent) result.get(AccountManager.KEY_INTENT);
                if (launch != null) {
                    startActivityForResult(launch, 0);
                    return;
                }
                MainApplication.token = token;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor edit = preferences.edit();
                edit.putString(getString(R.string.google_token_key), token);
                edit.commit();
            } catch (OperationCanceledException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AuthenticatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
