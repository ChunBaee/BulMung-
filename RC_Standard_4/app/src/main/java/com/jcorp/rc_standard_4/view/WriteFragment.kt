package com.jcorp.rc_standard_4.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jcorp.rc_standard_4.R
import com.jcorp.rc_standard_4.ViewModel
import com.jcorp.rc_standard_4.data.MemoListItem
import com.jcorp.rc_standard_4.databinding.FragmentWriteBinding
import java.text.SimpleDateFormat

class WriteFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentWriteBinding
    private val viewModel: ViewModel by viewModels({ requireActivity() })

    private val TAG = "/WRITE/"

    private var title = ""
    private var content: String? = ""
    private var date = ""
    private var time = ""


    var timeTxtBool = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.txtTime.setOnClickListener(this)

        viewModel.isSave.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    if (binding.edtContent.text.length > 10) {
                        title = binding.edtContent.text.toString().substring(0, 10)
                        content = binding.edtContent.text.toString().replace(title, "")

                        Log.d("/WRITE/", "onCreateView: $title, $content")
                        if(viewModel.isEdit.value == true) {
                            viewModel.editMemo(MemoListItem(title, date, time, content))
                        } else {
                            viewModel.addMemo(MemoListItem(title, date, time, content))
                        }
                    } else if (binding.edtContent.text.isNotEmpty()) {
                        title = binding.edtContent.text.toString()
                        content = null

                        Log.d("/WRITE/", "onCreateView: $title, $content")
                        if(viewModel.isEdit.value == true) {
                            viewModel.editMemo(MemoListItem(title, date, time, content))
                        } else {
                            viewModel.addMemo(MemoListItem(title, date, time, content))
                        }
                    }
                }
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel.isReadState(true)
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        viewModel.isEdit.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    if (viewModel.memoList.value!![viewModel.editPosition.value!!].content == null) {
                        binding.edtContent.setText(viewModel.memoList.value!![viewModel.editPosition.value!!].title)
                    } else {
                        Log.d(TAG, "onStart: ${viewModel.memoList.value!![viewModel.editPosition.value!!].content}")
                        binding.edtContent.setText(viewModel.memoList.value!![viewModel.editPosition.value!!].title + viewModel.memoList.value!![viewModel.editPosition.value!!].content)
                    }
                    date = viewModel.memoList.value!![viewModel.editPosition.value!!].date
                    time = viewModel.memoList.value!![viewModel.editPosition.value!!].time
                    Log.d(TAG, "onStart: TIME : $time")
                    binding.txtTime.text = date + time
                }

                false -> {
                    date = SimpleDateFormat("yyyy년 MM월 dd일").format(viewModel.curTime.value!!)
                    time = SimpleDateFormat("HH:mm").format(viewModel.curTime.value!!)
                    Log.d(TAG, "onStart: TIME : $time")
                    binding.txtTime.text = date + time
                }
            }
        })
    super.onStart()
}


override fun onPause() {
    if(viewModel.isDeleted.value == false) {
        viewModel.isSaveState(true)
    }
    viewModel.isReadState(false)
    super.onPause()
    Log.d(TAG, "onPause: onPause")

}

override fun onDetach() {
    viewModel.isSaveState(false)
    viewModel.isEditState(false)
    viewModel.isDeletedState(false)
    super.onDetach()
    Log.d(TAG, "onDetach: onDetach")
}

override fun onClick(v: View?) {
    when (v!!.id) {
        R.id.txt_time -> {
            when (timeTxtBool) {
                true -> {
                    binding.txtTime.text = "생성 날짜: ${date + time}"
                    timeTxtBool = !timeTxtBool
                    Log.d(TAG, "onClick: true")
                }

                false -> {
                    binding.txtTime.text = "편집 일자: ${date + time}"
                    timeTxtBool = !timeTxtBool
                    Log.d(TAG, "onClick: false")
                }
            }
        }
    }
}
}