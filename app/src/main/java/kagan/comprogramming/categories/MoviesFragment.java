package kagan.comprogramming.categories;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kagan.comprogramming.DbManager;
import kagan.comprogramming.Group;
import kagan.comprogramming.GroupActivity;
import kagan.comprogramming.GroupAdapter;
import kagan.comprogramming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    public static final String title = "Movies";
    ArrayList<Group> movies;
    ListView listView;


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                movies = new ArrayList<>();
                movies = DbManager.loadGroups(getContext(), 3);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (movies != null) {
                    GroupAdapter textViewArrayAdapter = new GroupAdapter(getActivity(), movies);
                    listView.setAdapter(textViewArrayAdapter);
                }
            }
        }.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("group", movies.get(i));
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
