package com.chunb.myapplication

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

object BindingAdapter {

    @BindingAdapter("checkIsOpen")
    @JvmStatic
    fun checkIsOpen(view : TextView, txt : String) {
        if(txt == "운영") {
            view.text = "운영중"
            view.alpha = 1.0F
        } else {
            view.text = "미운영중"
            view.alpha = 0.3F
        }
    }

    @BindingAdapter("setImageFromServer")
    @JvmStatic
    fun setImageFromServer (view : ImageView, url : String?) {
        if(url?.isNotEmpty() == true) {
            Glide.with(view.context)
                .load(url)
                .error(R.drawable.icon_no_pictures)
                .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    a_resource: Drawable,
                    a_transition: Transition<in Drawable>?
                ) {
                    view.background = a_resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        } else {
            Glide.with(view.context).load(R.drawable.icon_no_pictures).into(view)
        }
    }

    @BindingAdapter("checkIsPet")
    @JvmStatic
    fun checkIsPet(view : ImageView, txt : String) {
        if(txt == "불가능") {
            view.alpha = 1.0F
        } else {
            view.alpha = 0.3F
        }
    }

    @BindingAdapter("checkWeekend")
    @JvmStatic
    fun checkWeekend(view : TextView, txt : String) {
        if(txt.contains("주말")) {
            view.alpha = 1.0F
        } else {
            view.alpha = 0.3F
        }
    }

    @BindingAdapter("checkWeekday")
    @JvmStatic
    fun checkWeekday(view : TextView, txt : String) {
        if(txt.contains("평일")) {
            view.alpha = 1.0F
        } else {
            view.alpha = 0.3F
        }
    }

    @BindingAdapter("weather", "day")
    @JvmStatic
    fun addWeatherAndDay (view : TextView, weather : String, day : String) {
        view.text = "운영계절 및 요일 : $weather / ${day.replace("+", " , ")}"
    }

    @BindingAdapter("checkIntroduceNull")
    @JvmStatic
    fun checkIntroduceNull (view : TextView, content : String) {
        if(content.isEmpty()) {
            view.text = "등록된 소개가 없습니다."
        } else {
            view.text = content
        }
    }
}