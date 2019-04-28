package com.wyn88.resendmessage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindView;
import butterknife.OnClick;
import com.dou_pai.DouPai.common.helper.ViewStateHelper;
import com.dou_pai.DouPai.common.widget.ActionTitleBar;
import com.dou_pai.DouPai.common.widget.social.SocialView;
import com.dou_pai.DouPai.core.scheme.DPUtils;
import com.dou_pai.DouPai.core.sharesdk.SocialKits;
import com.dou_pai.DouPai.core.statis.DpAnalysis;
import com.dou_pai.DouPai.modul.loginregist.controller.UserLoginController;
import com.dou_pai.DouPai.modul.loginregist.helper.ResendHelper;
import com.dou_pai.DouPai.modul.loginregist.helper.ShareInfoHelper;
import com.dou_pai.DouPai.ui.base.Condition;
import com.dou_pai.DouPai.ui.base.LocalActivityBase;
import com.dou_pai.DouPai.ui.base.Permission;
import com.doupai.dao.http.HttpClientBase;
import com.doupai.tools.TextKits;
import com.doupai.tools.annotation.AccessPermission;
import com.doupai.tools.concurrent.Counter;
import com.doupai.tools.http.client.ClientError;
import com.doupai.tools.share.Platform;
import com.doupai.ui.custom.bar.CommonTitleBar;
import com.doupai.ui.custom.text.ClearableEditText;

/**
 * 手机验证码登录
 *
 * @version 1.0
 * @since 5.0.0
 * <p>
 * Created by Leo on 11/15/2017.
 */
@AccessPermission(Permission.SIGN)
public class LoginBySmsActivity extends LocalActivityBase {


    @Bind(R.id.cet_phone)
    ClearableEditText cetPhone;
    @Bind(R.id.cet_sms_code)
    ClearableEditText cetSmsCode;
    @Bind(R.id.tv_resend)
    TextView tvSend;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.social_view)
    SocialView socialView;

    @BindView(R.id.title_bar)
    ActionTitleBar titleBar;

    @BindView(R.id.ivLoginByWeChat)
    ImageView ivLoginByWeChat;
    @BindView(R.id.ivLoginBySina)
    ImageView ivLoginBySina;
    @BindView(R.id.ivLoginByQQ)
    ImageView ivLoginByQQ;


//    public boolean isCountDown;//倒计时清除手机号码标识
    private Counter counter;
