package com.solie.ev_map.dialog

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.google.android.gms.maps.model.LatLng
import com.skt.Tmap.TMapTapi
import com.solie.ev_nyang.InfoActivity
import com.solie.ev_nyang.R
import com.solie.ev_nyang.databinding.DialogStatusBinding
import com.solie.ev_nyang.item.FirebaseItem
import com.solie.ev_nyang.util.FirebaseData

class StatusDialog(Item: FirebaseItem, curPoint: LatLng) : DialogFragment(), FirebaseData {
    private lateinit var binding: DialogStatusBinding
    private lateinit var tmapApi: TMapTapi
    private var mItem = Item
    private var bookMark: Boolean = false
    private val mCurPoint = curPoint

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogStatusBinding.inflate(inflater, container, false)
        binding.item = mItem

        tmapApi = TMapTapi(requireActivity().applicationContext)
        tmapApi.setSKTMapAuthentication("l7xx2b30c93809e04db7ad83c80559ea266a")

        dialog!!.window?.setGravity(Gravity.BOTTOM)
        setDialog(mItem)

        binding.dialogBookmark.setOnClickListener {
            bookMark = when (bookMark) {
                true -> {
                    binding.dialogBookmark.setBackgroundResource(R.drawable.icon_star_unclicked)
                    false
                }
                false -> {
                    binding.dialogBookmark.setBackgroundResource(R.drawable.icon_star_cllicked)
                    true
                }
            }
        }

        binding.dialogNaviBtn.setOnClickListener {
            showNavi()
        }

        binding.dialogInfoBtn.setOnClickListener {
            val intent = Intent(requireActivity().applicationContext, InfoActivity::class.java)
            intent.putExtra("statId", mItem.statId)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        context?.dialogResize(this@StatusDialog, 1f, 0.65f)
    }

    fun Context.dialogResize(dialogFragment: StatusDialog, width: Float, height: Float) {
        val windowMananger = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT < 30) {
            val display = windowMananger.defaultDisplay
            val size = Point()
            display.getSize(size)

            val window = dialogFragment.dialog?.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()
            window?.setLayout(x, y)
        } else {
            val rect = windowMananger.currentWindowMetrics.bounds
            val window = dialogFragment.dialog?.window

            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }
    }

    private fun setDialog(mItem: FirebaseItem) {

        val curLocation = Location("CurLocation")
        curLocation.longitude = mCurPoint.longitude
        curLocation.latitude = mCurPoint.latitude

        val statLocation = Location("Station")
        statLocation.latitude = mItem.lat
        statLocation.longitude = mItem.lng

        binding.dialogDistance.text = "현재 약 ${
            String.format(
                "%.2f",
                ((curLocation.distanceTo(statLocation)) / 1000)
            )
        } km 거리에 있습니다."

    }

    private fun showNavi() {
        val isTmap = tmapApi.isTmapApplicationInstalled
        if (isTmap) {
            Log.d(TAG, "showNavi: tMap Installed")
            tmapApi.invokeNavigate(mItem.statNm, mItem.lng.toFloat(), mItem.lat.toFloat(), 0, true)
        } else {
            Log.d(TAG, "showNavi: tMap UnInstalled")
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=com.skt.tmap.ku")
            startActivity(intent)
        }

    }

}