package com.example.mediademo

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mediaPlayer : MediaPlayer? = null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var seekBar: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnPlay = findViewById<FloatingActionButton>(R.id.fabPlay)
        val btnPause = findViewById<FloatingActionButton>(R.id.fabPause)
        val btnStop = findViewById<FloatingActionButton>(R.id.fabStop)
        val seekBar = findViewById<SeekBar>(R.id.seekMusic)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {  }

        btnPlay.setOnClickListener{
            if(mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this, R.raw.music_lacore) // Whichever media is playing
                initializeSeekBar()
            }
            mediaPlayer?.start()
        }

        btnPause.setOnClickListener{
            mediaPlayer?.pause()
        }

        btnStop.setOnClickListener{
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }

    }

    private fun initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        seekBar.max = mediaPlayer!!.duration

        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable, 500)
            //handler.post(runnable)  <-- This can lead to bugs leading to the UI freezing
        }
        handler.postDelayed(runnable, 500)
        //handler.post(runnable) <-- This can lead to bugs leading to the UI freezing
    }
}