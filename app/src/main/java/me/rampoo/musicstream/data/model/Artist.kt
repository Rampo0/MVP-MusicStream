package me.rampoo.musicstream.data.model
import java.io.Serializable

data class Artist (
    val id: String,
    val artist_name : String,
    val artist_image : String
) : Serializable {
    constructor() : this("", "", ""){

    }
}