package com.chunb.myapplication.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.chunb.myapplication.MainActivity
import com.chunb.myapplication.R
import com.chunb.myapplication.ViewModel
import com.chunb.myapplication.databinding.DialogSettingBinding
import com.chunb.myapplication.util.ThemeManager

class SettingDialog : DialogFragment(), View.OnClickListener {
    private lateinit var binding : DialogSettingBinding
    private val viewModel by activityViewModels<ViewModel>()
    private var mSeekRange = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogSettingBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        checkRadioClicked()
        setThemeRadio()
        setSeekbar()
        setSelectedNavi()
        observe()

        binding.dialogBtnSelectKakaoNavi.setOnClickListener(this)
        binding.dialogBtnSelectKakaoMap.setOnClickListener(this)
        binding.dialogBtnSelectTMap.setOnClickListener(this)

        return binding.root
    }

    private fun checkRadioClicked() {
        when(viewModel.mThemeIsDark.value) {
            true -> {
                binding.dialogRadioDark.isChecked = true
                binding.dialogRadioLight.isChecked = false
            }
            false -> {
                binding.dialogRadioDark.isChecked = false
                binding.dialogRadioLight.isChecked = true
            }
        }
    }

    private fun setThemeRadio() {
        binding.dialogRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.dialog_radio_light -> {
                    viewModel.mThemeIsDark.value = false
                    ThemeManager.applyTheme(ThemeManager.ThemeMode.LIGHT)
                    Log.d("----", "onCreateView: light_Clicked")
                }
                else -> {
                    viewModel.mThemeIsDark.value = true
                    ThemeManager.applyTheme(ThemeManager.ThemeMode.DARK)
                    Log.d("----", "onCreateView: dark_Clicked")
                }
            }
        }
    }

    private fun setSeekbar() {
        binding.dialogSettingSeekbarMapRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                binding.dialogSettingTxtCurRadius.text = String.format("(현재 %s Km)", p0!!.progress)
                mSeekRange = p0.progress
                viewModel.changeMapSeekRange(mSeekRange * 1000)
            }

        })
    }

    private fun setSelectedNavi() {
        when(viewModel.mNaviSelected.value) {
            0 -> binding.dialogBtnSelectKakaoNavi.setBackgroundResource(R.drawable.form_background_selected_navigation)
            1 -> binding.dialogBtnSelectTMap.setBackgroundResource(R.drawable.form_background_selected_navigation)
            2 -> binding.dialogBtnSelectKakaoMap.setBackgroundResource(R.drawable.form_background_selected_navigation)
        }
    }

    private fun observe() {
        viewModel.mapSeekRange.observe(requireActivity(), Observer {
            mSeekRange = it / 1000
            binding.dialogSettingTxtCurRadius.text = ("(현재 $mSeekRange Km)")
            binding.dialogSettingSeekbarMapRadius.progress = mSeekRange
        })
    }

    override fun onResume() {
        super.onResume()
        dialogFragmentResize(requireContext(), this, 0.9F, 0.7F)
    }

    fun dialogFragmentResize(context: Context, dialogFragment: DialogFragment, width: Float, height: Float) {

        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30) {

            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            window?.setLayout(x, y)

        } else {

            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("----", "onDestroy: DESTROY")
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.dialog_btn_select_kakaoNavi -> {
                binding.dialogBtnSelectKakaoMap.background = null
                binding.dialogBtnSelectTMap.background = null
                binding.dialogBtnSelectKakaoNavi.setBackgroundResource(R.drawable.form_background_selected_navigation)
                viewModel.mNaviSelected.value = 0
            }

            R.id.dialog_btn_select_tMap -> {
                binding.dialogBtnSelectKakaoMap.background = null
                binding.dialogBtnSelectKakaoNavi.background = null
                binding.dialogBtnSelectTMap.setBackgroundResource(R.drawable.form_background_selected_navigation)
                viewModel.mNaviSelected.value = 1
            }

            R.id.dialog_btn_select_kakaoMap -> {
                binding.dialogBtnSelectKakaoNavi.background = null
                binding.dialogBtnSelectTMap.background = null
                binding.dialogBtnSelectKakaoMap.setBackgroundResource(R.drawable.form_background_selected_navigation)
                viewModel.mNaviSelected.value = 2
            }
        }
    }
}