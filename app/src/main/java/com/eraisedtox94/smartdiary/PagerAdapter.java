package com.eraisedtox94.smartdiary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

/**
 * Created by spraful on 4/5/2017.
 */
public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;
    private Context context;

    public PagerAdapter(FragmentManager fm, int NumOfTabs,Context mainContext) {
        super(fm);
        context = mainContext;
        this.mNumOfTabs = NumOfTabs;
    }

    //TODO hardcoded `number of tabs` = 3
    public Fragment tabFragments[] = new Fragment[3];


    @Override
    public Fragment getItem(int position) {

        if (tabFragments[position] != null) {
            // Return a tab we created earlier..
            return tabFragments[position];
        }

        switch (position) {
            case 0:
                FragmentDiaryMain tab1 = new FragmentDiaryMain();
                tabFragments[0]=tab1;
                return tab1;
            case 1:
                FragmentAllEntries tab2 = new FragmentAllEntries();
                tabFragments[1]=tab2;
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                tabFragments[2]=tab3;
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        //TODO to be looked in depth
        if(context==null){
            Log.d("you are culprit","null context");
        }
        int imageResId[] = {R.drawable.icon_pencil,R.drawable.icon_list,R.drawable.icon_settings};
        String tabTitles[] = {"WRITE","LIST","SETTINGS"};
        // Generate title based on item position

        Drawable image = context.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

}
