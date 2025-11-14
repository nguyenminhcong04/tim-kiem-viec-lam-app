package com.example.apptuyendungvieclam.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.databinding.RateUsDialogLayoutBinding

class RateUsDialog(context: Context) : Dialog(context) {
    private var binding: RateUsDialogLayoutBinding? = null
    private var userRate = 0f
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.rate_us_dialog_layout, null, false)
        setContentView(binding!!.root)
        onClick()
    }

    private fun onClick() {
        binding!!.ratingBar.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { _, rating, _ ->
                if (rating <= 1) {
                    binding!!.imgStatus.setImageResource(R.drawable.one_star)
                } else if (rating <= 2) {
                    binding!!.imgStatus.setImageResource(R.drawable.two_star)
                } else if (rating <= 3) {
                    binding!!.imgStatus.setImageResource(R.drawable.three_star)
                } else if (rating <= 4) {
                    binding!!.imgStatus.setImageResource(R.drawable.four_star)
                } else if (rating <= 5) {
                    binding!!.imgStatus.setImageResource(R.drawable.five_star)
                }
                animateImage(binding!!.imgStatus)
                userRate = rating
            }
        binding!!.btnRateNow.setOnClickListener({
            dismiss()
            Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show()
        })
        binding!!.btnLater.setOnClickListener({ dismiss() })
    }

    private fun animateImage(ratingImage: ImageView) {
        val scaleAnimation = ScaleAnimation(0F, 1f, 0F, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.fillAfter = true
        scaleAnimation.duration = 200
        binding!!.imgStatus.animation = scaleAnimation
    }
}