package com.eraisedtox94.smartdiary.emoticon;

/**
 * Created by spraful on 09-Jun-17.
 */

public class Sports {

    public static MyEmojicon[] getData(){
        MyEmojicon []DATA  = {
                new MyEmojicon(getEmojiByUnicode(0x26BD)),
                new MyEmojicon(getEmojiByUnicode(0x26BE)),
                new MyEmojicon(getEmojiByUnicode(0x26F3)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B1)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B2)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B3)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B4)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B5)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B6)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B7)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B8)),
                new MyEmojicon(getEmojiByUnicode(0x1F3B9)),
                new MyEmojicon(getEmojiByUnicode(0x1F3BA)),
                new MyEmojicon(getEmojiByUnicode(0x1F3BB)),
                new MyEmojicon(getEmojiByUnicode(0x1F3BC)),
                new MyEmojicon(getEmojiByUnicode(0x1F3BD)),
                new MyEmojicon(getEmojiByUnicode(0x1F3BE)),
                new MyEmojicon(getEmojiByUnicode(0x1F3BF)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C0)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C1)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C2)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C3)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C4)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C6)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C8)),
                new MyEmojicon(getEmojiByUnicode(0x1F3CA)),

                new MyEmojicon(getEmojiByUnicode(0x1F3C7)),
                new MyEmojicon(getEmojiByUnicode(0x1F3C9)),

        };


        return DATA;
    }


    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
