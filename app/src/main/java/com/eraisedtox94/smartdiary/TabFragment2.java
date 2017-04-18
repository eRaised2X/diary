package com.eraisedtox94.smartdiary;


import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by spraful on 4/5/2017.
 */
public class TabFragment2 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter adapter;
    private ListView entryListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment2, container, false);
        entryListView = (ListView) view.findViewById(R.id.listview_frag2);
        fillData();
        return view;
    }


    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] {DiaryEntryTableUtil.COLUMN_TITLE,DiaryEntryTableUtil.COLUMN_DATE_CREATED};
        // Fields on the UI to which we map
        int[] to = new int[] {R.id.tvDiaryTitle,R.id.tvDateCreated};

        //adapter = new SimpleCursorAdapter(getContext(), R.layout.row_diary_entry, null, from,
                //to, 0);

        //entryListView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null,this);
        adapter = new SimpleCursorAdapter(getContext(), R.layout.row_diary_entry, null, from,
                to, 0);

        entryListView.setAdapter(adapter);
    }

    // creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //String[] projection = { DiaryEntryTableUtil.COLUMN_ID, DiaryEntryTableUtil.COLUMN_TITLE};
        String[] projection = { DiaryEntryTableUtil.COLUMN_ID,DiaryEntryTableUtil.COLUMN_TITLE, DiaryEntryTableUtil.COLUMN_DATE_CREATED };
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                DiaryEntryContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
        }

}
