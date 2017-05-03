package kagan.comprogramming;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kagan on 1.05.2017.
 */

public class PersonAdapter extends ArrayAdapter<Person> {


    public PersonAdapter(Activity context, ArrayList<Person> members) {
        super(context, 0, members);
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
        imageView.setImageResource(currentPerson.getImageResourceId());


        return listItemView;
    }
}
