package com.eutechpro.movies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

@Deprecated
@SuppressLint("Registered")
public class DrawerActivity extends AppCompatActivity implements MvpActivityCallback, NavigationView.OnNavigationItemSelectedListener {
    protected NavigationView       navigationView;
    protected FloatingActionButton fab;
    private   DrawerLayout         drawer;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    public void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera){
            // Handle the camera action
        } else if (id == R.id.nav_gallery){

        } else if (id == R.id.nav_slideshow){

        } else if (id == R.id.nav_manage){

        } else if (id == R.id.nav_share){

        } else if (id == R.id.nav_send){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showToast(@StringRes int messageId, boolean shouldKillActivity) {
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
        killIfNeeded(shouldKillActivity);
    }

    @Override
    public void showToast(@StringRes int messageId, String params, boolean shouldKillActivity) {
        Toast.makeText(this, getString(messageId, params), Toast.LENGTH_SHORT).show();
        killIfNeeded(shouldKillActivity);
    }


    @Override
    public void openActivity(Bundle bundle, Class activityClass, boolean shouldKillActivity) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivity(intent);
        killIfNeeded(shouldKillActivity);
    }

    @Override
    public void openActivity(Bundle bundle, int requestCode, Class activityClass, boolean shouldKillActivity) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
        killIfNeeded(shouldKillActivity);
    }

    @Override
    public void openActivity(Intent intent, boolean shouldKillActivity) {
        startActivity(intent);
        killIfNeeded(shouldKillActivity);
    }

    private void killIfNeeded(boolean shouldKillActivity) {
        if (shouldKillActivity){
            finish();
        }
    }
}
