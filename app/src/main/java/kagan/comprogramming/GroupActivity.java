package kagan.comprogramming;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static kagan.comprogramming.GroupAdapter.decodeSampledBitmapFromResource;

public class GroupActivity extends AppCompatActivity {

    ArrayList<Event> events;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        group = (Group) getIntent().getSerializableExtra("group");
        final ListView listView = (ListView) findViewById(R.id.listViewEvents);

        System.out.println(CategoriesActivity.person);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                events = DbManager.loadEvents(GroupActivity.this, group);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (events != null) {
                    EventAdapter eventAdapter = new EventAdapter(GroupActivity.this, events);

                    listView.setAdapter(eventAdapter);
                }
            }
        }.execute();


        TextView desc = (TextView) findViewById(R.id.description);
        desc.setText(group.getDescription());


        TextView groupName = (TextView) findViewById(R.id.groupTitle);
        groupName.setText(group.toString());

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File f = new File(directory, "" + group.getName());

        try {
            imageView.setImageBitmap(decodeSampledBitmapFromResource(f, 100, 100));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GroupActivity.this, EventActivity.class);
                intent.putExtra("event", events.get(i));
                startActivity(intent);
            }
        });

//        Button member = (Button) findViewById(R.id.members);
//        member.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(GroupActivity.this, MembersActivity.class);
//                startActivity(intent);
//            }
//        });


//        Button join = (Button) findViewById(R.id.join);
//        join.setVisibility(View.INVISIBLE);


        Button event = (Button) findViewById(R.id.createEvent);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GroupActivity.this, CreateEventActivity.class);
                i.putExtra("asd", group);
                startActivity(i);
            }
        });


    }
}
