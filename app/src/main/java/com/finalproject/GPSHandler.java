package com.finalproject;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GPSHandler implements LocationListener
{
	private static final String TAG = "GPSHandler";
	private static GPSHandler currentInstance;
	public static Location lastLocation;
	public Location localLocal;
	private static final int DISTANCE_FOR_CLOSE = 100;
	
	
	public static GPSHandler getInstance(){
		return currentInstance;
	}
	
	
	public void setLocalLocal(Location localLocal)
	{
		this.localLocal = localLocal;
	}
	
	public Location getLocalLocal()
	{
		return localLocal;
	}
	
	public GPSHandler(){
		super();
		if(GPSHandler.getInstance() == null)
		{
			GPSHandler.currentInstance = this;
		}
	}
	
	
	public static Location getLastLocation()
	{
		return lastLocation;
	}
	
	public void pollLocation(Location location)
	{
		ArrayList<ListTaskEntry>  list = ListFragment.getInstance().tasks;
		list.forEach(task->{
			Location taskLocation = new Location((String) null);
			taskLocation.setLongitude(task.getLon());
			taskLocation.setLatitude(task.getLat());
			if(location.distanceTo(taskLocation) < DISTANCE_FOR_CLOSE){
				Log.d(TAG, "pollLocation: close To Task");
				MainActivity.mainActivity.sendNotification(task);
			}
		});
	
	}
	
	@Override
	public void onLocationChanged(@NonNull Location location)
	{
		lastLocation = location;
		Log.d(TAG, "onLocationChanged: " + location.getLatitude() + ", " + location.getLongitude());
		pollLocation(location);
	}
	
	
	@Override
	public void onLocationChanged(@NonNull List<Location> locations)
	{
		Log.d(TAG, "onLocationChanged: "+ locations.get(0));
		LocationListener.super.onLocationChanged(locations);
	}
	
	@Override
	public void onFlushComplete(int requestCode)
	{
		LocationListener.super.onFlushComplete(requestCode);
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		try{
			LocationListener.super.onStatusChanged(provider, status, extras);
			
		}catch (AbstractMethodError e)
		{
		
		}
	}
	
	@Override
	public void onProviderEnabled(@NonNull String provider)
	{
		LocationListener.super.onProviderEnabled(provider);
	}
	
	@Override
	public void onProviderDisabled(@NonNull String provider)
	{
		LocationListener.super.onProviderDisabled(provider);
	}
	
	
	
	
}
