package com.example.dndamb

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.log10


class WeatherActivity : AppCompatActivity() {

    var mBounded: Boolean = false
    var mService: AudioService ?= null

    val mServerConn = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBounded = true
            var mLocalBinder: AudioService.LocalBinder = service as AudioService.LocalBinder
            mService = mLocalBinder.getServiceInstance()
            if(mService == null){
                Log.d("Service", "Service is null")
            }
            else{
                Log.d("Service", "Service is not null")
                val volSB = findViewById<SeekBar>(R.id.Weather_Volume_Slider)
                volSB.progress = mService!!.currVol
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBounded = false
            mService = null
        }

    }

    lateinit var volSB: SeekBar

    override fun onStart() {
        super.onStart()

        var mIntent = object: Intent(this, AudioService::class.java){}
        startService(mIntent)
        bindService(mIntent, mServerConn, 0)
        Log.d("onStart", "onstart done, service should be bound now")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        // Find the seekbar and make its listener
        val volSB = findViewById<SeekBar>(R.id.Weather_Volume_Slider)
        volSB.progress = volSB.max
        volSB?.setOnSeekBarChangeListener(object:
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val maxVol = (seekBar.max).toFloat()
                val vol = (seekBar.progress).toFloat()
                var m = (1 - (log10(maxVol - vol) / log10(maxVol))).toFloat()
                mService!!.changeVol(m, seekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        if(mBounded){
            unbindService(mServerConn)
            mBounded = false
        }
    }

    fun playGentleRain(view: View){

        mService!!.playGentleRain()
    }
    fun playIntenseRain(view: View){
        mService?.playIntenseRain()
    }
    fun playGentleWind(view: View){
        mService?.playGentleWind()
    }
    fun playIntenseWind(view: View){
        mService?.playIntenseWind()
    }
    fun playGentleFire(view: View){
        mService?.playGentleFire()
    }
    fun playIntenseFire(view: View){
        mService?.playIntenseFire()
    }
}