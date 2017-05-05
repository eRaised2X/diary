package com.eraisedtox94.smartdiary;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IOnListItemClickListener{

    //RecyclerView recyclerView;
    PagerAdapter adapter;

    List<android.support.v4.app.Fragment> listOfAllFragments;
    FragmentDiaryMain fragmentDiaryMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Lorem ipsum");
        toolbar.setLogo(R.drawable.icon_diary);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.colorMyWhite));
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.colorMyWhite));


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        TabLayout.Tab tab1 = tabLayout.newTab();
        TabLayout.Tab tab2 = tabLayout.newTab();
        TabLayout.Tab tab3 = tabLayout.newTab();

        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.addTab(tab3);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(),this);


        tab1.setText(adapter.getPageTitle(0));
        tab2.setText(adapter.getPageTitle(1));
        tab3.setText(adapter.getPageTitle(2));



        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Hack to prevent loading of frag on each swipe
        viewPager.setOffscreenPageLimit(3);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public FragmentDiaryMain getInstanceOfFragmentDairyMain(){

        listOfAllFragments = getSupportFragmentManager().getFragments();
        if(listOfAllFragments == null){
            Log.d("list if fragments","is null");
            return null;
        }
        else {
            Log.d("list if fragments", "woaa!! not null");
            fragmentDiaryMain = (FragmentDiaryMain) listOfAllFragments.get(0);
        }
        return fragmentDiaryMain;
    }


    @Override
    public void listItemClickedListener(String filename) {
        Log.d("this is awesome","communication success");
        if(fragmentDiaryMain == null){
            Log.d("getting fragment main","null...");
            fragmentDiaryMain = getInstanceOfFragmentDairyMain();
        }
        fragmentDiaryMain.readFromFileInExternalStorage(filename);
    }

}
