package com.jcorp.e_vap.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object ChgerTypeAlpha {

    @BindingAdapter("Atype1")
    @JvmStatic
    fun Atype1(imageView: ImageView, type : Int) {
        when(type) {
            2 -> imageView.alpha = 1.0F

            else -> imageView.alpha = 0.2F
        }
    }

    @BindingAdapter("Chademo")
    @JvmStatic
    fun Chademo(imageView: ImageView, type : Int) {
        when(type) {
            1, 3, 5, 6 -> imageView.alpha = 1.0F

            else -> imageView.alpha = 0.2F
        }
    }

    @BindingAdapter("DCCombo")
    @JvmStatic
    fun DCCombo (imageView: ImageView, type: Int) {
        when(type) {
            4, 5, 6 -> imageView.alpha =  1.0F

            else -> imageView.alpha = 0.2F
        }
    }

    @BindingAdapter("AC3")
    @JvmStatic
    fun AC3 (imageView: ImageView, type: Int) {
        when (type) {
            3, 6, 7 -> imageView.alpha = 1.0F

            else -> imageView.alpha = 0.2F
        }
    }

}