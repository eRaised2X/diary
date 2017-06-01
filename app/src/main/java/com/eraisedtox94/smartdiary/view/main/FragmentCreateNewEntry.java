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
import android.widget.PopupWindow;
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

import github.ankushsachdeva.emojicon.EmojiconEditText;
import github.ankushsachdeva.emojicon.EmojiconGridView;
import github.ankushsachdeva.emojicon.EmojiconsPopup;
import github.ankushsachdeva.emojicon.emoji.Emojicon;

public class FragmentCreateNewEntry extends Fragment implements IViewContract.ICreateNewEntryView, View.OnClickListener {


    private FloatingActionButton floatingActionButtonForbtnSaveEntry;
    private ImageButton imageButtonSave;
    private FloatingActionButton floatingActionButtonForCreateNew;
    private ProgressBar progressBar;
    private Typeface typefaceforContentEditText;
    private EmojiconEditText emojiconEditText ;
    private ImageView emojiButton;
    //private ImageButton imageButtonKeyBoardChange;
    private EmojiconsPopup popup;

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
        tabView = inflater.inflate(R.layout.frag_create_new2, container, false);
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

        emojiconEditText = (EmojiconEditText) tabView.findViewById(R.id.emojicon_edit_text);
        emojiButton = (ImageView) tabView.findViewById(R.id.iv_keyboard_alt_black);
        //imageButtonKeyBoardChange = (ImageButton) tabView.findViewById(R.id.iv_keyboard_alt_black);
        imageButtonSave = (ImageButton) tabView.findViewById(R.id.iv_save_black);
        // Give the topmost view of your activity layout hierarchy. This will be used to measure soft keyboard height
        popup = new EmojiconsPopup(tabView, getContext());
        //Will automatically set size according to the soft keyboard size
        popup.setSizeForSoftKeyboard();

        //If the emoji popup is dismissed, change emojiButton to smiley icon
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                changeEmojiKeyboardIcon(emojiButton, R.drawable.smiley);
            }
        });

        //If the text keyboard closes, also dismiss the emoji popup
        popup.setOnSoftKeyboardOpenCloseListener(new EmojiconsPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight) {

            }

            @Override
            public void onKeyboardClose() {
                if(popup.isShowing())
                    popup.dismiss();
            }
        });



        //On emoji clicked, add it to edittext
        popup.setOnEmojiconClickedListener(new EmojiconGridView.OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emojicon emojicon) {
                if (emojiconEditText == null || emojicon == null) {
                    return;
                }

                int start = emojiconEditText.getSelectionStart();
                int end = emojiconEditText.getSelectionEnd();
                if (start < 0) {
                    emojiconEditText.append(emojicon.getEmoji());
                } else {
                    emojiconEditText.getText().replace(Math.min(start, end),
                            Math.max(start, end), emojicon.getEmoji(), 0,
                            emojicon.getEmoji().length());
                }
            }
        });
        //On backspace clicked, emulate the KEYCODE_DEL key event
        popup.setOnEmojiconBackspaceClickedListener(new EmojiconsPopup.OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                KeyEvent event = new KeyEvent(
                        0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                emojiconEditText.dispatchKeyEvent(event);
            }
        });

        // To toggle between text keyboard and emoji keyboard keyboard(Popup)
        emojiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //If popup is not showing => emoji keyboard is not visible, we need to show it
                if(!popup.isShowing()){

                    //If keyboard is visible, simply show the emoji popup
                    if(popup.isKeyBoardOpen()){
                        popup.showAtBottom();
                        changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }

                    //else, open the text keyboard first and immediately after that show the emoji popup
                    else{
                        emojiconEditText.setFocusableInTouchMode(true);
                        emojiconEditText.requestFocus();
                        popup.showAtBottomPending();
                        final InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(emojiconEditText, InputMethodManager.SHOW_IMPLICIT);
                        changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }
                }

                //If popup is showing, simply dismiss it to show the undelying text keyboard
                else{
                    popup.dismiss();
                }
            }
        });

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

        presenter.readFile(appPrefsManager.getLastOpenedFileIdFromSharedPref());


    }


    public void setListeners() {
        //floatingActionButtonForbtnSaveEntry.setOnClickListener(this);
        floatingActionButtonForCreateNew.setOnClickListener(this);
        imageButtonSave.setOnClickListener(this);
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
    public void setContentReadFromFile(String content){
        //TODO prone to crashes
        String title= "";
        String matter ="";
        if(content.indexOf(AppUtils.TITLE_CONTENT_SEPARATOR_FLAG)!=-1){
            title = content.substring(0,content.indexOf(AppUtils.TITLE_CONTENT_SEPARATOR_FLAG));
            matter = content.substring(content.indexOf(AppUtils.TITLE_CONTENT_SEPARATOR_FLAG)+
                    AppUtils.TITLE_CONTENT_SEPARATOR_FLAG.length(),content.length());
        }
        /*if(etContent == null || etTitle == null){
            Log.d("etContent or etTitle"," is null ,FragCreateNewEntry");
        }
        etContent.setText(matter);
        */
        if(emojiconEditText == null || etTitle == null){
            Log.d("emojiconEt or etTitle"," is null ,FragCreateNewEntry");
        }
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
        //todo modified thing to be handled
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
                //floatingActionButtonForbtnSaveEntry.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId){
        iconToBeChanged.setImageResource(drawableResourceId);
    }

}