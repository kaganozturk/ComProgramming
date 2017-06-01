package kagan.comprogramming;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static kagan.comprogramming.GroupAdapter.decodeSampledBitmapFromResource;

/**
 * Created by kagan on 1.05.2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    Activity activity;

    public EventAdapter(Activity context, ArrayList<Event> events) {
        super(context, 0, events);
        activity = context;
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
        city.setText(activity.getResources().getStringArray(R.array.cities)[currentEvent.getCity()]);

        TextView date = (TextView) listItemView.findViewById(R.id.eventDate);
        date.setText(currentEvent.getDate() + "  " + currentEvent.getTime());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView4);

        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDirevents", Context.MODE_PRIVATE);
        File f = new File(directory, "" + currentEvent.getName());
        try {
            imageView.setImageBitmap(decodeSampledBitmapFromResource(f, 100, 100));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return listItemView;
    }
}
