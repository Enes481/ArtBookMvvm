package com.enestigli.artbookmvvm.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enestigli.artbookmvvm.R
import com.enestigli.artbookmvvm.model.Art
import javax.inject.Inject

class ArtRecyclerAdapter @Inject constructor(
    val glide:RequestManager
) :RecyclerView.Adapter<ArtRecyclerAdapter.ArtViewHolder>(){


    //DiffUtil iki liste arasındaki farkı bulan ve güncellenmiş listeyi çıktı olarak sunan, RecyclerView'in performansını artıran yardımcı bir sınıftır.
    //recyclerview ın sadece gerekli yerlerini güncelleyen bir sınıf. Asenkron bir şekilde yapıyor işlemleri.  Recyclerview ı daha verimli bir hale getiriyor.
    private val diffUtil = object : DiffUtil.ItemCallback<Art>(){ //iki tane Art arasındaki farkı bulucaz

        //itemler aynı mı , eğer oldItem yani eski veri ile newItem yani yeni veri aynıysa true dönücek ,eşit değilse false dönücek
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        //Biz DiffUtil'u kullanarak bir değişim olduğunda bir güncelleme olduğunda veriler değişmişmi değişmemişmi kontrol edebilecez.
    }

    //DiffUtil helper
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)


    var artList :List<Art>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)



    class ArtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.art_row,parent,false)
        return ArtViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {

        val imageView = holder.itemView.findViewById<ImageView>(R.id.artRowImageView)
        val nameText = holder.itemView.findViewById<TextView>(R.id.artRowNameText)
        val artistNameText = holder.itemView.findViewById<TextView>(R.id.artRowArtistNameText)
        val yearText = holder.itemView.findViewById<TextView>(R.id.yearText)

        val art = artList[position]

        holder.itemView.apply {
            glide.load(art.imgUrl).into(imageView)
            nameText?.text = "Name : ${art.name}"
            artistNameText?.text = "Artist Name: ${art.artistName}"
            yearText?.text = "Year: ${art.year}"


        }



    }

    override fun getItemCount(): Int {
        return artList.size
    }
}