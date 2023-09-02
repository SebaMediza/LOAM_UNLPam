package com.unlpam.loam.tp01

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SoundControl : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_control)

        val btnSound: Button = findViewById(R.id.buttonPlaySound)
        val back: Button = findViewById(R.id.buttonGoBackSound)
        val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.cantinaband)
        var toggle = false
        btnSound.setOnClickListener{
            if (!toggle){
                toggle = true
                mediaPlayer.start()
            }else{
                toggle = false
                mediaPlayer.pause()
            }
        }
        back.setOnClickListener{
            val intento = Intent(this, MainActivity::class.java)
            mediaPlayer.stop()
            startActivity(intento)
        }
    }
}