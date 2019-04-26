package com.wyn88.resend.utils;

import android.widget.TextView;
import androidx.annotation.DrawableRes;
import com.wyn88.resend.R;


/**
 * 重新获取验证码，相关状态封装
 * Created by zxj on 2018/11/29.
 */
public class ResendHelper {

    private TextView textView;


    public ResendHelper(TextView textView) {
        this.textView = textView;
    }


    public void setText(String text) {
        this.textView.setText(text);
    }


    /**
     * 倒计时完成，重新获取验证码（验证码倒计时）
     *
     * @param
     */
    public void resend() {
        textView.setClickable(true);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_resend_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_resend_shape);
    }


    public void resend(int textColor, @DrawableRes int textRes) {
        textView.setClickable(true);
        if (textRes != 0) {
            textView.setBackgroundResource(textRes);
        }
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }


    /**
     * 倒计时过程更新状态
     *
     * @param
     */

    public void resendUpdate() {
        textView.setClickable(false);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_resend_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_resend_shape);
    }


    public void resendUpdate(int textColor, @DrawableRes int textRes) {
        textView.setClickable(false);
        if (textRes != 0) {
            textView.setBackgroundResource(textRes);
        }
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }


    /**
     * 不可点击状态
     *
     * @param
     */
    public void enable() {
        textView.setClickable(false);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_enable_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_disabled_shape);
    }

    public void enable(TextView textView, int textColor, @DrawableRes int textRes) {
        textView.setClickable(false);
        if (textRes != 0) {
            textView.setBackgroundResource(textRes);
        }
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }


    /**
     * 可点击（获取验证码）
     *
     * @param
     */
    public void getCode() {
        textView.setClickable(true);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_get_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_checked_shape);
    }


    public void getCode(int textColor, @DrawableRes int textRes) {
        textView.setClickable(true);
        if (textRes != 0) {
            textView.setBackgroundResource(textRes);
        }
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }

}
