package com.example.dndamb

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.view.View
import java.util.Random


class CombatService : Service() {
    // Declare the media players
    lateinit var genericMP: MediaPlayer
    lateinit var bossMP: MediaPlayer

    val genComMus: Array<Int> = arrayOf(
        R.raw.gencom_castlevania2_los_castlevania,
        R.raw.gencom_castlevania2_los_chaotic_battle,
        R.raw.gencom_castlevania2_los_nightmare,
        R.raw.gencom_castlevania2_los_paladin_of_god,
        R.raw.gencom_castlevania2_los_satan,
        R.raw.gencom_castlevania2_los_siege_titan,
        R.raw.gencom_castlevania2_los_titanic_struggle,
        R.raw.gencom_castlevania_los_final_confrontation,
        R.raw.gencom_castlevania_los_ice_titan,
        R.raw.gencom_castlevania_los_warg,
        R.raw.gencom_witcher3_cloak_and_dagger,
        R.raw.gencom_witcher3_forged_in_fire,
        R.raw.gencom_witcher3_hunt_or_be_hunted,
        R.raw.gencom_witcher3_silver_for_monsters,
        R.raw.gencom_witcher3_song_of_the_sword_dancer,
        R.raw.gencom_witcher3_steel_for_humans,
        R.raw.gencom_witcher3_youre_immortal
    )

    var r: Random = Random()

    val bossComMus: Array<Int> = arrayOf(
        R.raw.gencom_witcher3_silver_for_monsters
    )

    var currVol: Int = 100

    companion object {
        var instance: CombatService? = null
    }

    fun isCreated(): Boolean{
        return instance != null
    }

    open inner class LocalBinder : Binder() {
        fun getServiceInstance(): CombatService{
            return this@CombatService
        }
    }

    var mBinder = object: LocalBinder(){}

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("Service", "initialized!")
        instance = this

        genericMP = MediaPlayer.create(this, genComMus[r.nextInt(genComMus.size)])
        bossMP = MediaPlayer.create(this, genComMus[r.nextInt(genComMus.size)])

        genericMP.setOnCompletionListener {
            genericMP.reset()
            val afd = resources.openRawResourceFd(genComMus[r.nextInt(genComMus.size)])
            genericMP.setDataSource(afd.fileDescriptor, afd.startOffset, afd.declaredLength)
            genericMP.start()
        }

        return mBinder
    }

    override fun onStart(intent: Intent?, startId: Int) {

    }

    override fun onDestroy() {
        instance = null

        super.onDestroy()
    }

    // Handling the player stuff
    fun pauseAll(){
        if(genericMP.isPlaying)
            genericMP.pause()
        if(bossMP.isPlaying)
            bossMP.pause()
    }

    fun playGenCombatMusic(){
        if(genericMP.isPlaying)
            genericMP.pause()
        else{
            pauseAll()
            genericMP.start()
        }
    }


    fun changeVol(v: Float, prog: Int){
        currVol = prog
        genericMP.setVolume(v, v)
        bossMP.setVolume(v, v)
    }
}