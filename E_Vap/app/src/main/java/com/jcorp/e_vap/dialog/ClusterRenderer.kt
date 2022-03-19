package com.jcorp.e_vap.dialog

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.jcorp.e_vap.R
import com.jcorp.e_vap.model.MapItem

class ClusterRenderer (context : Context?, map : GoogleMap?, clusterManager: ClusterManager<MapItem>?) : DefaultClusterRenderer<MapItem>(context, map, clusterManager) {
    override fun onClustersChanged(clusters: MutableSet<out Cluster<MapItem>>?) {
        super.onClustersChanged(clusters)
    }

    override fun onBeforeClusterItemRendered(item: MapItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        /*when(item.getStatus()) {
            1 -> {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat1))
            }
            2 -> {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat2))
            }
            3 -> {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat3))
            }
            5 -> {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat5))
            }
            9 -> {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat9))
            }
            25 -> {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat25))
            }

            29 -> {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat29))
            }
        }*/
        
        val mList : MutableList<Any> = item.stat.values.toMutableList()

        if(mList.all { it == 2 }) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat2))
        } else if(mList.all { it == 3 }) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat3))
        } else if(mList.all {it == 5}) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat5))

        }else if(mList.all{it == 9}) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat9))
        }
        else if(mList.any{it == 2} && mList.any{it == 5}) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat25))

        } else if(mList.any { it == 2 } && mList.any { it == 9 }) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_stat29))
        } else {
            Log.d(TAG, "onBeforeClusterItemRendered: ${mList::class.simpleName} // 자료형")
            Log.d(TAG, "onBeforeClusterItemRendered: $mList // 내용물")
        }
    }

    override fun onClusterItemRendered(clusterItem: MapItem, marker: Marker) {
        super.onClusterItemRendered(clusterItem, marker)
        getMarker(clusterItem).showInfoWindow()
    }

    override fun setOnClusterItemClickListener(listener: ClusterManager.OnClusterItemClickListener<MapItem>?) {
        super.setOnClusterItemClickListener(listener)
    }
}