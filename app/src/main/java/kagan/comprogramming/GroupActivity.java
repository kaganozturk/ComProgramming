package kagan.comprogramming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity {

    ArrayList<Event> events;
    Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        group = (Group) getIntent().getSerializableExtra("object");

        events = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            events.add(new Event("Event name" + i, "antalya", "" + i, "asd"));
        }

        TextView groupName = (TextView) findViewById(R.id.groupTitle);
        groupName.setText(group.toString());

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(group.getImageResourceId());

        EventAdapter textViewArrayAdapter = new EventAdapter(this, events);

        ListView listView = (ListView) findViewById(R.id.listViewEvents);
        listView.setAdapter(textViewArrayAdapter);

        Button member = (Button) findViewById(R.id.members);
        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupActivity.this, MembersActivity.class);
                intent.putExtra("arraylist", group.getMembers());
                startActivity(intent);
            }
        });



    }
}
