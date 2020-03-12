package me.rampoo.musicstream.baseactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.rampoo.musicstream.R

class MusicPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
    }
}
