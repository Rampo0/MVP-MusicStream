package me.rampoo.musicstream.data.model
import java.io.Serializable

data class Music (
    val id: String,
    val name : String,
    val artist : String,
    val album : String,
    val image : String,
    val song_file :String,
    val updated_at : String,
    val created_at : String,
    val artist_user_id : String
) : Serializable {
    constructor() : this("","","","","","","","",""){

    }
}