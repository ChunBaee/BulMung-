package com.solie.mansworld.write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.solie.mansworld.R
import com.solie.mansworld.databinding.ActivityWriteBinding
import com.solie.mansworld.databinding.ActivityWriteBindingImpl
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class WriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityWriteBinding
    private var boardList = arrayOf<String>()

    private lateinit var selectedBoard: String
    private lateinit var userNickname: String

    private lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_write)

        binding.writeSaveBtn.setOnClickListener(this)
        binding.writeCancelBtn.setOnClickListener(this)

        setSpinner()
        getUserNick()

    }

    private fun setSpinner() {
        boardList = resources.getStringArray(R.array.board_list)
        var boardListAdapter = ArrayAdapter<String>(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            boardList
        )
        binding.writeSpinner.adapter = boardListAdapter
        binding.writeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    selectedBoard = binding.writeSpinner.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
    }

    private fun updateContents() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference =
            database.getReference("Contents")
        val now = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR")).format(now)

        var contentDB = mutableMapOf<String, Any>()
        contentDB["Title"] = binding.writeTitleEdittext.text.toString()
        contentDB["Content"] = binding.writeContentEdittext.text.toString()
        contentDB["Recommend"] = 0
        contentDB["Replies"] = 0
        contentDB["Date"] = dateFormat
        contentDB["NickName"] = userNickname
        contentDB["UID"] = uid
        contentDB["BoardName"] = selectedBoard

        myRef.push().setValue(contentDB)
    }

    private fun getUserNick() {
        uid = FirebaseAuth.getInstance().currentUser.uid
        FirebaseFirestore.getInstance().collection("UserDB")
            .document(uid)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("UserNick", task.result!!["NickName"].toString())
                    userNickname = task.result!!["NickName"].toString()
                }
            }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.write_save_btn -> {
                //저장버튼
                Toast.makeText(applicationContext, "저장버튼 눌림", Toast.LENGTH_SHORT).show()
                updateContents()
                finish()
            }

            R.id.write_cancel_btn -> {
                //취소버튼
                Toast.makeText(applicationContext, "취소버튼 눌림", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

}