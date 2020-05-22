package me.rampoo.musicstream.domain.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.data.retrofit.RetrofitClient
import me.rampoo.musicstream.domain.repository.IMusicApi
import me.rampoo.musicstream.presentation.repository.IMusicView
import retrofit2.Call
import retrofit2.Response

class MusicApi(val iMusicView : IMusicView) : IMusicApi {
    lateinit var musicList : ArrayList<Music>
    override fun Retrieve() {

        musicList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("10").child("data")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("APIERROR" , p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    for(h in p0.children){
                        Log.d("API" , h.toString())
                        val music = h.getValue(Music::class.java)
                        musicList.add(music!!)
                    }

                    iMusicView.onRetriveResult(musicList)
                }
                Log.d("API" , musicList.toString())
            }

        })

//        RetrofitClient.instance.GetMusics()
//            .enqueue(object : retrofit2.Callback<List<Music>>{
//                override fun onFailure(call: Call<List<Music>>, t: Throwable) {
//                    iMusicView.onRetrieveError(t.message.toString())
//                }
//
//                override fun onResponse(call: Call<List<Music>>, response: Response<List<Music>>) {
//
//                    try {
//                        val data = response.body() as ArrayList<Music>
//                        iMusicView.onRetriveResult(data)
//
//                    }catch (e: Error){
//                        Log.d("E",e.message.toString())
//                    }
//                }
//
//            })

    }
}