//    public String tmpNum;//记录上次号码


    // 请求客户端
    private UserLoginController userLoginController;

    @Override
    protected int bindLayout() {
        return R.layout.act_sign_in_by_sms;
    }

    @Override
    protected void onSetupView(@Nullable Bundle savedInstanceState) {
        super.onSetupView(savedInstanceState);
        this.userLoginController = new UserLoginController(this, socialView);
        ViewStateHelper.addStateController(btnLogin, this, cetPhone.getEditText(), cetSmsCode.getEditText());
        //DPUtils.setHideKeyboardOutside(this, cetPhone.getEditText(), cetSmsCode.getEditText());
        DPUtils.setHideKeyboardOutside(this, cetPhone, cetSmsCode);
        initResendState();

        titleBar.hideBack();
        titleBar.setOptions(R.drawable.ic_common_action_close);
        titleBar.setRightOptDrawablePadding(21);
        titleBar.setCallback(new CommonTitleBar.TitleBarCallback() {
            @Override
            public void onClickOption() {
                super.onClickOption();
                finish();
            }
        });

        initParams();
    }

    private void initParams() {

        if (!ShareInfoHelper.isWeixinAvilible(this)) {
            ivLoginByWeChat.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ivLoginBySina.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
        }
        if (!ShareInfoHelper.isWeiBoAvilible(this)) {

            ivLoginBySina.setVisibility(View.GONE);
        }
        if (!ShareInfoHelper.isQQClientAvailable(this)) {
            ivLoginByQQ.setVisibility(View.GONE);
        }

        if (!ShareInfoHelper.isWeixinAvilible(this) &&
                !ShareInfoHelper.isWeiBoAvilible(this) &&
                !ShareInfoHelper.isQQClientAvailable(this)) {
            findViewById(R.id.llSocView).setVisibility(View.GONE);
        }

    }

    private void initResendState() {

        tvSend.setClickable(cetPhone.getText().length() >= 1);
        if (cetPhone.getText().length() >= 1) {
            ResendHelper.getCode(tvSend);
        } else {
            ResendHelper.enable(tvSend);
        }

        cetPhone.setCallBack(() -> {
            if (tvSend != null && !isCountDown) {
                tvSend.setText(getString(R.string.get_sms_code));
            }

        });

    }


    @OnClick(value = R.id.tv_resend, required = {Condition.Network, Condition.FieldValid})
    public void getCode() {
        DpAnalysis.postEvent(DpAnalysis.EVENT_GETVERCODE_SUCCESS);
        tvSend.setClickable(false);
        showLoading();
        userLoginController.sendSms(cetPhone.getEditText(), new HttpClientBase.VoidCallback() {
            @Override
            public void onSuccess() {
                showToast(R.string.tips_code_already_send);
                hideLoading();
                setCountDown();
            }

            @Override
            public boolean onError(ClientError error) {
                hideLoading();
                tvSend.setClickable(true);
                return super.onError(error);
            }
        });
    }


    private void setCountDown() {
        counter = Counter.create(getHandler(), 60, 0, -1, 1000, new Counter.Listener() {
            @Override
            public void update(int count) {
                isCountDown = true;
                tvSend.setText(String.format(getString(R.string.resend) + "(%s)", String.valueOf(count)));
                ResendHelper.resendUpdate(tvSend);
            }

            @Override
            public void complete() {
                isCountDown = false;
                if (TextUtils.isEmpty(cetPhone.getText())) {
                    tvSend.setText(getString(R.string.get_sms_code));
                    ResendHelper.enable(tvSend);
                } else {
                    if (cetPhone.getText().equals(tmpNum)) {
                        //对比上次获取验证码的号码，如果一样则显示重新获取，否则显示获取验证码
                        tvSend.setText(getString(R.string.resend));
                    } else {
                        tvSend.setText(getString(R.string.get_sms_code));
                    }

                    ResendHelper.getCode(tvSend);
                }
            }
        });
        //记录获取验证码的号码
        tmpNum = cetPhone.getText().trim();
        counter.start();
    }


    public void stopCountDown() {

        if (counter != null) {
            counter.cancel(true);
        }
        isCountDown = false;

        if (TextUtils.isEmpty(cetPhone.getText())) {
            tvSend.setText(getString(R.string.get_sms_code));
            ResendHelper.enable(tvSend);
        }
    }


    @OnClick(R.id.tv_sign_in_sms)
    public void signInBySms() {
        dispatchActivity(LoginByPwActivity.class);
        finish();
    }


    @OnClick(R.id.tv_sign_up)
    public void signUp() {
        dispatchActivity(UserRegisterActivity.class);
        finish();
    }

    @OnClick(value = R.id.btn_login, required = {Condition.Network, Condition.FieldValid, Condition.ClickLight})
    public void login() {
        userLoginController.code(cetPhone.getEditText(), cetSmsCode.getEditText());
    }


    @OnClick(R.id.ivLoginByQQ)
    public void qq() {
        this.userLoginController.itemClick(Platform.QQ, SocialKits.SocialType.Login);
    }

    @OnClick(R.id.ivLoginBySina)
    public void sina() {
        this.userLoginController.itemClick(Platform.Sina, SocialKits.SocialType.Login);
    }


    @OnClick(R.id.ivLoginByWeChat)
    public void wechat() {
        this.userLoginController.itemClick(Platform.Wechat, SocialKits.SocialType.Login);
    }


    @Override
    public boolean checkState() {

        final String phone = cetPhone.getText();
        final String verifyCode = cetSmsCode.getText();
        tvSend.setClickable(false);
        tvSend.setTextColor(getResources().getColor(R.color.login_resend_ver_code));

        //先判断手机号
        if (TextUtils.isDigitsOnly(phone) && phone.length() >= 1) {
            if (tvSend.getText().toString().equals(getString(R.string.get_sms_code))) {
                ResendHelper.getCode(tvSend);

            } else if (tvSend.getText().toString().equals(getString(R.string.resend))) {
                ResendHelper.resend(tvSend);
            }

            return !TextUtils.isEmpty(phone) && phone.length() == 11 &&
                    !TextUtils.isEmpty(verifyCode) && verifyCode.length() == 6;
        } else {

            if (!isCountDown) {
                if (phone.length() >= 1) {
                    ResendHelper.getCode(tvSend);
                } else {
                    ResendHelper.enable(tvSend);
                }
            }
        }
        return false;

        /*final String phone = cetPhone.getEditText().getText().toString();
        final String smsCode = cetSmsCode.getEditText().getText().toString();
        if (TextUtils.isEmpty(phone)) {
            tvSend.setTextColor(getResources().getColor(R.color.font_black_light));
        } else if (!TextUtils.isEmpty(phone)
                && tvSend.getText().toString().equals(getString(R.string.get_sms_code))) {
            tvSend.setTextColor(getResources().getColor(R.color.raw_color_blue));
            // 验证码的长度为6, 手机号码为11时才可以点击登录
            if (!TextUtils.isEmpty(smsCode) && smsCode.length() == 6 && phone.length() == 11) {
                return true;
            }
        } else if (TextUtils.isEmpty(smsCode)) {
        } else {
            return true;
        }
        return false;*/
    }

    @Override
    public boolean checkInput() {
        final String phone = cetPhone.getEditText().getText().toString();
        if (!TextKits.checkPhone(phone)) {
            showToast(R.string.phone_format_error);
        } else {
            return true;
        }

        return false;
    }


    @Override
    protected void onPreDestroy() {
        super.onPreDestroy();
        stopCountDown();
    }
}
