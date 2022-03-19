package com.jcorp.rc_challenge_3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.jcorp.rc_challenge_3.databinding.ActivityLoginIdBinding

class LoginIDActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginIdBinding

    private var isLogin = false
    private var isIdTyped = false
    private var isPwTyped = false

    private val E_Mail = "춘배"
    private val PASSWORD = "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_id)

        binding.inputEmail.addTextChangedListener {
            isIdTyped = it!!.isNotEmpty()
        }
        binding.inputPassword.addTextChangedListener {
            isPwTyped = it!!.isNotEmpty()
        }

        if(isIdTyped && isPwTyped) {
           binding.btnLogin.alpha = 1.0F
           binding.btnLogin.isClickable = true
        }

        binding.btnLogin.setOnClickListener {
            if(binding.inputEmail.text.toString() == E_Mail && binding.inputPassword.text.toString() == PASSWORD) {
                val intent = Intent()
                intent.putExtra("isLogin", true)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Snackbar.make(binding.andBorder, "로그인에 실패하셨습니다.", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.loginFacebook.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, 100)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            isLogin = data!!.getBooleanExtra("isLogin", false)
        }
    }

    override fun onStart() {
        super.onStart()
        if(isLogin) {
            val intent = Intent()
            intent.putExtra("isLogin", true)
            setResult(RESULT_OK, intent)
            finish()
        }
    }


}