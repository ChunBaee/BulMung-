package com.jcorp.experience.retrofit

object API {
    const val Weather_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"
    const val Weather_APi = "getVilageFcst"


    const val service_Key = "mS+Jm4ryE9IQLhsedrzyXNRDQi3aqCSUuFRAMFx6hW+IVn7tnoEU97t8qwrpVyrJVp9RrZPFld+9kYbIGuw7rw=="

    enum class RESPONSE_STATE {
        OKAY,
        FAIL
    }
}