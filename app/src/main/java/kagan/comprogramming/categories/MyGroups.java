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

import kagan.comprogramming.Group;
import kagan.comprogramming.GroupActivity;
import kagan.comprogramming.GroupAdapter;
import kagan.comprogramming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyGroups extends Fragment {


    public static final String title = "My Groups";
    ArrayList<Group> myGroups;

    public MyGroups() {
        // Required empty public constructor
        myGroups = new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                myGroups = DbManager.loadmyGroups(getContext(), CategoriesActivity.person);
//            }
//        }).start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list, container, false);
        GroupAdapter textViewArrayAdapter = new GroupAdapter(getActivity(), myGroups);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(textViewArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("object", myGroups.get(i));
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public String toString() {
        return title;
    }

}
