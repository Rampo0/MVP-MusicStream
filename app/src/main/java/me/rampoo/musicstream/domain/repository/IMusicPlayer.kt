package me.rampoo.musicstream.domain.repository

import me.rampoo.musicstream.data.model.Music

interface IMusicPlayer {
    fun Play(pos : Int)
    fun Next()
    fun Prev()
    fun Pause()
    fun Resume()
    fun SetPlaylist(playlist : ArrayList<Music>)
}