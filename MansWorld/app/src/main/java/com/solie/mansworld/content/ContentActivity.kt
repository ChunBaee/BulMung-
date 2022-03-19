package com.solie.mansworld.content

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.solie.mansworld.R
import com.solie.mansworld.board.BoardItem
import com.solie.mansworld.data.FirebaseData
import com.solie.mansworld.databinding.ActivityContentBinding
import java.text.SimpleDateFormat
import java.util.*

class ContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentBinding
    private lateinit var adapter: ReplyAdapter


    private var list = mutableListOf<String>()
    private var contents = mutableListOf<String>()

    var replyCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_content)

        list = intent.getStringExtra("contents")!!.split(",") as MutableList<String>
        for (i in list) {
            contents.add(i.substring(i.lastIndexOf("=") + 1))

        }
        binding.contentTitle.text = contents[0]
        binding.contentDate.text = contents[2]
        binding.contentRecommend.text = contents[3]
        binding.contentWriter.text = contents[5]
        binding.contentMain.text = contents[7].substring(0, contents[7].length - 1)

        binding.contentReturn.setOnClickListener {
            finish()
        }

        binding.contentRecommendBtn.setOnClickListener {
            val contentKey = intent.getStringExtra("contentKey")!!
            val recomap = mutableMapOf<String, Any>()
            val database = FirebaseDatabase.getInstance().getReference("Contents/$contentKey")
            recomap["Recommend"] = contents[3].toInt() + 1
            database.updateChildren(recomap)
        }

        binding.replyBtn.setOnClickListener {

            val userNick = intent.getStringExtra("userNick")!!
            val now = Date(System.currentTimeMillis())
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("ko", "KR")).format(now)

            val reply = binding.replyTxt.text.toString()
            val contentKey = intent.getStringExtra("contentKey")!!
            val recomap = mutableMapOf<String, Any>()
            val database =
                FirebaseDatabase.getInstance().getReference("Contents/$contentKey/Reply/")
            recomap["UserNick"] = userNick
            recomap["Content"] = reply
            recomap["ReplyDate"] = dateFormat

            database.push().updateChildren(recomap)
            binding.replyTxt.setText("")
            getReply()
            updateReplyNum()
        }

        binding.contentMainList.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                binding.contentScroll.requestDisallowInterceptTouchEvent(true)
                return false
            }
        })

    }

    private fun updateReplyNum() {
        val contentKey = intent.getStringExtra("contentKey")!!
        val recomap = mutableMapOf<String, Any>()
        val replyList = mutableListOf<String>()
        val replyDatabase = FirebaseDatabase.getInstance().getReference("Contents/$contentKey/Reply")

        

        recomap["Replies"] = contents[4].toInt() + 1
        //replyDatabase.updateChildren(recomap)


    }

    override fun onStart() {
        super.onStart()
        getReply()
    }

    private fun getReply() {
        val list = mutableListOf<ReplyItem>()
        val contentKey = intent.getStringExtra("contentKey")!!

        adapter = ReplyAdapter(applicationContext)
        binding.contentMainList.adapter = adapter

        val database = FirebaseDatabase.getInstance().getReference("Contents/$contentKey/Reply")
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (datasnapshot in snapshot.children) {
                    val replyItem = datasnapshot.getValue(ReplyItem::class.java)
                    if (replyItem != null) {
                        list.add(replyItem)
                        Log.d("REPLY", replyItem.UserNick.toString())
                    }
                }
                adapter.replaceBoard(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }
}