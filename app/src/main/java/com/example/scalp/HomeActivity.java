package com.example.scalp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private static final String CHANNEL_ID = "01";
    private DrawerLayout drawer;
    private TextView headerUsername;
    private TextView headerEmail;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDB = new DatabaseHelper(this);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TicketsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_ticket);
        }

        //Get the headerview from navigation view
        View headerView = navigationView.getHeaderView(0);

        //get the username/email textview from the header
        TextView navUsername = (TextView) headerView.findViewById(R.id.home_header_username);
        TextView navEmail = (TextView) headerView.findViewById(R.id.home_header_email);

        //grab the data from shared preferences
        String shUsername = mPreferences.getString(getString(R.string.sessionUserName), "");
        //String shEmail = mPreferences.getString(getString(R.string.sessionEmail), "");
        Cursor data = myDB.getUserEmail(shUsername);
        String shEmail = "";
        while (data.moveToNext()) {
            shEmail = data.getString(0);
        }

        //set in textview
        navUsername.setText(shUsername);
        navEmail.setText(shEmail);


        createNotificationChannel();

        Intent intent = getIntent();
        Boolean extra = intent.getBooleanExtra("notification", false);

        if(extra == true){
            String title = "SUCCESS!";
            String text = "A new ticket has been added to the Scalp database!";
            notificationCall(title, text);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.nav_ticket:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TicketsFragment()).commit();
                break;
            case R.id.nav_post_ticket:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PostTicketFragment()).commit();
                break;
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotificationsFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
                break;
            case R.id.nav_sign_out:
                Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show();
                Intent signout = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(signout);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void cameraButton(View view) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void notificationCall(String title, String text) {
        NotificationCompat.Builder notifBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.notify(1, notifBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channelName);
            String description = getString(R.string.channelDesc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
