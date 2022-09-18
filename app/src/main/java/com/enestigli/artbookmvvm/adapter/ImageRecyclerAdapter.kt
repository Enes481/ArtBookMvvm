package com.enestigli.artbookmvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enestigli.artbookmvvm.R
import com.enestigli.artbookmvvm.model.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(val glide:RequestManager):RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {


    private val diffUtil = object : DiffUtil.ItemCallback<String>(){ //iki tane Art arasındaki farkı bulucaz

        //itemler aynı mı , eğer oldItem yani eski veri ile newItem yani yeni veri aynıysa true dönücek ,eşit değilse false dönücek
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        //Biz DiffUtil'u kullanarak bir değişim olduğunda bir güncelleme olduğunda veriler değişmişmi değişmemişmi kontrol edebilecez.
    }

    //DiffUtil helper
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var imageList :List<String>
        get()=recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    class ImageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ImageViewHolder(view)
    }

    //kullanıcı resme tıkladığı zaman url'yi aktarmak için kendimiz onItemClickListener yazdık
    private var onItemClickListener : ((String) -> Unit) ?= null


    fun setOnItemClickListener(listener: (String)-> Unit){
        onItemClickListener = listener
    }




    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val imageView  = holder.itemView.findViewById<ImageView>(R.id.singleArtImageView)
        val url = imageList[position]
        holder.itemView.apply {
            glide.load(url).into(imageView)
            setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}