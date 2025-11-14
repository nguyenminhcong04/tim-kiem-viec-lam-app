package com.example.apptuyendungvieclam.ui.payer.payper2

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.apptuyendungvieclam.R
import com.stripe.android.PaymentConfiguration
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

val BackendUrl = "http://10.0.2.2:4242/"

class Payer2Activity : AppCompatActivity() {

    private val httpClient = OkHttpClient()
    private lateinit var publishableKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payer2)
        fetchPublishableKey()

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

    private fun fetchPublishableKey(){
        val request = Request.Builder().url(BackendUrl + "config").build()
        httpClient.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                 displayAlert("Request failed","Error :$e")
            }
            override fun onResponse(call: Call, response: Response) {
                if(!response.isSuccessful){
                    displayAlert("Request failed response","Error : $response")
                }else{
                    val responseData  = response.body?.string()
                    val responseJson = responseData?.let { JSONObject(it) }?: JSONObject()
                    publishableKey = responseJson.getString("publishableKey")

                    PaymentConfiguration.init(applicationContext,publishableKey)
                }
            }
        })
    }

}