package com.eraisedtox94.smartdiary.view.main;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eraisedtox94.smartdiary.app.AppUtils;
import com.eraisedtox94.smartdiary.model.IAppPrefsManagerImpl;
import com.eraisedtox94.smartdiary.presenter.CreateEntryPresenterImpl;
import com.eraisedtox94.smartdiary.model.EventUpdateUI;
import com.eraisedtox94.smartdiary.presenter.IPresenterContract;
import com.eraisedtox94.smartdiary.presenter.MyCalendarClass;
import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.model.DiaryEntryContentProvider;
import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;
import com.eraisedtox94.smartdiary.view.IViewContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class FragmentCreateNewEntry extends Fragment implements IViewContract.ICreateNewEntryView, View.OnClickListener {


    private Button btnSaveEntry;
    private FloatingActionButton floatingActionButtonForCreateNew;
    private ProgressBar progressBar;
    private Typeface typefaceforContentEditText;

    private EditText etTitle;
    private EditText etContent;

    private View tabView;

    private MyCalendarClass mMyCalendarClass;
    private IAppPrefsManagerImpl mAppPrefsManagerImpl;
    private IPresenterContract.ICreateNewEntryPresenter presenter;
    private MyTextWatcher myTextWatcher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.fragment_create_new_entry, container, false);
        Log.d("life cycle FragMain", "onCreate called");
        presenter = new CreateEntryPresenterImpl();
        initialise();
        setListeners();

        presenter.attachView(this);
        return tabView;
    }

    //initialises private fields
    public void initialise() {

        etTitle = (EditText) tabView.findViewById(R.id.et_title_diary);
        etContent = (EditText) tabView.findViewById(R.id.et_content_diary);
        btnSaveEntry = (Button) tabView.findViewById(R.id.btnSave);
        btnSaveEntry.setVisibility(View.INVISIBLE);

        floatingActionButtonForCreateNew = (FloatingActionButton) tabView.findViewById(R.id.fabButtonNew);
        floatingActionButtonForCreateNew.setRippleColor(ContextCompat.getColor(getActivity(), R.color.colorFABNewOnClick));

        progressBar = (ProgressBar) tabView.findViewById(R.id.pbarDiaryMain);
        progressBar.setVisibility(View.INVISIBLE);

        // Loading Font Face
        typefaceforContentEditText = Typeface.createFromAsset(getContext().getAssets(), AppUtils.FONT_BRADLEY_RESOURCE_LOCATION);
        etContent.setTypeface(typefaceforContentEditText);

        mAppPrefsManagerImpl = new IAppPrefsManagerImpl(getContext());
        myTextWatcher = new MyTextWatcher();
        mMyCalendarClass = new MyCalendarClass();

        EventBus.getDefault().register(this);
        presenter.readFile(mAppPrefsManagerImpl.getLastOpenedFileIdFromSharedPref());

    }


    @Subscribe
    public void onEvent(EventUpdateUI event){
        Log.d("onEvent","event fired");
        etContent.setText(event.getFileName());
    }

    public void setListeners() {
        btnSaveEntry.setOnClickListener(this);
        floatingActionButtonForCreateNew.setOnClickListener(this);
        etContent.addTextChangedListener(myTextWatcher);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                presenter.handleSaveEntry();
                btnSaveEntry.setVisibility(View.INVISIBLE);
                break;
            case R.id.fabButtonNew:
                presenter.clearPageContent();
                break;
            default:
                break;
        }
    }

    @Override
    public void setContentReadFromFile(String content){
        etContent.setText(content);
    }


    @Override
    public void clearPage() {
        etTitle.setText("");
        etContent.setText("");
        mAppPrefsManagerImpl.setLastOpenedFileIdInsideSharedPref(null);
    }


    @Override
    public void handleClickOfSave() {
        //TODO file exceptions need to be handled, like scenarios of being unable to saved/written to external

        String titleString = etTitle.getText().toString();
        String textContent = etContent.getText().toString();
        String dateCreatedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();
        String dateModifiedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();

        Uri uri = null;
        String id = mAppPrefsManagerImpl.getLastOpenedFileIdFromSharedPref();
        if (id == null) {
            ContentValues values = new ContentValues();
            //values.put(DiaryEntryTableUtil.COLUMN_ID, id);
            values.put(DiaryEntryTableUtil.COLUMN_TITLE, titleString);
            //values.put(DiaryEntryTableUtil.COLUMN_FILENAME, "1");
            values.put(DiaryEntryTableUtil.COLUMN_DATE_CREATED, dateCreatedString);
            values.put(DiaryEntryTableUtil.COLUMN_DATE_MODIFIED, dateModifiedString);

            uri = getContext().getContentResolver().insert(
                    DiaryEntryContentProvider.CONTENT_URI, values);

            mAppPrefsManagerImpl.setLastOpenedFileIdInsideSharedPref(uri.getLastPathSegment());
        }

        presenter.writeFile(mAppPrefsManagerImpl.getLastOpenedFileIdFromSharedPref(),textContent);
        Toast.makeText(getContext(), "FILE SAVED", Toast.LENGTH_SHORT).show();
    }


    //TODO these overridden methods are pretty much not used
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("onViewCreated", "FragCreateEntry");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("onDetach", "FragCreateEntry");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("onDestroyView", "FragCreateEntry");
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d("onAttachFragment", "FragCreateEntry");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("onAttach context", "FragCreateEntry");
    }




    //this class has stub to observe any change in contentRead in edit text
    class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() != 0) {
                //TODO pass this to presenter probably
                btnSaveEntry.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

}