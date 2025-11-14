package com.example.apptuyendungvieclam.ui.payer

import android.widget.Toast
import com.example.apptuyendungvieclam.R
import com.android.volley.*
import com.example.apptuyendungvieclam.databinding.ActivityPayerBinding
import com.example.apptuyendungvieclam.ui.base.activity.BaseMVVMActivity
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class PayerActivity() : BaseMVVMActivity<PayerActivityCallBack,PayerActivityViewModel>(),PayerActivityCallBack{

//    private var SECRET_KEY : String = "sk_test_51LKdlnFE2VHDpf9G5XhkTsjlzRxgd7DDH5aPTtZy37xNbjPJOz8hJY6IvonqwtcnjnJZbep8pzogdnJ4PZyqbzEt00UXrDSDnN"
    private var PUBLISH_KEY : String = "YOUR_STRIPE_PUBLISHABLE_KEY"
    private var paymentSheet : PaymentSheet? = null
//    private var customerID: String? = null
//    private var EphericalKey: String? = null
//    private var ClientSecret: String? = null
    private var userId : String = ""

    override fun initComponents() {
        getBindingData().payerActivityViewModel = mModel
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finish()
                PayerActivityViewModel.ON_CLICK_PAY -> PaymentFlow()
                PayerActivityViewModel.ON_CLICK_TRIAL -> onTrial30Days()
                PayerActivityViewModel.ON_PAYER_SUCCESS -> onPayerSuccess()
            }
        }
        userId = intent.getStringExtra("userId").toString()
//        Toast.makeText(this,userId,Toast.LENGTH_SHORT).show()
        Toast.makeText(this,"Xin vui lòng đợi 10s rồi ấn chả tắt app :v",Toast.LENGTH_LONG).show()
        PaymentConfiguration.init(this,PUBLISH_KEY)
        paymentSheet = PaymentSheet(this) { paymentSheetResult: PaymentSheetResult? ->
            onPaymentResult(paymentSheetResult!!)
        }
        mModel.payer(applicationContext)
    }

//    private fun payer(){
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST, "https://api.stripe.com/v1/customers",
//            Response.Listener { response ->
//                try {
//                    val `object` = JSONObject(response)
//                    customerID = `object`.getString("id")
//                    getEphericalKey(customerID!!)
//                    Toast.makeText(this,"customerID"+customerID,Toast.LENGTH_SHORT).show()
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }, Response.ErrorListener {
//                println("error" + it.message)
//            }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val header: MutableMap<String, String> = HashMap()
//                header["Authorization"] = "Bearer $SECRET_KEY"
//                return header
//            }
//        }
//
//        val requestQueue = Volley.newRequestQueue(this)
//        requestQueue.add(stringRequest)
//    }

    override fun getLayoutMain(): Int {
        return R.layout.activity_payer
    }

    override fun setEvents() {

    }

    override fun getBindingData() = mBinding as ActivityPayerBinding

    override fun getViewModel(): Class<PayerActivityViewModel> {
        return PayerActivityViewModel::class.java
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
    fun onTrial30Days(){
        if(mModel.checkActive(applicationContext,userId)){
            Toast.makeText(this,"Tài khoản đã được gia hạn" , Toast.LENGTH_SHORT).show()
        }else{
            mModel.setActive(applicationContext,userId)
        }

    }
    fun onPayerSuccess(){
        Toast.makeText(this, "Đã gia hạn thành công", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun onPaymentResult(paymentSheetResult: PaymentSheetResult) {
        when(paymentSheetResult){
            is PaymentSheetResult.Canceled -> {
                Toast.makeText(this, "Hủy bỏ thanh toán", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show()
            }
            is PaymentSheetResult.Completed -> {
                Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show()
                mModel.setActive(applicationContext,userId)
            }
        }
    }

//    private fun getEphericalKey(customerID: String) {
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
//            Response.Listener { response ->
//                try {
//                    val `object` = JSONObject(response)
//                    EphericalKey = `object`.getString("id")
//                    Toast.makeText(this,"EphericalKey"+EphericalKey,Toast.LENGTH_SHORT).show()
//                    getClientSecret(customerID)
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }, Response.ErrorListener { }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val header: MutableMap<String, String> = java.util.HashMap()
//                header["Authorization"] = "Bearer $SECRET_KEY"
//                header["Stripe-Version"] = "2020-08-27"
//                return header
//            }
//
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String>? {
//                val params: MutableMap<String, String> = java.util.HashMap()
//                params["customer"] = customerID
//                return params
//            }
//        }
//        val requestQueue = Volley.newRequestQueue(this)
//        requestQueue.add(stringRequest)
//    }
//
//    private fun getClientSecret(customerID: String) {
//        val stringRequest: StringRequest = object : StringRequest(
//            Method.POST, "https://api.stripe.com/v1/payment_intents",
//            Response.Listener { response ->
//                try {
//                    val `object` = JSONObject(response)
//                    ClientSecret = `object`.getString("client_secret")
//                    Toast.makeText(this,"ClientSecret"+ClientSecret,Toast.LENGTH_SHORT).show()
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }, Response.ErrorListener { }) {
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String> {
//                val header: MutableMap<String, String> = java.util.HashMap()
//                header["Authorization"] = "Bearer $SECRET_KEY"
//                return header
//            }
//
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String>? {
//                val params: MutableMap<String, String> = java.util.HashMap()
//                params["customer"] = customerID
//                params["amount"] = "30" + "00"
//                params["currency"] = "usd"
//                params["automatic_payment_methods[enabled]"] = "true"
//                return params
//            }
//        }
//        val requestQueue = Volley.newRequestQueue(this)
//        requestQueue.add(stringRequest)
//    }

    private fun PaymentFlow() {
        if(mModel.checkActive(applicationContext,userId)){
            Toast.makeText(this,"Tai khoan da duoc gia han" , Toast.LENGTH_SHORT).show()
        }else{
            paymentSheet!!.presentWithPaymentIntent(
                mModel.getClientSecret(), PaymentSheet.Configuration(
                    "ABC Company", PaymentSheet.CustomerConfiguration(
                        mModel.getCustomerID(),
                        mModel.getEphericalKey()
                    )
                )
            )
        }
    }
}