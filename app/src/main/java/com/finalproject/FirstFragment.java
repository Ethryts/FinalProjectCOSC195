package com.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.databinding.FragmentFirstBinding;

import java.sql.SQLException;
import java.util.ArrayList;

public class FirstFragment extends Fragment {
    private static final String TAG = "FirstFragment";
    public static FirstFragment currentInstance;
    

    RecyclerView itemList;
    ArrayList<ListTaskEntry> tasks;
    ListAdapter adapter;


    private FragmentFirstBinding binding;
    
    public void itemClicked(View v, int adapterPosition)
    {
    
        Bundle bundle = new Bundle();
        int selection = adapterPosition;
        bundle.putInt("selection", selection);
        NavHostFragment.findNavController(FirstFragment.this)
                       .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
    }
    
    public static FirstFragment getInstance(){
        return currentInstance;
    }
    
    
    
    @Override
    public View onCreateView(
            
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        currentInstance = this;
    
        binding = FragmentFirstBinding.inflate(inflater, container, false);


        itemList = (RecyclerView)binding.getRoot().findViewById(R.id.recyclerView);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        try
        {
            DatabaseController dc = new DatabaseController(getContext());
            dc.writeTask(generateDummyData().get(0));
//            dc.readTasks(tasks.get(0));
    
            Log.d(TAG, "onCreateView: " + dc.countTasks());
            tasks = dc.getAllTasks();
            
            
            
            
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        adapter = new ListAdapter(tasks);

        itemList.setLayoutManager(LayoutManager);
        itemList.setAdapter(adapter);


        return binding.getRoot();






    }

    public ArrayList<ListTaskEntry> generateDummyData(){
        ArrayList<ListTaskEntry> returnList = new ArrayList<ListTaskEntry>();
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        returnList.add(new ListTaskEntry("Title", 39.2, 20.3, "asdf adsf That"));
        return returnList;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}