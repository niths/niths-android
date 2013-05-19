package main.java.no.niths.views.listeners;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import no.niths.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 11.05.13
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment fragment;
    private final Activity activity;
    private final String tag;
    private final Class<T> tClass;

    public TabListener(Activity activity, String tag, Class<T> tClass) {
        this.activity = activity;
        this.tag = tag;
        this.tClass = tClass;
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if(fragment == null) {
            fragment = Fragment.instantiate(activity, tClass.getName());
            fragmentTransaction.add(R.id.fragment_container, fragment, tag);
        } else {
            fragmentTransaction.attach(fragment);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (fragment != null){
            fragmentTransaction.detach(fragment);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
