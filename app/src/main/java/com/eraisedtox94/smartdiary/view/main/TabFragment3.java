package com.eraisedtox94.smartdiary.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eraisedtox94.smartdiary.R;

/**
 * Created by spraful on 4/5/2017.
 */
public class TabFragment3 extends Fragment {

    Button btn;
    RecyclerView rv;
    //ItemAdapter itemAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment3, container, false);
        //btn = (Button)view.findViewById(R.id.button1);
        Log.d("3rd frag ","oncreate3");
        /*itemAdapter = new ItemAdapter();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        */
        return view;

    }
}
