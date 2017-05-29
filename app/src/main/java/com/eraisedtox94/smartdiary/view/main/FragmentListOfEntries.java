package com.eraisedtox94.smartdiary.view.main;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eraisedtox94.smartdiary.model.DiaryEntryContentProvider;
import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;
import com.eraisedtox94.smartdiary.model.IAppPrefsManagerImpl;
import com.eraisedtox94.smartdiary.presenter.AllEntriesPresenterImpl;
import com.eraisedtox94.smartdiary.presenter.IAppPrefsManager;
import com.eraisedtox94.smartdiary.presenter.MyCursorRecyclerAdapter;
import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.presenter.IPresenterContract;
import com.eraisedtox94.smartdiary.view.IViewContract;

/**
 * Created by spraful on 4/5/2017.
 */
public class FragmentListOfEntries extends Fragment implements IViewContract.IListAllEntriesView {

    IPresenterContract.IAllEntriesPresenter allEntriesPresenter;
    IAppPrefsManager IAppPrefsManager;

    private MyCursorRecyclerAdapter mMyCursorRecyclerAdapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("life cycle AllEntries","Inside onCreate");

        View view = inflater.inflate(R.layout.frag_list_of_all_entries, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_all_entries);

        IAppPrefsManager = new IAppPrefsManagerImpl(getContext());

        allEntriesPresenter = new AllEntriesPresenterImpl(getLoaderManager(), IAppPrefsManager);
        allEntriesPresenter.attachView(this);
        allEntriesPresenter.fillViewWithListOfEntries();

        return view;
    }

    //TODO this can be shifted to MainActivity so as to reduce instantiation of View Pager probably
    public void switchToTab(int index){
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.setCurrentItem(index);
    }

    @Override
    public void deleteListItems(String[] ids) {
        getContext().getContentResolver().delete(
                DiaryEntryContentProvider.CONTENT_URI, DiaryEntryTableUtil.COLUMN_ID, ids);
    }

    @Override
    public void showList() {
        //here list is displayed probably

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mMyCursorRecyclerAdapter);
    }


    @Override
    public void notifyChange() {
        mMyCursorRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setTheAdapter(Cursor data) {
        mMyCursorRecyclerAdapter = new MyCursorRecyclerAdapter(getContext(),data);
        recyclerView.setAdapter(mMyCursorRecyclerAdapter);
    }

}