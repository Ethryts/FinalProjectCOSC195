package com.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    private static final String TAG = "ListAdapter";
    private static final String Tag = "ListAdapter";

    ArrayList<ListTaskEntry> entryList;
//    String title;
//    double lon;
//    double lat;
//    String description;

    /**
     * Initializes the Data for the View
     */
    public ListAdapter(ArrayList<ListTaskEntry> entryList) {
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_entry, parent, false);
    
    
        
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.getTitleText().setText(entryList.get(position).getTitle());
        holder.getLatText().setText(""+ entryList.get(position).getLat());
        holder.getLonText().setText(""+ entryList.get(position).getLon());
        holder.getDescText().setText(entryList.get(position).getDescription());


    }

    @Override
    public int getItemCount() {
        return entryList != null? entryList.size(): 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleText;
        private TextView lonText;
        private TextView latText;
        private TextView descText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
    
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                	ListFragment.getInstance().itemClicked(v, getAdapterPosition());
//                    NavHostFragment.findNavController(.this)
//                                   .navigate(R.id.action_SecondFragment_to_FirstFragment);
//                    Log.d(TAG, "onClick: "+ getAdapterPosition());
//                    Bundle bundle = new Bundle();
//                    int selection = getAdapterPosition();
//                    bundle.putInt("selection", selection);
//
//
//
//                    Fragment fragment = new SecondFragment();
//                    FragmentTransaction transaction = MainActivity.mainActivity.getSupportFragmentManager().beginTransaction();
//                    fragment.setArguments(bundle);
//                    transaction.replace(R.id.SecondFragment, fragment ); // give your fragment container id in first parameter
//                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                    transaction.commit();
//

                    

                }
            });
            titleText = itemView.findViewById(R.id.titleText);
            lonText = itemView.findViewById(R.id.lonText);
            latText = itemView.findViewById(R.id.latText);
            descText = itemView.findViewById(R.id.descTest);
        }

        public TextView getTitleText() {
            return titleText;
        }

        public TextView getLonText() {
            return lonText;
        }

        public TextView getLatText() {
            return latText;
        }

        public TextView getDescText() {
            return descText;
        }
    
    }

}
