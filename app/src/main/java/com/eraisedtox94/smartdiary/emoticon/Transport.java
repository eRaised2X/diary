package com.eraisedtox94.smartdiary.emoticon;

/**
 * Created by spraful on 10-Jun-17.
 */

public class Transport {

    public static MyEmojicon[] getData() {
        MyEmojicon[] DATA = {

                new MyEmojicon(getEmojiByUnicode(0x1F680)),
                new MyEmojicon(getEmojiByUnicode(0x1F683)),
                new MyEmojicon(getEmojiByUnicode(0x1F684)),
                new MyEmojicon(getEmojiByUnicode(0x1F685)),
                new MyEmojicon(getEmojiByUnicode(0x1F687)),
                new MyEmojicon(getEmojiByUnicode(0x1F689)),
                new MyEmojicon(getEmojiByUnicode(0x1F68C)),
                new MyEmojicon(getEmojiByUnicode(0x1F691)),
                new MyEmojicon(getEmojiByUnicode(0x1F692)),
                new MyEmojicon(getEmojiByUnicode(0x1F693)),
                new MyEmojicon(getEmojiByUnicode(0x1F694)),
                new MyEmojicon(getEmojiByUnicode(0x1F695)),
                new MyEmojicon(getEmojiByUnicode(0x1F696)),
                new MyEmojicon(getEmojiByUnicode(0x1F697)),
                new MyEmojicon(getEmojiByUnicode(0x1F698)),
                new MyEmojicon(getEmojiByUnicode(0x1F699)),
                new MyEmojicon(getEmojiByUnicode(0x1F681)),
                new MyEmojicon(getEmojiByUnicode(0x1F682)),
                new MyEmojicon(getEmojiByUnicode(0x1F686)),
                new MyEmojicon(getEmojiByUnicode(0x1F688)),
                new MyEmojicon(getEmojiByUnicode(0x1F68A)),
                new MyEmojicon(getEmojiByUnicode(0x1F68D)),
                new MyEmojicon(getEmojiByUnicode(0x1F68E)),
                new MyEmojicon(getEmojiByUnicode(0x1F690)),
                new MyEmojicon(getEmojiByUnicode(0x1F694)),
                new MyEmojicon(getEmojiByUnicode(0x1F696)),
                new MyEmojicon(getEmojiByUnicode(0x1F698)),
                new MyEmojicon(getEmojiByUnicode(0x1F69B)),
                new MyEmojicon(getEmojiByUnicode(0x1F69C)),
                new MyEmojicon(getEmojiByUnicode(0x1F69D)),
                new MyEmojicon(getEmojiByUnicode(0x1F69E)),
                new MyEmojicon(getEmojiByUnicode(0x1F69F)),
                new MyEmojicon(getEmojiByUnicode(0x1F6A0)),
                new MyEmojicon(getEmojiByUnicode(0x1F6A1)),
                new MyEmojicon(getEmojiByUnicode(0x1F6A3)),
                new MyEmojicon(getEmojiByUnicode(0x1F6A6)),
                new MyEmojicon(getEmojiByUnicode(0x1F6AF)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B0)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B1)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B3)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B4)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B5)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B7)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B8)),

                //places
                new MyEmojicon(getEmojiByUnicode(0x1F6B2)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B6)),
                new MyEmojicon(getEmojiByUnicode(0x1F6B9)),
                new MyEmojicon(getEmojiByUnicode(0x1F6BA)),
                new MyEmojicon(getEmojiByUnicode(0x1F6BB)),
                new MyEmojicon(getEmojiByUnicode(0x26EA)),
                new MyEmojicon(getEmojiByUnicode(0x26F5)),
                new MyEmojicon(getEmojiByUnicode(0x26FA)),
                new MyEmojicon(getEmojiByUnicode(0x1F303)),
                new MyEmojicon(getEmojiByUnicode(0x1F306)),
                new MyEmojicon(getEmojiByUnicode(0x1F307)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E0)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E1)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E2)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E3)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E5)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E6)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E7)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E8)),
                new MyEmojicon(getEmojiByUnicode(0x1F3E9)),
                new MyEmojicon(getEmojiByUnicode(0x1F3EA)),
                new MyEmojicon(getEmojiByUnicode(0x1F3EB)),
                new MyEmojicon(getEmojiByUnicode(0x1F3EC)),
                new MyEmojicon(getEmojiByUnicode(0x1F3ED)),
                new MyEmojicon(getEmojiByUnicode(0x1F3EE)),
                new MyEmojicon(getEmojiByUnicode(0x1F3EF)),
                new MyEmojicon(getEmojiByUnicode(0x1F3F0)),
                new MyEmojicon(getEmojiByUnicode(0x1F5FB)),
                new MyEmojicon(getEmojiByUnicode(0x1F5FC)),
                new MyEmojicon(getEmojiByUnicode(0x1F5FD)),
                new MyEmojicon(getEmojiByUnicode(0x1F5FF)),
        };
        return DATA;
    }


    public static String getEmojiByUnicode(int unicode) {
        return new String(Character.toChars(unicode));
    }

}
