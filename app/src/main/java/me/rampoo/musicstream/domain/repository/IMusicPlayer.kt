package me.rampoo.musicstream.domain.repository

import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.presentation.repository.IMusicPlayerView

interface IMusicPlayer {
    fun Play(pos : Int)
    fun Next()
    fun Prev()
    fun Pause()
    fun Resume()
    fun SetIView(view : IMusicPlayerView)
    fun SetPlaylist(playlist : ArrayList<Music>)
}