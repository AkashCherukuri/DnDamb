package com.example.dndamb

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.View


class AudioService : Service() {
    // Declare the media players
    lateinit var rainGentleMP: MediaPlayer
    lateinit var rainIntenseMP: MediaPlayer
    lateinit var windGentleMP: MediaPlayer
    lateinit var windIntenseMP: MediaPlayer
    lateinit var fireGentleMP: MediaPlayer
    lateinit var fireIntenseMP: MediaPlayer

    lateinit var MPL: Array<MediaPlayer>

    var currVol: Int = 100

    companion object {
        var instance: AudioService? = null
    }

    fun isCreated(): Boolean{
        return instance != null
    }

    open inner class LocalBinder : Binder() {
        fun getServiceInstance(): AudioService{
            return this@AudioService
        }
    }

    var mBinder = object: LocalBinder(){}

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("Service", "initialized!")
        instance = this

        rainGentleMP = MediaPlayer.create(this, R.raw.gentle_rain)
        rainGentleMP.isLooping = true
        rainIntenseMP = MediaPlayer.create(this, R.raw.intense_rain)
        rainIntenseMP.isLooping = true
        windGentleMP = MediaPlayer.create(this, R.raw.gentle_wind)
        windGentleMP.isLooping = true
        windIntenseMP = MediaPlayer.create(this, R.raw.intense_wind)
        windIntenseMP.isLooping = true
        fireGentleMP = MediaPlayer.create(this, R.raw.gentle_fire)
        fireGentleMP.isLooping = true
        fireIntenseMP = MediaPlayer.create(this, R.raw.intense_fire)
        fireIntenseMP.isLooping = true

        MPL = arrayOf(rainGentleMP, rainIntenseMP,
            windGentleMP, windIntenseMP,
            fireGentleMP, fireIntenseMP)

        return mBinder
    }

    override fun onStart(intent: Intent?, startId: Int) {

    }

    override fun onDestroy() {
        instance = null

        super.onDestroy()

        for (i in 0..MPL.size-1){
            MPL[i].stop()
        }
    }

    // Handling the player stuff
    fun pauseAll(){
        if(rainGentleMP.isPlaying)
            rainGentleMP.pause()
        if(rainIntenseMP.isPlaying)
            rainIntenseMP.pause()
        if(windGentleMP.isPlaying)
            windGentleMP.pause()
        if(windIntenseMP.isPlaying)
            windIntenseMP.pause()
        if(fireGentleMP.isPlaying)
            fireGentleMP.pause()
        if(fireIntenseMP.isPlaying)
            fireIntenseMP.pause()
    }

    fun playGentleRain(){
        if(rainGentleMP.isPlaying)
            rainGentleMP.pause()
        else {
            pauseAll()
            rainGentleMP.start()
        }
    }

    fun playIntenseRain(){
        if(rainIntenseMP.isPlaying)
            rainIntenseMP.pause()
        else{
            pauseAll()
            rainIntenseMP.start()
        }
    }
    fun playGentleWind(){
        if(windGentleMP.isPlaying)
            windGentleMP.pause()
        else {
            pauseAll()
            windGentleMP.start()
        }
    }

    fun playIntenseWind(){
        if(windIntenseMP.isPlaying)
            windIntenseMP.pause()
        else{
            pauseAll()
            windIntenseMP.start()
        }
    }
    fun playGentleFire(){
        if(fireGentleMP.isPlaying)
            fireGentleMP.pause()
        else {
            pauseAll()
            fireGentleMP.start()
        }
    }

    fun playIntenseFire(){
        if(fireIntenseMP.isPlaying)
            fireIntenseMP.pause()
        else{
            pauseAll()
            fireIntenseMP.start()
        }
    }

    fun changeVol(v: Float, prog: Int){
        currVol = prog
        for(i in 0..MPL.size-1){
            MPL[i].setVolume(v, v)
        }
    }
}