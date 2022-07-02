package com.jcorp.hwahae_clone.home.now.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.jcorp.hwahae_clone.R

class HomeRepository {

    fun setTopRvItem(): MutableList<HomeNowTopRvItem> {
        val list = mutableListOf<HomeNowTopRvItem>()
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_skincare,
                R.drawable.form_home_now_category_background_green,
                "스킨케어"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_cleansing,
                R.drawable.form_home_now_category_background_green,
                "클렌징/필링"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_maskpack,
                R.drawable.form_home_now_category_background_green,
                "마스크/팩"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_suncare,
                R.drawable.form_home_now_category_background_green,
                "선케어"
            )
        )

        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_basemakeup,
                R.drawable.form_home_now_category_background_red,
                "베이스메이크업"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_eyemakeup,
                R.drawable.form_home_now_category_background_red,
                "아이메이크업"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_lipmakeup,
                R.drawable.form_home_now_category_background_red,
                "립메이크업"
            )
        )

        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_body,
                R.drawable.form_home_now_category_background_purple,
                "바디"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_hair,
                R.drawable.form_home_now_category_background_purple,
                "헤어"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_nail,
                R.drawable.form_home_now_category_background_purple,
                "네일"
            )
        )
        list.add(
            HomeNowTopRvItem(
                R.drawable.icon_home_now_category_perfume,
                R.drawable.form_home_now_category_background_purple,
                "향수"
            )
        )

        return list
    }

    fun setNewProductData(list : MutableLiveData<MutableList<HomeNowNewItem>>) {
        var mList = mutableListOf<HomeNowNewItem>()
        FirebaseFirestore.getInstance().collection("NewProduct")
            .get().addOnCompleteListener {
                for (i in it.result) {
                    mList.add(i.toObject(HomeNowNewItem::class.java))
                }
                list.value = mList
            }
    }

    fun setNewAdvertiseData (list : MutableLiveData<MutableList<HomeNowAdItem>>) {
        var mList = mutableListOf<HomeNowAdItem>()
        FirebaseFirestore.getInstance().collection("Banner_Home")
            .get().addOnCompleteListener {
                for (i in it.result) {
                    mList.add(i.toObject(HomeNowAdItem::class.java))
                }
                list.value = mList
            }
    }
    fun setHomeUnderAdvertiseTitleData() : MutableList<Titles> {
        var list = mutableListOf<Titles>()

        list.add(Titles("HOT🔥랭킹", 0))
        list.add(Titles("화해쇼핑🛍", 1))
        list.add(Titles("화해가 준비한 풍성한 가정의달 혜택!", 2))
        list.add(Titles("화해MD가 제안하는 이주의 특가", 2))
        list.add(Titles("뷰티ON🚀", 3))

        return list
    }

    fun setHomeHotRankingData() : MutableList<HomeNowHotItem> {
        var list = mutableListOf<HomeNowHotItem>()

        list.add(HomeNowHotItem(R.drawable.icon_home_now_hot_category, "카테고리별"))
        list.add(HomeNowHotItem(R.drawable.icon_home_now_hot_jungsong, "중성"))
        list.add(HomeNowHotItem(R.drawable.icon_home_now_hot_man, "남성"))
        list.add(HomeNowHotItem(R.drawable.icon_home_now_hot_brand, "브랜드별"))
        list.add(HomeNowHotItem(R.drawable.icon_home_now_hot_20th, "20대"))

        return list
    }

    fun setHomeShoppingData(list : MutableLiveData<MutableList<HomeNowShoppingItem>>) {
        var mList = mutableListOf<HomeNowShoppingItem>()
        FirebaseFirestore.getInstance().collection("Shopping_Home")
            .get().addOnCompleteListener {
                for(i in it.result) {
                    mList.add(i.toObject(HomeNowShoppingItem::class.java))
                }
                list.value = mList
            }
    }

    fun setFamilyMonthData(list : MutableLiveData<MutableList<HomeNowVerticalItem>>) {
        var mList = mutableListOf<HomeNowVerticalItem>()
        FirebaseFirestore.getInstance().collection("FamilyMonth_Home")
            .get().addOnCompleteListener {
                for(i in it.result) {
                    mList.add(i.toObject(HomeNowVerticalItem::class.java))
                }
                list.value = mList
            }
    }

    fun setMdRecommendData(list : MutableLiveData<MutableList<HomeNowVerticalItem>>) {
        var mList = mutableListOf<HomeNowVerticalItem>()
        FirebaseFirestore.getInstance().collection("MdRecommend_Home")
            .get().addOnCompleteListener {
                for(i in it.result) {
                    mList.add(i.toObject(HomeNowVerticalItem::class.java))
                }
                list.value = mList
            }
    }

    fun setBeautyOnData(list : MutableLiveData<MutableList<HomeNowBeautyOnItem>>) {
        var mList = mutableListOf<HomeNowBeautyOnItem>()
        FirebaseFirestore.getInstance().collection("BeautyOn_Home")
            .get().addOnCompleteListener {
                for(i in it.result) {
                    mList.add(i.toObject(HomeNowBeautyOnItem::class.java))
                }
                list.value = mList
            }
    }
}