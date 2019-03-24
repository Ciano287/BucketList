package com.example.bucketlist;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import java.util.List;

public class BucketListAdapter extends RecyclerView.Adapter<BucketListAdapter.ViewHolder> {
    private List<BucketList> bucketLists;

    public BucketListAdapter(List<BucketList> bucketLists) {
        this.bucketLists = bucketLists;
    }

    @NonNull
    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bucket_item_layout
                , viewGroup, false);


        // Return a new holder instance

        return new BucketListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final BucketListAdapter.ViewHolder viewHolder, int i) {
        final BucketList bucketList = bucketLists.get(i);

        viewHolder.textViewTitle.setText(bucketList.getTitle());
        viewHolder.textViewDescription.setText(bucketList.getDescription());

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkBox.isChecked()) {
                    viewHolder.textViewTitle.setPaintFlags(viewHolder.textViewTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textViewDescription.setPaintFlags(viewHolder.textViewDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.textViewTitle.setPaintFlags(viewHolder.textViewTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textViewDescription.setPaintFlags(viewHolder.textViewDescription.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return bucketLists.size();
    }

    public void swapList(List<BucketList> newList) {
        bucketLists = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView textViewTitle;
        TextView textViewDescription;
        CheckBox checkBox;


        public ViewHolder(View itemView) {

            super(itemView);

            textViewTitle = itemView.findViewById(R.id.TextLayoutTitle);
            textViewDescription = itemView.findViewById(R.id.TextLayoutDescription);
            checkBox = itemView.findViewById(R.id.checkBox);


        }
    }
}

