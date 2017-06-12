package com.eraisedtox94.smartdiary.emoticon;

import android.view.View;

/**
 * Created by spraful on 12-Jun-17.
 */

public interface IEmoticonInterfaces {

    interface OnEmojiconBackspaceClickedListener {
        void onEmojiconBackspaceClicked(View v);
    }


    interface OnSoftKeyboardOpenCloseListener {
        void onKeyboardOpen(int keyBoardHeight);
        void onKeyboardClose();
    }
}
