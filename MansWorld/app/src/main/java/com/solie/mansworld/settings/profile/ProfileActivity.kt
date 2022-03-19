package com.solie.mansworld.settings.profile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.solie.mansworld.R
import com.solie.mansworld.data.FirebaseData
import com.solie.mansworld.databinding.ActivityProfileBinding
import java.io.ByteArrayOutputStream
import java.lang.Exception

class ProfileActivity : AppCompatActivity(), View.OnClickListener, FirebaseData {

    private lateinit var binding : ActivityProfileBinding
    private val GALLERY_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        binding.profileImage.setOnClickListener(this)
        binding.profileButton.setOnClickListener(this)

    }

    private fun upLoadImage(bitmap : Bitmap) {
        val imageSaveRef = firebaseStorage.child("UserDB/$userID/Image/Image.jpg")

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageByteArray = baos.toByteArray()

        val upLoadTask = imageSaveRef.putBytes(imageByteArray)
    }

    private fun nickNameCheck() {
        if(binding.profileEdittext.text.isEmpty() || binding.profileEdittext.text.length > 10) {
            Toast.makeText(applicationContext, "닉네임을 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
        } else {
            firebaseStore
                .whereEqualTo("NickName", binding.profileEdittext.text.toString())
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document!!.isEmpty) {
                            Toast.makeText(applicationContext, "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT)
                                .show()
                            saveUserInfo()
                        } else {
                            Toast.makeText(applicationContext, "중복된 닉네임입니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
        }
    }

    private fun saveUserInfo() {

        var UserDB = mutableMapOf<String, String>()
        UserDB["NickName"] = binding.profileEdittext.text.toString()
        firebaseStore.document(userID)
            .set(UserDB)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "저장 성공", Toast.LENGTH_SHORT).show()
                finish()
            }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {

            R.id.profile_image -> {
                val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(intent, GALLERY_CODE)
            }

            R.id.profile_button -> {
                nickNameCheck()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GALLERY_CODE) {
            if(resultCode == RESULT_OK) {
                val uri = data?.data

                try {
                    val image = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    Glide.with(applicationContext).load(image).into(binding.profileImage)
                    upLoadImage(image)
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}