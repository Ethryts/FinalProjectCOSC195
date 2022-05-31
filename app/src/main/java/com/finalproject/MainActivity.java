package com.finalproject;

import static android.location.LocationManager.GPS_PROVIDER;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.finalproject.databinding.FragmentListBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CancellationSignal;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.finalproject.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.j256.ormlite.*;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.android.*;


public class MainActivity extends AppCompatActivity implements LocationListener
{
    private static final String TAG = "MainActivity";
    
    
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public static MainActivity mainActivity;
    
    private static final float ACCURACY_METERS = 500;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    
        Log.d(TAG, "onCreate: ");
        mainActivity = this;
        super.onCreate(savedInstanceState);
        
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        setSupportActionBar(binding.toolbar);
        
        onCreateLocation();//Method bellow are made just to make organization easier
        onCreateListeners();
    
    
    }
    
    private void onCreateLocation()
    {
        GPSHandler gpsHandler = new GPSHandler();
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(GPS_PROVIDER, 10, 10, gpsHandler);


//        PeriodicWorkRequest gpsRequest = new PeriodicWorkRequest.Builder(GPSHandler.class, 15,
//                                                                         TimeUnit.MINUTES).build();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    DatabaseController dc = new DatabaseController(getApplicationContext());
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                                                           Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
//                    Location lastLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);
                    Location lastLocation = locationManager.getCurrentLocation(GPS_PROVIDER,
                                                                               new CancellationSignal(), Executors.newSingleThreadExecutor(), e->{});
                    Location taskLocation = new Location((String) null);
                    dc.getAllTasks().forEach(e->{
                        taskLocation.setLongitude((float)e.getLon());
                        taskLocation.setLatitude((float)e.getLat());
                        Log.d(TAG, "run: Getting Location");
                        double lon = (float)e.getLon();
                        double lat = (float)e.getLat();
                        float[] results = new float[1];
                        Log.d(TAG, "run: Lat: "+ lat +", Lon: " + lon);
                        
                        
//                        Location.distanceBetween(lat,lon, lastLocation.getLatitude(),
//                                                 lastLocation.getLongitude(),results);
                     
	                    float dist = lastLocation.distanceTo(taskLocation);
                        Log.d(TAG, "run: distance ="+ dist);
                        
                        if(dist < ACCURACY_METERS)
                        {
//                            Notification new
                        }
                        
                    });

                    
                    
                    
                    
                } catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                };
	            
            	
        
            }
        }, 0, 10, TimeUnit.SECONDS);
    
    }
    
    private void onCreateListeners(){
    
    
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                navController.navigate(R.id.action_FirstFragment_to_newEntryFragment,bundle);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    
    @Override
    public void onLocationChanged(@NonNull Location location)
    {
        double latitude = (double) (location.getLatitude());
        double longitude = (double) (location.getLongitude());
        Log.d(TAG, "onLocationChanged:  Lat: " + latitude + ", long: " + longitude);
    }
    
    @Override
    protected void onResume()
    {
        binding.fab.setVisibility(View.VISIBLE);
        super.onResume();
    }
    
    @Override
    protected void onRestart()
    {
        Log.d(TAG, "onRestart: ");
    	binding.fab.setVisibility(View.VISIBLE);
        super.onRestart();
    }
   @Override
    protected void onStart()
   {
       Log.d(TAG, "onStart: ");
       binding.fab.setVisibility(View.VISIBLE);
       super.onStart();
    
   }
    
    @Override
    protected void onResumeFragments()
    {
        Log.d(TAG, "onResumeFragments: This");
        super.onResumeFragments();
    }
    
    public ActivityMainBinding getBinding()
    {
        return binding;
    }
}