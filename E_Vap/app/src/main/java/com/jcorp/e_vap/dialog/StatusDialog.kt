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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.jcorp.e_vap.InfoActivity
import com.jcorp.e_vap.R
import com.jcorp.e_vap.databinding.DialogStatusBinding
import com.jcorp.e_vap.model.MapItem
import com.jcorp.e_vap.utils.BookmarkUpload
import com.jcorp.e_vap.utils.FirebaseData
import com.jcorp.e_vap.viewmodel.BookmarkViewModel
import com.jcorp.e_vap.viewmodel.BookmarkViewModelFactory
import com.jcorp.e_vap.viewmodel.StatusViewModel
import com.jcorp.e_vap.viewmodel.StatusViewModelFactory
import com.skt.Tmap.TMapTapi
import kotlin.properties.Delegates


class StatusDialog(item: MapItem, curPoint: LatLng) : DialogFragment(), FirebaseData {
    private lateinit var binding: DialogStatusBinding
    private lateinit var tmapApi: TMapTapi
    private var mItem = item
    private var bookMark: Boolean? = null
    private val mCurPoint = curPoint
    private var mBookmark = MutableLiveData<Boolean>()

    private lateinit var viewModel: StatusViewModel
    private lateinit var viewModelFactory: StatusViewModelFactory

    private lateinit var bookViewModel: BookmarkViewModel
    private lateinit var bookViewModelFactory: BookmarkViewModelFactory

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
        observeBookmark()

        binding.dialogBookmark.setOnClickListener {
            bookMark =
                when (bookMark) {
                    true -> {
                        binding.dialogBookmark.setBackgroundResource(R.drawable.icon_star_unclicked)
                        false
                    }
                    false -> {
                        binding.dialogBookmark.setBackgroundResource(R.drawable.icon_star_clicked)
                        true

                    }
                    else -> false
                }

            when (bookMark) {
                true -> mBookmark.value = bookMark!!
                false -> mBookmark.value = bookMark!!
            }

            Log.d(TAG, "onCreateView: ${bookMark} is BOOKMARK")
        }

        binding.dialogNaviBtn.setOnClickListener {
            showNavi()
        }

        binding.dialogInfoBtn.setOnClickListener {
            val intent = Intent(requireActivity().applicationContext, InfoActivity::class.java)
            Log.d(TAG, "onCreateView: ${mItem.statNm} / PUT!!!!")
            intent.putExtra("statNm", mItem.statNm)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bookViewModelFactory = BookmarkViewModelFactory(bookMark, mItem.statNm)
        bookViewModel =
            ViewModelProvider(this, bookViewModelFactory).get(BookmarkViewModel::class.java)
        bookViewModel.getCheckBookMark().observe(this, Observer {
            bookMark = it
            when (it) {
                true -> binding.dialogBookmark.setBackgroundResource(R.drawable.icon_star_clicked)
                false -> binding.dialogBookmark.setBackgroundResource(R.drawable.icon_star_unclicked)
            }
        })
        Log.d(TAG, "onAttach: $bookMark // BOOKMARK ATTACHED")
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

    private fun setDialog(mItem: MapItem) {

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

    private fun observeBookmark() {
        mBookmark.observe(this, {
            Log.d(TAG, "onCreateView: ${mItem.statNm} , $it , ${mBookmark.value} // BOOKMARKSTAT")
            viewModelFactory = StatusViewModelFactory(it, mItem.statNm)
            viewModel =
                ViewModelProvider(this, viewModelFactory).get(StatusViewModel::class.java)

            viewModel.getStatusLiveData().upLoadBookmark()
        })
    }

}