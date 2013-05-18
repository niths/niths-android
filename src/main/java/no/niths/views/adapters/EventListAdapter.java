package main.java.no.niths.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import main.java.no.niths.domain.school.Event;
import no.niths.android.R;

import java.util.List;

/**
 * Created by elotin on 18.05.13.
 */
public class EventListAdapter extends BaseAdapter {

    Context context;
    int layoutResourceId;
    List<Event> data = null;
    LayoutInflater inflater;

    public EventListAdapter(Context context, int layoutResourceId, List<Event> data, LayoutInflater inflater) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.inflater = inflater;
    }

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
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
        EventHolder holder = null;

        if (row == null) {
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EventHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.txtHeaderItem);

            row.setTag(holder);
        } else {
            holder = (EventHolder) row.getTag();
        }

        Event event = data.get(position);
        holder.txtTitle.setText(String.valueOf(event.getName()));

        return row;
    }

    class EventHolder {
        TextView txtTitle;
    }
}
