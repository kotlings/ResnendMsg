package com.wyn88.resend.utils;

import android.widget.TextView;
import androidx.annotation.DrawableRes;
import com.wyn88.resend.R;


/**
 * 重新获取验证码，相关状态封装
 * Created by zxj on 2018/11/29.
 */
public class ResendHelper {

    /**
     * 重新获取验证码（验证码倒计时）
     *
     * @param textView
     */
    public static void resend(TextView textView) {
        textView.setClickable(true);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_resend_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_resend_shape);
    }


    public static void resend(TextView textView, int textColor, @DrawableRes int textRes) {
        textView.setClickable(true);
        if (textRes != 0) {
            textView.setBackgroundResource(textRes);
        }
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }


    public static void resendUpdate(TextView textView) {
        textView.setClickable(false);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_resend_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_resend_shape);
    }


    public static void resendUpdate(TextView textView, int textColor, @DrawableRes int textRes) {
        textView.setClickable(false);
        if (textRes != 0) {
            textView.setBackgroundResource(textRes);
        }
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }


    /**
     * 不可点击
     *
     * @param textView
     */
    public static void enable(TextView textView) {
        textView.setClickable(false);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_enable_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_disabled_shape);
    }

    public static void enable(TextView textView, int textColor, @DrawableRes int textRes) {
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
     * @param textView
     */
    public static void getCode(TextView textView) {
        textView.setClickable(true);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.login_get_ver_code));
//        textView.setBackgroundResource(R.drawable.bg_login_vercode_checked_shape);
    }


    public static void getCode(TextView textView, int textColor, @DrawableRes int textRes) {
        textView.setClickable(true);
        if (textRes != 0) {
            textView.setBackgroundResource(textRes);
        }
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }

}
