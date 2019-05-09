package sg.edu.rp.c346.demodatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Task> {

    private Context context;
    private ArrayList<Task> tasks;
    private TextView tvID, tvDesc, tvDate;

    public CustomAdapter(Context context, int resource, ArrayList<Task> objects){
        super(context,resource,objects);
        tasks = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);

        tvID = rowView.findViewById(R.id.tvID);
        tvDate = rowView.findViewById(R.id.tvDate);
        tvDesc = rowView.findViewById(R.id.tvDesc);

        Task currentTasks = tasks.get(position);
        tvID.setText(Integer.toString(currentTasks.getId()));
        tvDesc.setText(currentTasks.getDescription());
        tvDate.setText(currentTasks.getDate());

        return rowView;
    }

}
