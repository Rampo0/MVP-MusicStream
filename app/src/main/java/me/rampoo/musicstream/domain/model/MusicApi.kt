package me.rampoo.musicstream.domain.model

import android.util.Log
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.data.retrofit.RetrofitClient
import me.rampoo.musicstream.domain.repository.IMusicApi
import me.rampoo.musicstream.presentation.repository.IMusicView
import retrofit2.Call
import retrofit2.Response

class MusicApi(val iMusicView : IMusicView) : IMusicApi {
    override fun Retrive() {
        RetrofitClient.instance.GetMusics()
            .enqueue(object : retrofit2.Callback<List<Music>>{
                override fun onFailure(call: Call<List<Music>>, t: Throwable) {
                    iMusicView.onRetrieveError(t.message.toString())
                }

                override fun onResponse(call: Call<List<Music>>, response: Response<List<Music>>) {

                    try {
                        val data = response.body() as ArrayList<Music>
                        iMusicView.onRetriveResult(data)

                    }catch (e: Error){
                        Log.d("E",e.message.toString())
                    }
                }

            })

    }
}