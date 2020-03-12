package me.rampoo.musicstream.data.retrofit

import me.rampoo.musicstream.data.model.Music
import retrofit2.Call
import retrofit2.http.GET

interface IApi {

    @GET("/api/musics/")
    fun GetMusics() : Call<List<Music>>

}