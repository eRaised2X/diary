package com.eraisedtox94.smartdiary.emoticon;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = View.inflate(getContext(), R.layout.my_emojicon_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (TextView) v.findViewById(R.id.my_emojicon_icon);
            v.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) v.getTag();
        holder.icon.setText(data[position].emojiconCharRepresentation);

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiClickListener.onEmojiconClicked(data[position].emojiconCharRepresentation);
                Log.d("view Clicked in adptr","="+position);
                //todo may be here handle the onclick event
            }
        });
        return v;
    }

    class ViewHolder {
        TextView icon;
    }
}