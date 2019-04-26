package com.wyn88.resendmessage

import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wyn88.resend.Counter
import com.wyn88.resend.imp.Conditions
import com.wyn88.resend.utils.ResendHelper
import com.wyn88.resend.utils.ViewStateHelper
import com.wyn88.resend.weight.ClearableEditText
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @Description:
 * @Auther: zxj
 * @Date: 2019-04-26
 *
 */

class MainActivity : AppCompatActivity(), Conditions {

    private val TAG = "MainActivity"

    private lateinit var etPhone: ClearableEditText
    private lateinit var edtCode: ClearableEditText
    private lateinit var tvSend: TextView
    private lateinit var tvNext: TextView

    private lateinit var counter: Counter

    var resendHelper: ResendHelper? = null

    val handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            1 -> ""
            2 -> ""
            3 -> ""
            4 -> ""
            else -> {

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


        resendHelper = ResendHelper().init(tvSend)
        initEvent()

    }

    fun initEvent() {

        tvSend.setOnClickListener {

            if (etPhone.text.toString().isEmpty()) {
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //TODO 网络请求部分


        }

    }


    private fun setCountDown() {



        counter = Counter.create(handler, 60, 0, -1, 1000, object : Counter.Listener {
            override fun update(count: Int) {
                tvSend.text = String.format("重新获取(%s)", count.toString())
                resendHelper!!.resendUpdate(0, R.drawable.bg_btn_gray_full_14_shape)
            }

            override fun complete() {
                tvSend.text = "重新获取"
                resendHelper!!.getCode(0, R.drawable.bg_btn_orange_full_14_shape)
            }
        })

        counter.start()
    }


    override fun checkState(): Boolean {

        val smsCode = etPhone.text.toString()
        return smsCode.isNotEmpty()
    }


}
