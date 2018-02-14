package com.example.andreea.eat_and_meet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager FM;
    FragmentTransaction FT;
    LinearLayout pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Person loggedUser;
        String emailUser;


        navigationView= (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        View headerview = navigationView.getHeaderView(0);

        emailUser = PersonFactory.getInstance().getLoggedUser();
        loggedUser = PersonFactory.getInstance().getUserByEmail(emailUser);


        TextView profilename = (TextView) headerview.findViewById(R.id.name);
        profilename.setText(loggedUser.getName()+" "+ loggedUser.getSurname());


        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = PersonFactory.getInstance().getLoggedUser();
                Intent t1 = new Intent(HomePage.this, LoggedProfile.class);
                startActivity(t1);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    drawerLayout.closeDrawers();

                    if (item.getItemId()== R.id.events) {
                        Intent t = new Intent(HomePage.this, HomePage.class);
                        startActivity(t);
                    }else if (item.getItemId() == R.id.create_event) {
                        Intent t = new Intent(HomePage.this, CreateEvent.class);
                        startActivity(t);

                        } else if (item.getItemId() == R.id.search_events) {
                        Intent t = new Intent(HomePage.this,SearchEvents.class);
                        startActivity(t);

                        } else if (item.getItemId() == R.id.notifies) {

                        } else if (item.getItemId() == R.id.logout) {
                                Intent t = new Intent(HomePage.this, Login_activity.class);
                                startActivity(t);
                        }

                            return false;
                        }
                    });



        toolbar = (Toolbar)findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("I MIEI EVENTI"));
        tabLayout.addTab(tabLayout.newTab().setText("LE MIE PRENOTAZIONI"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        final SectionsPagerAdapter adapter = new SectionsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    MyEvents tab1 = new MyEvents();
                    return tab1;
                case 1:
                    MyBookings tab2 = new MyBookings();
                    return tab2;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }


}


