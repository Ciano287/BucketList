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
    List<BucketList> bucketLists;
//    CheckBox checkBox;

    public BucketListAdapter(List<BucketList> bucketLists){
        this.bucketLists = bucketLists;
    }
    @NonNull
    @Override
    public BucketListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        // Return a new holder instance
        BucketListAdapter.ViewHolder viewHolder = new BucketListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BucketListAdapter.ViewHolder viewHolder, int i) {
        BucketList bucketList = bucketLists.get(i);
        viewHolder.textView.setText(bucketList.getDescription());
        viewHolder.textView2.setText(bucketList.getTitle());
//        viewHolder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//                checkBox = (CheckBox) v;
//
//                if (checkBox.isChecked()){
//                    viewHolder.tvTitle.setPaintFlags(viewHolder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//                } else if (!checkBox.isChecked()){
//                    viewHolder.tvTitle.setPaintFlags(viewHolder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                }
             }



    @Override
    public int getItemCount() {
        return bucketLists.size();
    }

    public void swapList (List<BucketList> newList) {
        bucketLists = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        TextView textView;
        TextView textView2;
        CheckBox checkBox;



        public ViewHolder(View itemView) {

            super(itemView);

            textView = itemView.findViewById(android.R.id.text1);
            textView2 = itemView.findViewById(android.R.id.text2);
            checkBox = itemView.findViewById(android.R.id.checkbox);

//            checkBox.setOnClickListener(this);
//        }
//
//        public void setItemClickListener(ItemClickListener ic){
//            this.itemClickListener = ic;
//        }
//
//        @Override
//        public void onClick(View v) {
//            this.itemClickListener.onItemClick(v,getLayoutPosition());
//        }
//    }

        }

    }
}