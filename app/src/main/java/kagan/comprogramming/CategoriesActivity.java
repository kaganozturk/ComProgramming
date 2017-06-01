package kagan.comprogramming;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kagan.comprogramming.categories.ArtsFragment;
import kagan.comprogramming.categories.BookFragment;
import kagan.comprogramming.categories.GamesFragment;
import kagan.comprogramming.categories.MoviesFragment;
import kagan.comprogramming.categories.MusicFragment;
import kagan.comprogramming.categories.SportsFragment;
import kagan.comprogramming.categories.TheaterFragment;
import kagan.comprogramming.categories.TravelFragment;

public class CategoriesActivity extends AppCompatActivity {


    public static Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        person = (Person) getIntent().getSerializableExtra("user");
        System.out.println(person);


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                DbManager.loadUser(CategoriesActivity.this, person);
                return null;
            }
        }.execute();




        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

//        adapter.addFragment(new MyGroups());
        adapter.addFragment(new SportsFragment());
        adapter.addFragment(new ArtsFragment());
        adapter.addFragment(new MusicFragment());
        adapter.addFragment(new MoviesFragment());
        adapter.addFragment(new GamesFragment());
        adapter.addFragment(new TravelFragment());
        adapter.addFragment(new BookFragment());
        adapter.addFragment(new TheaterFragment());


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

        FloatingActionButton addGroup = (FloatingActionButton) findViewById(R.id.floatbutton);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CategoriesActivity.this, CreateGroupActivity.class);
                startActivity(i);
            }
        });
    }


}
