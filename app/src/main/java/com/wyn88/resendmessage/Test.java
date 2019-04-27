package com.wyn88.resendmessage;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.wyn88.resendmessage.weight.HttpingDialog;

/**
 * Created by zxj on 2019-04-27.
 */
public class Test  extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
