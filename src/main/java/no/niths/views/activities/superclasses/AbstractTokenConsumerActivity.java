package main.java.no.niths.views.activities.superclasses;

import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import main.java.no.niths.MainApplication;
import no.niths.android.R;

import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractTokenConsumerActivity extends AbstractAsyncActivity {

    private AccountManager accountManager;
    private Account account;
    private Context context;
    private int ACCOUNTSELECT_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        accountManager = AccountManager.get(this);
        setAccountFromPreferences();

        if (account == null) {
            checkAccounts();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void checkAccounts() {
        Intent selectAccount = getSelectAccountIntent();
        // Får vi null tilbake fra selectAccount betyr det at vi har funnet en account i listen.
        if (selectAccount != null){
            startActivityForResult(selectAccount, ACCOUNTSELECT_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != FragmentActivity.RESULT_CANCELED) {
            if (requestCode != ACCOUNTSELECT_CODE) {
                refreshToken();
            } else {
                String accountname = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(getString(R.string.NITH_EMAIL_KEY), accountname);
                editor.commit();
                setAccountForEmail(accountname);
            }
        }
    }

    private void setAccountFromPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = preferences.getString(getString(R.string.NITH_EMAIL_KEY), null);
        if (email != null){
            setAccountForEmail(email);
        }
    }

    private void setAccountForEmail(String email) {
        Account[] accounts = accountManager.getAccounts();

        for (Account currentAccount : accounts) {
            if (currentAccount.name.equals(email)) {
                account = currentAccount;

            }
        }
    }

    public void refreshToken() {
        if(MainApplication.token != null)
            accountManager.invalidateAuthToken(getString(R.string.account_type), MainApplication.token);
        getToken();
    }

    public void getToken() {
        accountManager.getAuthToken(
                account,                     // Account retrieved using getAccountsByType()
                getString(R.string.google_auth_scope),            // Auth scope
                true,                           // Your activity
                new OnTokenAcquired(),
                null);
    }

    public Intent getSelectAccountIntent() {
        Account[] unfilteredAccounts = accountManager.getAccountsByType("com.google");
        ArrayList<Account> filteredAccounts = new ArrayList<Account>();
        for (Account unfilteredAccount : unfilteredAccounts) {
            if (unfilteredAccount.name.matches("^\\w+@nith.no$")) {
                filteredAccounts.add(unfilteredAccount);
            }
        }
        if (filteredAccounts.size() == 1) {
            account = filteredAccounts.get(0);
        } else {
            return AccountManager.newChooseAccountIntent(null, filteredAccounts, null, true, "Velg en nith konto eller legg til en på enheten", getString(R.string.google_auth_scope), null, null);
        }
        return null;
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
                newTokenAquired();
            } catch (OperationCanceledException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AuthenticatorException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    protected abstract void newTokenAquired();

    public boolean isOnline(){
        MainApplication application = (MainApplication) getApplication();
        return application.isOnline();
    }
}
