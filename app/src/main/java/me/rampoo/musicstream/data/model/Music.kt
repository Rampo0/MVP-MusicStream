package me.rampoo.musicstream.data.model
import java.io.Serializable

data class Music (
    val id: Int,
    val name : String,
    val artist : String,
    val album : String,
    val image : String,
    val song_file :String
) : Serializable