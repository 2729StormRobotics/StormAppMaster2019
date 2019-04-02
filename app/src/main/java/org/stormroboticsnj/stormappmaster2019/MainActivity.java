package org.stormroboticsnj.stormappmaster2019;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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

import org.stormroboticsnj.stormappmaster2019.Fragments.DeleteFragment;
import org.stormroboticsnj.stormappmaster2019.Fragments.MatchData;
import org.stormroboticsnj.stormappmaster2019.db.Handler;
import org.stormroboticsnj.stormappmaster2019.Fragments.TeamData;
import org.stormroboticsnj.stormappmaster2019.Fragments.Home;
import org.stormroboticsnj.stormappmaster2019.Fragments.SortTeams;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Home.OnFragmentInteractionListener, SortTeams.OnFragmentInteractionListener,
                    TeamData.OnFragmentInteractionListener, MatchData.OnFragmentInteractionListener, DeleteFragment.OnFragmentInteractionListener {
    private static final int RC_CAMERA_PERM = 42;
    private static final int RC_FILE_PERM = 43;
    DeleteFragment df;
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentMAIN, Home.newInstance("", "")).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        df = new DeleteFragment();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mContext = getApplicationContext();
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
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = Home.class;
        boolean match = false;
        if (id == R.id.btnHome) {
            fragmentClass = Home.class;
        } else if (id == R.id.btnTeamData) {
            fragmentClass = TeamData.class;
        } else if (id == R.id.btnMatchData) {
            fragmentClass = MatchData.class;
            match = true;
        } else if (id == R.id.btnSortTeams) {
            fragmentClass = SortTeams.class;
        } else if (id == R.id.btnQR) {
            scan();
        } else if (id == R.id.btnClear) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Clear Database")
                    .setMessage("This action cannot not be undone.  Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Handler.getInstance(getApplicationContext()).clearTable();
                            Toast.makeText(MainActivity.this, "Database Cleared", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else if (id == R.id.btnDump) {
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Dump Database")
                    .setMessage("Dump the database to a CSV file on the device.  Are you sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dumpDB();
                            Toast.makeText(MainActivity.this, "Database Dumped", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else if (id == R.id.btnTools) {
            fragmentClass = DeleteFragment.class;
        } else if (id == R.id.btnPrivacyPolicy) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1pekyxdfwHPDj2OjCiJmTcjYs2Yio9eCuyKBUQ-bqzcI/edit?usp=sharing"));
            startActivity(browserIntent);
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentMAIN, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @AfterPermissionGranted(RC_FILE_PERM)
    public void dumpDB() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Handler.getInstance(getApplicationContext()).exportCsv();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_file), RC_FILE_PERM, perms);
        }
    }
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void scan(){
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent scan = new Intent(this, QrScanner.class);
            startActivity(scan);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, perms);
        }
    }

    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();

//        // save the note's current draft, because the activity is stopping
//        // and we want to be sure the current note progress isn't lost.
//        ContentValues values = new ContentValues();
//        values.put(NotePad.Notes.COLUMN_NAME_NOTE, getCurrentNoteText());
//        values.put(NotePad.Notes.COLUMN_NAME_TITLE, getCurrentNoteTitle());
//
//        // do this update in background on an AsyncQueryHandler or equivalent
//        mAsyncQueryHandler.startUpdate (
//                mToken,  // int token to correlate calls
//                null,    // cookie, not used here
//                mUri,    // The URI for the note to update.
//                values,  // The map of column names and new values to apply to them.
//                null,    // No SELECT criteria are used.
//                null     // No WHERE columns are used.
//        );

        // TODO***** Save data when master app is closed*****

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void deleteTeamFromMatch(View v) {
        df.deleteTeamFromMatch();
    }

    public void deleteTeam(View v) {
        df.deleteTeam();
    }

    public void deleteDuplicates(View v) {
        df.deleteDuplicates();
    }

    public void deleteMatch(View v) {
        df.deleteMatch();
    }
}