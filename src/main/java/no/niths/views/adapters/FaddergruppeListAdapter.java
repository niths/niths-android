package main.java.no.niths.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import main.java.no.niths.domain.school.Committee;
import main.java.no.niths.domain.school.Faddergruppe;
import no.niths.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 10.05.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class FaddergruppeListAdapter extends BaseAdapter {

    Context context;
    int layoutResourceId;
    List<Faddergruppe> data = null;
    LayoutInflater inflater;

    public FaddergruppeListAdapter(Context context, int layoutResourceId, List<Faddergruppe> data, LayoutInflater inflater) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.inflater = inflater;
    }

    public List<Faddergruppe> getData() {
        return data;
    }

    public void setData(List<Faddergruppe> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FaddergruppeHoldder holder = null;

        if (row == null) {
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FaddergruppeHoldder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtHeaderItem);

            row.setTag(holder);
        } else {
            holder = (FaddergruppeHoldder) row.getTag();
        }

        Faddergruppe faddergruppe = data.get(position);
        holder.txtTitle.setText(String.valueOf(faddergruppe.getGroupNumber()));

        return row;
    }

    class FaddergruppeHoldder {
        TextView txtTitle;
    }
}

