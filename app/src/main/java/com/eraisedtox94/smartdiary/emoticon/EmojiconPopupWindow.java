package com.eraisedtox94.smartdiary.emoticon;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.eraisedtox94.smartdiary.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by spraful on 06-Jun-17.
 */

public class EmojiconPopupWindow extends PopupWindow implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private Context mContext;
    private View rootView;

    private ImageButton imageButtonTab1;
    private ImageButton imageButtonTab2;
    private ImageButton imageButtonTab3;
    private ImageButton imageButtonTab4;
    private ImageButton imageButtonTab5;
    private ImageButton imageButtonTab6;
    private ImageButton imageButtonTab7;
    private ImageButton imageButtonTab8;

    private int keyBoardHeight = 0;
    private Boolean pendingOpen = false;
    private Boolean isOpened = false;


    private int[] imageButtonResources = {
            R.drawable.icon_happy_face_white,
            R.drawable.icon_happy_face_dark,
            R.drawable.icon_plum_white,
            R.drawable.icon_plum_dark,
            R.drawable.icon_dog_white,
            R.drawable.icon_dog_dark,
            R.drawable.icon_soccer_white,
            R.drawable.icon_soccer_dark,
            R.drawable.icon_car_white,
            R.drawable.icon_car_dark,
            R.drawable.icon_bulb_white,
            R.drawable.icon_bulb_dark,
            R.drawable.icon_spade_white,
            R.drawable.icon_spade_dark,
            R.drawable.icon_keyboard_delete_dark};

    private ViewPager emojisViewPager;

    EmojisPagerAdapter mEmojisPagerAdapter;

    IEmoticonInterfaces.OnSoftKeyboardOpenCloseListener onSoftKeyboardOpenCloseListener;
    IEmoticonInterfaces.OnEmojiconBackspaceClickedListener onEmojiconBackspaceClickedListener;
    IEmojiconClickedListener emojiconClickedListener;

    public EmojiconPopupWindow(View rootView, Context context) {
        mContext = context;
        this.rootView = rootView;

        View customView = createCustomView();
        setContentView(customView);
        Log.d("popup window created", "here");
        //default size manual
        setSize(WindowManager.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.keyboard_height));
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }


    private View createCustomView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.my_emoticons, (ViewGroup) rootView, false);
        emojisViewPager = (ViewPager) view.findViewById(R.id.view_pager_emoticon);
        emojisViewPager.setOnPageChangeListener(this);

        mEmojisPagerAdapter = new EmojisPagerAdapter(
                Arrays.asList(
                        new MyEmojiconGridView(mContext, People.getData(), this),
                        new MyEmojiconGridView(mContext, Food.getData(), this),
                        new MyEmojiconGridView(mContext, Nature.getData(), this),
                        new MyEmojiconGridView(mContext, Sports.getData(), this),
                        new MyEmojiconGridView(mContext, Transport.getData(), this),
                        new MyEmojiconGridView(mContext, Daily.getData(), this),
                        new MyEmojiconGridView(mContext, Symbols.getData(), this)
                )
        );
        emojisViewPager.setAdapter(mEmojisPagerAdapter);

        imageButtonTab1 = (ImageButton) view.findViewById(R.id.my_emojis_tab_1_people);
        imageButtonTab2 = (ImageButton) view.findViewById(R.id.my_emojis_tab_2_food);
        imageButtonTab3 = (ImageButton) view.findViewById(R.id.my_emojis_tab_3_nature);
        imageButtonTab4 = (ImageButton) view.findViewById(R.id.my_emojis_tab_4_sports);
        imageButtonTab5 = (ImageButton) view.findViewById(R.id.my_emojis_tab_5_transport);
        imageButtonTab6 = (ImageButton) view.findViewById(R.id.my_emojis_tab_6_daily);
        imageButtonTab7 = (ImageButton) view.findViewById(R.id.my_emojis_tab_7_symbols);
        imageButtonTab8 = (ImageButton) view.findViewById(R.id.my_emojis_tab_8_backspace);

        imageButtonTab1.setOnClickListener(this);
        imageButtonTab2.setOnClickListener(this);
        imageButtonTab3.setOnClickListener(this);
        imageButtonTab4.setOnClickListener(this);
        imageButtonTab5.setOnClickListener(this);
        imageButtonTab6.setOnClickListener(this);
        imageButtonTab7.setOnClickListener(this);



        imageButtonTab8.setOnTouchListener(new RepeatListener(1000, 50, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEmojiconBackspaceClickedListener != null)
                            onEmojiconBackspaceClickedListener.onEmojiconBackspaceClicked(v);
                    }
                })
        );

        return view;
    }
    //      a b c d e f g h
    //white 0 2 4 6 8 10 12 14
    //light 1 3 5 7 9 11 13
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_emojis_tab_1_people:
                emojisViewPager.setCurrentItem(0);
                handleEmojiTabHighlighting(imageButtonResources[0],imageButtonResources[3],
                        imageButtonResources[5],imageButtonResources[7],imageButtonResources[9],
                        imageButtonResources[11], imageButtonResources[13],imageButtonResources[14]);
                break;
            case R.id.my_emojis_tab_2_food:
                emojisViewPager.setCurrentItem(1);
                handleEmojiTabHighlighting(imageButtonResources[1],imageButtonResources[2],imageButtonResources[5],
                        imageButtonResources[7],imageButtonResources[9],imageButtonResources[11],
                        imageButtonResources[13], imageButtonResources[14]);
                break;
            case R.id.my_emojis_tab_3_nature:
                emojisViewPager.setCurrentItem(2);
                handleEmojiTabHighlighting(imageButtonResources[1],imageButtonResources[3],imageButtonResources[4],
                        imageButtonResources[7],imageButtonResources[9],imageButtonResources[11],
                        imageButtonResources[13], imageButtonResources[14]);
                break;
            case R.id.my_emojis_tab_4_sports:
                emojisViewPager.setCurrentItem(3);
                handleEmojiTabHighlighting(imageButtonResources[1],imageButtonResources[3],imageButtonResources[5],
                        imageButtonResources[6],imageButtonResources[9],imageButtonResources[11],
                        imageButtonResources[13], imageButtonResources[14]);
                break;
            case R.id.my_emojis_tab_5_transport:
                emojisViewPager.setCurrentItem(4);
                handleEmojiTabHighlighting(imageButtonResources[1],imageButtonResources[3],imageButtonResources[5],
                        imageButtonResources[7],imageButtonResources[8],imageButtonResources[11],
                        imageButtonResources[13], imageButtonResources[14]);
                break;
            case R.id.my_emojis_tab_6_daily:
                emojisViewPager.setCurrentItem(5);
                handleEmojiTabHighlighting(imageButtonResources[1],imageButtonResources[3],imageButtonResources[5],
                        imageButtonResources[7],imageButtonResources[9],imageButtonResources[10],
                        imageButtonResources[13], imageButtonResources[14]);
                break;
            case R.id.my_emojis_tab_7_symbols:
                emojisViewPager.setCurrentItem(6);
                handleEmojiTabHighlighting(imageButtonResources[1],imageButtonResources[3],imageButtonResources[5],
                        imageButtonResources[7],imageButtonResources[9],imageButtonResources[11],
                        imageButtonResources[12], imageButtonResources[14]);
                break;
            case R.id.my_emojis_tab_8_backspace:
                //TODO //for delete key..
                //imageButtonResources[15] should be put
                emojisViewPager.setCurrentItem(7);
                handleEmojiTabHighlighting(imageButtonResources[1],imageButtonResources[3],imageButtonResources[5],
                        imageButtonResources[7],imageButtonResources[9],imageButtonResources[11],
                        imageButtonResources[13], imageButtonResources[14]);
                break;
            default:
                break;
        }

    }

    public void handleEmojiTabHighlighting(int idBtn1, int idBtn2, int idBtn3, int idBtn4,
                                           int idBtn5, int idBtn6, int idBtn7, int idBtn8) {
        imageButtonTab1.setImageResource(idBtn1);
        imageButtonTab2.setImageResource(idBtn2);
        imageButtonTab3.setImageResource(idBtn3);
        imageButtonTab4.setImageResource(idBtn4);
        imageButtonTab5.setImageResource(idBtn5);
        imageButtonTab6.setImageResource(idBtn6);
        imageButtonTab7.setImageResource(idBtn7);
        imageButtonTab8.setImageResource(idBtn8);
    }


    /**
     * A class, that can be used as a TouchListener on any view (e.g. a Button).
     * It cyclically runs a clickListener, emulating keyboard-like behaviour. First
     * click is fired immediately, next before initialInterval, and subsequent before
     * normalInterval.
     * <p/>
     * <p>Interval is scheduled before the onClick completes, so it has to run fast.
     * If it runs slow, it does not generate skipped onClicks.
     */
    public static class RepeatListener implements View.OnTouchListener {

        private Handler handler = new Handler();

        private int initialInterval;
        private final int normalInterval;
        private final View.OnClickListener clickListener;

        private Runnable handlerRunnable = new Runnable() {
            @Override
            public void run() {
                if (downView == null) {
                    return;
                }
                handler.removeCallbacksAndMessages(downView);
                handler.postAtTime(this, downView, SystemClock.uptimeMillis() + normalInterval);
                clickListener.onClick(downView);
            }
        };

        private View downView;

        /**
         * @param initialInterval The interval before first click event
         * @param normalInterval  The interval before second and subsequent click
         *                        events
         * @param clickListener   The OnClickListener, that will be called
         *                        periodically
         */
        public RepeatListener(int initialInterval, int normalInterval, View.OnClickListener clickListener) {
            if (clickListener == null)
                throw new IllegalArgumentException("null runnable");
            if (initialInterval < 0 || normalInterval < 0)
                throw new IllegalArgumentException("negative interval");

            this.initialInterval = initialInterval;
            this.normalInterval = normalInterval;
            this.clickListener = clickListener;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downView = view;
                    handler.removeCallbacks(handlerRunnable);
                    handler.postAtTime(handlerRunnable, downView, SystemClock.uptimeMillis() + initialInterval);
                    clickListener.onClick(view);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    handler.removeCallbacksAndMessages(downView);
                    downView = null;
                    return true;
            }
            return false;
        }
    }


    private static class EmojisPagerAdapter extends PagerAdapter {
        private List<MyEmojiconGridView> views;

        public EmojisPagerAdapter(List<MyEmojiconGridView> views) {
            super();
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = views.get(position).rootView;
            ((ViewPager) container).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            ((ViewPager) container).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View view, Object key) {
            return key == view;
        }
    }


    /**
     * Set the listener for the event of keyboard opening or closing.
     */
    public void setOnSoftKeyboardOpenCloseListener(IEmoticonInterfaces.OnSoftKeyboardOpenCloseListener listener) {
        this.onSoftKeyboardOpenCloseListener = listener;
    }


    /**
     * Set the listener for the event when backspace on emojicon popup is clicked
     */
    public void setOnEmojiconBackspaceClickedListener(IEmoticonInterfaces.OnEmojiconBackspaceClickedListener listener){
        this.onEmojiconBackspaceClickedListener = listener;
    }

    /**
     * Set the listener for the event when any of the emojicon is clicked
     */
    public void setEmojiconClickedListener(IEmojiconClickedListener listener) {
        this.emojiconClickedListener = listener;
    }


    /**
     * Call this function to resize the emoji popup according to your soft keyboard size
     */
    public void setSizeForSoftKeyboard() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);

                int screenHeight = getUsableScreenHeight();
                int heightDifference = screenHeight
                        - (r.bottom - r.top);
                int resourceId = mContext.getResources()
                        .getIdentifier("status_bar_height",
                                "dimen", "android");
                if (resourceId > 0) {

                    heightDifference -= mContext.getResources()
                            .getDimensionPixelSize(resourceId);
                }
                if (heightDifference > 100) {
                    keyBoardHeight = heightDifference;
                    setSize(WindowManager.LayoutParams.MATCH_PARENT, keyBoardHeight);
                    if (!isOpened) {
                        if (onSoftKeyboardOpenCloseListener != null)
                            onSoftKeyboardOpenCloseListener.onKeyboardOpen(keyBoardHeight);
                    }
                    isOpened = true;
                    if (pendingOpen) {
                        showAtBottom();
                        pendingOpen = false;
                    }
                } else {
                    isOpened = false;
                    if (onSoftKeyboardOpenCloseListener != null)
                        onSoftKeyboardOpenCloseListener.onKeyboardClose();
                }
            }
        });
    }


    /**
     * @return Returns true if the soft keyboard is open, false otherwise.
     */
    public Boolean isKeyBoardOpen() {
        return isOpened;
    }


    public void showAtBottom() {
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }


    /**
     * Use this function when the soft keyboard has not been opened yet. This
     * will show the emoji popup after the keyboard is up next time.
     * Generally, you will be calling InputMethodManager.showSoftInput function after
     * calling this function.
     */
    public void showAtBottomPending() {
        if (isKeyBoardOpen())
            showAtBottom();
        else
            pendingOpen = true;
    }


    private int getUsableScreenHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();

            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            return metrics.heightPixels;

        } else {
            return rootView.getRootView().getHeight();
        }
    }


    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}