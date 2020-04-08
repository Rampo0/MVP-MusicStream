package me.rampoo.musicstream.domain.model

import android.util.Log
import me.rampoo.musicstream.data.model.Artist
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.data.retrofit.RetrofitClient
import me.rampoo.musicstream.domain.repository.IArtistApi
import me.rampoo.musicstream.presentation.repository.IArtistView
import retrofit2.Call
import retrofit2.Response

class ArtistApi(val iArtistView: IArtistView) : IArtistApi {
    override fun ArtistRetrieve() {
        RetrofitClient.instance.GetArtists()
            .enqueue(object : retrofit2.Callback<List<Artist>>{
                override fun onFailure(call: Call<List<Artist>>, t: Throwable) {
                    iArtistView.onRetrieveArtistError(t.message.toString())
                }

                override fun onResponse(call: Call<List<Artist>>, response: Response<List<Artist>>) {

                    try {
                        val data = response.body() as ArrayList<Artist>
                        iArtistView.onRetriveArtistResult(data)

                    }catch (e: Error){
                        Log.d("E",e.message.toString())
                    }
                }

            })

    }
}