package com.solie.ev_nyang.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.solie.ev_nyang.R

object BindingAdapter {

    @BindingAdapter("setStatusImage")
    @JvmStatic
    fun setStatusImage(imageView : ImageView, status : Int) {
        when(status) {
            2 ->  Glide.with(imageView.context).load(R.drawable.icon_slow).into(imageView)

            else -> Glide.with(imageView.context).load(R.drawable.icon_fast).into(imageView)
        }
    }

    @BindingAdapter("setLimitTxt", "setLmtDetail")
    @JvmStatic
    fun setLimitTxt (textView: TextView, status : String, detail : String) {
        when(status) {
            "Y" -> {
                textView.text = detail
            }
            "N" -> {
                textView.text = "제한 없음"
            }
        }
    }

    @BindingAdapter("ParkingFree")
    @JvmStatic
    fun ParkingFree (textView: TextView, stat : String) {
        when(stat) {
            "Y" -> textView.text = "무료"
            "N" -> textView.text = "유료"
            else -> textView.text = "현장 확인"
        }
    }

    @BindingAdapter("setStatNum")
    @JvmStatic
    fun setStatNum(textView: TextView, stat : Int) {
        textView.text = ("${stat + 1}번").toString()
    }

    @BindingAdapter("ChgerStatus")
    @JvmStatic
    fun ChgerStatus(imageView: ImageView, stat : Int) {
        var imgResource = 0
        when(stat) {
            1 -> {
                imgResource = R.drawable.icon_error
            }
            2 -> {
                imgResource = R.drawable.icon_can_charge
            }
            3 -> {
                imgResource = R.drawable.icon_in_charge
            }
            4 -> {
                imgResource = R.drawable.icon_stop
            }
            5 -> {
                imgResource = R.drawable.icon_inspection
            }
            9 -> {
                imgResource = R.drawable.icon_unknown
            }
        }
        Glide.with(imageView.context).load(imgResource).into(imageView)
    }

    @BindingAdapter("StatusText")
    @JvmStatic
    fun StatusText (textView: TextView, stat : Int) {
        var text = ""
        when(stat) {
            1 -> {
                text = "통신이상"
            }
            2 -> {
                text = "충전대기"
            }
            3 -> {
                text = "충전중"
            }
            4 -> {
                text = "운영중지"
            }
            5 -> {
                text = "점검중"
            }
            9 -> {
                text = "상태 미확인"
            }
        }
        textView.text = text
    }

}