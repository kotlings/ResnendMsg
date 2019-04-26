package com.wyn88.resend.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.animation.Animation;
import android.widget.SeekBar;

/**
 * 简化回调写法
 * Created by zxj on 2015/10/13.
 */
public class ListenerUtils {

    private ListenerUtils() {
    }

    public static abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public static abstract class SimpleAnimListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    public static abstract class SimpleSeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    public interface SimpleCallback<T> {
        void complete(T o);

    }


    public interface ImageLoadListener<T> {
        void complete(T o);

        void onFail();
    }


}
