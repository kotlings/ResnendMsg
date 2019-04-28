package com.wyn88.resend;

import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import com.wyn88.resend.utils.ResendHelper;

/**
 * @Description: 核心的控制层
 * @Auther: zxj
 * @Date: 2019-04-26 17:40
 */
public class ResendControl {

    private ResendHelper resendHelper;

    private Counter counter;

    private static Handler handler = new Handler(Looper.getMainLooper());

    int textColor;
    @DrawableRes
    int selectBackground;

    @DrawableRes
    int enableBackground;
    EditText editText;

    private String tmpNum;//记录上次号码

    private boolean isCountDown;//倒计时清除手机号码标识

    public ResendControl(TextView textView, EditText editText) {
        resendHelper = new ResendHelper(textView);
        this.editText = editText;
    }


    public void injectStyle(int textColor, int light, int dark) {
        this.textColor = textColor;
        this.selectBackground = light;
        this.enableBackground = dark;

    }


    public void startCountDown() {

        //TODO 倒计时过程中删除号码也不打断倒计时
        String temp = "";
        counter = Counter.create(handler, 60, 0, -1, 1000, new Counter.Listener() {
            @Override
            public void update(int count) {
                resendHelper.setText(String.format("重新获取(%s)", String.valueOf(count)));
                resendHelper.resendUpdate(textColor, enableBackground == 0 ? R.drawable.bg_btn_gray_full_14_shape : enableBackground);
            }

            @Override
            public void complete() {
                resendHelper.setText("重新获取");
                resendHelper.getCode(textColor, selectBackground == 0 ? R.drawable.bg_btn_orange_full_14_shape : selectBackground);
            }
        });

        counter.start();
    }


    private void clear() {
        if (counter != null) {
            counter.stop();
        }
    }


}
