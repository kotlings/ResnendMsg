package com.wyn88.resendmessage.weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.wyn88.resendmessage.R;


public class HttpingDialog extends Dialog {

    private boolean isCancelable;
    private boolean isCanceledOnTouchOutside;
    private TextView tvHint;

    private WindowManager.LayoutParams mParams;


    public HttpingDialog(Context context, boolean isCanBack) {
        super(context, R.style.SimpleDialog);
        this.isCancelable = isCanBack;
        this.isCanceledOnTouchOutside = isCanBack;
    }

    public HttpingDialog(Context context, boolean isCancelable, boolean isCanceledOnTouchOutside) {
        super(context, R.style.SimpleDialog);
        this.isCancelable = isCancelable;
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutside;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.ui_dialog_loading);
        setCancelable(isCancelable);
        setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        this.mParams = getWindow().getAttributes();
//        getWindow().setWindowAnimations(R.style.FadeAnim);
        this.mParams.gravity = Gravity.CENTER;
        this.mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        this.mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        this.mParams.alpha = 1.0f;
        this.mParams.dimAmount = 0.0f;
        initView();
    }

    private void initView() {
        this.tvHint = findViewById(R.id.ui_tv_loading_hint);
        if (null != tvHint) {
            this.tvHint.setVisibility(View.GONE);
        }
    }


    public void setHint(@NonNull final String hint) {
        if (null != tvHint) {
            tvHint.post(() -> {
                if (!TextUtils.isEmpty(hint)) {
                    tvHint.setVisibility(View.VISIBLE);
                    tvHint.setText(hint);
                } else {
                    tvHint.setVisibility(View.GONE);
                }

            });
        }
    }


    public void setHint(@StringRes final int hint) {
        setHint(0 != hint ? getContext().getResources().getString(hint) : "");
    }

    @Override
    public void cancel() {
        super.cancel();
    }


}
