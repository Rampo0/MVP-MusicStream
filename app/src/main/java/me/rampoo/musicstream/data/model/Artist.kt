package me.rampoo.musicstream.data.model
import java.io.Serializable

data class Artist (
    val id: Int,
    val artist_name : String,
    val artist_image : String
) : Serializable