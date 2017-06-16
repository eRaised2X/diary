package com.eraisedtox94.smartdiary.emoticon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.eraisedtox94.smartdiary.R;

import java.util.Arrays;

/**
 * Created by spraful on 08-Jun-17.
 */

public class MyEmojiconGridView {
    public View rootView;
    EmojiconPopupWindow mEmojiconPopup;
    //EmojiconRecents mRecents;
    MyEmojicon[] mData;

    public MyEmojiconGridView(Context context, MyEmojicon[] emojicons,EmojiconPopupWindow emojiconPopup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        mEmojiconPopup = emojiconPopup;
        rootView = inflater.inflate(R.layout.my_emojicon_grid, null);

        final GridView gridView = (GridView) rootView.findViewById(R.id.my_Emoji_GridView);

        if (emojicons== null) {
            mData = People.getData();
        } else {
            Object[] o = (Object[]) emojicons;
            mData = Arrays.asList(o).toArray(new MyEmojicon[o.length]);
        }
        final MyEmojiAdapter mAdapter = new MyEmojiAdapter(rootView.getContext(), mData);
        mAdapter.setEmojiClickListener(new IEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(String emojiChar) {
                if (mEmojiconPopup.emojiconClickedListener != null) {
                    mEmojiconPopup.emojiconClickedListener.onEmojiconClicked(emojiChar);
                    Log.d("emoji Click in gridview","="+emojiChar);

                }
            }
        });
        gridView.setAdapter(mAdapter);
    }

}