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
import android.widget.ImageView;

import com.eraisedtox94.smartdiary.model.AppPrefsManagerImpl;
import com.eraisedtox94.smartdiary.model.DiaryEntryContentProvider;
import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;
import com.eraisedtox94.smartdiary.presenter.mediators.AllEntriesPresenterImpl;
import com.eraisedtox94.smartdiary.presenter.mediators.IAppPrefsManager;
import com.eraisedtox94.smartdiary.presenter.adapters.MyCursorRecyclerAdapter;
import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.presenter.mediators.IPresenterContract;
import com.eraisedtox94.smartdiary.view.util.IViewContract;

import java.util.ArrayList;

/**
 * Created by spraful on 4/5/2017.
 */
public class FragmentListOfEntries extends Fragment implements IViewContract.IListAllEntriesView,View.OnClickListener {

    IPresenterContract.IAllEntriesPresenter allEntriesPresenter;
    IAppPrefsManager appPrefsManager;

    private MyCursorRecyclerAdapter mMyCursorRecyclerAdapter;
    private RecyclerView recyclerView;

    ImageView iv_deleteEntries;
    ImageView iv_cancelDeleteAction;

    //todo
    View allEntriesview;
    public FragmentListOfEntries(){

    }

    public static FragmentListOfEntries newInstance() {
        return new FragmentListOfEntries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("life cycle AllEntries","Inside onCreate");

        View view = inflater.inflate(R.layout.frag_list_of_all_entries, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_all_entries);

        appPrefsManager = new AppPrefsManagerImpl(getContext());

        allEntriesPresenter = new AllEntriesPresenterImpl(getLoaderManager(), appPrefsManager);
        allEntriesPresenter.setView(this);
        allEntriesPresenter.fillViewWithListOfEntries();

        iv_deleteEntries = (ImageView)view.findViewById(R.id.iv_deleteListItems);
        iv_cancelDeleteAction = (ImageView)view.findViewById(R.id.iv_cancelDeleteListItems);
        iv_deleteEntries.setOnClickListener(this);
        iv_cancelDeleteAction.setOnClickListener(this);

        allEntriesview = view;
        return view;
    }

    //TODO this stub can be improved /shifted /modelled well.. probably
    @Override
    public void switchToTab(int index){
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.setCurrentItem(index);
    }

    @Override
    public void deleteListItems(ArrayList<String> itemlist) {

        String[] ids = itemlist.toArray(new String[itemlist.size()]);

        getContext().getContentResolver().delete(
                DiaryEntryContentProvider.CONTENT_URI, DiaryEntryTableUtil.COLUMN_ID, ids);
    }

    @Override
    public void showBottomToolbar(){
        allEntriesview.findViewById(R.id.rl_list_bottomToolbar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBottomToolbar(){
        allEntriesview.findViewById(R.id.rl_list_bottomToolbar).setVisibility(View.GONE);
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
        mMyCursorRecyclerAdapter = new MyCursorRecyclerAdapter(getContext(),data,allEntriesPresenter);
        recyclerView.setAdapter(mMyCursorRecyclerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_deleteListItems:
                hideBottomToolbar();
                mMyCursorRecyclerAdapter.handleListBottomToolbarActionDelete();
                break;
            case R.id.iv_cancelDeleteListItems:
                mMyCursorRecyclerAdapter.handleListBottomToolbarActionCancelDelete();
                break;
            default:
                break;
        }
    }
}