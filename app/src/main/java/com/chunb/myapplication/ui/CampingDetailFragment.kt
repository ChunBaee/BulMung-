package com.chunb.myapplication.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.chunb.myapplication.R
import com.chunb.myapplication.ViewModel
import com.chunb.myapplication.adapter.CampingDetailPagerAdapter
import com.chunb.myapplication.databinding.FragmentCampingDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.sdk.navi.NaviClient
import com.kakao.sdk.navi.model.CoordType
import com.kakao.sdk.navi.model.Location
import com.kakao.sdk.navi.model.NaviOption

class CampingDetailFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentCampingDetailBinding
    private val viewModel by activityViewModels<ViewModel>()

    private val imagePagerAdapter by lazy {
        CampingDetailPagerAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCampingDetailBinding.inflate(inflater, container, false)

        binding.detailBottomBtnCall.setOnClickListener(this)
        binding.detailBottomBtnHomepage.setOnClickListener(this)
        binding.detailBottomBtnReserve.setOnClickListener(this)
        binding.detailBottomBtnNavigation.setOnClickListener(this)

        observe()
        setPager()

        return binding.root
    }

    private fun observe() {
        viewModel.curSelectedCampingDetailData.observe(requireActivity(), Observer {
            this.binding.campingData = it
            viewModel.getCurCampingImageData(it.contentId!!)
            Log.d("----", "observe: ${it.contentId}")
        })
        viewModel.curSelectedCampingImageData.observe(requireActivity(), Observer {
            if (it != null) {
                Log.d("----", "observe: $it")
                imagePagerAdapter.setImageList(it)
            }
        })
        viewModel.mNaviSelected.observe(requireActivity(), Observer {
            when (it) {
                0 -> {
                    binding.detailBottomIconNavi.setImageResource(R.drawable.icon_kakao_navi)
                    binding.detailBottomTxtNavi.text = "카카오내비"
                }
                1 -> {
                    binding.detailBottomIconNavi.setImageResource(R.drawable.icon_tmap)
                    binding.detailBottomTxtNavi.text = "T맵"
                }
                2 -> {
                    binding.detailBottomIconNavi.setImageResource(R.drawable.icon_kakao_map)
                    binding.detailBottomTxtNavi.text = "카카오맵"
                }
            }
        })
    }

    private fun setPager() {
        binding.detailImagePager.adapter = imagePagerAdapter
        TabLayoutMediator(
            binding.detailImagePagerIndicator,
            binding.detailImagePager
        ) { _, _ -> }.attach()
    }

    override fun onStart() {
        super.onStart()
        viewModel.showFab.value = false
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deleteCurCampingImageData()
        viewModel.showFab.value = true
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.detail_bottom_btn_navigation -> {
                when (viewModel.mNaviSelected.value) {
                    0 -> {
                        //카카오내비
                        if (NaviClient.instance.isKakaoNaviInstalled(requireActivity())) {
                            startActivity(
                                NaviClient.instance.navigateIntent(
                                    Location(
                                        viewModel.curSelectedCampingDetailData.value!!.facltNm!!,
                                        viewModel.curSelectedCampingDetailData.value!!.mapX.toString(),
                                        viewModel.curSelectedCampingDetailData.value!!.mapY.toString()
                                    ),
                                    NaviOption(coordType = CoordType.WGS84)
                                )
                            )
                        } else {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.locnall.KimGiSa")
                                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            )
                        }
                    }
                    1 -> {
                        //T맵
                        if (viewModel.tMap!!.isTmapApplicationInstalled) {
                            viewModel.tMap!!.invokeNavigate(
                                viewModel.curSelectedCampingDetailData.value!!.facltNm!!,
                                viewModel.curSelectedCampingDetailData.value!!.mapX.toFloat(),
                                viewModel.curSelectedCampingDetailData.value!!.mapY.toFloat(),
                                0,
                                true
                            )
                        } else {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.skt.tmap.ku")
                                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            )
                        }
                    }
                    2 -> {
                        //카카오맵
                        try {
                            Log.d("----", "onClick: CUR (${viewModel.getMapLocation().latitude},${viewModel.getMapLocation().longitude}) END (${viewModel.curSelectedCampingDetailData.value!!.mapX},${viewModel.curSelectedCampingDetailData.value!!.mapY})")
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("kakaomap://route?sp=${viewModel.getMapLocation().latitude},${viewModel.getMapLocation().longitude}&ep=${viewModel.curSelectedCampingDetailData.value!!.mapY},${viewModel.curSelectedCampingDetailData.value!!.mapX}&by=CAR")
                                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            )
                        } catch (e : ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=net.daum.android.map")
                                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            )
                        }
                    }
                }
            }

            R.id.detail_bottom_btn_homepage -> {
                if (viewModel.curSelectedCampingDetailData.value!!.homepage?.isEmpty() == false) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(viewModel.curSelectedCampingDetailData.value!!.homepage)
                        )
                    )
                } else {
                    Snackbar.make(
                        binding.detailBottomView,
                        "해당 캠핑장의 홈페이지 정보를 불러올 수 없습니다.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.detail_bottom_btn_reserve -> {
                if (viewModel.curSelectedCampingDetailData.value!!.resveUrl?.isEmpty() == false) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(viewModel.curSelectedCampingDetailData.value!!.resveUrl)
                        )
                    )
                } else {
                    Snackbar.make(
                        binding.detailBottomView,
                        "해당 캠핑장의 예약 페이지 정보를 불러올 수 없습니다.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.detail_bottom_btn_call -> {
                if (viewModel.curSelectedCampingDetailData.value!!.tel?.isEmpty() == false) {
                    startActivity(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse(
                                "tel:${
                                    viewModel.curSelectedCampingDetailData.value!!.tel!!.replace(
                                        "-",
                                        ""
                                    )
                                }"
                            )
                        )
                    )
                } else {
                    Snackbar.make(
                        binding.detailBottomView,
                        "해당 캠핑장의 전화번호 정보를 불러올 수 없습니다.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}