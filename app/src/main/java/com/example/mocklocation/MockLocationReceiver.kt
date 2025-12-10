package com.example.mocklocation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.util.Log

class MockLocationReceiver : BroadcastReceiver() {
    
    companion object {
        private const val TAG = "MockLocationReceiver"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        val lat = intent.getDoubleExtra("lat", 0.0)
        val lon = intent.getDoubleExtra("lon", 0.0)
        
        if (lat == 0.0 && lon == 0.0) {
            Log.e(TAG, "无效的坐标")
            return
        }
        
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = LocationManager.GPS_PROVIDER
        
        try {
            try {
                locationManager.addTestProvider(
                    provider,
                    false, false, false, false,
                    true, true, true,
                    0, 5
                )
            } catch (e: Exception) {
                // 已存在
            }
            
            locationManager.setTestProviderEnabled(provider, true)
            
            val location = Location(provider).apply {
                latitude = lat
                longitude = lon
                altitude = 0.0
                time = System.currentTimeMillis()
                accuracy = 1.0f
                bearing = 0f
                speed = 0f
            }
            
            locationManager.setTestProviderLocation(provider, location)
            Log.i(TAG, "成功设置模拟位置: $lat, $lon")
            
        } catch (e: SecurityException) {
            Log.e(TAG, "权限不足", e)
        } catch (e: Exception) {
            Log.e(TAG, "设置失败", e)
        }
    }
}
