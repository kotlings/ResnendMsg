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
    int textBackground;

    public ResendControl(TextView textView, EditText editText) {
        resendHelper = new ResendHelper(textView);


    }


    public void injectAttr(int textColor, int textBackground) {
        this.textColor = textColor;
        this.textBackground = textBackground;

    }


    private void setCountDown() {

        //TODO 倒计时过程中删除号码也不打断倒计时

        String tmp = "";
        counter = Counter.create(handler, 60, 0, -1, 1000, new Counter.Listener() {
            @Override
            public void update(int count) {
                resendHelper.setText(String.format("重新获取(%s)", String.valueOf(count)));
                resendHelper.resendUpdate(textColor, textBackground == 0 ? R.drawable.bg_btn_gray_full_14_shape : textBackground);
            }

            @Override
            public void complete() {
                resendHelper.setText("重新获取");
                resendHelper.getCode(textColor, textBackground == 0 ? R.drawable.bg_btn_orange_full_14_shape : textBackground);
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
