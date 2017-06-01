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
public class MusicFragment extends Fragment {

    ListView listView;


    public static final String title = "Music";
    ArrayList<Group> musics;


    public MusicFragment() {
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
                musics = new ArrayList<>();
                musics = DbManager.loadGroups(getContext(), 2);
                System.out.println("doinBacground");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (musics != null) {
                    GroupAdapter textViewArrayAdapter = new GroupAdapter(getActivity(), musics);
                    listView.setAdapter(textViewArrayAdapter);
                }
                System.out.println("onPostExecute");
            }
        }.execute();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("group", musics.get(i));
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
