package com.example.isebenzi.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.isebenzi.R;
import com.example.isebenzi.fragments.ProviderDashboardFragment;
import com.example.isebenzi.fragments.HelpSettingsFragment;
import com.example.isebenzi.fragments.MyJobsFragment;
import com.example.isebenzi.fragments.ProfileFragment;
import com.example.isebenzi.fragments.ShareFragment;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

public class ProviderDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProviderDashboardFragment dashboardFragment = new ProviderDashboardFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private MyJobsFragment myJobsFragment = new MyJobsFragment();
    private HelpSettingsFragment helpSettingsFragment = new HelpSettingsFragment();
    private ShareFragment shareFragment = new ShareFragment();
    public static FrameLayout flFrame;

    public static FrameLayout getFlFrame() {
        return flFrame;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_pro);
        flFrame= (FrameLayout) findViewById(R.id.flFrame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvName= (TextView) header.findViewById(R.id.tvNameDrawer);
        if (CommonObjects.getUser().getFirstname()!=null&&CommonObjects.getUser().getLastname()!=null){
            String name=CommonObjects.getUser().getFirstname()+" "+CommonObjects.getUser().getLastname();
            tvName.setText(name);
        }
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        CommonMethods.callFragment(dashboardFragment, R.id.flFrame, 0, 0, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvName= (TextView) header.findViewById(R.id.tvNameDrawer);
        if (CommonObjects.getUser().getFirstname()!=null&&CommonObjects.getUser().getLastname()!=null){
            String name=CommonObjects.getUser().getFirstname()+" "+CommonObjects.getUser().getLastname();
            tvName.setText(name);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.dashboard, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            if(dashboardFragment==null)
                dashboardFragment=new ProviderDashboardFragment();
            CommonMethods.callFragment(dashboardFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_profile) {
            if(profileFragment==null)
                profileFragment=new ProfileFragment();
            CommonMethods.callFragment(profileFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_my_jobs) {
            if(myJobsFragment==null)
                myJobsFragment= new MyJobsFragment();
            CommonMethods.callFragment(myJobsFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_help) {
            if(helpSettingsFragment==null)
                helpSettingsFragment= new HelpSettingsFragment();
            CommonMethods.callFragment(helpSettingsFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_share) {
            if(shareFragment==null)
                shareFragment= new ShareFragment();
            CommonMethods.callFragment(shareFragment, R.id.flFrame, 0, 0, this);
        }else if(id==R.id.nav_logout){
            ProviderDashboardActivity.this.finish();
            CommonMethods.setStringPreference(ProviderDashboardActivity.this, "isebenzi", "Proemail", "");
            CommonMethods.setStringPreference(ProviderDashboardActivity.this, "isebenzi", "Propassword", "");
            CommonMethods.setBooleanPreference(ProviderDashboardActivity.this, "isebenzi", "ProisLogin", false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
