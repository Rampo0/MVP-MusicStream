package me.rampoo.musicstream.domain.model

import android.media.AudioManager
import android.media.MediaPlayer
import me.rampoo.musicstream.domain.repository.IMusicPlayer

object MusicPlayer : IMusicPlayer {

    val mediaPlayer : MediaPlayer? = MediaPlayer().apply {
        setAudioStreamType(AudioManager.STREAM_MUSIC)
    }

    init {
        mediaPlayer!!.setOnPreparedListener {
            it.start()
        }
    }

    override fun Play() {
        mediaPlayer!!.setDataSource("")
        mediaPlayer!!.prepareAsync()
    }

    override fun Next() {

    }

    override fun Prev() {

    }

}