package com.eraisedtox94.smartdiary.presenter.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eraisedtox94.smartdiary.R;

import java.util.ArrayList;

/**
 * Created by spraful on 03-May-17.
 */

class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    ArrayList mItems = new ArrayList<String>();

    ItemAdapter() {
        mItems.add("one");
        mItems.add("2");
        mItems.add("3");
        mItems.add("4");
        mItems.add("5");
        mItems.add("6");
/*        mItems.add("7");
        mItems.add("8");
        mItems.add("9");
        mItems.add("10");
        mItems.add("11");
        mItems.add("12");
        mItems.add("13");
        mItems.add("14");
        */
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext()).inflate(R.layout.row_diary_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData((String)mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    /**
     * my viewHolder class
     */

    class ViewHolder extends RecyclerView.ViewHolder  {

        TextView textView;
        TextView textView2;
        TextView textView3;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvEntryId);
            textView2 = (TextView) itemView.findViewById(R.id.tvDateCreated);
            textView3 = (TextView) itemView.findViewById(R.id.tvDiaryTitle);
        }

        void setData(String item) {
            textView.setText(item);
            textView2.setText(item);
            textView3.setText(item);
        }
    }


}