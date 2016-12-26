package net.volgatech.lks.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.volgatech.lks.R;
import net.volgatech.lks.fragments.AchievementsFragment;
import net.volgatech.lks.fragments.EducationFragment;
import net.volgatech.lks.fragments.ExamsFragment;
import net.volgatech.lks.fragments.GrantsFragment;
import net.volgatech.lks.fragments.InfoFragment;
import net.volgatech.lks.fragments.ScheduleFragment;
import net.volgatech.lks.fragments.ProgressFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean viewIsAtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        displayView(R.id.info_page);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (!viewIsAtHome) {
            displayView(R.id.info_page);
        } else if (!drawer.isDrawerOpen(GravityCompat.START)){
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId){

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.info_page:
                fragment = new InfoFragment();
                title  = getString(R.string.info);
                viewIsAtHome = true;
                break;
            case R.id.progress:
                fragment = new ProgressFragment();
                title = getString(R.string.progress);
                viewIsAtHome = false;
                break;
            case R.id.exams:
                fragment = new ExamsFragment();
                title = getString(R.string.exams);
                viewIsAtHome = false;
                break;
            case R.id.achievements:
                fragment = new AchievementsFragment();
                title = getString(R.string.achievements);
                viewIsAtHome = false;
                break;
            case R.id.schedule:
                fragment = new ScheduleFragment();
                title = getString(R.string.schedule);
                viewIsAtHome = false;
                break;
            case R.id.grants:
                fragment = new GrantsFragment();
                title = getString(R.string.grants);
                viewIsAtHome = false;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
