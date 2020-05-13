package me.rampoo.musicstream.presentation.repository

import android.media.MediaPlayer
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.model.MusicPlayer

interface IMusicPlayerView {
    fun onPlay(music : Music)
    fun onMusicPause()
    fun onMusicResume()
    fun onPrepared(mp : MediaPlayer?)
}