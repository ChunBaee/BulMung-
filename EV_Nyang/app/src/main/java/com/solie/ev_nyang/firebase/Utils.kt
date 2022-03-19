package com.solie.ev_nyang.firebase

object API {
    const val BASE_URL = "http://apis.data.go.kr/B552584/EvCharger/"

    const val getChargerInfo = "getChargerInfo"

    const val getChargerStatus = "getChargerStatus"

    const val serviceKey = "mS+Jm4ryE9IQLhsedrzyXNRDQi3aqCSUuFRAMFx6hW+IVn7tnoEU97t8qwrpVyrJVp9RrZPFld+9kYbIGuw7rw=="

    enum class RESPONSE_STATE {
        OKAY,
        FAIL
    }
}