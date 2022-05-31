package com.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController
{
	private static final String TAG = "DatabaseController";
	Dao< ListTaskEntry, Integer> dao;
	
	
	
	public DatabaseController(Context context) throws SQLException
	{
		
		 dao = new DatabaseHelper(context).getDao();
	}
	
	
	public void writeTask(ListTaskEntry taskEntry)
	{
		try
		{
			dao.create(taskEntry);
			
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		
	}
	
	public List<ListTaskEntry> readTasks(ListTaskEntry taskEntry)
	{
		try
		{
			return dao.queryForMatching(taskEntry);
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * returns a count of the Database Table
	 * @return
	 */
	public int countTasks()
	{
		try
		{
			return (int) dao.countOf();
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * Deletes the supplied taskEntry from the DB
	 * @param taskEntry
	 */
	public void deleteTask(ListTaskEntry taskEntry)
	{
		try
		{
			dao.delete(taskEntry);
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
	}
	
	
	public ArrayList<ListTaskEntry> getAllTasks()
	{
		try
		{
			return (ArrayList<ListTaskEntry>) dao.queryForAll();
		} catch (SQLException throwables)
		{
			throwables.printStackTrace();
		}
		return null;
	}

	public ListTaskEntry getTaskMatches(ListTaskEntry task)
	{
		try {
			return(ListTaskEntry) dao.queryForMatching(task).get(0);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	
	
}

	
	
	
	
