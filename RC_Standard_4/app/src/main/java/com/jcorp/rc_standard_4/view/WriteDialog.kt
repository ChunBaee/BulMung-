package com.jcorp.rc_standard_4.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.jcorp.rc_standard_4.ListAdapter
import com.jcorp.rc_standard_4.R
import com.jcorp.rc_standard_4.ViewModel
import com.jcorp.rc_standard_4.data.DialogListItem
import com.jcorp.rc_standard_4.databinding.DialogWriteBinding
import java.text.SimpleDateFormat

class WriteDialog : DialogFragment(), View.OnClickListener {
    private lateinit var binding: DialogWriteBinding
    private val viewModel: ViewModel by viewModels({ requireActivity() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogWriteBinding.inflate(inflater, container, false)

        dialog?.window?.setGravity(Gravity.BOTTOM)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        binding.btnDismiss1.setOnClickListener(this)
        binding.btnDismiss2.setOnClickListener(this)
        binding.statTrash.setOnClickListener(this)

        binding.layoutRv1.clipToOutline = true
        binding.layoutRv2.clipToOutline = true
        binding.layoutRv3.clipToOutline = true

        setDialog()
        setLists()

        return binding.root
    }

    private fun setDialog() {
        binding.txtDialogTitle.text = viewModel.mMemoList[viewModel.editPosition.value!!].title
        if (viewModel.mMemoList[viewModel.editPosition.value!!].date == SimpleDateFormat("yyyy년 MM월 dd일").format(
                viewModel.curTime.value!!
            )
        ) {
            binding.txtDialogTime.text = viewModel.mMemoList[viewModel.editPosition.value!!].time
        } else {
            binding.txtDialogTime.text = viewModel.mMemoList[viewModel.editPosition.value!!].date
        }
    }

    private fun setLists() {
        val list1 = mutableListOf<DialogListItem>(
            DialogListItem("메모 공유", R.drawable.icon_share),
            DialogListItem("복사본 보내기", R.drawable.icon_outshare)
        )

        val list2 = mutableListOf<DialogListItem>(DialogListItem("메모에서 찾기", R.drawable.icon_search_white),
            DialogListItem("메모 이동", R.drawable.icon_folder),
            DialogListItem("줄 및 격자", R.drawable.icon_table),
            DialogListItem("프린트", R.drawable.icon_printer)
        )

        val list3 = mutableListOf<DialogListItem>(DialogListItem("밝은 배경 사용", R.drawable.icon_bright))

        val adapter1 = ListAdapter(requireActivity(), list1)
        val adapter2 = ListAdapter(requireActivity(), list2)
        val adapter3 = ListAdapter(requireActivity(), list3)


        binding.statRv1.adapter = adapter1
        binding.statRv2.adapter = adapter2
        binding.statRv3.adapter = adapter3


    }


    fun Context.dialogResize(dialogFragment: WriteDialog, width: Float, height: Float) {
        val windowMananger = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT < 30) {
            val display = windowMananger.defaultDisplay
            val size = Point()
            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            window?.setLayout(x, y)
        } else {
            val rect = windowMananger.currentWindowMetrics.bounds
            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }

    override fun onResume() {
        super.onResume()
        context?.dialogResize(this@WriteDialog, 0.95f, 0.75f)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_dismiss1, R.id.btn_dismiss2 -> {
                dismiss()
            }

            R.id.stat_trash -> {
                viewModel.deleteMemo(viewModel.editPosition.value!!)
                viewModel.isDeletedState(true)
                viewModel.isEditState(false)
                viewModel.isSaveState(false)
                dismiss()
            }
        }
    }

}