package me.rampoo.musicstream.presentation.repository

import me.rampoo.musicstream.data.model.Music

interface IMusicView {
    fun onRetriveResult(musics : ArrayList<Music>)
    fun onRetrieveError(messages : String)
}