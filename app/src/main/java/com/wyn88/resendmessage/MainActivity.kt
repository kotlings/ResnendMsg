package com.wyn88.resendmessage

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wyn88.resend.ResendControl
import com.wyn88.resend.imp.ICondition
import com.wyn88.resend.utils.ViewStateHelper
import com.wyn88.resend.weight.ClearEditTextView
import com.wyn88.resendmessage.weight.HttpingDialog
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Description:
 * @Auther: zxj
 * @Date: 2019-04-26
 *
 */

class MainActivity : AppCompatActivity(), ICondition {

    private val TAG = "MainActivity"

    private lateinit var etPhone: ClearEditTextView
    private lateinit var edtCode: ClearEditTextView
    private lateinit var tvSend: TextView
    private lateinit var tvNext: TextView
    private var httpDialog: HttpingDialog? = null

    companion object {
        const val HINT_DIALOG = 0X00034
    }


    var resendControl: ResendControl? = null

    private val handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            HINT_DIALOG -> {
                httpDialog!!.dismiss()
                setCountDown()
            }
            else -> {
                Log.e(TAG, "default....")
            }
        }
        false
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etPhone = findViewById(R.id.edtPhone)
        edtCode = findViewById(R.id.edtCode)
        tvSend = findViewById(R.id.tvResend)
        tvNext = findViewById(R.id.tvNext)

        ViewStateHelper.addtrimController(edtCode.editText, edtPhone.editText)
        ViewStateHelper.addStateControllerBt(
            this,
            tvSend,
            R.drawable.bg_btn_orange_full_14_shape,
            R.drawable.bg_btn_gray_full_14_shape,
            this,
            etPhone.editText
        )

        ViewStateHelper.addStateControllerBt(
            this,
            tvNext,
            R.drawable.bg_btn_orange_full_14_shape,
            R.drawable.bg_btn_gray_full_14_shape,
            this,
            etPhone.editText
        )
        resendControl = ResendControl(tvSend, etPhone.editText)
        initEvent()
    }

    private fun initEvent() {

        tvSend.setOnClickListener {
            if (etPhone.text.toString().isEmpty()) {
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            getData()
        }

        etPhone.setCallBack {

            //TODO 判断是否在倒计时
            if (!resendControl!!.isCountDown) {
                tvSend.text = "获取验证码"
            }
        }

    }


    //网络请求部分
    private fun getData() {
        httpDialog = HttpingDialog(this, false)
        httpDialog!!.show()
        handler.sendEmptyMessageDelayed(HINT_DIALOG, 3000)
    }


    private fun setCountDown() {
        resendControl!!.startCountDown()
    }


    override fun checkState(): Boolean {
        val smsCode = etPhone.text.toString()
        resendControl!!.checkStyle()

        if (resendControl!!.isCountDown) {
            return false
        }
        return smsCode.isNotEmpty()
    }


    override fun onDestroy() {
        super.onDestroy()
        resendControl!!.clear()
    }

}
