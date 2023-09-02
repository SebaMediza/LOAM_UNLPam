package com.unlpam.loam.tp01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GPSControl : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    override fun onMapReady(p0: GoogleMap) {
        map = p0
        createMarker()
    }
    private fun createMarker(){
        val gym = LatLng(-35.664059, -63.751803)
        map.addMarker(MarkerOptions().position(gym).title("El Gym"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(gym, 18f),4000,null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpscontrol)
        createMapFrament()
    }
    private fun createMapFrament(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}