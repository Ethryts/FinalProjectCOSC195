package com.finalproject;

import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.databinding.FragmentFirstBinding;
import com.finalproject.databinding.ListEntryBinding;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    RecyclerView itemList;
    ArrayList<ListTaskEntry> tasks;
    ListAdapter adapter;


    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);


        itemList = (RecyclerView)binding.getRoot().findViewById(R.id.recyclerView);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        tasks = generateDummyData();
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