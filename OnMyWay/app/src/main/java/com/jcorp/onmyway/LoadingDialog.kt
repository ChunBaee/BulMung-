package com.jcorp.onmyway

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jcorp.onmyway.databinding.DialogLoadingBinding

class LoadingDialog(context : Context) : Dialog(context) {
    private lateinit var binding : DialogLoadingBinding

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

}