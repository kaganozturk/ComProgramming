package kagan.comprogramming;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kagan on 17.04.2017.
 */

public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(Activity context, ArrayList<Group> groups) {
        super(context, 0, groups);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_group, parent, false);
        }

        Group currentGroup = getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.textGroup);
        textView.setText(currentGroup.toString());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageGroup);
        imageView.setImageResource(currentGroup.getImageResourceId());

        return listItemView;

    }
}
