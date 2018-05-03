package com.labela.sinem.myapplication;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.labela.sinem.myapplication.Fragments.CharactersFragment;
import com.labela.sinem.myapplication.Fragments.FavoriteCharactersFragment;
import com.labela.sinem.myapplication.presenter.CharacterItemAdapter;
import com.labela.sinem.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //clearSharedPreferences();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new CharactersFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch(item.getItemId()){
            case R.id.name:
                Toast.makeText(this ,"Characters sorted by name",Toast.LENGTH_SHORT).show();


                break;

            case R.id.birth:
                Toast.makeText(this ,"Characters sorted by birth",Toast.LENGTH_SHORT).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fm.beginTransaction().replace(R.id.content_frame, new CharactersFragment()).commit();
        } else if (id == R.id.nav_gallery) {

            fm.beginTransaction().replace(R.id.content_frame, new FavoriteCharactersFragment()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void clearSharedPreferences() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences(Constants.PREFS_TAG, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();

        sp = getApplicationContext().getSharedPreferences(Constants.CHARACTERS_TAG, 0);
        editor = sp.edit();
        editor.clear().commit();

        SharedPreferences spForCreateTrip = getApplicationContext().getSharedPreferences(Constants.FAVS_TAG, 0);
        SharedPreferences.Editor editorForCreateTrip = spForCreateTrip.edit();
        editorForCreateTrip.clear().commit();
    }

}
