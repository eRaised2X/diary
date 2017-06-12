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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eraisedtox94.smartdiary.app.AppUtils;
import com.eraisedtox94.smartdiary.emoticon.EmojiconPopupWindow;
import com.eraisedtox94.smartdiary.emoticon.IEmojiconClickedListener;
import com.eraisedtox94.smartdiary.emoticon.IEmoticonInterfaces;
import com.eraisedtox94.smartdiary.model.AppPrefsManagerImpl;
import com.eraisedtox94.smartdiary.presenter.mediators.CreateEntryPresenterImpl;
import com.eraisedtox94.smartdiary.presenter.mediators.IAppPrefsManager;
import com.eraisedtox94.smartdiary.presenter.mediators.IPresenterContract;
import com.eraisedtox94.smartdiary.presenter.util.MyCalendarClass;
import com.eraisedtox94.smartdiary.R;
import com.eraisedtox94.smartdiary.model.DiaryEntryContentProvider;
import com.eraisedtox94.smartdiary.model.DiaryEntryTableUtil;
import com.eraisedtox94.smartdiary.view.util.IViewContract;
import com.eraisedtox94.smartdiary.view.util.LinedEditText;

public class FragmentCreateNewEntry extends Fragment
        implements IViewContract.ICreateNewEntryView, View.OnClickListener,
        IEmojiconClickedListener, IEmoticonInterfaces.OnEmojiconBackspaceClickedListener {


    private FloatingActionButton floatingActionButtonForbtnSaveEntry;
    private ImageButton imageButtonSave;
    private FloatingActionButton floatingActionButtonForCreateNew;
    private ProgressBar progressBar;
    private Typeface typefaceforContentEditText;
    private LinedEditText emojiconEditText;
    private ImageView emojiButton;
    private ImageButton imageButtonKeyBoardChange;
    private ImageButton imageButtonShowEmojiPopup;

    private EditText etTitle;

    private View tabView;
    private View rootView;

    private MyCalendarClass mMyCalendarClass;
    private IAppPrefsManager appPrefsManager;
    private IPresenterContract.ICreateNewEntryPresenter presenter;
    private MyTextWatcher myTextWatcher;

    EmojiconPopupWindow emojiconPopup;

    public FragmentCreateNewEntry() {

    }

    public static FragmentCreateNewEntry newInstance() {
        return new FragmentCreateNewEntry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tabView = inflater.inflate(R.layout.frag_create_new2, container, false);
        Log.d("life cycle FragMain", "onCreate called");

        initialise();
        setListeners();

        presenter.setView(this);

        emojiconPopup = new EmojiconPopupWindow(rootView, getContext());

        //emojicon stuff
        emojiconPopup.setEmojiconClickedListener(this);
        emojiconPopup.setOnEmojiconBackspaceClickedListener(this);

        //Will automatically set size according to the soft keyboard size
        emojiconPopup.setSizeForSoftKeyboard();


                emojiconPopup.setOnSoftKeyboardOpenCloseListener(new IEmoticonInterfaces.OnSoftKeyboardOpenCloseListener() {

                    @Override
                    public void onKeyboardOpen(int keyBoardHeight) {

                    }

                    @Override
                    public void onKeyboardClose() {
                        if (emojiconPopup.isShowing())
                            emojiconPopup.dismiss();
                    }
                });

        return tabView;

    }

    //initialises private fields
    public void initialise() {

        appPrefsManager = new AppPrefsManagerImpl(getContext());
        presenter = new CreateEntryPresenterImpl(appPrefsManager);

        emojiconEditText = (LinedEditText) tabView.findViewById(R.id.emojicon_edit_text);
        emojiButton = (ImageView) tabView.findViewById(R.id.iv_keyboard_alt_black);
        imageButtonKeyBoardChange = (ImageButton) tabView.findViewById(R.id.iv_keyboard_alt_black);
        imageButtonSave = (ImageButton) tabView.findViewById(R.id.iv_save_black);
        imageButtonShowEmojiPopup = (ImageButton) tabView.findViewById(R.id.iv_star_border_black);

        etTitle = (EditText) tabView.findViewById(R.id.et_title_diary);
        //etContent = (EditText) tabView.findViewById(R.id.et_content_diary);
        //floatingActionButtonForbtnSaveEntry = (FloatingActionButton) tabView.findViewById(R.id.fabBtnSave);
        //floatingActionButtonForbtnSaveEntry.setVisibility(View.INVISIBLE);

        floatingActionButtonForCreateNew = (FloatingActionButton) tabView.findViewById(R.id.fabButtonNew);
        floatingActionButtonForCreateNew.setRippleColor(ContextCompat.getColor(getActivity(), R.color.colorFABNewOnClick));

        progressBar = (ProgressBar) tabView.findViewById(R.id.pbarDiaryMain);
        progressBar.setVisibility(View.INVISIBLE);

        // Loading Font Face
        typefaceforContentEditText = Typeface.createFromAsset(getContext().getAssets(), AppUtils.FONT_BRADLEY_RESOURCE_LOCATION);
        //etContent.setTypeface(typefaceforContentEditText);
        emojiconEditText.setTypeface(typefaceforContentEditText);

        myTextWatcher = new MyTextWatcher();
        mMyCalendarClass = new MyCalendarClass();


        //do initializations before readFile call
        presenter.readFile(appPrefsManager.getLastOpenedFileIdFromSharedPref());
        //todo can it go up
        rootView = tabView.findViewById(R.id.root_view);

    }


    public void setListeners() {
        //floatingActionButtonForbtnSaveEntry.setOnClickListener(this);
        floatingActionButtonForCreateNew.setOnClickListener(this);
        imageButtonKeyBoardChange.setOnClickListener(this);
        imageButtonSave.setOnClickListener(this);
        imageButtonShowEmojiPopup.setOnClickListener(this);
        //etContent.addTextChangedListener(myTextWatcher);
        emojiconEditText.addTextChangedListener(myTextWatcher);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            /*case R.id.fabBtnSave:
                presenter.handleSaveEntry();
                floatingActionButtonForbtnSaveEntry.setVisibility(View.INVISIBLE);
                break;
            */
            case R.id.iv_star_border_black:
                //If popup is not showing => emoji keyboard is not visible, we need to show it
                if (!emojiconPopup.isShowing()) {

                    //If keyboard is visible, simply show the emoji popup
                    if (emojiconPopup.isKeyBoardOpen()) {
                        emojiconPopup.showAtBottom();
                        //changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }

                    //else, open the text keyboard first and immediately after that show the emoji popup
                    else {
                        emojiconEditText.setFocusableInTouchMode(true);
                        emojiconEditText.requestFocus();
                        emojiconPopup.showAtBottomPending();
                        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(emojiconEditText, InputMethodManager.SHOW_IMPLICIT);
                        //changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }
                }

                //If popup is showing, simply dismiss it to show the undelying text keyboard
                else {
                    emojiconPopup.dismiss();
                }
                break;
            case R.id.iv_keyboard_alt_black:
                presenter.handleEmojiClicked(0);
                break;
            case R.id.iv_save_black:
                presenter.handleSaveEntry();
                //floatingActionButtonForbtnSaveEntry.setVisibility(View.INVISIBLE);
                break;

            case R.id.fabButtonNew:
                presenter.clearPageContent();
                break;
            default:
                break;
        }
    }

    @Override
    public void putEmoji(int emojiId) {
        //TODO emoji may be for title as well.., handle that
        emojiconEditText.getText().insert(emojiconEditText.getSelectionStart(), "\ud83d\ude01");

        /*
        this is probably better approach
        String sampleSmiley = "\ud83d\ude01";
        int start = Math.max(emojiconEditText.getSelectionStart(), 0);
        int end = Math.max(emojiconEditText.getSelectionEnd(), 0);
        emojiconEditText.getText().replace(Math.min(start, end), Math.max(start, end),
                sampleSmiley, 0, sampleSmiley.length());
        */
    }

    @Override
    public void setContentReadFromFile(String content) {
        //TODO prone to crashes
        Log.d("this is lag", "here");
        String title = "";
        String matter = "";
        if (content.indexOf(AppUtils.TITLE_CONTENT_SEPARATOR_FLAG) != -1) {
            title = content.substring(0, content.indexOf(AppUtils.TITLE_CONTENT_SEPARATOR_FLAG));
            matter = content.substring(content.indexOf(AppUtils.TITLE_CONTENT_SEPARATOR_FLAG) +
                    AppUtils.TITLE_CONTENT_SEPARATOR_FLAG.length(), content.length());
        }
        /*if(etContent == null || etTitle == null){
            Log.d("etContent or etTitle"," is null ,FragCreateNewEntry");
        }
        etContent.setText(matter);
        */
        if (emojiconEditText == null || etTitle == null) {
            Log.d("emojiconEt or etTitle", " is null ,FragCreateNewEntry");
        }
        Log.d("lag may be here", "as well");

        //todo lazy loading is required here
        emojiconEditText.setText(matter);
        etTitle.setText(title);
    }


    @Override
    public void clearPage() {
        etTitle.setText("");
        //etContent.setText("");
        emojiconEditText.setText("");
        appPrefsManager.setLastOpenedFileIdInsideSharedPref(null);
    }


    @Override
    public void handleClickOfSave() {
        //TODO file exceptions need to be handled, like scenarios of being unable to saved/written to external

        String textTitle = etTitle.getText().toString();
        //String textContent = etContent.getText().toString();
        String textContent = emojiconEditText.getText().toString();
        textContent = textTitle + AppUtils.TITLE_CONTENT_SEPARATOR_FLAG + textContent;
        String dateCreatedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();
        //todo `date modified` thing to be handled
        String dateModifiedString = mMyCalendarClass.getFormattedDate() + " at " + mMyCalendarClass.getFormattedTime();

        Uri uri = null;
        String id = appPrefsManager.getLastOpenedFileIdFromSharedPref();
        if (id.equals(AppUtils.DEFAULT_FILE_ID)) {
            ContentValues values = new ContentValues();
            values.put(DiaryEntryTableUtil.COLUMN_TITLE, textTitle);
            values.put(DiaryEntryTableUtil.COLUMN_DATE_CREATED, dateCreatedString);
            values.put(DiaryEntryTableUtil.COLUMN_DATE_MODIFIED, dateModifiedString);

            uri = getContext().getContentResolver().insert(
                    DiaryEntryContentProvider.CONTENT_URI, values);

            appPrefsManager.setLastOpenedFileIdInsideSharedPref(uri.getLastPathSegment());
        }

        presenter.writeFile(appPrefsManager.getLastOpenedFileIdFromSharedPref(), textContent);
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

    @Override
    public void onEmojiconClicked(String emojiChar) {
        //this stub should handle render emoji at pos
        Log.d("render emoji ", ":" + emojiChar);
        int start = emojiconEditText.getSelectionStart();
        int end = emojiconEditText.getSelectionEnd();
        if (start < 0) {
            emojiconEditText.append(emojiChar);
        } else {
            emojiconEditText.getText().replace(Math.min(start, end),
                    Math.max(start, end), emojiChar, 0,
                    emojiChar.length());
        }
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        Log.d("receiving delete","keyboard press");
        //emulate keyboard delete key press here
        KeyEvent event = new KeyEvent(
                0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        emojiconEditText.dispatchKeyEvent(event);
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

                //floatingActionButtonForbtnSaveEntry.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

}