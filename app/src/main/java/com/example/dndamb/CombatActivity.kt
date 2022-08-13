package com.example.dndamb

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


class CombatActivity : AppCompatActivity() {

    var mBounded: Boolean = false
    var mService: CombatService ?= null

    val mServerConn = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBounded = true
            var mLocalBinder: CombatService.LocalBinder = service as CombatService.LocalBinder
            mService = mLocalBinder.getServiceInstance()
            if(mService == null){
                Log.d("Service", "Service is null")
            }
            else{
                Log.d("Service", "Service is not null")
                val volSB = findViewById<SeekBar>(R.id.Combat_Volume_Slider)
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

        var mIntent = object: Intent(this, CombatService::class.java){}
        startService(mIntent)
        bindService(mIntent, mServerConn, 0)
        Log.d("onStart", "onstart done, service should be bound now")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_combat)

        // Find the seekbar and make its listener
        val volSB = findViewById<SeekBar>(R.id.Combat_Volume_Slider)
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

    fun playGenCom(view: View){
        mService!!.playGenCombatMusic()
    }
}