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
import com.eraisedtox94.smartdiary.model.AppPrefsManagerImpl;
import com.eraisedtox94.smartdiary.presenter.mediators.CreateEntryPresenterImpl;
import com.eraisedtox94.smartdiary.presenter.mediators.IAppPrefsManager;
import com.eraisedtox94.smartdiary.presenter.mediators.IPresenterContract;
import com.eraisedtox94.smartdiary.presenter.util.MyCalendarClass;
import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.model.DiaryEntryContentProvider;
import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;
import com.eraisedtox94.smartdiary.view.util.IViewContract;

public class FragmentCreateNewEntry extends Fragment implements IViewContract.ICreateNewEntryView, View.OnClickListener {


    private Button btnSaveEntry;
    private FloatingActionButton floatingActionButtonForCreateNew;
    private ProgressBar progressBar;
    private Typeface typefaceforContentEditText;

    private EditText etTitle;
    private EditText etContent;

    private View tabView;

    private MyCalendarClass mMyCalendarClass;
    private IAppPrefsManager appPrefsManager;
    private IPresenterContract.ICreateNewEntryPresenter presenter;
    private MyTextWatcher myTextWatcher;

    public FragmentCreateNewEntry(){

    }
    public static FragmentCreateNewEntry newInstance() {
        return new FragmentCreateNewEntry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.fragment_create_new_entry, container, false);
        Log.d("life cycle FragMain", "onCreate called");

        initialise();
        setListeners();

        presenter.setView(this);
        return tabView;
    }

    //initialises private fields
    public void initialise() {

        appPrefsManager = new AppPrefsManagerImpl(getContext());
        presenter = new CreateEntryPresenterImpl(appPrefsManager);

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

        myTextWatcher = new MyTextWatcher();
        mMyCalendarClass = new MyCalendarClass();

        presenter.readFile(appPrefsManager.getLastOpenedFileIdFromSharedPref());

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
        if(etContent==null){
            Log.d("etcontent null","FragCreateNewEntry");
        }
        etContent.setText(content);
    }


    @Override
    public void clearPage() {
        etTitle.setText("");
        etContent.setText("");
        appPrefsManager.setLastOpenedFileIdInsideSharedPref(null);
    }


    @Override
    public void handleClickOfSave() {
        //TODO file exceptions need to be handled, like scenarios of being unable to saved/written to external

        String titleString = etTitle.getText().toString();
        String textContent = etContent.getText().toString();
        String dateCreatedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();
        String dateModifiedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();

        Uri uri = null;
        String id = appPrefsManager.getLastOpenedFileIdFromSharedPref();
        if (id.equals(AppUtils.DEFAULT_FILE_ID)) {
            ContentValues values = new ContentValues();
            values.put(DiaryEntryTableUtil.COLUMN_TITLE, titleString);
            values.put(DiaryEntryTableUtil.COLUMN_DATE_CREATED, dateCreatedString);
            values.put(DiaryEntryTableUtil.COLUMN_DATE_MODIFIED, dateModifiedString);

            uri = getContext().getContentResolver().insert(
                    DiaryEntryContentProvider.CONTENT_URI, values);

            appPrefsManager.setLastOpenedFileIdInsideSharedPref(uri.getLastPathSegment());
        }

        presenter.writeFile(appPrefsManager.getLastOpenedFileIdFromSharedPref(),textContent);
        Toast.makeText(getContext(), "FILE SAVED", Toast.LENGTH_SHORT).show();
    }


    //TODO these overridden methods are pretty much not used
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("onViewCreated", "FragCreateEntry");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("onStart", "FragCreateEntry");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "FragCreateEntry");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause", "FragCreateEntry");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onStop", "FragCreateEntry");
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