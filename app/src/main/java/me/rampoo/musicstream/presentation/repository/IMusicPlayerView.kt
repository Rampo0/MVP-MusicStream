package me.rampoo.musicstream.presentation.repository

import me.rampoo.musicstream.data.model.Music

interface IMusicPlayerView {
    fun onPlay(music : Music)
}