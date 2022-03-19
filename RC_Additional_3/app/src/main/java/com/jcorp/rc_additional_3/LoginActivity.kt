package com.jcorp.rc_additional_3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jcorp.rc_additional_3.databinding.ActivityLoginBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    lateinit var mNaverLoginModule: OAuthLogin
    lateinit var naverAccessToken : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnLoginNaver.setOnClickListener(this)
        binding.btnLoginKakao.setOnClickListener(this)

        checkPreference()
    }

    private fun checkPreference() {
        val prefs = getSharedPreferences("NaverOAuthLoginPreferenceData", MODE_PRIVATE)
        val accessToken = prefs.getString("ACCESS_TOKEN", "")
        if(accessToken == null || accessToken.isEmpty()) {
        } else {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("Type", "Naver")
            intent.putExtra("Token", accessToken)
            startActivity(intent)
            finish()
        }
    }

    private fun naverLogin() {
        //네이버 로그인 인스턴스 초기화
        mNaverLoginModule = OAuthLogin.getInstance()
        mNaverLoginModule.init(
            this,
            getString(R.string.naver_client_id),
            getString(R.string.naver_client_secret),
            getString(R.string.app_name)
        )

        @SuppressLint("HandlerLeak")
        var mOAuthLoginHandler = object : OAuthLoginHandler() {
            override fun run(success: Boolean) {
                if(success) {
                    naverAccessToken = mNaverLoginModule.getAccessToken(applicationContext)
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("Type", "Naver")
                    intent.putExtra("Token", naverAccessToken)
                    startActivity(intent)

                    Log.d("----", "run: $naverAccessToken")
                } else {
                    Log.d("----", "run: FAILED")
                }
            }

        }
        mNaverLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler)
    }

    private fun kakaoLogin() {
        KakaoSdk.init(this, R.string.kakao_native_key.toString())
        UserApiClient.instance.loginWithKakaoAccount(applicationContext) { token, error ->
            if(error != null) {
                Log.d("----", "kakaoLogin: 실패 ", error)
            } else if(token != null) {
                Log.d("----", "kakaoLogin: 성공 ${token.accessToken}")
                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.putExtra("Type", "Kakao")
                intent.putExtra("Token", token.accessToken)
                startActivity(intent)
                finish()
            }
        }
    }



    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_login_naver -> {
                naverLogin()
            }

            R.id.btn_login_kakao -> {
                kakaoLogin()
            }
        }
    }
}