package main.java.no.niths.services.auth;

import android.accounts.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
import main.java.no.niths.MainApplication;
import main.java.no.niths.exceptions.NoNITHMailFoundException;
import no.niths.android.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author NITHs
 *
 */
public class AccountController {
    private Context context;
    private Account account;
    private AccountManager accountManager;

    public AccountController(Context context) {
        this.context = context;
        accountManager = AccountManager.get(context);
    }

    /**
     * Checks the accounts on the device
     * @return boolean Success
     * @throws NoNITHMailFoundException
     */
    public boolean checkAccounts()
            throws NoNITHMailFoundException {
        ArrayList<Account> nithAccounts = new ArrayList<Account>();
        
        for (Account account : accountManager.getAccounts()) {
            if (account.name.matches("^\\w+@gmail.no$")) {
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

    public void refreshToken(Activity activity) {
        accountManager.invalidateAuthToken(context.getString(R.string.account_type), MainApplication.token);
        Bundle options = new Bundle();
        accountManager.getAuthToken(
                account,                     // Account retrieved using getAccountsByType()
                "Manage your tasks",            // Auth scope
                options,                        // Authenticator-specific options
                activity,                           // Your activity
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

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_account);
        builder.setItems(nithAccountNames, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                for (String accountName : nithAccountNames) {
                    if (accountName.equals(nithAccountNames[which])) {
                        account = nithAccounts.get(which);
                        Toast.makeText(context, context.getString(
                                R.string.account_chosen),
                                Toast.LENGTH_LONG).show();
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
                Bundle bundle = bundleAccountManagerFuture.getResult();
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
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