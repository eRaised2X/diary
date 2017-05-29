package com.eraisedtox94.smartdiary.presenter.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.presenter.AllEntriesPresenterImpl;
import com.eraisedtox94.smartdiary.presenter.util.IPresenterContract;

/**
 * Created by spraful on 06-May-17.
 */

public class MyCursorRecyclerAdapter extends RecyclerView.Adapter<MyCursorRecyclerAdapter.ViewHolder>
        implements View.OnClickListener,View.OnLongClickListener{

    // Because RecyclerView.Adapter in its current form doesn't natively
    // support cursors, we wrap a CursorAdapter that will do all the job
    // for us.
    IPresenterContract.IAllEntriesPresenter entriesPresenter;

    CursorAdapter mCursorAdapter;

    Context mContext;

    public MyCursorRecyclerAdapter(Context context, Cursor c) {

        mContext = context;

        mCursorAdapter = new CursorAdapter(mContext, c, 0) {

            //TODO I somehow feel this constructor (i.e MyCursorRecyclerAdapter) is time consuming
            //because of too many FindViewById
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View tempv = LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
                // Inflate the createEntryView here
                return tempv;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                // Binding operations
                TextView tv1 = (TextView) view.findViewById(R.id.tvEntryId);
                TextView tv2 = (TextView) view.findViewById(R.id.tvDiaryTitle);
                TextView tv3 = (TextView) view.findViewById(R.id.tvDateCreated);
                tv1.setText(cursor.getString(0));
                tv2.setText(cursor.getString(1));
                tv3.setText(cursor.getString(2));
            }
        };
    }

    @Override
    public void onClick(View view) {
        //TODO note that findViewById is a heavy operation
        String id = ((TextView)view.findViewById(R.id.tvEntryId)).getText().toString();
        Log.d("clicked",id +":is the entryId");

        if(entriesPresenter==null){
            entriesPresenter = new AllEntriesPresenterImpl();
        }
        entriesPresenter.listItemClickListener(id);

        return;
    }

    @Override
    public boolean onLongClick(View view) {

        Log.d("got long Clicked",((TextView)view.findViewById(R.id.tvEntryId)).getText()+":is the id clicked");
        if(entriesPresenter==null){
            entriesPresenter = new AllEntriesPresenterImpl();
        }
        String [] a = {"3","4"};
        entriesPresenter.listItemLongClickListener(a);
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.v1);
        }
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Passing the binding operation to cursor loader
        //TODO removing if statement here gives exception on restarting activity find out what's happening
        if(mCursorAdapter.getCursor()!=null && !mCursorAdapter.getCursor().isClosed()){
            mCursorAdapter.getCursor().moveToPosition(position);
            mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Passing the inflater job to the cursor-adapter
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        //TODO findViewById makes things slow
        v.findViewById(R.id.v1).setOnClickListener(this);
        v.findViewById(R.id.v1).setOnLongClickListener(this);
        return new ViewHolder(v);
    }
}