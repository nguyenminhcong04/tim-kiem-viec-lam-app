package com.example.apptuyendungvieclam.ui.payer.payper2

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.databinding.ActivityCardBinding
import com.google.gson.GsonBuilder
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent


class CardActivity : AppCompatActivity() {
    private lateinit var paymentIntentClientSecret : String
    private lateinit var stripe : Stripe
    private var binding : ActivityCardBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_card)

        stripe = Stripe(this,PaymentConfiguration.getInstance(applicationContext).publishableKey)
        startCheckOut()

    }
    private fun displayAlert(title: String,message: String){
        runOnUiThread{
            val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
            builder.setPositiveButton("OK",null)
            builder.create().show()
        }
    }

    private fun startCheckOut(){
        ApiClient().createPaymentIntent("card","usd", completion = {
            paymentIntentClientSecret,error ->
            run{
                paymentIntentClientSecret?.let {
                    this.paymentIntentClientSecret = it
                }
                error?.let{
                     displayAlert("Failed to load PaymentIntent","Error: $error")
                }
            }
        })

        binding!!.payButton.setOnClickListener {
            binding!!.cardInputWidget.paymentMethodCreateParams?.let { params ->
                val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params,paymentIntentClientSecret)
                stripe.confirmPayment(this,confirmParams)

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        stripe.onPaymentResult(requestCode, data,object :ApiResultCallback<PaymentIntentResult>{
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                if(paymentIntent.status == StripeIntent.Status.Succeeded){
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    displayAlert("Payment succeded", gson.toJson(paymentIntent))
                }else if(paymentIntent.status == StripeIntent.Status.RequiresPaymentMethod){
                    displayAlert("Payment failed",paymentIntent.lastPaymentError?.message.orEmpty())
                }
            }

            override fun onError(e: Exception) {
                displayAlert("Error",e.toString())
            }

        })
    }
}