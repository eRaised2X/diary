package com.eraisedtox94.smartdiary.emoticon;

/**
 * Created by spraful on 09-Jun-17.
 */

public class Food {

    public static MyEmojicon[] getData() {
        MyEmojicon[] DATA = {
                new MyEmojicon(getEmojiByUnicode(0x1F345)),
                new MyEmojicon(getEmojiByUnicode(0x1F345)),
                new MyEmojicon(getEmojiByUnicode(0x1F346)),
                new MyEmojicon(getEmojiByUnicode(0x1F347)),
                new MyEmojicon(getEmojiByUnicode(0x1F348)),
                new MyEmojicon(getEmojiByUnicode(0x1F349)),
                new MyEmojicon(getEmojiByUnicode(0x1F34A)),
                new MyEmojicon(getEmojiByUnicode(0x1F34C)),
                new MyEmojicon(getEmojiByUnicode(0x1F34D)),
                new MyEmojicon(getEmojiByUnicode(0x1F34E)),
                new MyEmojicon(getEmojiByUnicode(0x1F34F)),
                new MyEmojicon(getEmojiByUnicode(0x1F351)),
                new MyEmojicon(getEmojiByUnicode(0x1F352)),
                new MyEmojicon(getEmojiByUnicode(0x1F353)),
                new MyEmojicon(getEmojiByUnicode(0x1F354)),
                new MyEmojicon(getEmojiByUnicode(0x1F355)),
                new MyEmojicon(getEmojiByUnicode(0x1F356)),
                new MyEmojicon(getEmojiByUnicode(0x1F357)),
                new MyEmojicon(getEmojiByUnicode(0x1F358)),
                new MyEmojicon(getEmojiByUnicode(0x1F359)),
                new MyEmojicon(getEmojiByUnicode(0x1F35A)),
                new MyEmojicon(getEmojiByUnicode(0x1F35B)),
                new MyEmojicon(getEmojiByUnicode(0x1F35C)),
                new MyEmojicon(getEmojiByUnicode(0x1F35D)),
                new MyEmojicon(getEmojiByUnicode(0x1F35E)),
                new MyEmojicon(getEmojiByUnicode(0x1F35F)),
                new MyEmojicon(getEmojiByUnicode(0x1F360)),
                new MyEmojicon(getEmojiByUnicode(0x1F361)),
                new MyEmojicon(getEmojiByUnicode(0x1F362)),
                new MyEmojicon(getEmojiByUnicode(0x1F363)),
                new MyEmojicon(getEmojiByUnicode(0x1F364)),
                new MyEmojicon(getEmojiByUnicode(0x1F365)),
                new MyEmojicon(getEmojiByUnicode(0x1F366)),
                new MyEmojicon(getEmojiByUnicode(0x1F367)),
                new MyEmojicon(getEmojiByUnicode(0x1F368)),
                new MyEmojicon(getEmojiByUnicode(0x1F369)),
                new MyEmojicon(getEmojiByUnicode(0x1F36A)),
                new MyEmojicon(getEmojiByUnicode(0x1F36B)),
                new MyEmojicon(getEmojiByUnicode(0x1F36C)),
                new MyEmojicon(getEmojiByUnicode(0x1F36D)),
                new MyEmojicon(getEmojiByUnicode(0x1F36E)),
                new MyEmojicon(getEmojiByUnicode(0x1F370)),
                new MyEmojicon(getEmojiByUnicode(0x1F371)),
                new MyEmojicon(getEmojiByUnicode(0x1F372)),
                new MyEmojicon(getEmojiByUnicode(0x1F373)),
                new MyEmojicon(getEmojiByUnicode(0x1F374)),
                new MyEmojicon(getEmojiByUnicode(0x1F375)),
                new MyEmojicon(getEmojiByUnicode(0x1F376)),
                new MyEmojicon(getEmojiByUnicode(0x1F377)),
                new MyEmojicon(getEmojiByUnicode(0x1F378)),
                new MyEmojicon(getEmojiByUnicode(0x1F379)),
                new MyEmojicon(getEmojiByUnicode(0x1F37A)),
                new MyEmojicon(getEmojiByUnicode(0x1F37B)),
                new MyEmojicon(getEmojiByUnicode(0x1F34B)),
                new MyEmojicon(getEmojiByUnicode(0x1F350)),
                new MyEmojicon(getEmojiByUnicode(0x1F37C)),

        };
        return DATA;
    }


    public static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

}
