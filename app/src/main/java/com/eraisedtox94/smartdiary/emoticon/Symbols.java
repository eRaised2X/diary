package com.eraisedtox94.smartdiary.emoticon;

/**
 * @author spraful
 */
public class Symbols {


    public static MyEmojicon[] getData() {
        MyEmojicon[] DATA = {
                new MyEmojicon(getEmojiByUnicode(0x2714)),
                new MyEmojicon(getEmojiByUnicode(0x2716)),
                new MyEmojicon(getEmojiByUnicode(0x274C)),
                new MyEmojicon(getEmojiByUnicode(0x2753)),
                new MyEmojicon(getEmojiByUnicode(0x2755)),
                new MyEmojicon(getEmojiByUnicode(0x2764)),
                new MyEmojicon(getEmojiByUnicode(0x2795)),
                new MyEmojicon(getEmojiByUnicode(0x27B0)),
                new MyEmojicon(getEmojiByUnicode(0x27A1)),
                new MyEmojicon(getEmojiByUnicode(0x1F6AB)),
                new MyEmojicon(getEmojiByUnicode(0x00A9)),
                new MyEmojicon(getEmojiByUnicode(0x00AE)),
                new MyEmojicon(getEmojiByUnicode(0x23E9)),
                new MyEmojicon(getEmojiByUnicode(0x23EA)),
                new MyEmojicon(getEmojiByUnicode(0x2660)),
                new MyEmojicon(getEmojiByUnicode(0x2663)),
                new MyEmojicon(getEmojiByUnicode(0x2665)),
                new MyEmojicon(getEmojiByUnicode(0x2666)),
                new MyEmojicon(getEmojiByUnicode(0x267F)),
                new MyEmojicon(getEmojiByUnicode(0x26A0)),
                new MyEmojicon(getEmojiByUnicode(0x26A1)),
                new MyEmojicon(getEmojiByUnicode(0x26D4)),
                new MyEmojicon(getEmojiByUnicode(0x1F4AF)),
                new MyEmojicon(getEmojiByUnicode(0x1F518)),
                new MyEmojicon(getEmojiByUnicode(0x1F519)),
                new MyEmojicon(getEmojiByUnicode(0x1F51A)),
                new MyEmojicon(getEmojiByUnicode(0x1F51B)),
                new MyEmojicon(getEmojiByUnicode(0x1F51C)),
                new MyEmojicon(getEmojiByUnicode(0x1F51D)),
                new MyEmojicon(getEmojiByUnicode(0x1F51E)),
                new MyEmojicon(getEmojiByUnicode(0x1F534)),
                new MyEmojicon(getEmojiByUnicode(0x1F535)),
                new MyEmojicon(getEmojiByUnicode(0x1F536)),
                new MyEmojicon(getEmojiByUnicode(0x1F537)),
                new MyEmojicon(getEmojiByUnicode(0x1F538)),
                new MyEmojicon(getEmojiByUnicode(0x1F539)),
                new MyEmojicon(getEmojiByUnicode(0x1F53A)),
                new MyEmojicon(getEmojiByUnicode(0x1F53B)),
                new MyEmojicon(getEmojiByUnicode(0x1F53C)),
                new MyEmojicon(getEmojiByUnicode(0x1F53D)),
                new MyEmojicon(getEmojiByUnicode(0x1F551)),
                new MyEmojicon(getEmojiByUnicode(0x1F552)),
                new MyEmojicon(getEmojiByUnicode(0x1F553)),
                new MyEmojicon(getEmojiByUnicode(0x1F554)),
                new MyEmojicon(getEmojiByUnicode(0x1F555)),
                new MyEmojicon(getEmojiByUnicode(0x1F556)),
                new MyEmojicon(getEmojiByUnicode(0x1F557)),
                new MyEmojicon(getEmojiByUnicode(0x1F558)),
                new MyEmojicon(getEmojiByUnicode(0x1F559)),
                new MyEmojicon(getEmojiByUnicode(0x1F55A)),
                new MyEmojicon(getEmojiByUnicode(0x1F55B)),
                new MyEmojicon(getEmojiByUnicode(0x1F6C2)),
                new MyEmojicon(getEmojiByUnicode(0x1F6C3)),
                new MyEmojicon(getEmojiByUnicode(0x1F6C4)),
                new MyEmojicon(getEmojiByUnicode(0x1F6C5)),
                new MyEmojicon(getEmojiByUnicode(0x1F500)),
                new MyEmojicon(getEmojiByUnicode(0x1F501)),
                new MyEmojicon(getEmojiByUnicode(0x1F502)),
                new MyEmojicon(getEmojiByUnicode(0x1F504)),
                new MyEmojicon(getEmojiByUnicode(0x1F505)),
                new MyEmojicon(getEmojiByUnicode(0x1F506)),
        };


        return DATA;
    }

    public static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }
}
