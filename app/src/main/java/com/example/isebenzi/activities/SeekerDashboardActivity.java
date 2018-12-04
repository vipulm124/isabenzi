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
import com.example.isebenzi.fragments.HelpSettingsFragment;
import com.example.isebenzi.fragments.SeekerProfileFragment;
import com.example.isebenzi.fragments.SeekerRequestServicesFragment;
import com.example.isebenzi.fragments.SeekerStatusReportsFragment;
import com.example.isebenzi.fragments.ShareFragment;
import com.example.isebenzi.utils.CommonMethods;
import com.example.isebenzi.utils.CommonObjects;

public class SeekerDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//    private SeekerDashboardFragment dashboardFragment = new SeekerDashboardFragment();
    private SeekerRequestServicesFragment seekerRequestServicesFragment=new SeekerRequestServicesFragment();
    private SeekerProfileFragment profileFragment = new SeekerProfileFragment();
    private HelpSettingsFragment helpSettingsFragment = new HelpSettingsFragment();
    private ShareFragment shareFragment = new ShareFragment();
    private SeekerStatusReportsFragment seekerStatusReportsFragment= new SeekerStatusReportsFragment();
    public static FrameLayout flFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_seeker);
        flFrame = (FrameLayout) findViewById(R.id.flFrame);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvName= (TextView) header.findViewById(R.id.tvName);
        if (CommonObjects.getUser().getFirstname()!=null&&CommonObjects.getUser().getLastname()!=null){
            String name=CommonObjects.getUser().getFirstname()+" "+CommonObjects.getUser().getLastname();
            tvName.setText(name);
        }
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        CommonMethods.callFragment(profileFragment, R.id.flFrame, 0, 0, this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvName= (TextView) header.findViewById(R.id.tvNameDrawer);
        try {
            if (CommonObjects.getUser().getFirstname()!=null&&CommonObjects.getUser().getLastname()!=null){
                String name=CommonObjects.getUser().getFirstname()+" "+CommonObjects.getUser().getLastname();
                tvName.setText(name);
            }
        }catch (Exception e){
            e.printStackTrace();
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

//        if (id == R.id.nav_home) {
//            // Handle the camera action
//            if (dashboardFragment == null)
//                dashboardFragment = new SeekerDashboardFragment();
//            CommonMethods.callFragment(dashboardFragment, R.id.flFrame, 0, 0, this);
//        } else
        if (id == R.id.nav_profile) {
            CommonMethods.callFragment(profileFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_status_reports) {
            CommonMethods.callFragment(seekerStatusReportsFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_request_services) {
            CommonMethods.callFragment(seekerRequestServicesFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_help) {
            CommonMethods.callFragment(helpSettingsFragment, R.id.flFrame, 0, 0, this);
        } else if (id == R.id.nav_share) {
            CommonMethods.callFragment(shareFragment, R.id.flFrame, 0, 0, this);
        } else if(id==R.id.nav_logout){
            SeekerDashboardActivity.this.finish();
            CommonMethods.setStringPreference(SeekerDashboardActivity.this, "isebenzi", "email", "");
            CommonMethods.setStringPreference(SeekerDashboardActivity.this, "isebenzi", "password", "");
            CommonMethods.setBooleanPreference(SeekerDashboardActivity.this, "isebenzi", "isLogin", false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
