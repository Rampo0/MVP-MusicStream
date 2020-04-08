package me.rampoo.musicstream.presentation.repository

import me.rampoo.musicstream.data.model.Artist

interface IArtistView {
    fun onRetriveArtistResult(artists : ArrayList<Artist>)
    fun onRetrieveArtistError(messages : String)
}