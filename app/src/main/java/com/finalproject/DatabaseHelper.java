package com.finalproject;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
	private static final String TAG = "DatabaseHelper";
	
	
	public static final String DATABASE_NAME = "task.db";
	
	private static final int DATABASE_VERSION = 1;
	
	
	private Dao<ListTaskEntry, Integer> dao = null;
	
	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION, (DatabaseErrorHandler) null);
		
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
	{
		try{
			Log.d(TAG, "onCreate: Creating Database " + getDatabaseName());
			TableUtils.createTable(connectionSource, ListTaskEntry.class);
			
			
		} catch (SQLException throwables)
		{
			Log.d(TAG, "onCreate: Error Thrown " + throwables.getMessage());
			throwables.printStackTrace();
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion)
	{
		try
		{
			Log.d(TAG, "onUpgrade: Upgrade attempted");
			TableUtils.dropTable(connectionSource, ListTaskEntry.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException throwables)
		{
			Log.d(TAG, "onUpgrade: Upgrade Failed");
			throwables.printStackTrace();
		}
	}
	
	
	
	public Dao<ListTaskEntry, Integer> getDao() throws SQLException
	{
		if (dao==null)
		{
			dao = DaoManager.createDao(getConnectionSource(), ListTaskEntry.class);
		}
		return dao;
	}
}
