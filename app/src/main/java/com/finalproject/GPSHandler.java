package com.finalproject;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

public class GPSHandler implements LocationListener
{
	private static final String TAG = "GPSHandler";
	private static GPSHandler currentInstance;
	private static Location lastLocation;
	
	public static GPSHandler getInstance(){
		return currentInstance;
	}
	
	public GPSHandler(){
		if(GPSHandler.getInstance() == null)
		{
			GPSHandler.currentInstance = this;
		}
	}
	
	public static Location getLastLocation()
	{
		return lastLocation;
	}
	
	@Override
	public void onLocationChanged(@NonNull Location location)
	{
		lastLocation = location;
		Log.d(TAG, "onLocationChanged: " + location.getLatitude() + ", " + location.getLongitude());
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
