package com.example.socialx

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
//import androidx.recyclerview.widget.RecyclerView.findNestedRecyclerView
import com.example.socialx.apiModels.Artical

class NewsAdapter(val context : Context, val artical: List<Artical> = ArrayList()) : RecyclerView.Adapter<NewsAdapter.ArticalViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticalViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.news_items, parent, false)
        return ArticalViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticalViewHolder, position: Int) {
        val artical = artical[position]

        holder.publishedAt.text = artical.publishedAt
        holder.title.text = artical.title
        holder.description.text = artical.description
//        holder.source.text = artical.source.toString()
        Glide.with(context).load(artical.urlToImage)
            .placeholder(R.drawable.image_not_found)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Redirecting...", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailedNews::class.java)
            intent.putExtra("URL", artical.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return artical.size
    }

    class ArticalViewHolder(itemView: View) : ViewHolder(itemView){
        var title : TextView = itemView.findViewById(R.id.text_title)
        var image : ImageView = itemView.findViewById(R.id.img_headline)
        var publishedAt : TextView = itemView.findViewById(R.id.publishTime)
        var source : TextView = itemView.findViewById(R.id.source)
        var description : TextView = itemView.findViewById(R.id.textDescription)


    }

}