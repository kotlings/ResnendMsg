package com.wyn88.resend.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.wyn88.resend.imp.Conditions;
import com.wyn88.resend.R;

/**
 * 控件状态切换
 * Created by zxj on 17/6/12.
 */

public class ViewStateHelper {

    /**
     * 输入密码禁止空格
     *
     * @param editTexts
     * @param editTexts
     */

    public static void addtrimController(EditText... editTexts) {

        for (final EditText editText : editTexts) {
            editText.addTextChangedListener(new ListenerUtils.SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    if (s.toString().contains(" ")) {
                        String[] str = s.toString().split(" ");
                        String str1 = "";
                        for (int i = 0; i < str.length; i++) {
                            str1 += str[i];
                        }
                        editText.setText(str1);
                        editText.setSelection(start);
                    }
                }
            });
        }
    }


    /**
     * 切换按钮状态
     *
     * @param view
     * @param controller
     * @param editTexts
     */
    public static void addStateController(final View view, final Conditions controller, EditText... editTexts) {
        view.setClickable(false);
        if (view.getBackground() != null)
//            view.getBackground().setLevel(view.getResources().getInteger(R.integer.disable_level));
            view.setEnabled(false);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new ListenerUtils.SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if ((controller.checkState())) {
                        view.setEnabled(true);
                        if (view.getBackground() != null)
                            view.getBackground().setLevel(view.getResources().getInteger(R.integer.enable_level));
                        view.setClickable(true);
                    } else {
                        view.setEnabled(false);
                        view.setClickable(false);
                        if (view.getBackground() != null)
                            view.getBackground().setLevel(view.getResources().getInteger(R.integer.disable_level));
                    }
                }
            });
        }
    }


    /**
     * 切换按钮状态
     *
     * @param view
     * @param controller
     * @param editTexts
     */
    public static void addStateControllerBt(final Context context, final TextView view, final int select, final int unSelect, final Conditions controller, EditText... editTexts) {
        view.setClickable(false);
        if (view.getBackground() != null)
            view.setEnabled(false);
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new ListenerUtils.SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if ((controller).checkState()) {
                        view.setEnabled(true);
                        if (view.getBackground() != null) {
                            if (select == 0) {
                                view.setBackgroundResource(R.drawable.bg_btn_orange_full_14_shape);
                            } else {
                                view.setBackgroundResource(select);
                            }
                        }
                        view.setTextColor(context.getResources().getColor(R.color.white));
                        view.setClickable(true);
                    } else {
                        view.setEnabled(false);
                        view.setClickable(false);
                        view.setTextColor(context.getResources().getColor(R.color.white));
                        if (view.getBackground() != null) {
                            if (unSelect == 0) {
                                view.setBackgroundResource(R.drawable.bg_btn_gray_full_14_shape);
                            } else {
                                view.setBackgroundResource(unSelect);
                            }
                        }
                    }
                }
            });
        }
    }


}
