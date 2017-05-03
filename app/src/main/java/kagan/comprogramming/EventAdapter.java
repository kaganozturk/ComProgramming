package kagan.comprogramming;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kagan on 1.05.2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    public EventAdapter(Activity context, ArrayList<Event> events) {
        super(context, 0, events);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_event, parent, false);
        }

        Event currentEvent = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.eventName);
        name.setText(currentEvent.getName());


        TextView city = (TextView) listItemView.findViewById(R.id.eventCity);
        city.setText(currentEvent.getCity());


        return listItemView;
    }
}
