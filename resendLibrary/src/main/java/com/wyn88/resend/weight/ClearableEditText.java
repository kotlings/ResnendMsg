package com.wyn88.resend.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.wyn88.resend.R;

import java.lang.reflect.Field;


/**
 * 自带删除按钮EditText
 * Created by zxj on 2018/11/29.
 */

public class ClearableEditText extends RelativeLayout
        implements View.OnFocusChangeListener, View.OnClickListener {

    private EditText mEditText;
    private ImageView mIvClear;

    private ClearEditCallBack callBack;

    public void setCallBack(ClearEditCallBack callBack) {
        this.callBack = callBack;
    }

    public ClearableEditText(@NonNull Context context) {
        this(context, null);
    }

    public ClearableEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearableEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Drawable clearDrawable = null;
        int maxEms = 0;

        if (null != attrs) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ClearableEditText);

            clearDrawable = array.getDrawable(R.styleable.ClearableEditText_clearDrawable);
            maxEms = array.getInteger(R.styleable.ClearableEditText_maxEms, -1);
            array.recycle();
        }

        mEditText = new EditText(context, attrs);
        mEditText.setId(NO_ID);
        mIvClear = new ImageView(context, attrs);
        mIvClear.setId(NO_ID);
        mIvClear.setId(R.id.iv_clear);
        mIvClear.setScaleType(ImageView.ScaleType.CENTER);

        if (null != clearDrawable) {
            mIvClear.setImageDrawable(clearDrawable);
        }

        if (maxEms > 0) {
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxEms)});
        }

        LayoutParams params = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        addView(mIvClear, params);

        params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.START_OF, mIvClear.getId());

        mEditText.setGravity(Gravity.CENTER_VERTICAL);
        addView(mEditText, params);

        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mEditText, R.drawable.common_bg_editext_cursor_shape);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mIvClear.setVisibility(GONE);
        mIvClear.setOnClickListener(this);
        mEditText.setOnFocusChangeListener(this);
        mEditText.addTextChangedListener(new TextWatcher());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mIvClear.getLayoutParams().width = getHeight();
        int padding = getHeight() / 3;
        mIvClear.setPadding(padding, padding, padding, padding);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && mEditText.getText().length() > 0) {
            mIvClear.setVisibility(VISIBLE);
        } else {
            mIvClear.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        mEditText.setText("");
        if (callBack != null) {
            callBack.onClear();
        }

    }

    public EditText getEditText() {
        return mEditText;
    }

    public String getText() {

        return mEditText.getText().toString().trim();
    }


    public void setmEditText(String s){

        mEditText.setText(s);
    }


    public void setClearDrawable(@DrawableRes int drawable) {
        mIvClear.setImageResource(drawable);
    }

    public void setClearDrawable(@NonNull Drawable drawable) {
        mIvClear.setImageDrawable(drawable);
    }

    private class TextWatcher implements android.text.TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mEditText.hasFocus() && mEditText.getText().length() > 0) {
                mIvClear.setVisibility(VISIBLE);
            } else {
                mIvClear.setVisibility(GONE);
            }
        }
    }


    public interface ClearEditCallBack {

        void onClear();
    }


}
