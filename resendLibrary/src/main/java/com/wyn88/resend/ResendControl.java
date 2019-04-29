package com.wyn88.resend;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import com.wyn88.resend.utils.ResendCounter;
import com.wyn88.resend.utils.ResendHelper;

/**
 * @Description: 核心的控制层
 * @Auther: zxj
 * @Date: 2019-04-26 17:40
 */
public class ResendControl {

    private ResendHelper resendHelper;

    private ResendCounter counter;

    private static Handler handler = new Handler(Looper.getMainLooper());


    //默认字体颜色
    int textColor;

    //可点击 状态背景色
    @DrawableRes
    int selectBackground = R.drawable.bg_btn_orange_full_14_shape;

    //不可点击状态颜色
    @DrawableRes
    int enableBackground = R.drawable.bg_btn_gray_full_14_shape;

    EditText editText;


    private TextView tvSend;

    private String tmpNum;//记录上次号码

    private boolean isCountDown;//倒计时清除手机号码标识

    public ResendControl(TextView textView, EditText editText) {
        resendHelper = new ResendHelper(textView);
        this.editText = editText;
        this.tvSend = textView;
    }


    public void injectStyle(int textColor, int light, int dark) {
        this.textColor = textColor;
        this.selectBackground = light;
        this.enableBackground = dark;

    }


    public boolean isCountDown() {

        return isCountDown;
    }


    public void startCountDown() {

        //TODO 倒计时过程中删除号码也不打断倒计时
        counter = ResendCounter.create(handler, 60, 0, -1, 1000, new ResendCounter.Listener() {
            @Override
            public void update(int count) {
                isCountDown = true;
                resendHelper.setText(String.format("重新获取(%s)", String.valueOf(count)));
                resendHelper.resendUpdate(textColor, enableBackground == 0 ? R.drawable.bg_btn_gray_full_14_shape : enableBackground);
            }

            @Override
            public void complete() {
                isCountDown = false;
                //TODO 倒计时完成删除号码更改显示
                if (TextUtils.isEmpty(editText.getText())) {
                    tvSend.setText("获取验证码");
                    //edtTextView文本为空不能点击
                    resendHelper.enable(textColor, enableBackground == 0 ? R.drawable.bg_btn_gray_full_14_shape : enableBackground);
                } else {
                    if (editText.getText().equals(tmpNum)) {
                        //对比上次获取验证码的号码，如果一样则显示重新获取，否则显示获取验证码
                        tvSend.setText("重新获取");
                    } else {
                        tvSend.setText("获取验证码");
                    }
                    //都是可点击状态
                    resendHelper.getCode(textColor, selectBackground == 0 ? R.drawable.bg_btn_orange_full_14_shape : selectBackground);
                }
            }
        });

        //记录获取验证码的号码
        tmpNum = editText.getText().toString().trim();
        counter.start();
    }


    //检查倒计时
    public void checkStyle() {

        String phone = editText.getText().toString();

        if (TextUtils.isDigitsOnly(phone) && phone.length() >= 1) {
            if (tvSend.getText().toString().equals("获取验证码")) {
                resendHelper.getCode();
            } else if (tvSend.getText().toString().equals("重新获取")) {
                resendHelper.resend();
            } else {
                //倒计时中
                resendHelper.enable();
            }

        } else {

            if (!isCountDown) {
                if (phone.length() >= 1) {
                    resendHelper.getCode();
                } else {
                    resendHelper.enable();
                }
            }
        }

    }


    public void clear() {
        if (counter != null) {
            counter.stop();
        }
    }


}
