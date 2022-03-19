package com.solie.ev_nyang.map

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.solie.ev_nyang.R
import com.solie.ev_nyang.item.ClustItem

class MarkerRenderer(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<ClustItem>?
) : DefaultClusterRenderer<ClustItem>(context, map, clusterManager) {

    override fun onClustersChanged(clusters: MutableSet<out Cluster<ClustItem>>?) {
        super.onClustersChanged(clusters)
    }

    override fun onBeforeClusterItemRendered(item: ClustItem, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)
            when(item.getStatus()) {
                2 -> {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_able))
                }

                else -> {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_unable))
                }
            }
    }

    override fun onClusterItemRendered(clusterItem: ClustItem, marker: Marker) {
        super.onClusterItemRendered(clusterItem, marker)
        getMarker(clusterItem).showInfoWindow()
    }

    override fun setOnClusterItemClickListener(listener: ClusterManager.OnClusterItemClickListener<ClustItem>?) {
        super.setOnClusterItemClickListener(listener)

    }
}