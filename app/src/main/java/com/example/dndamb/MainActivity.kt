package com.example.dndamb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onWeatherButtPress(view: View){
        val intent = Intent(this, WeatherActivity::class.java).apply{
            // Incase we wanna send stuff over, not doing anything rn
        }
        startActivity(intent)
    }

    fun incomplete(){
        Toast.makeText(this, "To be done!", Toast.LENGTH_SHORT).show()
    }

    fun onGeneralButtPress(view: View){
        incomplete()
    }

    fun onSocialButtPress(view: View){
        incomplete()
    }

    fun onCombatButtPress(view: View){
        val intent = Intent(this, CombatActivity::class.java).apply{
            // blah blah
        }
        startActivity(intent)
    }

    fun onEffectsPress(view: View){
        incomplete()
    }
}