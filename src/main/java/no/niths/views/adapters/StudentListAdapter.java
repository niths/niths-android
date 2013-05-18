package main.java.no.niths.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import main.java.no.niths.domain.school.Student;
import no.niths.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: elotin
 * Date: 10.05.13
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
    public class StudentListAdapter extends ArrayAdapter<Student>{

        Context context;
        int layoutResourceId;
        Student data[] = null;
        LayoutInflater inflater;

        public StudentListAdapter(Context context, int layoutResourceId, Student[] data, LayoutInflater inflater) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
            this.inflater = inflater;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            StudentHolder holder = null;

            if(row == null)
            {
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new StudentHolder();
                holder.txtTitle = (TextView)row.findViewById(R.id.txtHeaderItem);

                row.setTag(holder);
            }
            else
            {
                holder = (StudentHolder)row.getTag();
            }

            Student student = data[position];
            holder.txtTitle.setText(student.getFirstName() + " " + student.getLastName());

            return row;
        }

        class StudentHolder
        {
            TextView txtTitle;
        }
    }

