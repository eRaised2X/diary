package com.eraisedtox94.smartdiary;


import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by spraful on 4/5/2017.
 */
public class FragmentAllEntries extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter adapter;
    private ListView entryListView;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_frgmnt_entries, container, false);
        entryListView = (ListView) view.findViewById(R.id.listview_frag2);
        Log.d("life cycle AllEntries","Inside onCreate");
        fillData();
        /*if(getFragmentManager().getFragments()==null){
            Log.d("....its null....",".......");
        }
        else{
            FragmentDiaryMain frdm = (FragmentDiaryMain)getFragmentManager().getFragments().get(0);
            Log.d("new or old instance","....??...");
            frdm.setContentInEditText("trial");
        }*/
        return view;
    }

    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] {DiaryEntryTableUtil.COLUMN_ID,DiaryEntryTableUtil.COLUMN_TITLE,DiaryEntryTableUtil.COLUMN_DATE_CREATED};
        // Fields on the UI to which we map
        int[] to = new int[] {R.id.tvEntryId, R.id.tvDiaryTitle, R.id.tvDateCreated};

        //adapter = new SimpleCursorAdapter(getMainActivityContext(), R.layout.row_diary_entry, null, from,
                //to, 0);

        //entryListView.setAdapter(adapter);
        getLoaderManager().initLoader(0, null,this);
        adapter = new SimpleCursorAdapter(getContext(), R.layout.row_diary_entry, null, from,
                to, 0);

        entryListView.setAdapter(adapter);
        entryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getMainActivityContext(),"clicked"+i,Toast.LENGTH_SHORT).show();
                switchToTab(0);
                TextView tv = (TextView) view.findViewById(R.id.tvEntryId);
                String filename = tv.getText().toString();
                Log.d("reaching on click","clicked="+filename);
                Toast.makeText(getContext(),"id="+tv.getText(),Toast.LENGTH_SHORT).show();
                //FragmentDiaryMain fragMain = new FragmentDiaryMain();
                //fragMain.readFromFileInExternalStorage(filename);
                /*mainActivity = getMainActivityInstance();//new MainActivity();
                mainActivity.handleListItemClicked(filename);
                */
                mainActivity = (MainActivity) getActivity();
                //mainActivity.handleListItemClicked(filename);
                mainActivity.listItemClickedListener(filename);
            }
        });

    }

    //TODO this can be shifted to MainActivity so as to reduce instantiation of View Pager probably
    public void switchToTab(int index){
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.setCurrentItem(index);
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

    //
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

}
