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

public class PersonAdapter extends ArrayAdapter<Person> {


    Activity activity;
    public PersonAdapter(Activity context, ArrayList<Person> members) {
        super(context, 0, members);
        activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_members, parent, false);
        }

        Person currentPerson = getItem(position);

        TextView name = (TextView) listItemView.findViewById(R.id.name);
        name.setText(currentPerson.toString());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.profilePhoto);
        ContextWrapper cw = new ContextWrapper(activity.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDirpersons", Context.MODE_PRIVATE);
        File f = new File(directory, "" + currentPerson.getNickname() + currentPerson.getPassword());
        try {
            imageView.setImageBitmap(decodeSampledBitmapFromResource(f, 100, 100));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return listItemView;
    }
}
