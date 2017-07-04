package com.eraisedtox94.smartdiary.emoticon;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.eraisedtox94.smartdiary.R;

/**
 * Created by spraful on 08-Jun-17.
 */

public class MyEmojiAdapter extends ArrayAdapter<MyEmojicon> {

    IEmojiconClickedListener emojiClickListener;
    MyEmojicon[] data;
    Context mContext ;

    public MyEmojiAdapter(Context context, MyEmojicon[] data) {
        super(context, R.layout.my_emojicon_item, data);
        mContext = context;
        this.data = data;
    }

    public void setEmojiClickListener(IEmojiconClickedListener listener) {
        this.emojiClickListener = listener;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View myView = convertView;
        if (myView == null) {
            myView = View.inflate(getContext(), R.layout.my_emojicon_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (TextView) myView.findViewById(R.id.my_emojicon_icon);
            myView.setTag(holder);
        }

        final View mytempView = myView;
        ViewHolder holder = (ViewHolder) myView.getTag();
        holder.icon.setText(data[position].emojiconCharRepresentation);

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                emojiClickListener.onEmojiconClicked(data[position].emojiconCharRepresentation);
                Log.d("myView Clicked in adptr","="+position);
                //todo may be this pseudo onclick feedback can be improved
                mytempView.setBackgroundColor(mContext.getResources().getColor(R.color.colorDiaryMainLayoutBackground));
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mytempView.setBackgroundColor(Color.TRANSPARENT);
                    }
                }, 100);
            }
        });

        return myView;
    }

    class ViewHolder {
        TextView icon;
    }

}