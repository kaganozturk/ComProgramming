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
public class GamesFragment extends Fragment {


    public static final String title = "Games";
    ArrayList<Group> games;
    ListView listView;


    public GamesFragment() {
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
                games = new ArrayList<>();
                games = DbManager.loadGroups(getContext(), 4);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (games != null) {
                    GroupAdapter textViewArrayAdapter = new GroupAdapter(getActivity(), games);
                    listView.setAdapter(textViewArrayAdapter);
                }
            }
        }.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("group", games.get(i));
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
