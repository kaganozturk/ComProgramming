package kagan.comprogramming;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static kagan.comprogramming.GroupAdapter.decodeSampledBitmapFromResource;

public class EventActivity extends AppCompatActivity {
    Event event;
    ArrayList<Person> going;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        System.out.println(CategoriesActivity.person);

        event = (Event) getIntent().getSerializableExtra("event");
        listView = (ListView) findViewById(R.id.eventgoerlistview);


        loadGoers();


        TextView title = (TextView) findViewById(R.id.textView4);
        title.setText(event.getName());

        TextView desc = (TextView) findViewById(R.id.e_description);
        desc.setText(event.getDescription());

        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDirevents", Context.MODE_PRIVATE);
        File f = new File(directory, "" + event.getName());
        try {
            imageView.setImageBitmap(decodeSampledBitmapFromResource(f, 100, 100));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final Button join = (Button) findViewById(R.id.joinEvent);


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        DbManager.joinEvent(EventActivity.this, event, CategoriesActivity.person);
                        return null;
                    }
                }.execute();
                loadGoers();
                Toast.makeText(EventActivity.this, "You are going to the event!!!", Toast.LENGTH_SHORT).show();
                join.setVisibility(View.GONE);
            }
        });


    }

    private void loadGoers() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                going = DbManager.loadGoers(EventActivity.this, event);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (going != null) {
                    PersonAdapter personAdapter = new PersonAdapter(EventActivity.this, going);

                    listView.setAdapter(personAdapter);

                }
            }
        }.execute();
    }
}
