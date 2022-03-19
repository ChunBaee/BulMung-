package com.jcorp.rc_challenge_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jcorp.rc_challenge_2.databinding.ActivityMainBinding
import me.relex.circleindicator.CircleIndicator3
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence
import uk.co.samuelwall.materialtaptargetprompt.extras.PromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.FullscreenPromptBackground
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.CirclePromptFocal


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnFab.bringToFront()
        binding.btnGyoljae.bringToFront()
        binding.btnMembership.bringToFront()
        binding.btnSongum.bringToFront()

        setTargetSequence()

        var fragList = mutableListOf<Fragment>()
        for (i in 0 until 3) {
            fragList.add(GridFragments(i))
        }

        adapter = PagerAdapter(this)
        for (frag: Fragment in fragList) {
            adapter.addItem(frag)
        }
        binding.mContainer.adapter = adapter

        val indicator: CircleIndicator3 = binding.indicator
        indicator.createIndicators(5, 0)
        indicator.animatePageSelected(0)
        indicator.setViewPager(binding.mContainer)
    }

    private fun setTargetSequence() {
        MaterialTapTargetSequence()
            .addPrompt(MaterialTapTargetPrompt.Builder(this)
            .setTarget(R.id.icon_new)
            .setPrimaryText("이곳을 눌러 새로운 알림을 받아보세요!")
            .setSecondaryText("내 카카오페이의 입금, 송금 내역 등을 볼 수 있습니다.")
                .setPromptBackground(FullscreenPromptBackground())
                .setPromptFocal(CirclePromptFocal())
            .create())

            .addPrompt(MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.icon_paybot)
                .setPrimaryText("이곳을 눌러 페이봇과 대화해보세요!")
                .setSecondaryText("문제나 궁금한 점이 있으시면 언제든 페이봇에게 물어보세요!")
                .setPromptBackground(FullscreenPromptBackground())
                .setPromptFocal(CirclePromptFocal())
                .create())

            .addPrompt(MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.btn_see_all)
                .setPrimaryText("이곳을 눌러 카카오페이의 모든 서비스를 확인해보세요!")
                .setPromptBackground(FullscreenPromptBackground())
                .setPromptFocal(CirclePromptFocal())
                .create())

            .addPrompt(MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.btn_fab)
                .setPrimaryText("이곳을 눌러 QR 결제를 실행해보세요!")
                .setSecondaryText("오프라인 결제, 송급받기, 종이고지서 납부 등을 할 수 있습니다!")
                .setPromptBackground(FullscreenPromptBackground())
                .setPromptFocal(CirclePromptFocal())
                .create())

            .show()
    }
}