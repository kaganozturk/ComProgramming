package kagan.comprogramming;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import kagan.comprogramming.categories.ArtsFragment;
import kagan.comprogramming.categories.BookFragment;
import kagan.comprogramming.categories.GamesFragment;
import kagan.comprogramming.categories.MoviesFragment;
import kagan.comprogramming.categories.MusicFragment;
import kagan.comprogramming.categories.MyGroups;
import kagan.comprogramming.categories.SportsFragment;
import kagan.comprogramming.categories.TheatreFragment;
import kagan.comprogramming.categories.TravelFragment;

public class Categories extends AppCompatActivity {

    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        categories = new ArrayList<>();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MyGroups());
        adapter.addFragment(new SportsFragment());
        adapter.addFragment(new ArtsFragment());
        adapter.addFragment(new MusicFragment());
        adapter.addFragment(new MoviesFragment());
        adapter.addFragment(new GamesFragment());
        adapter.addFragment(new TravelFragment());
        adapter.addFragment(new BookFragment());
        adapter.addFragment(new TheatreFragment());

        categories.add(MyGroups.title);
        categories.add(SportsFragment.title);
        categories.add(ArtsFragment.title);
        categories.add(MusicFragment.title);
        categories.add(MoviesFragment.title);
        categories.add(GamesFragment.title);
        categories.add(TravelFragment.title);
        categories.add(BookFragment.title);
        categories.add(TheatreFragment.title);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
