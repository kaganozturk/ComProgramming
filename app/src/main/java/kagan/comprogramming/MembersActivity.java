package kagan.comprogramming;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        ArrayList<Person> members = (ArrayList<Person>) getIntent().getSerializableExtra("arraylist");

        PersonAdapter personAdapter = new PersonAdapter(this, members);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(personAdapter);


    }
}
