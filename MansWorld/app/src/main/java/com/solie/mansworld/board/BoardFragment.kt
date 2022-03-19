package com.solie.mansworld.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.lakue.pagingbutton.OnPageSelectListener
import com.solie.mansworld.R
import com.solie.mansworld.content.ContentActivity
import com.solie.mansworld.data.FirebaseData
import com.solie.mansworld.databinding.FragmentBoardBinding
import com.solie.mansworld.write.WriteActivity

class BoardFragment : Fragment(), FirebaseData {
    private lateinit var binding: FragmentBoardBinding

    private lateinit var adapter: BoardAdapter
    private lateinit var userNickname: String

    private lateinit var query : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(inflater, container, false)

        binding.boardWriteBtn.setOnClickListener {
            val intent = Intent(context, WriteActivity::class.java)
            startActivityForResult(intent, 10)
        }

        setSpinner()
        showBoard(0)

        setPagingButton()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getUserNick()
    }

    private fun setSpinner() {
        val boardList = resources.getStringArray(R.array.show_board_list)
        var boardListAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            boardList
        )
        binding.boardSpinner.adapter = boardListAdapter
        binding.boardSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    showBoard(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
    }

    private fun showBoard(position : Int) {
        adapter = BoardAdapter()
        binding.boardRecycler.adapter = adapter
        binding.boardRecycler.setHasFixedSize(true)
        val list = mutableListOf<BoardItem>()
        val keyList = mutableListOf<String>()
        when(position) {
            0 -> databaseAllContent.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    keyList.clear()
                    for (datasnapshot in snapshot.children) {
                        val boardItem = datasnapshot.getValue(BoardItem::class.java)
                        if (boardItem != null) {
                            list.add(boardItem)
                            keyList.add(datasnapshot.key.toString())
                            Log.d("BOARD", datasnapshot.key.toString())
                        }

                    }
                    list.reverse()
                    adapter.replaceBoard(list)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })


            1 -> databaseKongJi.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    keyList.clear()
                    for (datasnapshot in snapshot.children) {
                        val boardItem = datasnapshot.getValue(BoardItem::class.java)
                        if (boardItem != null) {
                            list.add(boardItem)
                            keyList.add(datasnapshot.key.toString())
                            Log.d("BOARD", datasnapshot.key.toString())
                        }

                    }
                    list.reverse()
                    adapter.replaceBoard(list)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })


            2 -> databaseFree.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    keyList.clear()
                    for (datasnapshot in snapshot.children) {
                        val boardItem = datasnapshot.getValue(BoardItem::class.java)
                        if (boardItem != null) {
                            list.add(boardItem)
                            keyList.add(datasnapshot.key.toString())
                            Log.d("BOARD", datasnapshot.key.toString())
                        }

                    }
                    list.reverse()
                    adapter.replaceBoard(list)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })


            3 -> databaseTip.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    keyList.clear()
                    for (datasnapshot in snapshot.children) {
                        val boardItem = datasnapshot.getValue(BoardItem::class.java)
                        if (boardItem != null) {
                            list.add(boardItem)
                            keyList.add(datasnapshot.key.toString())
                            Log.d("BOARD", datasnapshot.key.toString())
                        }

                    }
                    list.reverse()
                    adapter.replaceBoard(list)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })


            4 -> databaseAsk.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    keyList.clear()
                    for (datasnapshot in snapshot.children) {
                        val boardItem = datasnapshot.getValue(BoardItem::class.java)
                        if (boardItem != null) {
                            list.add(boardItem)
                            keyList.add(datasnapshot.key.toString())
                            Log.d("BOARD", datasnapshot.key.toString())
                        }

                    }
                    list.reverse()
                    adapter.replaceBoard(list)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        adapter.boardClickListener(object : BoardAdapter.BoardClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ContentActivity::class.java)
                intent.putExtra("contents", list[position].toString())
                intent.putExtra("contentKey", keyList[position])
                intent.putExtra("userNick", userNickname)
                startActivityForResult(intent, 20)
            }
        })
    }

    private fun getUserNick() {
        firebaseStore.document(userID)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("UserNick", task.result!!["NickName"].toString())
                    userNickname = task.result!!["NickName"].toString()
                }
            }
    }

    private fun setPagingButton() {
        binding.boardPagingButton.setPageItemCount(5)
        binding.boardPagingButton.addBottomPageButton(10, 1)
        binding.boardPagingButton.setOnPageSelectListener(object : OnPageSelectListener {
            override fun onPageBefore(now_page: Int) {
                binding.boardPagingButton.addBottomPageButton(10, now_page)
            }

            override fun onPageCenter(now_page: Int) {
                Toast.makeText(context, now_page.toString() + "페이지", Toast.LENGTH_SHORT).show()
            }

            override fun onPageNext(now_page: Int) {
                binding.boardPagingButton.addBottomPageButton(10, now_page)
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

}