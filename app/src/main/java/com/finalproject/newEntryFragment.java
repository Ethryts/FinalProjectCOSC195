package com.finalproject;

import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.sql.SQLException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newEntryFragment extends Fragment
{
	private static final String TAG = "newEntryFragment";
	
	
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	private String title;
	private double lon;
	private double lat;
	private String desc;
	
	
	private TextView latField;
	private TextView lonField;
	
	
	
	
	public newEntryFragment()
	{
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment newEntryFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static newEntryFragment newInstance(String param1, String param2)
	{
		newEntryFragment fragment = new newEntryFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (getArguments() != null)
		{
//			mParam1 = getArguments().getString(ARG_PARAM1);
//			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		
		
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState)
	{
		
		
		
		// Inflate the layout for this fragment
		
		View view = inflater.inflate(R.layout.new_entry_fragment, container, false);
		
		
		latField = (TextView)view.findViewById(R.id.lonField);
		lonField = (TextView)view.findViewById(R.id.latField);
		
		view.findViewById(R.id.sendNewEntryButton).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			
			{
				try
				{
					title = (String) ((TextView)view.findViewById(R.id.titleField)).getText().toString();
					lon = Double.parseDouble((String) (lonField).getText().toString());
					lat = Double.parseDouble((String) (latField).getText().toString());
					desc = (String) ((TextView)view.findViewById(R.id.entryDesc)).getText().toString();
					
					DatabaseController dc = new DatabaseController(getContext());
					dc.writeTask(new ListTaskEntry(title,lon,lat,desc));
				} catch (SQLException throwables)
				{
					throwables.printStackTrace();
				}
				NavHostFragment.findNavController(newEntryFragment.this).navigate(R.id.action_newEntryFragment_to_FirstFragment);
			}
		});
		
		view.findViewById(R.id.getLocation).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
				Location location = GPSHandler.getLastLocation();
				latField.setText( "" +location.getLatitude());
				lonField.setText( "" +location.getLongitude());
				
				
			}
		});
		
		
		return view;
	}
}