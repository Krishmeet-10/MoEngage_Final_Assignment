package com.example.krishmeet_moengage

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView

class rv_adapter(private val news_data : List<NewsArticle>) : RecyclerView.Adapter<rv_adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return news_data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curr_item = news_data[position]

        holder.author.text = curr_item.author
        holder.title.text = curr_item.title
        holder.source.text ="Source: "+ curr_item.source.name

        if(curr_item.author.toString()=="null"){
            holder.author.text = ""
        }
        if(curr_item.title=="null"){
            holder.title.text=""
        }
        if(curr_item.source.name=="null"){
            holder.source.text=""
        }

        Glide.with(holder.itemView.context)
            .load(curr_item.urlToImage)
            .into(holder.title_image)

        holder.itemView.setOnClickListener{
            val url = curr_item.url

            val intent1 = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            holder.itemView.context.startActivity(intent1)

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title_image :ShapeableImageView = itemView.findViewById(R.id.rv_image)
        val title: TextView = itemView.findViewById(R.id.rv_title)
        val author :TextView = itemView.findViewById(R.id.rv_author)
        val source :TextView = itemView.findViewById(R.id.rv_source)

    }
}