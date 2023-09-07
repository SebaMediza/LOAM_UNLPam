package com.unlpam.loam.tp01

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GPSControl : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    // Callback llamado cuando el mapa está listo para ser usado
    override fun onMapReady(p0: GoogleMap) {
        map = p0
        createMarker()
        enableLocation()
    }
    // Función para crear un marcador en el mapa
    private fun createMarker() {
        val gym = LatLng(-35.664059, -63.751803)
        map.addMarker(MarkerOptions().position(gym).title("El Gym"))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(gym, 18f), 4000, null)
    }
    // Función que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpscontrol)
        createMapFrament()
    }
    // Función para crear el fragmento del mapa
    private fun createMapFrament() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    // Función para verificar si se ha otorgado el permiso de ubicación
    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    // Función para habilitar la capa de ubicación en el mapa
    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }
    // Función para solicitar el permiso de ubicación al usuario
    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Permisos denegados por el Usuario", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)
        }
    }
    // Función llamada cuando el usuario responde a la solicitud de permisos
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(this, "Permisos denegados por el Usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // Función para manejar la visibilidad de la capa de ubicación cuando se reanudan los fragmentos
    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if (!isLocationPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return
            }
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Permisos denegados por el Usuario", Toast.LENGTH_SHORT).show()
        }
    }
}