package me.rampoo.musicstream.domain.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import me.rampoo.musicstream.data.model.Artist
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.data.retrofit.RetrofitClient
import me.rampoo.musicstream.domain.repository.IArtistApi
import me.rampoo.musicstream.presentation.repository.IArtistView
import retrofit2.Call
import retrofit2.Response

class ArtistApi(val iArtistView: IArtistView) : IArtistApi {
    lateinit var artistList : ArrayList<Artist>
    override fun ArtistRetrieve() {

        artistList = ArrayList()

        val ref = FirebaseDatabase.getInstance().getReference("11").child("data")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("APIERROR" , p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()){
                    for(h in p0.children){
                        Log.d("API" , h.toString())
                        val artist = h.getValue(Artist::class.java)
                        artistList.add(artist!!)
                    }

                    iArtistView.onRetriveArtistResult(artistList)
                }
                Log.d("API" , artistList.toString())
            }

        })

//        RetrofitClient.instance.GetArtists()
//            .enqueue(object : retrofit2.Callback<List<Artist>>{
//                override fun onFailure(call: Call<List<Artist>>, t: Throwable) {
//                    iArtistView.onRetrieveArtistError(t.message.toString())
//                }
//
//                override fun onResponse(call: Call<List<Artist>>, response: Response<List<Artist>>) {
//
//                    try {
//                        val data = response.body() as ArrayList<Artist>
//                        iArtistView.onRetriveArtistResult(data)
//
//                    }catch (e: Error){
//                        Log.d("E",e.message.toString())
//                    }
//                }
//
//            })

    }
}