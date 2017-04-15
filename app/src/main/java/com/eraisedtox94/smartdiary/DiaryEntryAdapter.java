package com.eraisedtox94.smartdiary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by spraful on 4/6/2017.
 */

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.MyViewHolder> {

    private List<DiaryEntry> diaryEntryList;

    public DiaryEntryAdapter(List<DiaryEntry> diaryEntryList) {
        this.diaryEntryList = diaryEntryList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DiaryEntry mDiaryEntry = diaryEntryList.get(position);
        holder.titleDiary.setText(mDiaryEntry.title);
        holder.dateCreated.setText(mDiaryEntry.timeCreated+"");
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_diary_entry,parent, false);
        return new MyViewHolder(v);
    }


    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleDiary;
        public TextView dateCreated;

        public MyViewHolder(View view) {
            super(view);
            titleDiary = (TextView)view.findViewById(R.id.tvDiaryTitle);
            dateCreated = (TextView)view.findViewById(R.id.tvDateCreated);
        }
    }

    @Override
    public int getItemCount() {
        return diaryEntryList.size();
    }
}
