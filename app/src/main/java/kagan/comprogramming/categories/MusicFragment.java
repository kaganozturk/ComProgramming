package kagan.comprogramming.categories;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kagan.comprogramming.Addable;
import kagan.comprogramming.Group;
import kagan.comprogramming.GroupActivity;
import kagan.comprogramming.GroupAdapter;
import kagan.comprogramming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment implements Addable {


    public static final String title = "Music";
    ArrayList<Group> musics;


    public MusicFragment() {
        // Required empty public constructor
        musics = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            musics.add(new Group("Music " + i));
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list, container, false);
        GroupAdapter textViewArrayAdapter = new GroupAdapter(getActivity(), musics);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(textViewArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("object", musics.get(i));
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public void add(Group group) {
        musics.add(group);
    }
}
