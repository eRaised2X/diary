package com.eraisedtox94.smartdiary.presenter.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.presenter.mediators.IPresenterContract;

import java.util.ArrayList;

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
    boolean isUserInMultipleSelectionMode;
    boolean isLongClickAlreadyTriggered;

    ImageView marker ;

    Typeface blokletters_viltstift_font;
    Typeface blokletters_ballpen_font;

    ArrayList<String> items = new ArrayList<String>();

    public MyCursorRecyclerAdapter(Context context, Cursor c, IPresenterContract.IAllEntriesPresenter entriesPresenter) {

        mContext = context;
        this.entriesPresenter = entriesPresenter;
        //todo probably otf doesn't work on 7.0+
        blokletters_viltstift_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/blokletters_viltstift.ttf");
        blokletters_ballpen_font = Typeface.createFromAsset(mContext.getAssets(),  "fonts/blokletters_balpen.ttf");
        mCursorAdapter = new CursorAdapter(mContext, c, 0) {

            //TODO I somehow feel this constructor (i.e MyCursorRecyclerAdapter) is time consuming
            //because of too many FindViewById
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View tempv = LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.single_entry_layout, parent, false);
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

                tv2.setTypeface(blokletters_viltstift_font);
                tv3.setTypeface(blokletters_ballpen_font);
            }
        };
    }


    @Override
    public void onClick(View view) {
        //TODO note that findViewById is a bit heavy operation
        String clickedItemId = ((TextView)view.findViewById(R.id.tvEntryId)).getText().toString();
        Log.d("clicked", clickedItemId +":is the entryId");

        marker = (ImageView) view.findViewById(R.id.iv_internal_ItemMarkedIndicator);
        if(isUserInMultipleSelectionMode){

            if(marker.getVisibility() == View.INVISIBLE){
                items.add(clickedItemId);
                Log.d("adding=", clickedItemId +"");
                marker.setVisibility(View.VISIBLE);
            }
            else{
                marker.setVisibility(View.INVISIBLE);
                items.remove(clickedItemId);
                Log.d("removing=", clickedItemId +"");
            }
        }
        else{
            isUserInMultipleSelectionMode = false;
            entriesPresenter.listItemClickListener(clickedItemId);
        }
    }

    //handleActionDelete(items)
    //items.clear()
    //isLongClickAlreadyTriggered = false
    //
    @Override
    public boolean onLongClick(View view) {
        if(!isLongClickAlreadyTriggered){

            String clickedItemId = ((TextView) view.findViewById(R.id.tvEntryId)).getText().toString();
            Log.d("got long Clicked",clickedItemId+":is the id clicked");

            entriesPresenter.listItemLongClickListener();
            isUserInMultipleSelectionMode=true;
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);

            ImageView marker =(ImageView) view.findViewById(R.id.iv_internal_ItemMarkedIndicator);
            if(marker.getVisibility() == View.INVISIBLE){
                marker.setVisibility(View.VISIBLE);
                Log.d("long click adding",clickedItemId);
                items.add(clickedItemId);
            }
            else{
                marker.setVisibility(View.INVISIBLE);
                items.remove(clickedItemId);
                Log.d("long click removing ",clickedItemId);
            }

            isLongClickAlreadyTriggered = true;
        }
        else {
            view.performClick();
        }
        return true;
    }

    public void handleListBottomToolbarActionDelete(){
        entriesPresenter.handleActionDeleteEntries(items);
        isLongClickAlreadyTriggered = false;
        items.clear();
    }


    public void handleListBottomToolbarActionCancelDelete(){
        isLongClickAlreadyTriggered = false;
        items.clear();
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