package com.finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.finalproject.databinding.FragmentTaskViewBinding;

import java.sql.SQLException;

public class TaskViewFragment extends Fragment {
    private static final String TAG = "TaskViewFragment";
    

    private FragmentTaskViewBinding binding;
    private ListTaskEntry task;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentTaskViewBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        task =  new ListTaskEntry();
        Bundle b = getArguments();

        Log.d(TAG, "onViewCreated: " + b.get("selection"));
        task.setId(b.getLong("id"));
        try {
            task = new DatabaseController(getContext()).getTaskMatches(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextView titleText = view.findViewById(R.id.entryTitleText);
        TextView lonText =view.findViewById(R.id.entryLongText);
        TextView latText =view.findViewById(R.id.entryLatTest);
        TextView descText = view.findViewById(R.id.entryDesc);
        
        
        
        titleText.setText(b.getString("title"));
        lonText.setText(b.get("lon").toString());
        latText.setText(b.get("lat").toString());
        descText.setText(b.get("desc").toString());
        
        
        
        

        binding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TaskViewFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    DatabaseController db = new DatabaseController(getContext());
                    NavHostFragment.findNavController(TaskViewFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                    db.deleteTask(task);



                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        
        binding = null;
        
    }
    
    
}