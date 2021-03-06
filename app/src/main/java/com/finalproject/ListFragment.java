package com.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.databinding.FragmentListBinding;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class ListFragment extends Fragment {
    private static final String TAG = "ListFragment";
    public static ListFragment currentInstance;
    

    RecyclerView itemList;
    ArrayList<ListTaskEntry> tasks;
    ListAdapter adapter;
    
    
    
    private FragmentListBinding binding;
    
    public void itemClicked(View v, int adapterPosition)
    {
    
        Bundle bundle = new Bundle();
        int selection = adapterPosition;
        ListTaskEntry entry = tasks.get(adapterPosition);

        bundle.putLong("id",entry.getId());
        bundle.putString("title", entry.getTitle());
        bundle.putDouble("lon", entry.getLon());
        bundle.putDouble("lat", entry.getLat());
        bundle.putString("desc", entry.getDescription());
        NavHostFragment.findNavController(ListFragment.this)
                       .navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
    }
    
    public static ListFragment getInstance(){
        return currentInstance;
    }
    
    @Override
    public View onCreateView(
            
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        currentInstance = this;
    
        binding = FragmentListBinding.inflate(inflater, container, false);


        itemList = (RecyclerView)binding.getRoot().findViewById(R.id.recyclerView);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        try
        {
            DatabaseController dc = new DatabaseController(getContext());
            Log.d(TAG, "onCreateView: " + dc.countTasks());
            tasks = dc.getAllTasks();
            
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        adapter = new ListAdapter(tasks);

        itemList.setLayoutManager(LayoutManager);
        itemList.setAdapter(adapter);

        
        
        View view = binding.getRoot();
        TextView searchTextView = view.findViewById(R.id.searchTextBox);
        view.findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    DatabaseController dc = new DatabaseController(getContext());
                CharSequence searchText =  searchTextView.getText();
                tasks = dc.getAllTasks();
                tasks =
                        tasks.stream().filter(e-> e.getTitle().contains(searchText)).collect(Collectors.toCollection(ArrayList::new));
                    adapter = new ListAdapter(tasks);
    
                    itemList.setAdapter(adapter);
    
                } catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                }
        
            }
        });
        
        

        return  view;
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
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
        binding = null;
        MainActivity.mainActivity.getBinding().fab.setVisibility(View.INVISIBLE);
    }
    
    @Override
    public void onResume()
    {
        Log.d(TAG, "onResume: ");
        
        super.onResume();
        MainActivity.mainActivity.getBinding().fab.setVisibility(View.VISIBLE);
        
    }
    

}