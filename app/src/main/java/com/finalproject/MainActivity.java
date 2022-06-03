package com.finalproject;

import static android.location.LocationManager.FUSED_PROVIDER;
import static android.location.LocationManager.GPS_PROVIDER;

import static com.finalproject.GPSHandler.*;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLink;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.finalproject.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


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
//        locationManager.requestLocationUpdates(FUSED_PROVIDER, 10, 10, gpsHandler);


//        PeriodicWorkRequest gpsRequest = new PeriodicWorkRequest.Builder(GPSHandler.class, 15,
//                                                                         TimeUnit.MINUTES).build();
//        Intent intent = new Intent(this, GPSService.class);
//        startService(intent);
        
        
        ScheduledExecutorService testScheduler = Executors.newScheduledThreadPool(1);
        testScheduler.scheduleAtFixedRate(new Runnable()
        {
            @Override
            public void run()
            {
    
            }
        }, 0,10, TimeUnit.SECONDS);
        
        
        
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
                    
                    Consumer<Location> locationConsumer = location -> {
                        Log.d(TAG, "run: inside the Consumer Location is " + location);
                        GPSHandler.lastLocation= location;
                        GPSHandler.getInstance().setLocalLocal(location);
                    };
//                    Location lastLocation = locationManager.getLastKnownLocation(GPS_PROVIDER);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Log.d(TAG, "run: This is inside the version THing");
                        locationManager.getCurrentLocation(
                                GPS_PROVIDER,
                                new CancellationSignal(),
                                Executors.newSingleThreadExecutor(),
                                locationConsumer
                        );
                    }
                    else{
    
                        Log.d(TAG, "run: Test");
                    }



                    Location taskLocation = new Location((String) null);
                    dc.getAllTasks().forEach(e->{
                        taskLocation.setLongitude((float)e.getLon());
                        taskLocation.setLatitude((float)e.getLat());
                        Log.d(TAG, "run: Getting Location");
                        Log.d(TAG,
                              "run: TaskLocation Lat: "+ taskLocation.getLatitude()  +", Lon: " + taskLocation.getLongitude());
                        GPSHandler.getInstance().getLocalLocal();
                        Log.d(TAG, "run: LastLocation Lat: "+ lastLocation.getLatitude() +", Lon:" +
                                   " " + lastLocation.getLongitude());
    
                        Log.d(TAG, "run: ===============================================");

//                        Location.distanceBetween(lat,lon, lastLocation.getLatitude(),
//                                                 lastLocation.getLongitude(),results);

//	                    float dist = lastLocation.distanceTo(taskLocation);
//                        LocationManager lm;
//
//                        Log.d(TAG, "run: distance =" + dist);

//                        if(dist < ACCURACY_METERS)
//                        {
//                            Notification new
//                        }

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
            }
        });
    }
    
    
    public void sendNotification(ListTaskEntry entry){
    
        Log.d(TAG, "sendNotification: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Channel_1", "name",
                                                                  NotificationManager.IMPORTANCE_LOW);
            channel.setShowBadge(true); // set false to disable badges, Oreo exclusive
    
            NotificationManager notificationManager =
                    (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
        // the check ensures that the channel will only be made
        // if the device is running Android 8+
    
        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(this, "Channel_1");
        // the second parameter is the channel id.
        // it should be the same as passed to the makeNotificationChannel() method
    
    
        Bundle bundle = new Bundle();
    
        bundle.putLong("id",entry.getId());
        bundle.putString("title", entry.getTitle());
        bundle.putDouble("lon", entry.getLon());
        bundle.putDouble("lat", entry.getLat());
        bundle.putString("desc", entry.getDescription());
        
        
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        PendingIntent intent = new NavDeepLinkBuilder(getApplicationContext())
                .setComponentName(MainActivity.class)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.SecondFragment)
                .setArguments(bundle)
                .createPendingIntent();
        
        notification
                .setSmallIcon(R.mipmap.ic_launcher) // can use any other icon
                .setContentTitle("Reminder for this Location!")
                .setContentText("You have a reminder titled: " + entry.getTitle() + " for this " +
                                "location")
                .setContentIntent(intent);
    
        
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    
        assert notificationManager != null;
        notificationManager.notify(1, notification.build());
        // it is better to not use 0 as notification id, so used 1.
        
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